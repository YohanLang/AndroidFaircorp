package com.faircorp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuildingsCreateActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_create)
    }

    fun create(view: View) {
        val buildingName = findViewById<EditText>(R.id.edit_building_name).text.toString()
        val buildingStatus =
            findViewById<TextView>(R.id.edit_building_out).text.toString().toDouble()


        var buildingDto = BuildingDto(null, buildingName, buildingStatus)
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().buildingsApiService.create(buildingDto).execute() } // (2)
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