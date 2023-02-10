package com.example.notesapp.Database

import androidx.room.*
import com.example.notesapp.Model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // database should perform the following operations:
    /*
    1. Insert
    2. Delete
    3. update
    4. Query
     */

    @Insert
    suspend fun insertNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllNotes () : Flow<List<Note>>

    @Query("DELETE FROM note_table")
    suspend fun deleteAll ()
}