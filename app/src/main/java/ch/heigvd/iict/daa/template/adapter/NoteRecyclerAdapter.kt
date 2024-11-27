package ch.heigvd.iict.daa.template.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Type
import ch.heigvd.iict.daa.template.R
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
            val iconResources = when (noteAndSchedule.note.type) {
                Type.NONE -> R.drawable.note
                Type.TODO -> R.drawable.todo
                Type.SHOPPING -> R.drawable.shopping
                Type.WORK -> R.drawable.work
                Type.FAMILY -> R.drawable.family
            }
            when(binding) {
                is NoteViewBinding -> binding.apply {
                    with(noteAndSchedule){
                        noteTitle.text = note.title
                        noteText.text = note.text
                        icon.setImageResource(iconResources)
                    }
                }
                is ScheduleViewBinding -> binding.apply {
                    with(noteAndSchedule){
                        noteTitle.text = note.title
                        noteText.text = note.text
                        noteTime.text = schedule?.date.toString()
                        icon.setImageResource(iconResources)
                    }
                }
            }
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