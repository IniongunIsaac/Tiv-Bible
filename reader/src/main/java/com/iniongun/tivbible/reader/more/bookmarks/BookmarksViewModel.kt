package com.iniongun.tivbible.reader.more.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.entities.Bookmark
import com.iniongun.tivbible.entities.Setting
import com.iniongun.tivbible.reader.utils.TapAction
import com.iniongun.tivbible.reader.utils.TapAction.*
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.bookmark.IBookmarkRepo
import com.iniongun.tivbible.repository.room.settings.ISettingsRepo
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

class BookmarksViewModel @Inject constructor(
    private val bookmarksRepo: IBookmarkRepo,
    private val settingsRepo: ISettingsRepo,
    private val appPreferencesRepo: IAppPreferencesRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _bookmarks = MutableLiveData<List<Bookmark>>()
    val bookmarks: LiveData<List<Bookmark>> = _bookmarks

    private val _settings = MutableLiveData<Setting>()
    val settings: LiveData<Setting> = _settings

    private val _showReaderModule = MutableLiveData<LiveDataEvent<Boolean>>()
    val showReaderModule: LiveData<LiveDataEvent<Boolean>> = _showReaderModule

    private val _tapActionData = MutableLiveData<Pair<TapAction, Bookmark>>()
    val tapActionData: LiveData<Pair<TapAction, Bookmark>> = _tapActionData

    init {
        getBookmarksAndSettings()
    }

    private fun getBookmarksAndSettings() {
        postLoadingState()
        compositeDisposable.add(
            Observable.zip(
                settingsRepo.getAllSettings(),
                bookmarksRepo.getBookmarks(),
                BiFunction<List<Setting>, List<Bookmark>, Pair<List<Setting>, List<Bookmark>>> { settings, bookmarks -> Pair(settings, bookmarks) }
            ).subscribeOnIoObserveOnUi(schedulerProvider, {
                removeLoadingState()
                _settings.value = it.first.first()
                _bookmarks.value = it.second
            }) { removeLoadingState() }
        )
    }

    fun handleBookmarkSelected(bookmark: Bookmark) {
        appPreferencesRepo.currentVerse = bookmark.verse
        appPreferencesRepo.currentChapter = bookmark.chapter
        appPreferencesRepo.currentBook = bookmark.book
        _showReaderModule.value = LiveDataEvent(true)
    }

    fun handleBookmarkActionButtonTapped(tapAction: TapAction, bookmark: Bookmark) {
        when (tapAction) {
            SHARE, COPY -> { _tapActionData.value = Pair(tapAction, bookmark) }
            DELETE -> { deleteBookmark(bookmark) }
        }
    }

    private fun deleteBookmark(bookmark: Bookmark) {
        postLoadingState()
        compositeDisposable.add(
            bookmarksRepo.deleteBookmarks(listOf(bookmark))
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()
                    postSuccessMessage("Bookmark deleted successfully!")
                    getBookmarksAndSettings()
                }) {
                    postFailureNotification("Bookmark not deleted, please try again!")
                    removeLoadingState()
                }
        )
    }

    override fun handleCoroutineException(throwable: Throwable) {
        postFailureNotification(throwable.message)
    }

}