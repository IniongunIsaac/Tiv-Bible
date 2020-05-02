package com.iniongun.tivbible.reader.read

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.common.utils.state.AppResult
import com.iniongun.tivbible.common.utils.state.AppState
import com.iniongun.tivbible.entities.Book
import com.iniongun.tivbible.entities.Chapter
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import timber.log.Timber
import javax.inject.Inject

class ReadViewModelNew @Inject constructor(
    private val bookRepo: IBookRepo,
    private val verseRepo: IVersesRepo,
    private val chapterRepo: IChapterRepo,
    private val appPreferencesRepo: IAppPreferencesRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _verses = MutableLiveData<List<Verse>>()

    private val _chapters = MutableLiveData<List<Verse>>()

    private val _currentVerses = MutableLiveData<List<Verse>>()
    val currentVerses: LiveData<List<Verse>> = _currentVerses

    private val _bookNameAndChapterNumber = MutableLiveData<String>()
    val bookNameAndChapterNumber: LiveData<String> = _bookNameAndChapterNumber

    var chapterNum = 0
    var verseNum = 0
    var originalVerseNumber = 0
    private var currentVerse: Verse? = null
    private var currentChapter: Chapter? = null
    private var currentBook: Book? = null

    private val _verseNumber = MutableLiveData<LiveDataEvent<Int>>()
    val verseNumber: LiveData<LiveDataEvent<Int>> = _verseNumber

    var newBookNameAndChapterNumber = "Genese:1"

    private val _verseSelected = MutableLiveData<LiveDataEvent<Boolean>>()
    val verseSelected: LiveData<LiveDataEvent<Boolean>> = _verseSelected

    private val _selectedVersesText = MutableLiveData<LiveDataEvent<String>>()
    val selectedVersesText: LiveData<LiveDataEvent<String>> = _selectedVersesText

    var shareableSelectedVersesText = ""

    val selectedVerses = arrayListOf<Verse>()

    init { appPreferencesRepo.shouldReloadVerses = true }

    fun getBookFromSavedPreferencesOrInitializeWithGenese() {
        if (appPreferencesRepo.shouldReloadVerses) {
            appPreferencesRepo.shouldReloadVerses = false
            try {
                val book = appPreferencesRepo.currentBook
                getBookVerses(book)
            } catch (e: Exception) {
                _notificationLiveData.value = LiveDataEvent(AppResult.loading())
                compositeDisposable.add(
                    bookRepo.getBookByName("genese")
                        .subscribeOnIoObserveOnUi(schedulerProvider, {
                            getAndSetCurrentChapterOnFirstTimeLaunch(it)
                        })
                )
            }
        }
    }

    private fun getAndSetCurrentChapterOnFirstTimeLaunch(book: Book) {
        compositeDisposable.add(
            chapterRepo.getChapterByBookAndChapterNumber(book.id, 1)
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    currentChapter = it
                    appPreferencesRepo.currentChapter = it
                    _notificationLiveData.value = LiveDataEvent(AppResult.success())
                    getBookVerses(book)
                })
        )
    }

    private fun getSavedChapterNumber() {
        try {
            val chapter = appPreferencesRepo.currentChapter
            chapterNum = chapter.chapterNumber
            currentChapter = chapter
            getCurrentVerses(chapter.id)
        } catch (e: Exception) {
            Timber.e("Chapter is null")
            getCurrentVerses()
        }
    }

    fun getChapterVerses(number: Int) {
        currentBook?.let { curBook ->
            currentChapter?.let { curChap ->
                val chapNum = curChap.chapterNumber + number
                when {
                    chapNum <= 0 -> setMessage("You're currently on the first chapter!", AppState.FAILED)
                    chapNum > _chapters.value!!.size -> setMessage("You're currently on the last chapter!", AppState.FAILED)
                    else -> {
                        _notificationLiveData.value = LiveDataEvent(AppResult.loading())
                        compositeDisposable.add(
                            chapterRepo.getChapterByBookAndChapterNumber(curBook.id, chapNum)
                                .subscribeOnIoObserveOnUi(schedulerProvider, {
                                    currentChapter = it
                                    appPreferencesRepo.currentChapter = it
                                    _notificationLiveData.value = LiveDataEvent(AppResult.success())
                                    appPreferencesRepo.currentVerseString = ""
                                    clearSelectedVerses()
                                    getCurrentVerses(it.id)
                                }) {
                                    setMessage("Unable to get next chapter, please try again!", AppState.FAILED)
                                }
                        )
                    }
                }
            }
        }
    }

    private fun getSavedVerseNumber() {
        try {
            val verse = appPreferencesRepo.currentVerse
            _verseNumber.value = LiveDataEvent(verse.number)
            verseNum = verse.number
            originalVerseNumber = verse.number
            currentVerse = verse
        } catch (e: Exception) {
            _verseNumber.value = LiveDataEvent(0)
            Timber.e("Verse is null")
        }
    }

    private fun getBookVerses(book: Book) {
        currentBook = book
        val nameAndChapter = newBookNameAndChapterNumber.split(":")
        newBookNameAndChapterNumber = "${ book.name.capitalize() }:${nameAndChapter[1]}"
        _bookNameAndChapterNumber.value = newBookNameAndChapterNumber.replace(":", " ")

        _notificationLiveData.value = LiveDataEvent(AppResult.loading())

        compositeDisposable.add(
            verseRepo.getVersesByBook(book.id)
                .subscribeOnIoObserveOnUi(schedulerProvider, { verses ->
                    _notificationLiveData.value = LiveDataEvent(AppResult.success())
                    _chapters.value = verses.distinctBy { it.chapterId }
                    _verses.value = verses
                    getSavedChapterNumber()
                })
        )
    }

    private fun getCurrentVerses(chapterId: String = _chapters.value!![0].chapterId) {
        _currentVerses.value = _verses.value?.filter { it.chapterId == chapterId }

        currentChapter?.let {
            val nameAndChapter = newBookNameAndChapterNumber.split(":")
            newBookNameAndChapterNumber = "${ nameAndChapter[0] }:${it.chapterNumber}"
            _bookNameAndChapterNumber.value = newBookNameAndChapterNumber.replace(":", " ")
        }

        getSavedVerseNumber()
    }

    fun toggleSelectedVerse(verse: Verse) {
        with(verse) {
            isSelected = !isSelected
            if (isSelected) {
                selectedVerses.add(this)
            } else {
                selectedVerses.remove(this)
            }
            if (selectedVerses.isNotEmpty())
                getSelectedVersesText()
            else
                shareableSelectedVersesText = ""

            _verseSelected.value = LiveDataEvent(!isSelected)
        }
    }

    private fun getSelectedVersesText() {
        val selectedVersesList = selectedVerses.sortedBy { it.number }
        val verses = selectedVersesList.map { it.number }
        var left: Int? = null
        var right: Int? = null
        val groups = arrayListOf<String>()

        for (index in verses.first()..verses.last() + 1) {
            if (verses.contains(index)) {
                if (left == null) {
                    left = index
                } else {
                    right = index
                }
            } else {
                if (left == null) continue
                val leftx = left
                if (right != null) {
                    groups.add("$leftx - $right")
                } else {
                    groups.add("$leftx")
                }
                left = null
                right = null
            }
        }
        _selectedVersesText.value = LiveDataEvent("${_bookNameAndChapterNumber.value!!} : ${groups.joinToString(separator = ", ")}" )
        shareableSelectedVersesText = selectedVersesList.joinToString("\n\n") { "${it.number}.\t${it.text}" }
    }

    fun setMessage(message: String, messageType: AppState) {
        when(messageType) {
            AppState.FAILED -> _notificationLiveData.value = LiveDataEvent(AppResult.failed(message))
            AppState.SUCCESS -> _notificationLiveData.value = LiveDataEvent(AppResult.success(message = message))
        }

    }

    fun setHighlightColorForSelectedVerse(color: Int) {
        setMessage("Coming soon!", AppState.SUCCESS)
    }

    fun clearSelectedVerses() {
        selectedVerses.clear()
        _selectedVersesText.value = LiveDataEvent("")
        shareableSelectedVersesText = ""
    }

    override fun handleCoroutineException(throwable: Throwable) {
        _notificationLiveData.postValue(LiveDataEvent(AppResult.failed(throwable.message)))
    }
}