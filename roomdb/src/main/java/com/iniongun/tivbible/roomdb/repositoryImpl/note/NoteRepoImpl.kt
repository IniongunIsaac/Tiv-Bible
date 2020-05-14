package com.iniongun.tivbible.roomdb.repositoryImpl.note

import com.iniongun.tivbible.entities.Note
import com.iniongun.tivbible.repository.room.note.INoteRepo
import com.iniongun.tivbible.roomdb.dao.NoteDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 14/05/2020.
 * For Tiv Bible project.
 */

class NoteRepoImpl @Inject constructor(
    private val noteDao: NoteDao
) : INoteRepo {

    override fun getNotes() = noteDao.getNotes()

    override fun getNoteByDate(takenOn: String) = noteDao.getNoteByDate(takenOn)

    override fun insertNotes(notes: List<Note>) = noteDao.insertNotes(notes)

    override fun deleteNotes(notes: List<Note>)  = noteDao.deleteNotes(notes)

}