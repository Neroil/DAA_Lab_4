package ch.heigvd.iict.daa.template.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.R

class NoteRecyclerAdapter(
    private val _items: List<NoteAndSchedule> = listOf()
    ) :
    RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {

    var items = listOf<NoteAndSchedule>()
        set(value) {
            field = value
            //notifyDataSetChanged()
        }

    init {
        items = _items
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
        return when (viewType) {
            NOTE -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_unscheduled, parent, false)
            )
            else -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_scheduled, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val noteTitle = view.findViewById<TextView>(R.id.note_title)
        private val noteText = view.findViewById<TextView>(R.id.note_text)
        private val noteTime = view.findViewById<TextView>(R.id.note_time)

        fun bind(noteAndSchedule: NoteAndSchedule) {
            when (getItemViewType(adapterPosition)) {
                NOTE -> {
                    noteTitle?.text = noteAndSchedule.note.title
                    //noteContent?.text = noteAndSchedule.note.content
                }

                /*SCHEDULE -> {
                    scheduleTitle?.text = noteAndSchedule.note.title
                    scheduleDateTime?.text = noteAndSchedule.schedule?.dateTime.toString()
                }

                 */
            }
        }
    }
}
