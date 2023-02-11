package com.example.notesapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.R
import kotlinx.android.synthetic.main.activity_update_note.*

class UpdateNote : AppCompatActivity() {

    var getId = -1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        getSetValues()

        cancelButton.setOnClickListener {
            Toast.makeText(this, "Nothing updated !", Toast.LENGTH_SHORT).show()
            finish()
        }

        updateButton.setOnClickListener {

            updateNote()
        }

    }

    fun getSetValues() {
        
        // getting the values from the intent: 
        val getTitle : String = intent.getStringExtra("updateTitle").toString()
        val getDescription : String = intent.getStringExtra("updateDescription").toString()
        getId  = intent.getIntExtra("noteId", -1)
        
        // setting the vaues into the new title and new description
        newTitle.setText(getTitle)
        newDescription.setText(getDescription)
        
    }

    fun updateNote() {
        val updatedTitle = newTitle.text.toString()
        val updatedDescription = newDescription.text.toString()

        val intent = Intent()
        intent.putExtra("Updated Title", updatedTitle)
        intent.putExtra("Updated Description", updatedDescription)

        if (getId != -1) {
            intent.putExtra("Id", getId)
            setResult(RESULT_OK,intent)
            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}