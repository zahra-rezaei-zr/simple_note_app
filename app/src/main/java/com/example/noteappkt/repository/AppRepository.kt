package com.example.noteappkt.repository

import com.example.noteappkt.model.Note
import com.example.noteappkt.room.AppDatabase
import com.example.noteappkt.room.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepository @Inject constructor(appDatabase: AppDatabase) {
    private val roomDao = appDatabase.roomDao()


    fun insertNote(noteModel:Note){
        val noteEntity=NoteEntity(0,noteModel)
        roomDao.insert(noteEntity)
    }


    fun updateNote(noteEntity: NoteEntity){
        roomDao.update(noteEntity)
    }


    fun deleteNote(noteEntity: NoteEntity){
        roomDao.delete(noteEntity)
    }



    fun getAllData():Flow<List<NoteEntity>>{
       return roomDao.getAll()
    }
}