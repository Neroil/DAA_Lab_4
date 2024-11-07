package ch.heigvd.iict.daa.template.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.iict.daa.labo4.models.Note

@Database(entities = [Note::class],
          version = 1,
          exportSchema = true)
@TypeConverters(NoteConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao



    companion object {
        private var INSTANCE : NoteDatabase? = null


        fun getDatabase(context: Context) : NoteDatabase{
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
}