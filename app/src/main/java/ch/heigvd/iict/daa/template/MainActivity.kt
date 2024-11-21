package ch.heigvd.iict.daa.template

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.iict.daa.template.database.note.NoteDatabase
import ch.heigvd.iict.daa.template.database.note.NoteRepository

class MainActivity : AppCompatActivity() {

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
            R.id.main_menu_generate -> { /* do something */ true}
            R.id.main_menu_delete -> { /* do something */ true}
            R.id.sort_menu_creation_date -> {true}
            R.id.sort_menu_eta -> {true}
            else -> super.onOptionsItemSelected (item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}