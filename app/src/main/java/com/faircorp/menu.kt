package com.faircorp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class BasicActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_windows -> startActivity(
                Intent(this, WindowsActivity::class.java)
            )
            R.id.menu_website -> startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://dev-mind.fr"))
            )
            R.id.menu_email -> startActivity(
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto://guillaume@dev-mind.fr"))
            )
            R.id.menu_heaters -> startActivity(
                Intent(this, HeatersActivity::class.java)
            )
            R.id.menu_rooms -> startActivity(
                Intent(this, RoomsActivity::class.java)
            )
            R.id.menu_buildings -> startActivity(
                Intent(this, BuildingsActivity::class.java)
            )
            R.id.menu_create_window -> startActivity(
                Intent(this, WindowsCreateActivity::class.java)
            )
            R.id.menu_create_room -> startActivity(
                Intent(this, RoomCreateActivity::class.java)
            )
            R.id.menu_create_heater -> startActivity(
                Intent(this, HeatersCreateActivity::class.java)
            )

        }
        return super.onContextItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}