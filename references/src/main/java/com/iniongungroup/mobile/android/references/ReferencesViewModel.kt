package com.iniongungroup.mobile.android.references

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.common.utils.state.AppResult
import com.iniongun.tivbible.entities.Book
import com.iniongun.tivbible.entities.Chapter
import com.iniongun.tivbible.entities.History
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.repository.room.history.IHistoryRepo
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 26/04/2020.
 * For Tiv Bible project.
 */

class ReferencesViewModel @Inject constructor(
    bookRepo: IBookRepo,
    private val chaptersRepo: IChapterRepo,
    private val verseRepo: IVersesRepo,
    private val historyRepo: IHistoryRepo,
    private val appPreferencesRepo: IAppPreferencesRepo,
    private val schedulerProvider: SchedulerProvider
): BaseViewModel() {

    private val _verses = MutableLiveData<LiveDataEvent<List<Verse>>>()
    val verses: LiveData<LiveDataEvent<List<Verse>>> = _verses

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books
    private val _originalBooks = MutableLiveData<List<Book>>()

    private val _chapters = MutableLiveData<LiveDataEvent<List<Chapter>>>()
    val chapters: LiveData<LiveDataEvent<List<Chapter>>> = _chapters

    private val _spanCount = MutableLiveData(1)
    val spanCount: LiveData<Int> = _spanCount

    private val _showChaptersFragment = MutableLiveData<LiveDataEvent<Boolean>>()
    val showChaptersFragment: LiveData<LiveDataEvent<Boolean>> = _showChaptersFragment

    private val _showVersesFragment = MutableLiveData<LiveDataEvent<Boolean>>()
    val showVersesFragment: LiveData<LiveDataEvent<Boolean>> = _showVersesFragment

    private val _showReaderFragment = MutableLiveData<LiveDataEvent<Boolean>>()
    val showReaderFragment: LiveData<LiveDataEvent<Boolean>> = _showReaderFragment

    private lateinit var selectedChapter: Chapter
    private lateinit var selectedBook: Book
    private lateinit var selectedVerse: Verse

    init {
        _notificationLiveData.value = LiveDataEvent(AppResult.loading())
        compositeDisposable.add(
            bookRepo.getAllBooks()
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    _books.value = it
                    _originalBooks.value = it
                    _notificationLiveData.value = LiveDataEvent(AppResult.success())
                }) { removeLoadingState() }
        )
    }

    fun setSpanCount(spanCount: Int) {
        _spanCount.value = spanCount
    }

    fun filterBooks(text: String) {
        if(text.isEmpty())
            _books.value = _originalBooks.value
        else
            _originalBooks.value?.filter { it.name.contains(text, true) }?.let {
                if (it.isNotEmpty()) _books.value = it
            }
    }

    fun getBookChapters(book: Book) {
        selectedBook = book
        _notificationLiveData.value = LiveDataEvent(AppResult.loading())
        compositeDisposable.add(
            chaptersRepo.getChaptersByBook(book.id)
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    _chapters.value = LiveDataEvent(it)
                    _notificationLiveData.value = LiveDataEvent(AppResult.success())
                    _showChaptersFragment.value = LiveDataEvent(true)
                }) { removeLoadingState() }
        )
    }

    fun getChapterVerses(chapter: Chapter) {
        selectedChapter = chapter
        _notificationLiveData.value = LiveDataEvent(AppResult.loading())
        compositeDisposable.add(
            verseRepo.getVersesByChapter(chapter.id)
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    _verses.value = LiveDataEvent(it)
                    _notificationLiveData.value = LiveDataEvent(AppResult.success())
                    _showVersesFragment.value = LiveDataEvent(true)
                }) { removeLoadingState() }
        )
    }

    fun handleVerseSelected(verse: Verse) {
        selectedVerse = verse
        appPreferencesRepo.currentBook = selectedBook
        appPreferencesRepo.currentChapter = selectedChapter
        appPreferencesRepo.currentVerse = selectedVerse
        appPreferencesRepo.shouldReloadVerses = true
        saveHistory(selectedChapter)
    }

    private fun saveHistory(chapter: Chapter) {
        postLoadingState()
        compositeDisposable.add(
            historyRepo.insertHistory(listOf(History(chapter, selectedBook, "${selectedBook.name} : ${chapter.chapterNumber}")))
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _showReaderFragment.value = LiveDataEvent(true)
                }) { removeLoadingState() }
        )
    }

    override fun handleCoroutineException(throwable: Throwable) {
        postFailureNotification(throwable.message)
    }
}