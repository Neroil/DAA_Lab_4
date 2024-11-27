package ch.heigvd.iict.daa.template.database.note

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Schedule

/**
 * Data Access Object of the database.
 *
 * Authors : Junod Arthur, Dunant Guillaume, Häffner Edwin
 */
@Dao
interface NoteDao {

    // Insertion
    @Insert
    fun insert(note: Note): Long

    @Insert
    fun insert(schedule: Schedule): Long

    @Insert
    fun insertAllNotes(vararg notes: Note)

    @Insert
    fun insertAllSchedules(vararg schedules: Schedule)

    // Mise à jour
    @Update
    fun update(note: Note)

    @Update
    fun update(schedule: Schedule)

    // Suppression
    @Query("DELETE FROM Note WHERE noteId = :id")
    fun deleteNoteById(id: Long)

    @Query("DELETE FROM Note")
    fun deleteAllNotes()

    @Query ("DELETE FROM Schedule")
    fun deleteAllSchedules()

    // Récupération de toutes les Notes
    @Transaction
    @Query("SELECT * FROM Note")
    fun getAllNotes(): LiveData<List<Note>>

    // Compteur de Notes
    @Query("SELECT COUNT(*) FROM Note")
    fun getNbNotes(): LiveData<Int>

    // Compteur de Notes direct
    @Query("SELECT COUNT(*) FROM Note")
    fun getNbNotesDirect(): Int

    // Récupération d'une Note par son ID
    @Query("SELECT * FROM Note WHERE noteId = :id")
    fun getNoteById(id: Long): LiveData<Note>

    // Récupération de toutes les Notes et leurs Schedules (relation One-to-One facultative)
    @Query("SELECT * FROM Note LEFT JOIN Schedule ON Schedule.ownerId = Note.noteId")
    fun getAllNoteAndSchedule(): LiveData<List<NoteAndSchedule>>

}
