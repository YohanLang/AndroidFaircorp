package com.faircorp


import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val Building_NAME_PARAM2 = "com.faircorp.Buildingname.attribute"

class BuildingActivity : BasicActivity() {
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


}