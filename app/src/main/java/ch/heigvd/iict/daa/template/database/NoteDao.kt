package ch.heigvd.iict.daa.template.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ch.heigvd.iict.daa.labo4.models.Note

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note): Long

    @Insert
    fun insertAll(vararg notes: Note)

    @Query("DELETE FROM Note WHERE noteId = :id")
    fun deleteNote(id: Long)

    @Query("DELETE FROM Note")
    fun deleteAllNotes()

    @Query("SELECT * FROM Note")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT COUNT(*) FROM Note")
    fun getNbNotes(): LiveData<Int>

    @Query("SELECT * FROM Note WHERE noteId = :id")
    fun getNoteById(id: Long): LiveData<Note>


}