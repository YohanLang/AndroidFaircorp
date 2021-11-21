package com.faircorp


import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val Heater_NAME_PARAM2 = "com.faircorp.Heatername.attribute"

class HeaterActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getLongExtra(Heater_NAME_PARAM2, 0)
        val listArg: MutableList<String?> = mutableListOf()

        lifecycleScope.launch(Dispatchers.Default) { // (1)
            runCatching { ApiServices().heatersApiService.findById(id).execute() } // (2)
                .onSuccess {
                    val iname: String? = it.body()?.name
                    val iRoomName: String? = it.body()?.roomName
                    val iPower: String? = it.body()?.power.toString()
                    val iStatus: String? = it.body()?.status.toString()
                    listArg.add(iname)
                    listArg.add(iRoomName)
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
                            "id $id Error on Heaters loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            withContext(context = Dispatchers.Main) {
                findViewById<TextView>(R.id.txt_heater_name).text = listArg.get(0)
                findViewById<TextView>(R.id.txt_heater_room_name).text = listArg.get(1)
                findViewById<TextView>(R.id.txt_heater_status).text = listArg.get(2)
                findViewById<TextView>(R.id.txt_power).text = listArg.get(3)
            }
        }
    }

    fun switch(view: View) {
        val id = intent.getLongExtra(Heater_NAME_PARAM2, 0)

        lifecycleScope.launch(Dispatchers.Default) { // (1)
            runCatching { ApiServices().heatersApiService.switch(id).execute() } // (2)
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
                            "id $id Error on windows switching $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }

    fun delete(view: View) {
        val id = intent.getLongExtra(Heater_NAME_PARAM2, 0)

        lifecycleScope.launch(Dispatchers.Default) { // (1)
            runCatching { ApiServices().heatersApiService.delete(id).execute() } // (2)
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
                            "id $id Error on windows deleting $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}