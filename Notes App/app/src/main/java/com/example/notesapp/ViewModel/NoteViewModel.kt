package com.example.notesapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Model.Note
import com.example.notesapp.Repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repo : NoteRepository) : ViewModel()  {

    val myNote : LiveData<List<Note>> = repo.myNotes.asLiveData()

    fun insert (note : Note) = viewModelScope.launch(Dispatchers.IO){
        repo.insert(note)
    }

    fun delete (note : Note) = viewModelScope.launch(Dispatchers.IO){
        repo.delete(note)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO){
        repo.deleteAllNotes()
    }

    fun update (note : Note) = viewModelScope.launch(Dispatchers.IO){
        repo.update(note)
    }



}