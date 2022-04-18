package com.radenmas.belajar_iot

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tvTemp: TextView
        var tvHum: TextView
        var imgLamp: ImageView
        var tvStateLamp: TextView
        var swLamp: SwitchMaterial

        tvTemp = findViewById(R.id.tvTemp)
        tvHum = findViewById(R.id.tvHum)
        imgLamp = findViewById(R.id.imgLamp)
        tvStateLamp = findViewById(R.id.tvLamp)
        swLamp = findViewById(R.id.swLamp)

        var dbSuhu = Firebase.database.getReference("suhu")
        dbSuhu.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // get value from firebase
                var valueSuhu = snapshot.child("suhu").value
                var valueHum = snapshot.child("hum").value

                // set value to TextView
                tvTemp.text = "$valueSuhu \u2103"
                tvHum.text = "$valueHum %"
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


        swLamp.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked){
                tvStateLamp.text = "On"
                imgLamp.setImageDrawable(resources.getDrawable(R.drawable.ic_light_bulb_on))

                // perintah set On ke Firebase
                var onLamp = Firebase.database.getReference("lampu")
                onLamp.setValue(1)
            } else {
                tvStateLamp.text = "Off"
                imgLamp.setImageDrawable(resources.getDrawable(R.drawable.ic_light_bulb_off))

                // perintah set Off ke Firebase
                var offLamp = Firebase.database.getReference("lampu")
                offLamp.setValue(0)
            }
        }
    }
}