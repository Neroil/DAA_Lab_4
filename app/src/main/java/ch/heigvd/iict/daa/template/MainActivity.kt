package ch.heigvd.iict.daa.template

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu (menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu , menu)
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