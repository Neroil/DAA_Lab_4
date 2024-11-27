package ch.heigvd.iict.daa.template.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.database.note.NoteRepository

/**
 * ViewModel for the NoteFragment.
 *
 * Allows the fragment to show the notes in the database, sort them, create new notes and delete all notes.
 *
 * Authors : Junod Arthur, Dunant Guillaume, Häffner Edwin
 */
class NoteViewModel(private val repo: NoteRepository) : ViewModel() {

    // Member variables
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


    /**
     * Generate a new note and schedule.
     */
    fun generateANoteAndSchedule() {
        val note = Note.generateRandomNote()
        val schedule = Note.generateRandomSchedule()

        repo.insertNotesAndMaybeSchedule(note, schedule)
    }

    /**
     * Delete all notes and schedules from the database.
     */
    fun deleteAllNoteAndSchedules() {
        repo.deleteAll()
    }

    /**
     * Set which sort type you want to use
     * CREATION_DATE : Sort by creation date
     * ETA : Sort by ETA (notes without schedule will be put to the back)
     */
    fun setSortType(sortType: SortType) {
        Log.d("SortTypeSet", "Sort type set to $sortType")
        this.sortType = sortType
        _sortedNotesAndSchedules.value = sortNotes(_allNotesAndSchedules.value)
    }

    /**
     * Sort the notes according to the sort type.
     */
    private fun sortNotes(notes: List<NoteAndSchedule>?): List<NoteAndSchedule> {
        return when (sortType) {
            SortType.CREATION_DATE -> notes?.sortedByDescending { it.note.creationDate }
                ?: emptyList()

            SortType.ETA -> notes?.sortedWith(compareBy(
                { it.schedule?.date == null },
                { it.schedule?.date }
            )) ?: emptyList()

        }
    }


}