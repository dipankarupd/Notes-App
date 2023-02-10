package com.example.notesapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.R
import kotlinx.android.synthetic.main.activity_add_new_note.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.note_items.*

class AddNewNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_note)

        cancelButton.setOnClickListener {
            Toast.makeText(this, "Nothing saved", Toast.LENGTH_SHORT).show()
            finish()
        }

        addNoteButton.setOnClickListener {
            saveNote()

        }
    }

    fun saveNote () {
        val noteTitle : String  = newNoteTitle.text.toString()
        val noteDescription : String = newNoteDescription.text.toString()

        val intent = Intent()
        intent.putExtra("title", noteTitle)
        intent.putExtra("description", noteDescription)
        setResult(RESULT_OK, intent)
        finish()
    }
}