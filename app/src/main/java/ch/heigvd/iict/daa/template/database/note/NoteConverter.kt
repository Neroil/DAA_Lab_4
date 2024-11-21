package ch.heigvd.iict.daa.template.database.note

import androidx.room.TypeConverter
import java.util.Calendar
import java.util.Date

class NoteConverter {

    @TypeConverter
    fun toCalendar(dateLong: Long) =
        Calendar.getInstance().apply {
            time = Date(dateLong)
        }

    @TypeConverter
    fun fromCalendar(date: Calendar) =
        date.time.time // Long
}
