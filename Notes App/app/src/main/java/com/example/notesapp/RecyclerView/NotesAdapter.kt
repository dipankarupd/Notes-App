package com.example.notesapp.RecyclerView

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Model.Note
import com.example.notesapp.R
import com.example.notesapp.View.MainActivity
import com.example.notesapp.View.UpdateNote

// constructor takes context of min activity for updating the intent here
class NotesAdapter(val activity : MainActivity) : RecyclerView.Adapter<NoteViewHolder>() {

    var items : List<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {


        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.note_items,parent,false)
        val holder : NoteViewHolder = NoteViewHolder(view)


        return holder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val current = items[position]

        holder.title.text = current.title
        holder.description.text = current.description

        // adding a click listener to update:
        holder.itemView.setOnClickListener {

            // create a new activity for updating:
            val intent = Intent(activity, UpdateNote :: class.java)
            intent.putExtra("updateTitle", current.title)
            intent.putExtra("updateDescription",current.description)
            intent.putExtra("noteId", current.id)
            activity.updateActivityResultLauncher.launch(intent)

        }
    }

    override fun getItemCount(): Int {

        return items.size
    }

    fun setNote(notes : List<Note>) {
        this.items = notes
        notifyDataSetChanged()
    }

    // getting a note at a given index of the list:
    fun getNote(position : Int) : Note {
        return items[position]
    }
}