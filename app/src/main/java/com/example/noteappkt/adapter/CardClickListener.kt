package com.example.noteappkt.adapter

import android.view.View
import com.example.noteappkt.room.entities.NoteEntity

interface CardClickListener {
    fun onItemClickListener(noteEntity: NoteEntity)

    fun onMenuItemClickListener(imageFilterButton: View , noteEntity: NoteEntity)
}