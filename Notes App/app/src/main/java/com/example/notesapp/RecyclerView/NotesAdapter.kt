package com.example.notesapp.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Model.Note
import com.example.notesapp.R

class NotesAdapter() : RecyclerView.Adapter<NoteViewHolder>() {

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
    }

    override fun getItemCount(): Int {

        return items.size
    }

    fun setNote(notes : List<Note>) {
        this.items = notes
    }
}