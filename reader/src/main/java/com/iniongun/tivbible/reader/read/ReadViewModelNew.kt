package com.iniongun.tivbible.reader.read

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.*
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.common.utils.state.AppState.FAILED
import com.iniongun.tivbible.common.utils.state.AppState.SUCCESS
import com.iniongun.tivbible.entities.*
import com.iniongun.tivbible.reader.utils.LineSpacingType
import com.iniongun.tivbible.reader.utils.LineSpacingType.*
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.bookmark.IBookmarkRepo
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.repository.room.fontStyle.IFontStyleRepo
import com.iniongun.tivbible.repository.room.highlight.IHighlightRepo
import com.iniongun.tivbible.repository.room.highlightColor.IHighlightColorRepo
import com.iniongun.tivbible.repository.room.history.IHistoryRepo
import com.iniongun.tivbible.repository.room.note.INoteRepo
import com.iniongun.tivbible.repository.room.settings.ISettingsRepo
import com.iniongun.tivbible.repository.room.theme.IThemeRepo
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import org.threeten.bp.OffsetDateTime
import timber.log.Timber
import javax.inject.Inject

class ReadViewModelNew @Inject constructor(
    private val context: Context,
    private val bookRepo: IBookRepo,
    private val verseRepo: IVersesRepo,
    private val chapterRepo: IChapterRepo,
    private val appPreferencesRepo: IAppPreferencesRepo,
    private val settingsRepo: ISettingsRepo,
    private val fontStyleRepo: IFontStyleRepo,
    private val themeRepo: IThemeRepo,
    private val bookmarkRepo: IBookmarkRepo,
    private val highlightColorRepo: IHighlightColorRepo,
    private val highlightRepo: IHighlightRepo,
    private val historyRepo: IHistoryRepo,
    private val schedulerProvider: SchedulerProvider,
    private val noteRepo: INoteRepo
) : BaseViewModel() {

    private val _verses = MutableLiveData<List<Verse>>()

    private val _chapters = MutableLiveData<List<Verse>>()

    private val _currentVerses = MutableLiveData<List<Verse>>()
    val currentVerses: LiveData<List<Verse>> = _currentVerses

    private val _bookNameAndChapterNumber = MutableLiveData<String>()
    val bookNameAndChapterNumber: LiveData<String> = _bookNameAndChapterNumber

    private var chapterNum = 0
    private var verseNum = 0
    private var originalVerseNumber = 0
    private var currentVerse: Verse? = null
    private var currentChapter: Chapter? = null
    private var currentBook: Book? = null

    private val _verseNumber = MutableLiveData<LiveDataEvent<Int>>()
    val verseNumber: LiveData<LiveDataEvent<Int>> = _verseNumber

    private var newBookNameAndChapterNumber = "Genese:1"

    private val _verseSelected = MutableLiveData<LiveDataEvent<Boolean>>()
    val verseSelected: LiveData<LiveDataEvent<Boolean>> = _verseSelected

    private val _selectedVersesText = MutableLiveData<LiveDataEvent<String>>()
    val selectedVersesText: LiveData<LiveDataEvent<String>> = _selectedVersesText

    var shareableSelectedVersesText = ""

    val selectedVerses = arrayListOf<Verse>()

    private var _settings = MutableLiveData<Setting>()
    val settings: LiveData<Setting> = _settings
    lateinit var currentSettings: Setting

    private val _shouldEnableFontSettingsUIControls = MutableLiveData<Boolean>()
    val shouldEnableFontSettingsUIControls: LiveData<Boolean> = _shouldEnableFontSettingsUIControls

    private val deviceScreenSize by lazy { getDeviceScreenSize(context.resources) }

    private val _fontStylesAndThemes = MutableLiveData<Pair<List<FontStyle>, List<Theme>>>()
    val fontStylesAndThemes: LiveData<Pair<List<FontStyle>, List<Theme>>> = _fontStylesAndThemes

    var highlightColorsList = listOf<HighlightColor>()
    private val _highlightColors = MutableLiveData<List<HighlightColor>>()
    val highlightColors: LiveData<List<HighlightColor>> = _highlightColors

    private var highlightsList = listOf<Highlight>()
    private val _highlights = MutableLiveData<List<Highlight>>()
    val highlights: LiveData<List<Highlight>> = _highlights

    private val _shouldDismissNotesDialog = MutableLiveData<LiveDataEvent<Boolean>>()
    val shouldDismissNotesDialog: LiveData<LiveDataEvent<Boolean>> = _shouldDismissNotesDialog

    init {
        getUserSettings()
        appPreferencesRepo.shouldReloadVerses = true
    }

    private fun getUserSettings() {
        postLoadingState()
        compositeDisposable.add(
            settingsRepo.getAllSettings().subscribeOnIoObserveOnUi(schedulerProvider, {
                removeLoadingState()
                val settings = it.first()
                currentSettings = settings
                _settings.value = settings
            }) { removeLoadingState() }
        )
    }

    fun getBookFromSavedPreferencesOrInitializeWithGenese() {
        if (appPreferencesRepo.shouldReloadVerses) {
            appPreferencesRepo.shouldReloadVerses = false
            try {
                val book = appPreferencesRepo.currentBook
                getBookVerses(book)
            } catch (e: Exception) {
                postLoadingState()
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
                    removeLoadingState()
                    getBookVerses(book)
                })
        )
    }

    private fun getSavedChapterNumber() {
        try {
            val chapter = appPreferencesRepo.currentChapter
            chapterNum = chapter.chapterNumber
            currentChapter = chapter
            saveHistory(chapter)
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
                    chapNum <= 0 -> setMessage("You're currently on the first chapter!", FAILED)
                    chapNum > _chapters.value!!.size -> setMessage("You're currently on the last chapter!", FAILED)
                    else -> {
                        postLoadingState()
                        compositeDisposable.add(
                            chapterRepo.getChapterByBookAndChapterNumber(curBook.id, chapNum)
                                .subscribeOnIoObserveOnUi(schedulerProvider, {
                                    currentChapter = it
                                    appPreferencesRepo.shouldReloadVerses = true
                                    appPreferencesRepo.currentChapter = it
                                    removeLoadingState()
                                    appPreferencesRepo.currentVerseString = ""
                                    clearSelectedVerses()
                                    getCurrentVerses(it.id)
                                    saveHistory(it)
                                }) {
                                    setMessage("Unable to get next chapter, please try again!", FAILED)
                                }
                        )
                    }
                }
            }
        }
    }

    private fun getSavedVerseNumber() {
        getVersesHighlights()
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

        postLoadingState()

        compositeDisposable.add(
            verseRepo.getVersesByBook(book.id)
                .subscribeOnIoObserveOnUi(schedulerProvider, { verses ->
                    removeLoadingState()
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

    fun getHighlightColors() {
        if (highlightColorsList.isEmpty()) {
            postLoadingState()
            compositeDisposable.add(
                highlightColorRepo.getAllHighlightColors()
                    .subscribeOnIoObserveOnUi(schedulerProvider, {
                        highlightColorsList = it
                        _highlightColors.value = it
                        removeLoadingState()
                    })
            )
        }
    }

    fun setHighlightColorForSelectedVerses(color: HighlightColor) {
        val highlights = selectedVerses.map { Highlight(OffsetDateTime.now(), color, it, currentBook!!, currentChapter!!) }
        postLoadingState()
        compositeDisposable.add(
            highlightRepo.insertHighlights(highlights)
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    getVersesHighlights()
                })
        )
    }

    private fun getVersesHighlights() {
        postLoadingState()
        compositeDisposable.add(
            highlightRepo.getAllHighlights()
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    highlightsList = it
                    getHighlightedVerses()
                    removeLoadingState()
                })
        )
    }

    private fun getHighlightedVerses() {
        _currentVerses.value?.forEach { vs ->
            highlightsList.forEach { hglght ->
                if (hglght.verse.text == vs.text && hglght.verse.number == vs.number) {
                    vs.isHighlighted = true
                    vs.highlight = hglght
                }
            }
        }
        _highlights.value = highlightsList

//        _currentVerses.value?.filter { verse -> verse.id in highlightsList.map { it.verse.id } }?.forEach { vs ->
//            vs.isHighlighted = true
//            vs.highlight = highlightsList.first { it.verse.text == vs.text && it.verse.number == vs.number }
//        }
//        _highlights.value = highlightsList

    }

    fun clearSelectedVerses() {
        selectedVerses.clear()
        _selectedVersesText.value = LiveDataEvent("")
        shareableSelectedVersesText = ""
    }

    private fun updateUserSettings() {
        postLoadingState()
        _shouldEnableFontSettingsUIControls.value = false
        compositeDisposable.add(
            settingsRepo.updateSetting(currentSettings)
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _shouldEnableFontSettingsUIControls.value = true
                    getUserSettings()
                })
        )
    }

    fun increaseFontSize() {
        val maximumFontSizeForDevice = getMaximumFontSizeForDevice(deviceScreenSize)
        if (currentSettings.fontSize == maximumFontSizeForDevice)
            setMessage("Maximum font size for your device is ${maximumFontSizeForDevice}px", FAILED)
        else {
            ++currentSettings.fontSize
            updateUserSettings()
        }
    }

    fun decreaseFontSize() {
        val minimumFontSizeForDevice = getMinimumFontSizeForDevice(deviceScreenSize)
        if (currentSettings.fontSize == minimumFontSizeForDevice)
            setMessage("Minimum font size for your device is ${minimumFontSizeForDevice}px", FAILED)
        else {
            --currentSettings.fontSize
            updateUserSettings()
        }
    }

    fun updateLineSpacing(lineSpacingType: LineSpacingType) {
        val lineSpacing = when(lineSpacingType) {
            TWO -> getDeviceLineSpacingTwo(deviceScreenSize)
            THREE -> getDeviceLineSpacingThree(deviceScreenSize)
            FOUR -> getDeviceLineSpacingFour(deviceScreenSize)
        }

        currentSettings.lineSpacing = lineSpacing
        updateUserSettings()
    }

    fun getAppFontStylesAndThemes() {
        if (_fontStylesAndThemes.value?.first == null || _fontStylesAndThemes.value?.second == null) {
            postLoadingState()
            compositeDisposable.add(
                Observable.zip(
                    fontStyleRepo.getAllFontStyles(),
                    themeRepo.getAllThemes(),
                    BiFunction<List<FontStyle>, List<Theme>, Pair<List<FontStyle>, List<Theme>>> { fontStyles, themes -> Pair(fontStyles, themes) }
                ).subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _fontStylesAndThemes.value = it
                })
            )
        }
    }

    fun changeFontStyle(fontStyle: FontStyle) {
        currentSettings.fontStyle = fontStyle
        updateUserSettings()
    }

    fun changeTheme(theme: Theme) {
        currentSettings.theme = theme
        updateUserSettings()
    }

    fun saveBookmarks() {
        val bookmarks = selectedVerses.map { Bookmark(OffsetDateTime.now(), it, currentBook!!, currentChapter!!) }
        compositeDisposable.add(
            bookmarkRepo.insertBookmarks(bookmarks)
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    setMessage("Verse(s) bookmarked successfully!", SUCCESS)
                })
        )
    }

    private fun saveHistory(chapter: Chapter) {
        postLoadingState()
        compositeDisposable.add(
            historyRepo.insertHistory(listOf(History(chapter, currentBook!!, "${currentBook!!.name} : ${chapter.chapterNumber}")))
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                }) { removeLoadingState() }
        )
    }

    override fun handleCoroutineException(throwable: Throwable) {
        postFailureNotification(throwable.message)
    }

    override fun onCleared() {
        appPreferencesRepo.currentVerseString = ""
        super.onCleared()
    }

    fun saveNote(comment: String) {
        postLoadingState()
        compositeDisposable.add(
            noteRepo.insertNotes(listOf(Note(OffsetDateTime.now(), selectedVerses, currentBook!!, currentChapter!!, comment)))
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    postSuccessMessage("Note saved successfully!")
                    _shouldDismissNotesDialog.value = LiveDataEvent(true)
                }) {
                    postFailureNotification("Note not saved, please try again!")
                    removeLoadingState()
                }
        )

    }
}