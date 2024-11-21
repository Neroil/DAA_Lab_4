package ch.heigvd.iict.daa.template.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Schedule
import ch.heigvd.iict.daa.template.database.note.NoteRepository

class NoteViewModel(private val repo:NoteRepository) : ViewModel() {

    val nbNotesAndSchedules : LiveData<Int>get() = _nbNotesAndSchedules
    val allNotesAndSchedules : LiveData<List<NoteAndSchedule>>get() = _allNotesAndSchedules

    private val _allNotesAndSchedules = repo.allNoteAndSchedule
    private val _nbNotesAndSchedules = repo.nbNotes


    fun generateANote() {
        val note = Note.generateRandomNote()
        repo.insertNotes(note)
    }

    fun generateASchedule() {
        val schedule = Note.generateRandomSchedule()
        if (schedule != null) {
            repo.insertSchedule(schedule)
        }
    }

    fun deleteAllNoteAndSchedules() {
        repo.deleteAll()
    }
}