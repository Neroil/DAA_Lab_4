package ch.heigvd.iict.daa.template.database.note

import androidx.lifecycle.LiveData
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Schedule
import kotlin.concurrent.thread

class NoteRepository(private val noteDao: NoteDao) {

    val allNoteAndSchedule: LiveData<List<NoteAndSchedule>> = noteDao.getAllNoteAndSchedule()
    val nbNotes: LiveData<Int> = noteDao.getNbNotes()

    fun insertNotesAndMaybeSchedule(note: Note, schedule: Schedule? = null) {
        thread {
            val noteId = noteDao.insert(note)
            schedule?.let {
                it.ownerId = noteId
                noteDao.insert(it)
            }
        }
    }

    fun deleteAll(){
        thread {
            noteDao.deleteAllNotes()
            noteDao.deleteAllSchedules()
        }
    }
}