package ch.heigvd.iict.daa.template.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.database.note.NoteConverter
import java.util.Calendar
import java.util.concurrent.TimeUnit
import ch.heigvd.iict.daa.template.databinding.ListItemScheduledBinding as ScheduleViewBinding
import ch.heigvd.iict.daa.template.databinding.ListItemUnscheduledBinding as NoteViewBinding

class NoteRecyclerAdapter(
    listItems: List<NoteAndSchedule> = listOf()
    ) :
    RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, NoteAndScheduleDiffCallback())

    var items = listOf<NoteAndSchedule>()
        get() = differ.currentList
        set(value) {
            //val diffCallback = NoteAndScheduleDiffCallback()
            //val diffItems = DiffUtil.calculateDiff(diffCallback)
            Log.d("RecyclerViewAdapter", "Nouvelle valeur de taille ${value.size}: $value")
            field = value
            differ.submitList(value)
        }

    init {
        items = listItems
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items[position].schedule != null) SCHEDULE else NOTE
    }

    companion object {
        private const val NOTE = 1
        private const val SCHEDULE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == NOTE) {
            val binding = NoteViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolder(binding)
        }else {
            val binding = ScheduleViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("RecyclerViewAdapter", "Binding $position with ${items[position]}")
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(noteAndSchedule: NoteAndSchedule) {
            when(binding) {
                is NoteViewBinding -> binding.apply {
                    with(noteAndSchedule){
                        noteTitle.text = note.title
                        noteText.text = note.text
                    }
                }
                is ScheduleViewBinding -> binding.apply {
                    with(noteAndSchedule){
                        noteTitle.text = note.title
                        noteText.text = note.text
                        if (schedule != null) {
                            noteTime.visibility = View.VISIBLE
                            // Convert the note's schedule date to a friendly time string
                            val friendlyTime = schedule.date.let { date ->
                                CalendarConverter().convertDateToSomethingCool(itemView.context, date)
                            }
                            noteTime.text = friendlyTime

                            // Check if the schedule is "Late" and set text color
                            if (friendlyTime == itemView.context.getString(R.string.late)) {
                                noteTime.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                            } else {
                                noteTime.setTextColor(ContextCompat.getColor(itemView.context, R.color.grey))
                            }
                        } else {
                            noteTime.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}

class CalendarConverter {
    fun convertDateToSomethingCool(context: Context, date: Calendar): String {
        val now = Calendar.getInstance()
        val diff = date.timeInMillis - now.timeInMillis

        // If date is in the past
        if (diff < 0) {
            return context.getString(R.string.late)
        }

        val days = TimeUnit.MILLISECONDS.toDays(diff)
        val weeks = days / 7
        val months = days / 30

        return when {
            months > 0 -> context.getString(R.string.months, months)
            weeks > 0 -> context.getString(R.string.weeks, weeks)
            days > 0 -> context.getString(R.string.days, days)
            else -> context.getString(R.string.soon)
        }
    }
}


class NoteAndScheduleDiffCallback : DiffUtil.ItemCallback<NoteAndSchedule>() {
    override fun areItemsTheSame(oldItem: NoteAndSchedule, newItem: NoteAndSchedule): Boolean {
        return oldItem.note.noteId == newItem.note.noteId
    }

    override fun areContentsTheSame(oldItem: NoteAndSchedule, newItem: NoteAndSchedule): Boolean {
        return oldItem == newItem
    }
}