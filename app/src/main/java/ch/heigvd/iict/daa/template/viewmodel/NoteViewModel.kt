package ch.heigvd.iict.daa.template.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Schedule
import ch.heigvd.iict.daa.template.database.note.NoteRepository

class NoteViewModel(private val repo:NoteRepository) : ViewModel() {

    private var sortType: SortType = SortType.CREATION_DATE

    private val _allNotesAndSchedules = repo.allNoteAndSchedule
    private val _nbNotesAndSchedules = repo.nbNotes

    private val _sortedNotesAndSchedules = MediatorLiveData<List<NoteAndSchedule>>().apply {
        addSource(_allNotesAndSchedules) { notes ->
            value = sortNotes(notes)
        }
    }

    val nbNotesAndSchedules: LiveData<Int> get() = _nbNotesAndSchedules
    val allNotesAndSchedules: LiveData<List<NoteAndSchedule>> = _sortedNotesAndSchedules


    fun generateANoteAndSchedule() {
        val note = Note.generateRandomNote()
        val schedule = Note.generateRandomSchedule()

        repo.insertNotesAndMaybeSchedule(note, schedule)
    }

    fun deleteAllNoteAndSchedules() {
        repo.deleteAll()
    }

    fun setSortType(sortType: SortType) {
        Log.d("SortTypeSet", "Sort type set to $sortType")
        this.sortType = sortType
        _sortedNotesAndSchedules.value = sortNotes(_allNotesAndSchedules.value)
    }

    private fun sortNotes(notes: List<NoteAndSchedule>?): List<NoteAndSchedule> {
        return when (sortType) {
            SortType.CREATION_DATE -> notes?.sortedByDescending { it.note.creationDate } ?: emptyList()
            SortType.ETA -> notes?.sortedWith(compareBy(
                { it.schedule?.date == null },
                { it.schedule?.date }
            )) ?: emptyList()


        }
    }



}