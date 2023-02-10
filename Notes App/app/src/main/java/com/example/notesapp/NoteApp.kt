package com.example.notesapp

import android.app.Application
import com.example.notesapp.Database.NoteDatabase
import com.example.notesapp.Repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApp : Application() {

    /*
    When you create a class that extends the Android Application class in Kotlin,
    you create a custom application class. This class allows you to define global application-level
    behaviors, such as initializing libraries, setting default fonts, and more. The custom
    application class is instantiated before any other class in your app, and its instance is
    kept in memory throughout the lifetime of your app. When you extend the Application class,
    you must specify the fully-qualified name of your custom class in the android:name attribute of
    the <application> element in your app's AndroidManifest.xml file.
     */

    val applicationScope = CoroutineScope(SupervisorJob())

    /*
     Lazy initialization means that the property value is not computed until it is first accessed
      and the lambda expression that creates the value is only executed once.
     */
    val database by lazy {
        NoteDatabase.getDatabase(this,applicationScope)
    }

    val repository by lazy {
        NoteRepository(database.getNoteDao())   // NoteDatabase class has an abstract method
    }
}