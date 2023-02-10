package com.example.notesapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.Repository.NoteRepository

class NoteViewModelFactory (private var repo : NoteRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NoteViewModel :: class.java)) {
            return NoteViewModel(repo) as T
        }
        else {
            throw IllegalArgumentException("Unknown resource")
        }
    }
}