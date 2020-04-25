package com.iniongun.tivbible.reader.read

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.common.utils.state.AppResult
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.repository.room.verse.IVersesRepo
import javax.inject.Inject

class ReadViewModel @Inject constructor(
    bookRepo: IBookRepo,
    verseRepo: IVersesRepo,
    schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _verses = MutableLiveData<List<Verse>>()
    val verses: LiveData<List<Verse>> = _verses

    private val _chapters = MutableLiveData<List<Verse>>()
    val chapters: LiveData<List<Verse>> = _chapters

    private val _currentVerses = MutableLiveData<List<Verse>>()
    val currentVerses: LiveData<List<Verse>> = _currentVerses

    init {
        _notificationLiveData.value = LiveDataEvent(AppResult.loading())
        compositeDisposable.add(
            bookRepo.getBookByName("genese")
                .subscribeOnIoObserveOnUi(schedulerProvider, { book ->
                    compositeDisposable.add(
                        verseRepo.getVersesByBook(book.id)
                            .subscribeOnIoObserveOnUi(schedulerProvider, { verses ->
                                _chapters.value = verses.distinctBy { it.chapterId }
                                _verses.value = verses
                                _notificationLiveData.value = LiveDataEvent(AppResult.success())
                            })
                    )
                })
        )
    }

    fun getCurrentVerses(chapterId: String = _chapters.value!![0].chapterId) {
        _currentVerses.value = _verses.value?.filter { it.chapterId == chapterId }
    }

    override fun handleCoroutineException(throwable: Throwable) {
        _notificationLiveData.postValue(LiveDataEvent(AppResult.failed(throwable.message)))
    }
}