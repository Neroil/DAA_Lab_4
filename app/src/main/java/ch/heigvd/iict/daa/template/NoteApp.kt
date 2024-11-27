package ch.heigvd.iict.daa.template

import android.app.Application
import ch.heigvd.iict.daa.template.database.note.NoteDatabase
import ch.heigvd.iict.daa.template.database.note.NoteRepository

/**
 * Application class for the app. Only used to create the repository.
 *
 * Authors : Junod Arthur, Dunant Guillaume, Häffner Edwin
 */
class NoteApp : Application() {

    val repository by lazy {
        val noteDatabase = NoteDatabase.getDatabase(this)
        NoteRepository(noteDatabase.noteDao())
    }
}