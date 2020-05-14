package com.iniongun.tivbible.repository.room.note

import com.iniongun.tivbible.entities.Note
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Isaac Iniongun on 14/05/2020.
 * For Tiv Bible project.
 */

interface INoteRepo {

    fun getNotes(): Observable<List<Note>>

    fun getNoteByDate(takenOn: String): Observable<List<Note>>

    fun insertNotes(notes: List<Note>): Completable

    fun deleteNotes(notes: List<Note>): Completable

}