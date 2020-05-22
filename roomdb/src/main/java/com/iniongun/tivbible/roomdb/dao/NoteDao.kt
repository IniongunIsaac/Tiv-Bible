package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Note
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Isaac Iniongun on 14/05/2020.
 * For Tiv Bible project.
 */

@Dao
interface NoteDao {

    @Query("select * from Note order by datetime(taken_on) desc")
    fun getNotes(): Observable<List<Note>>

    @Query("select * from Note where taken_on = :takenOn order by datetime(taken_on) desc")
    fun getNoteByDate(takenOn: String): Observable<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: List<Note>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(vararg notes: Note): Completable

    @Delete
    fun deleteNotes(notes: List<Note>): Completable

}