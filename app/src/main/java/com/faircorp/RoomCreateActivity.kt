package com.faircorp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomCreateActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms_create)
        val arrayId = arrayListOf<Long?>()
        val arrayName = arrayListOf<String?>()

        // access the items of the list
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().buildingsApiService.findAll().execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        for (i in 0..(it.body()?.size!! - 1)) {
                            val dispId: Long? = it.body()?.get(i)?.id
                            val dispName: String? = it.body()?.get(i)?.name
                            arrayId.add(dispId)
                            arrayName.add(dispName)
                        }
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Error on building loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            withContext(context = Dispatchers.Main) {
                // access the spinner
                val spinner = findViewById<Spinner>(R.id.spinner_room)
                if (spinner != null) {
                    val adapter = ArrayAdapter(
                        applicationContext,
                        android.R.layout.simple_spinner_item, arrayName
                    )
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View, position: Int, id: Long
                        ) {

                            findViewById<TextView>(R.id.edit_room_building_id).text =
                                arrayId[position].toString()

                            Toast.makeText(
                                applicationContext,
                                getString(R.string.selected_item) + " " +
                                        "" + arrayId[position], Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            TODO("Not anything selected")

                        }
                    }
                }

            }
        }
    }


    fun create(view: View) {
        val roomName = findViewById<EditText>(R.id.edit_room_name).text.toString()
        val roomFloor = findViewById<EditText>(R.id.edit_room_floor).text.toString().toDouble()
        val roomCurrent =
            findViewById<EditText>(R.id.edit_room_current_temperature).text.toString().toDouble()
        val roomTarget =
            findViewById<EditText>(R.id.edit_room_target_temperature).text.toString().toDouble()
        val roomBuildingId =
            findViewById<TextView>(R.id.edit_room_building_id).text.toString().toLong()


        var roomDto = RoomDto(null, roomName, roomCurrent, roomTarget, roomFloor, roomBuildingId)
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomsApiService.create(roomDto).execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Successfully created",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Error Creating ${it.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


        }
    }
}