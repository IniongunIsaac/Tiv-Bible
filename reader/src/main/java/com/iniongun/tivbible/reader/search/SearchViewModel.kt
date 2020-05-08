package com.iniongun.tivbible.reader.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.entities.*
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.repository.room.history.IHistoryRepo
import com.iniongun.tivbible.repository.room.recentSearch.IRecentSearchRepo
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val bookRepo: IBookRepo,
    private val chapterRepo: IChapterRepo,
    private val recentSearchRepo: IRecentSearchRepo,
    private val historyRepo: IHistoryRepo,
    private val verseRepo: IVersesRepo,
    private val appPreferencesRepo: IAppPreferencesRepo
) : BaseViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _chapters = MutableLiveData<List<Chapter>>()
    val chapters: LiveData<List<Chapter>> = _chapters
    private var chaptersList = listOf<Chapter>()

    private val _recentSearches = MutableLiveData<List<RecentSearch>>()
    val recentSearches: LiveData<List<RecentSearch>> = _recentSearches

    private val _history = MutableLiveData<List<History>>()
    val history: LiveData<List<History>> = _history

    private val _searchVerses = MutableLiveData<List<Verse>>()
    val searchVerses: LiveData<List<Verse>> = _searchVerses

    private var selectedChapter: Chapter? = null
    private val _chapterSelected = MutableLiveData<LiveDataEvent<Boolean>>()
    val chapterSelected : LiveData<LiveDataEvent<Boolean>> = _chapterSelected

    private val _revisitHistory = MutableLiveData<LiveDataEvent<Boolean>>()
    val revisitHistory : LiveData<LiveDataEvent<Boolean>> = _revisitHistory

    private val _showSearchResults = MutableLiveData<LiveDataEvent<Boolean>>()
    val showSearchResults : LiveData<LiveDataEvent<Boolean>> = _showSearchResults

    init {
        getBooks()
    }

    fun getRecentSearches() {
        postLoadingState()
        compositeDisposable.add(
            recentSearchRepo.getAllRecentSearches()
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _recentSearches.value = it
                }) { removeLoadingState() }
        )
    }

    fun clearRecentSearches() {
        _recentSearches.value?.let {
            if (it.isNotEmpty()) {
                postLoadingState()
                compositeDisposable.add(
                    recentSearchRepo.deleteRecentSearches(it)
                        .subscribeOnIoObserveOnUi(schedulerProvider, {
                            removeLoadingState()
                            getRecentSearches()
                        }) { removeLoadingState() }
                )
            }
        }
    }

    fun getHistory() {
        postLoadingState()
        compositeDisposable.add(
            historyRepo.getAllHistory()
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _history.value = it
                }) { removeLoadingState() }
        )
    }

    fun clearHistory() {
        _history.value?.let {
            if (it.isNotEmpty()) {
                postLoadingState()
                compositeDisposable.add(
                    historyRepo.deleteHistory(it)
                        .subscribeOnIoObserveOnUi(schedulerProvider, {
                            removeLoadingState()
                            getHistory()
                        }) { removeLoadingState() }
                )
            }
        }

    }

    fun handleSelectedHistory(history: History) {
        appPreferencesRepo.currentBook = history.book
        appPreferencesRepo.currentChapter = history.chapter
        appPreferencesRepo.currentVerseString = ""
        appPreferencesRepo.shouldReloadVerses = true
        _revisitHistory.value = LiveDataEvent(true)
    }

    private fun getBooks() {
        postLoadingState()
        compositeDisposable.add(
            bookRepo.getAllBooks()
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _books.value = it
                }) { removeLoadingState() }
        )
    }

    fun getChapters(bookName: String) {
        postLoadingState()
        compositeDisposable.add(
            chapterRepo.getChaptersByBook(_books.value!!.first { it.name.toLowerCase(Locale.getDefault()) == bookName.toLowerCase(Locale.getDefault()) }.id)
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _chapters.value = it
                    chaptersList = it
                }) { removeLoadingState() }
        )
    }

    fun handleChapterSelected(chapter: Chapter) {
        selectedChapter = chapter
        with(chapter) {
            isSelected = !isSelected
            if (!isSelected) selectedChapter = null
            _chapters.value?.filter { it.id != chapter.id }?.forEach { it.isSelected = false }
            _chapterSelected.value = LiveDataEvent(true)
        }
    }

    fun search(text: String) {
        postLoadingState()

        val versesObservable = if (selectedChapter != null) verseRepo.getVersesByTextAndChapter(text, selectedChapter!!.id) else verseRepo.getVersesByText(text)

        compositeDisposable.add(
            versesObservable.subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    if (it.isNotEmpty()) {
                        saveRecentSearch(text)
                        _searchVerses.value = it
                    } else {
                        postSuccessMessage("No results found for your search!")
                    }
                }) { removeLoadingState() }
        )
    }

    private fun saveRecentSearch(text: String) {
        postLoadingState()
        compositeDisposable.add(
            recentSearchRepo.insertRecentSearches(listOf(RecentSearch(text)))
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _showSearchResults.value = LiveDataEvent(true)
                }) { removeLoadingState() }
        )
    }

    override fun handleCoroutineException(throwable: Throwable) {
        postFailureNotification(throwable.message)
    }
}