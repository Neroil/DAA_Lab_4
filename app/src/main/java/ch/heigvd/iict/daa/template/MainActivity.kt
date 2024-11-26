package ch.heigvd.iict.daa.template

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.template.adapter.NoteRecyclerAdapter
import ch.heigvd.iict.daa.template.database.note.NoteDatabase
import ch.heigvd.iict.daa.template.database.note.NoteRepository
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModel
import ch.heigvd.iict.daa.template.viewmodel.factory.NoteVMFactory

class MainActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels {
        NoteVMFactory((application as NoteApp).repository)
    }



    override fun onCreateOptionsMenu (menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu , menu)

        // Hide/Show menu depending on orientation
        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            menu?.findItem(R.id.main_menu_generate)?.setVisible(true)
            menu?.findItem(R.id.main_menu_delete)?.setVisible(true)
        } else {
            menu?.findItem(R.id.main_menu_generate)?.setVisible(false)
            menu?.findItem(R.id.main_menu_delete)?.setVisible(false)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected (item: MenuItem): Boolean{
        return when (item. itemId ){
            R.id.main_menu_generate -> {
                noteViewModel.generateANoteAndSchedule()
                true
            }
            R.id.main_menu_delete -> {
                noteViewModel.deleteAllNoteAndSchedules()
                true
            }
            R.id.sort_menu_creation_date -> {
                //noteViewModel.setSortType(NoteViewModel.SortType.CREATION_DATE)
                true
            }
            R.id.sort_menu_eta -> {
                //noteViewModel.setSortType(NoteViewModel.SortType.ETA)
                true
            }
            else -> super.onOptionsItemSelected (item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}