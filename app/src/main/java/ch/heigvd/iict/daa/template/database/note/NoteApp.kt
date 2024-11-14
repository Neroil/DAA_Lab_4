package ch.heigvd.iict.daa.template.database.note

import android.app.Application

class NoteApp : Application() {

    val repository by lazy {
        val noteDatabase = NoteDatabase.getDatabase(this)
    }
}