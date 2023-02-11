package com.example.notesapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Model.Note
import com.example.notesapp.NoteApp
import com.example.notesapp.R
import com.example.notesapp.RecyclerView.NotesAdapter
import com.example.notesapp.ViewModel.NoteViewModel
import com.example.notesapp.ViewModel.NoteViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //instance of view model
    lateinit var noteViewModel : NoteViewModel

    // instance of activityLauncher:
    lateinit var addActivityResultLauncher : ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // recycler view:
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesAdapter(this)

        recyclerView.adapter = adapter


        // button clicks handling:

        btnAdd.setOnClickListener {
            addNote()
        }

        //registering of the activityResultLauncher:
        registerActivityResultLauncher()

        // view model works:

        val viewModelFactory = NoteViewModelFactory((application as NoteApp).repository)
        noteViewModel = ViewModelProvider( this,viewModelFactory).get(NoteViewModel::class.java)

        noteViewModel.myNote.observe(this, Observer {

            // UI design and changes in the data:

            adapter.setNote(it)

        })

        // delete a note:
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNote(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recyclerView)
    }

    // creating the option menu:
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu , menu)
        return true
    }

    // telling what to do when the button is clicked:
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            // when the item id clicked matches the deleteall id then
            R.id.deleteall -> {

                // code that should be executed:
                noteViewModel.deleteAll()
                Toast.makeText(this, "Deleted all the notes", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    //registration function:
    fun registerActivityResultLauncher() {

        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                val resultCode = it.resultCode
                val resultData = it.data

                // getting the note from the intent
                if (resultCode == RESULT_OK && resultData != null) {

                    val noteTitle : String = resultData.getStringExtra("title").toString()
                    val noteDescription : String = resultData.getStringExtra("description").toString()

                    // adding the note to the database:
                    val note = Note(noteTitle,noteDescription)
                    noteViewModel.insert(note)

                }
            }
        )
        updateActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                val resultCode = it.resultCode
                val resultData = it.data

                // getting the note from the intent
                if (resultCode == RESULT_OK && resultData != null) {
                    val updatedTitle : String = resultData.getStringExtra("Updated Title").toString()
                    val updatedDescription : String = resultData.getStringExtra("Updated Description").toString()
                    val updatedId : Int = resultData.getIntExtra("Id",-1)

                    val newNote = Note(updatedTitle,updatedDescription)
                    newNote.id = updatedId

                    noteViewModel.update(newNote)
                }
            }
        )
    }

    // adding the new note
    fun addNote () {
        val intent = Intent(this, AddNewNote::class.java)
        addActivityResultLauncher.launch(intent)
    }


}