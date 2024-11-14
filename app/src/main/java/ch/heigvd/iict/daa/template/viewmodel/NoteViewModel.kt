package ch.heigvd.iict.daa.template.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteViewModel : ViewModel() {

    val nbNotesAndSchedules : LiveData<Int>get() = _nbNotesAndSchedules
    val allNotesAndSchedules : LiveData<List<Int>>get() = _allNotesAndSchedules

    private val _nbNotesAndSchedules = MutableLiveData(0) // TODO get from repo
    private val _allNotesAndSchedules = MutableLiveData(List(1) { _ -> 0 }) // TODO get from repo

    fun generateANote() {

    }

    fun generateASchedule() {

    }

    fun deleteAllNoteAndSchedules() {

    }
}