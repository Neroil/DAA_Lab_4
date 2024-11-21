package ch.heigvd.iict.daa.template

import android.app.Application
import ch.heigvd.iict.daa.template.database.note.NoteDatabase
import ch.heigvd.iict.daa.template.database.note.NoteRepository

class NoteApp : Application() {

    val repository by lazy {
        val noteDatabase = NoteDatabase.getDatabase(this)
        NoteRepository(noteDatabase.noteDao())
    }
}