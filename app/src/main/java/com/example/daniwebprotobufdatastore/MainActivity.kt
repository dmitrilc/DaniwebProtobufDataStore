package com.example.daniwebprotobufdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            restoreCheckBoxStates()
        }
    }

    private suspend fun restoreCheckBoxStates() {
        val checkbox1 = findViewById<CheckBox>(R.id.checkBox1)
        val checkbox2 = findViewById<CheckBox>(R.id.checkBox2)
        val checkbox3 = findViewById<CheckBox>(R.id.checkBox3)
        val checkbox4 = findViewById<CheckBox>(R.id.checkBox4)

        applicationContext.checkboxStatesDataStore.data.collect { states ->
            checkbox1.isChecked = states.checkBox1
            checkbox2.isChecked = states.checkBox2
            checkbox3.isChecked = states.checkBox3
            checkbox4.isChecked = states.checkBox4
        }
    }

    private suspend fun saveCheckboxStates() {
        val checkbox1 = findViewById<CheckBox>(R.id.checkBox1)
        val checkbox2 = findViewById<CheckBox>(R.id.checkBox2)
        val checkbox3 = findViewById<CheckBox>(R.id.checkBox3)
        val checkbox4 = findViewById<CheckBox>(R.id.checkBox4)

        applicationContext.checkboxStatesDataStore.updateData { states ->
            states.toBuilder()
                .setCheckBox1(checkbox1.isChecked)
                .setCheckBox2(checkbox2.isChecked)
                .setCheckBox3(checkbox3.isChecked)
                .setCheckBox4(checkbox4.isChecked)
                .build()
        }
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch {
            saveCheckboxStates()
        }
    }

}