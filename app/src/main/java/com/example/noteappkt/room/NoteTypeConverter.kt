package com.example.noteappkt.room

import androidx.room.TypeConverter
import com.example.noteappkt.model.Note
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class NoteTypeConverter {

    @TypeConverter
    fun toJson(notesModel: Note): String? {
        if (notesModel == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Note?>() {}.type
        return gson.toJson(notesModel, type)
    }

    @TypeConverter
    fun toDataClass(note: String?): Note? {
        if (note == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Note?>() {}.type
        return gson.fromJson(note, type)
    }
}