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
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.chapter.IChapterRepo
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 26/04/2020.
 * For Tiv Bible project.
 */

class ReferencesViewModel @Inject constructor(
    bookRepo: IBookRepo,
    chaptersRepo: IChapterRepo,
    verseRepo: IVersesRepo,
    schedulerProvider: SchedulerProvider
): BaseViewModel() {

    private val _verses = MutableLiveData<List<Verse>>()
    val verses: LiveData<List<Verse>> = _verses

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books
    private val _originalBooks = MutableLiveData<List<Book>>()

    private val _chapters = MutableLiveData<List<Chapter>>()
    val chapters: LiveData<List<Chapter>> = _chapters

    init {
        _notificationLiveData.value = LiveDataEvent(AppResult.loading())
        compositeDisposable.add(
            bookRepo.getAllBooks()
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    _books.value = it
                    _originalBooks.value = it
                    _notificationLiveData.value = LiveDataEvent(AppResult.success())
                })
        )
    }

    fun filterBooks(text: String) {
        if(text.isEmpty())
            _books.value = _originalBooks.value
        else
            _originalBooks.value?.filter { it.name.contains(text, true) }?.let {
                if (it.isNotEmpty()) _books.value = it
            }
    }

    override fun handleCoroutineException(throwable: Throwable) {
        _notificationLiveData.postValue(LiveDataEvent(AppResult.failed(throwable.message)))
    }
}