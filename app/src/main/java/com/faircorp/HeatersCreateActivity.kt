package com.faircorp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeatersCreateActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_heater_create)
        val arrayId = arrayListOf<Long?>()
        val arrayName = arrayListOf<String?>()
        val arrayStatus = arrayListOf<HeaterStatus>()
        arrayStatus.add(HeaterStatus.ON)
        arrayStatus.add(HeaterStatus.OFF)
        // access the items of the list
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomsApiService.findAll().execute() } // (2)
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
                            "Error on rooms loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            withContext(context = Dispatchers.Main) {
                // access the spinner
                val spinner = findViewById<Spinner>(R.id.spinnerheater)
                val spinner2 = findViewById<Spinner>(R.id.spinnerheater2)
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

                            findViewById<TextView>(R.id.edit_heater_room_id).text =
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
                if (spinner2 != null) {
                    val adapter = ArrayAdapter(
                        applicationContext,
                        android.R.layout.simple_spinner_item, arrayStatus
                    )
                    spinner2.adapter = adapter

                    spinner2.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View, position: Int, id: Long
                        ) {

                            findViewById<TextView>(R.id.edit_heater_status).text =
                                arrayStatus[position].toString()

                            Toast.makeText(
                                applicationContext,
                                getString(R.string.selected_item) + " " +
                                        "" + arrayStatus[position].toString(), Toast.LENGTH_SHORT
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
        val heaterName = findViewById<EditText>(R.id.edit_heater_name).text.toString()
        val heaterStatus = findViewById<TextView>(R.id.edit_heater_status).text.toString()
        val heaterRoomId = findViewById<TextView>(R.id.edit_heater_room_id).text.toString().toLong()
        val heaterPower = findViewById<EditText>(R.id.edit_heater_power).text.toString().toDouble()

        when (heaterStatus) {
            "ON" -> {
                var heaterDto =
                    HeaterDto(null, heaterName, heaterPower, null, heaterRoomId, HeaterStatus.ON)
                lifecycleScope.launch(context = Dispatchers.IO) { // (1)
                    runCatching {
                        ApiServices().heatersApiService.create(heaterDto).execute()
                    } // (2)
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
            "OFF" -> {
                var heaterDto =
                    HeaterDto(null, heaterName, null, null, heaterRoomId, HeaterStatus.OFF)
                lifecycleScope.launch(context = Dispatchers.IO) { // (1)
                    runCatching {
                        ApiServices().heatersApiService.create(heaterDto).execute()
                    } // (2)
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


    }
}