package ch.heigvd.iict.daa.template.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteViewModel : ViewModel() {

    val nbNotes : LiveData<Int>get() = _nbNotes
    val allNotes : LiveData<List<Int>>get() = _allNotes

    private val _nbNotes = MutableLiveData(0) // TODO get from repo
    private val _allNotes = MutableLiveData(List(1) { _ -> 0 }) // TODO get from repo

    fun generateANote() {

    }

    fun deleteAllNote() {

    }
}