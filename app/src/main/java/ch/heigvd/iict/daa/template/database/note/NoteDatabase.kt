package ch.heigvd.iict.daa.template.database.note

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.Schedule
import kotlin.concurrent.thread

/**
 * Note database.
 *
 * Authors : Junod Arthur, Dunant Guillaume, Häffner Edwin
 */
@Database(entities = [Note::class, Schedule::class],
    version = 1,
    exportSchema = true)
@TypeConverters(NoteConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE : NoteDatabase? = null


        fun getDatabase(context: Context) : NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    NoteDatabase::class.java, "notedatabase.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(NoteDatabaseCallBack())
                    .build()
                INSTANCE!!
            }
        }
    }

    class NoteDatabaseCallBack : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                thread {
                    val isEmpty = database.noteDao().getNbNotesDirect() == 0

                    if(isEmpty) {

                        //Generate 20 notes and schedules
                        for (i in 1..20) {
                            val note = Note.generateRandomNote()
                            val schedule = Note.generateRandomSchedule()
                            database.noteDao().insert(note)
                            schedule?.let {
                                database.noteDao().insert(it)
                            }
                        }

                    }
                }
            }
        }
        override fun onOpen(db: SupportSQLiteDatabase) { super.onOpen(db) }
        override fun onDestructiveMigration(db: SupportSQLiteDatabase) { super.onDestructiveMigration(db) }
    }
}