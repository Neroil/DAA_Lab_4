package ch.heigvd.iict.daa.template.database

import android.app.Application

class NoteApp : Application() {

    val repository by lazy {
        val database = NoteDatabase.getDatabase(this)
    }
}