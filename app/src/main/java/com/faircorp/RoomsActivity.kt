package com.faircorp


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomsActivity : BasicActivity(), OnRoomSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        val recyclerView = findViewById<RecyclerView>(R.id.list_rooms) // (2)
        val adapter = RoomAdapter(this) // (3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomsApiService.findAll().execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        adapter.update(it.body() ?: emptyList())
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Error on Rooms loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    override fun onRoomSelected(id: Long?) {

        val intent = Intent(this, RoomActivity::class.java).putExtra(Room_NAME_PARAM2, id)
        startActivity(intent)
    }

}