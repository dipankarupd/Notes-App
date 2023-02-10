package com.example.notesapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notesapp.Model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE note_table ADD COLUMN new_id INTEGER NOT NULL DEFAULT 0")
        database.execSQL("UPDATE note_table SET new_id = id")
        database.execSQL("ALTER TABLE note_table RENAME TO note_table_old")
        database.execSQL("CREATE TABLE note_table (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT NOT NULL, description TEXT NOT NULL)")
        database.execSQL("INSERT INTO note_table (id, title, description) SELECT new_id, title, description FROM note_table_old")
        database.execSQL("DROP TABLE note_table_old")
    }
}

@Database (entities = [Note :: class], version = 2)
abstract class NoteDatabase  : RoomDatabase(){

    abstract fun getNoteDao() : NoteDao

    //singleton:

    companion object {

        private var INSTANCE : NoteDatabase? = null

        fun getDatabase(context : Context, scope : CoroutineScope) : NoteDatabase {
            return INSTANCE ?: synchronized(this) {

                var instance = Room.databaseBuilder(context.applicationContext,
                    NoteDatabase :: class.java, "note_db")
                    .addMigrations(MIGRATION_1_2)
                    .addCallback(NoteDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
    private class NoteDatabaseCallback(val scope : CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {

            INSTANCE ?.let {database ->

                scope.launch {

                    val noteDao = database.getNoteDao()
                    noteDao.insertNote(Note("Title 1", "Description 1"))
                    noteDao.insertNote(Note("Title 2", "Description 2"))
                    noteDao.insertNote(Note("Title 3" , "Description 3"))
                }
            }
        }
    }


}
