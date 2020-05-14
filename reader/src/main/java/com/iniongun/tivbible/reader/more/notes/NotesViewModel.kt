package com.iniongun.tivbible.reader.more.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iniongun.tivbible.common.base.BaseViewModel
import com.iniongun.tivbible.common.utils.liveDataEvent.LiveDataEvent
import com.iniongun.tivbible.common.utils.rxScheduler.SchedulerProvider
import com.iniongun.tivbible.common.utils.rxScheduler.subscribeOnIoObserveOnUi
import com.iniongun.tivbible.entities.Note
import com.iniongun.tivbible.entities.Setting
import com.iniongun.tivbible.reader.utils.TapAction
import com.iniongun.tivbible.repository.preference.IAppPreferencesRepo
import com.iniongun.tivbible.repository.room.note.INoteRepo
import com.iniongun.tivbible.repository.room.settings.ISettingsRepo
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 10/05/2020.
 * For Tiv Bible project.
 */

class NotesViewModel @Inject constructor(
    private val settingsRepo: ISettingsRepo,
    private val appPreferencesRepo: IAppPreferencesRepo,
    private val noteRepo: INoteRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    private val _settings = MutableLiveData<Setting>()
    val settings: LiveData<Setting> = _settings

    private val _showNoteDetails = MutableLiveData<LiveDataEvent<Boolean>>()
    val showNoteDetails: LiveData<LiveDataEvent<Boolean>> = _showNoteDetails

    private val _tapActionData = MutableLiveData<LiveDataEvent<Pair<TapAction, Note>>>()
    val tapActionData: LiveData<LiveDataEvent<Pair<TapAction, Note>>> = _tapActionData

    private val _currentNote = MutableLiveData<Note>()
    val currentNote: LiveData<Note> = _currentNote

    private val _showNotes = MutableLiveData<LiveDataEvent<Boolean>>()
    val showNotes: LiveData<LiveDataEvent<Boolean>> = _showNotes

    fun getNotesAndSettings() {
        postLoadingState()
        compositeDisposable.add(
            Observable.zip(
                settingsRepo.getAllSettings(),
                noteRepo.getNotes(),
                BiFunction<List<Setting>, List<Note>, Pair<List<Setting>, List<Note>>> { settings, notes -> Pair(settings, notes) }
            ).subscribeOnIoObserveOnUi(schedulerProvider, {
                removeLoadingState()
                _settings.value = it.first.first()
                _notes.value = it.second
            }) { removeLoadingState() }
        )
    }

    fun handleNoteSelected(note: Note) {
        _currentNote.value = note
        appPreferencesRepo.currentVerse = note.verses.first()
        appPreferencesRepo.currentChapter = note.chapter
        appPreferencesRepo.currentBook = note.book
        _showNoteDetails.value = LiveDataEvent(true)
    }

    fun handleNoteActionButtonTapped(tapAction: TapAction, note: Note, deletedFromNoteDetails: Boolean = false) {
        when (tapAction) {
            TapAction.SHARE, TapAction.COPY -> { _tapActionData.value = LiveDataEvent(Pair(tapAction, note)) }
            TapAction.DELETE -> { deleteNote(note, deletedFromNoteDetails) }
        }
    }

    private fun deleteNote(note: Note, deletedFromNoteDetails: Boolean = false) {
        postLoadingState()
        compositeDisposable.add(
            noteRepo.deleteNotes(listOf(note))
                .subscribeOnIoObserveOnUi(schedulerProvider, {
                    removeLoadingState()

                    postSuccessMessage("Note deleted successfully!")

                    getNotesAndSettings()

                    if (deletedFromNoteDetails)
                        _showNotes.value = LiveDataEvent(true)

                }) {
                    postFailureNotification("Note not deleted, please try again!")
                    removeLoadingState()
                }
        )
    }

    override fun handleCoroutineException(throwable: Throwable) {
        postFailureNotification(throwable.message)
    }

}