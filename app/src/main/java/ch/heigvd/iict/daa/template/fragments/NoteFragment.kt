package ch.heigvd.iict.daa.template.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.template.NoteApp
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.adapter.NoteRecyclerAdapter
import ch.heigvd.iict.daa.template.databinding.FragmentNoteBinding
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModel
import ch.heigvd.iict.daa.template.viewmodel.factory.NoteVMFactory

/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoteFragment : Fragment() {

    private lateinit var noteRecyclerAdapter: NoteRecyclerAdapter

    private var _binding : FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel : NoteViewModel by activityViewModels{
        NoteVMFactory((requireActivity().application as NoteApp).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializing the adapter
        noteRecyclerAdapter = NoteRecyclerAdapter()


        binding.list.apply {
            adapter = noteRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // observing the live data
        viewModel.allNotesAndSchedules.observe(viewLifecycleOwner) { notes ->
            //Log.d("NoteFragment", "All notes from ViewModel $notes")
            noteRecyclerAdapter.items = notes
            //Log.d("NoteFragment", "Should have all notes ${noteRecyclerAdapter.items}")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}