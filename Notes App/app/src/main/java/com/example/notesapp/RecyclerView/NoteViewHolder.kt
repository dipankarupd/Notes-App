package com.example.notesapp.RecyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var title : TextView = itemView.findViewById(R.id.title)
    val description : TextView = itemView.findViewById(R.id.description)

}