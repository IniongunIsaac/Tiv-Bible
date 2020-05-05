package com.iniongun.tivbible.reader.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.entities.Book
import com.iniongun.tivbible.entities.Chapter
import com.iniongun.tivbible.entities.History
import com.iniongun.tivbible.entities.RecentSearch
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.repository.room.history.IHistoryRepo
import com.iniongun.tivbible.repository.room.recentSearch.IRecentSearchRepo
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val bookRepo: IBookRepo,
    private val chapterRepo: IChapterRepo,
    private val recentSearchRepo: IRecentSearchRepo,
    private val historyRepo: IHistoryRepo
) : BaseViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _chapters = MutableLiveData<List<Chapter>>()
    val chapters: LiveData<List<Chapter>> = _chapters

    private val _recentSearches = MutableLiveData<List<RecentSearch>>()
    val recentSearches: LiveData<List<RecentSearch>> = _recentSearches

    private val _history = MutableLiveData<List<History>>()
    val history: LiveData<List<History>> = _history

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
                })
        )
    }

    fun getHistory() {
        postLoadingState()
        compositeDisposable.add(
            historyRepo.getAllHistory()
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _history.value = it
                })
        )
    }

    private fun getBooks() {
        postLoadingState()
        compositeDisposable.add(
            bookRepo.getAllBooks()
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _books.value = it
                })
        )
    }

    fun getChapters(position: Int) {
        postLoadingState()
        compositeDisposable.add(
            chapterRepo.getChaptersByBook(_books.value!![position].id)
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    _chapters.value = it
                })
        )
    }

    fun handleChapterSelected(chapter: Chapter) {
        Timber.e(chapter.id)
    }

    override fun handleCoroutineException(throwable: Throwable) {
        postFailureNotification(throwable.message)
    }
}