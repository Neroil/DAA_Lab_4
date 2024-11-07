package ch.heigvd.iict.daa.template.database

import ch.heigvd.iict.daa.labo4.models.Note
import kotlin.concurrent.thread

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes = noteDao.getAllNotes()
    val nbNotes = noteDao.getNbNotes()

    fun insertNotes(vararg notes: Note) {
     thread {
         noteDao.insertAll(*notes)
     }
    }

    fun deleteAllNotes(){
        thread {
            noteDao.deleteAllNotes()
        }
    }

    fun deleteNote(id: Long){
        thread {
            noteDao.deleteNote(id)
        }
    }

}