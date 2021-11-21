package com.faircorp


import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val Room_NAME_PARAM2 = "com.faircorp.Roomname.attribute"

class RoomActivity : BasicActivity() {
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
    fun switch_Windows(view : View) {
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
    fun switch_Heaters(view : View) {
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
    fun delete(view : View) {
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


}