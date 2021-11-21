package com.faircorp
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class WindowsCreateActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_windows_create)
        val arrayId = arrayListOf<Long?>()
        val arrayName = arrayListOf<String?>()
        val arrayStatus = arrayListOf<Status>()
        arrayStatus.add(Status.OPEN)
        arrayStatus.add(Status.CLOSED)
        // access the items of the list
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomsApiService.findAll().execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        for (i in 0..(it.body()?.size!!-1)) {
                            val dispId:Long? = it.body()?.get(i)?.id
                            val dispName:String? = it.body()?.get(i)?.name
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
                val spinner = findViewById<Spinner>(R.id.spinner)
                val spinner2 = findViewById<Spinner>(R.id.spinner2)
                if (spinner != null) {
                    val adapter = ArrayAdapter(applicationContext,
                        android.R.layout.simple_spinner_item, arrayName)
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>,
                                                    view: View, position: Int, id: Long) {

                            findViewById<TextView>(R.id.edit_window_room_id).text = arrayId[position].toString()

                            Toast.makeText(applicationContext,
                                getString(R.string.selected_item) + " " +
                                        "" + arrayId[position], Toast.LENGTH_SHORT).show()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                                TODO("Not anything selected")

                        }                        }
            }
                if (spinner2 != null) {
                    val adapter = ArrayAdapter(applicationContext,
                        android.R.layout.simple_spinner_item, arrayStatus)
                    spinner2.adapter = adapter

                    spinner2.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>,
                                                    view: View, position: Int, id: Long) {

                            findViewById<TextView>(R.id.edit_window_status).text = arrayStatus[position].toString()

                            Toast.makeText(applicationContext,
                                getString(R.string.selected_item) + " " +
                                        "" + arrayStatus[position].toString(), Toast.LENGTH_SHORT).show()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            TODO("Not anything selected")

                        }                        }
                }
        }
        }
    }


    fun create(view: View){
        val windowName = findViewById<EditText>(R.id.edit_window_name).text.toString()
        val windowStatus = findViewById<TextView>(R.id.edit_window_status).text.toString()
        val windowRoomId = findViewById<TextView>(R.id.edit_window_room_id).text.toString().toLong()

        when (windowStatus) {
            "OPEN" -> {
                var windowDto = WindowDto(null, windowName, null, windowRoomId, Status.OPEN)
                lifecycleScope.launch(context = Dispatchers.IO) { // (1)
                    runCatching { ApiServices().windowsApiService.create(windowDto).execute() } // (2)
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
            "CLOSED" -> {
                var windowDto = WindowDto(null, windowName, null, windowRoomId, Status.CLOSED)
                lifecycleScope.launch(context = Dispatchers.IO) { // (1)
                    runCatching { ApiServices().windowsApiService.create(windowDto).execute() } // (2)
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