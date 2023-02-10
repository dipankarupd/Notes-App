package com.example.notesapp.Repository

import com.example.notesapp.Database.NoteDao
import com.example.notesapp.Model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao : NoteDao) {

    val myNotes : Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note : Note) {
        noteDao.insertNote(note)
    }

    suspend fun delete(note : Note) {
        noteDao.deleteNote(note)
    }

    suspend fun update (note : Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteAllNotes () {
        noteDao.deleteAll()
    }
}