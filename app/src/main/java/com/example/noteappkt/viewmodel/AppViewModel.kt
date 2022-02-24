package com.example.noteappkt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappkt.model.Note
import com.example.noteappkt.repository.AppRepository
import com.example.noteappkt.room.entities.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor
    (private val repository: AppRepository):ViewModel(){


    init {
        getAllDataFromDB()
    }

    private var data :MutableLiveData<List<NoteEntity>> = MutableLiveData()
    var liveData : LiveData<List<NoteEntity>> = data

        fun insertToDataBase(noteModel:Note){
            viewModelScope.launch (Dispatchers.IO){
                repository.insertNote(noteModel)
            }
        }



    fun updateNoteDatabase(noteEntity: NoteEntity){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateNote(noteEntity)
        }
    }


    fun deleteNote(noteEntity: NoteEntity){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteNote(noteEntity)
        }
    }


    private fun getAllDataFromDB(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllData().collect {

                data.postValue(it)
            }
        }
    }
    }