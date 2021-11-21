package com.faircorp


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val Room_NAME_PARAM2 = "com.faircorp.Roomname.attribute"

class RoomActivity : BasicActivity(), OnWindowSelectedListener, OnHeaterSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getLongExtra(Room_NAME_PARAM2, 0)
        val listArg: MutableList<String?> = mutableListOf()

        lifecycleScope.launch(Dispatchers.Default) { // (1)
            runCatching { ApiServices().roomsApiService.findById(id).execute() } // (2)
                .onSuccess {
                    val iname: String? = it.body()?.name
                    val iRoomName: String? = it.body()?.currentTemperature.toString()
                    val iTargetTemperature: String? = it.body()?.targetTemperature.toString()
                    val iPower: String? = it.body()?.floor.toString()
                    val iStatus: String? = it.body()?.buildingId.toString()
                    listArg.add(iname)
                    listArg.add(iRoomName)
                    listArg.add(iTargetTemperature)
                    listArg.add(iStatus)
                    listArg.add(iPower)
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Success $iname, $iRoomName,  $iStatus",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "id $id Error on Rooms loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            withContext(context = Dispatchers.Main) {
                findViewById<TextView>(R.id.text_room).text = listArg.get(0)
                findViewById<TextView>(R.id.text_current_temp).text = listArg.get(1)
                findViewById<TextView>(R.id.text_target_temp).text = listArg.get(2)
                findViewById<TextView>(R.id.text_floor).text = listArg.get(4)
                findViewById<TextView>(R.id.text_room_building).text = listArg.get(3)
            }
        }
    }

    fun switch_Windows(view: View) {
        val id = intent.getLongExtra(Room_NAME_PARAM2, 0)

        lifecycleScope.launch(Dispatchers.Default) { // (1)
            runCatching { ApiServices().roomsApiService.switch_Windows(id).execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Success  switch",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "id $id Error on rooms switching $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }

    fun switch_Heaters(view: View) {
        val id = intent.getLongExtra(Room_NAME_PARAM2, 0)

        lifecycleScope.launch(Dispatchers.Default) { // (1)
            runCatching { ApiServices().roomsApiService.switch_Heaters(id).execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Success  switch",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "id $id Error on rooms switching $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    fun delete(view: View) {
        val id = intent.getLongExtra(Room_NAME_PARAM2, 0)

        lifecycleScope.launch(Dispatchers.Default) { // (1)
            runCatching { ApiServices().roomsApiService.delete(id).execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Success  deleting",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "id $id Error on rooms deleting $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    fun findWindowsByRoom(view: View) {

        setContentView(R.layout.activity_windows)
        val id = intent.getLongExtra(Room_NAME_PARAM2, 0)

        val recyclerView = findViewById<RecyclerView>(R.id.list_windows) // (2)
        val adapter = WindowAdapter(this) // (3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomsApiService.findWindowsByRoom(id).execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        adapter.update(it.body() ?: emptyList())
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Error on windows loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    fun findHeatersByRoom(view: View) {

        setContentView(R.layout.activity_heaters)
        val id = intent.getLongExtra(Room_NAME_PARAM2, 0)

        val recyclerView = findViewById<RecyclerView>(R.id.list_heaters) // (2)
        val adapter = HeaterAdapter(this) // (3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomsApiService.findHeatersByRoom(id).execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        adapter.update(it.body() ?: emptyList())
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Error on windows loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    override fun onWindowSelected(id: Long?) {
        val intent = Intent(this, WindowActivity::class.java).putExtra(WINDOW_NAME_PARAM2, id)
        startActivity(intent)

    }

    override fun onHeaterSelected(id: Long?) {
        val intent = Intent(this, HeaterActivity::class.java).putExtra(Heater_NAME_PARAM2, id)
        startActivity(intent)
    }


}