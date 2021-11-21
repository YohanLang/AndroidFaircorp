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


const val Building_NAME_PARAM2 = "com.faircorp.Buildingname.attribute"

class BuildingActivity : BasicActivity(), OnRoomSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getLongExtra(Building_NAME_PARAM2, 0)
        val listArg: MutableList<String?> = mutableListOf()

        lifecycleScope.launch(Dispatchers.Default) { // (1)
            runCatching { ApiServices().buildingsApiService.findById(id).execute() } // (2)
                .onSuccess {
                    val iname: String? = it.body()?.name
                    val iout: String? = it.body()?.outsideTemperature.toString()
                    listArg.add(iname)
                    listArg.add(iout)

                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "id $id Error on Buildings loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            withContext(context = Dispatchers.Main) {
                findViewById<TextView>(R.id.text_building).text = listArg.get(0)
                findViewById<TextView>(R.id.text_out).text = listArg.get(1)
            }
        }
    }

    fun delete(view: View) {
        val id = intent.getLongExtra(Building_NAME_PARAM2, 0)

        lifecycleScope.launch(Dispatchers.Default) { // (1)
            runCatching { ApiServices().buildingsApiService.delete(id).execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Success deleting",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "id $id Error on building deleting $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    fun findRoomByBuilding(view: View) {

        setContentView(R.layout.activity_rooms)
        val id = intent.getLongExtra(Building_NAME_PARAM2, 0)

        val recyclerView = findViewById<RecyclerView>(R.id.list_rooms) // (2)
        val adapter = RoomAdapter(this) // (3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching {
                ApiServices().buildingsApiService.findRoomByBuilding(id).execute()
            } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        adapter.update(it.body() ?: emptyList())
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
        }
    }


    override fun onRoomSelected(id: Long?) {
        val intent = Intent(this, RoomActivity::class.java).putExtra(Room_NAME_PARAM2, id)
        startActivity(intent)
    }


}