package ch.heigvd.iict.daa.template.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import ch.heigvd.iict.daa.template.MainActivity
import ch.heigvd.iict.daa.template.NoteApp
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModel
import ch.heigvd.iict.daa.template.viewmodel.factory.NoteVMFactory

/**
 * A simple [Fragment] subclass.
 * Use the [ControlFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ControlFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels {
        NoteVMFactory((activity?.application as NoteApp).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.generate_btn).setOnClickListener {
            noteViewModel.generateANoteAndSchedule();
        }


        view.findViewById<Button>(R.id.delete_btn).setOnClickListener {
            noteViewModel.deleteAllNoteAndSchedules();
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_control, container, false)
    }
}