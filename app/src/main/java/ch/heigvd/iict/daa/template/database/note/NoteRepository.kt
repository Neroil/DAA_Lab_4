package ch.heigvd.iict.daa.template.database.note

import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.Schedule
import kotlin.concurrent.thread

class NoteRepository(private val noteDao: NoteDao) {

    val allNoteAndSchedule = noteDao.getAllNoteAndSchedule()

    fun insertNotes(vararg notes: Note) {
        thread {
            noteDao.insertAllNotes(*notes)
        }
    }

    fun insertSchedule(vararg schedules: Schedule) {
        thread {
            noteDao.insertAllSchedules(*schedules)
        }
    }

    fun deleteAll(){
        thread {
            noteDao.deleteAllNotes()
        }
    }
}