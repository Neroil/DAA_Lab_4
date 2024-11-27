package ch.heigvd.iict.daa.template.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import ch.heigvd.iict.daa.template.MainActivity
import ch.heigvd.iict.daa.template.NoteApp
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.databinding.FragmentControlBinding
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModel
import ch.heigvd.iict.daa.template.viewmodel.factory.NoteVMFactory

/**
 * A simple [Fragment] subclass.
 * Use the [ControlFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ControlFragment : Fragment() {

    private var _binding : FragmentControlBinding? = null
    private val binding get() = _binding!!

    private val noteViewModel: NoteViewModel by viewModels {
        NoteVMFactory((activity?.application as NoteApp).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assignation de l'action associée au bouton generate
        binding.generateBtn.setOnClickListener {
            noteViewModel.generateANoteAndSchedule();
        }

        // Assignation de l'action associée au bouton delete
        binding.deleteBtn.setOnClickListener {
            noteViewModel.deleteAllNoteAndSchedules();
        }

        noteViewModel.nbNotesAndSchedules.observe(this.viewLifecycleOwner) { value ->
            binding.noteCounte.text = value.toString()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentControlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}