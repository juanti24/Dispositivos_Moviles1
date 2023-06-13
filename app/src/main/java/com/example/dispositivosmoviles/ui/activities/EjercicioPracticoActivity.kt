package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.databinding.ActivityEjercicioPracticoBinding
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar

class EjercicioPracticoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEjercicioPracticoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEjercicioPracticoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun initClass() {
        binding.button2.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.textView6.text.toString(),
                binding.textView8.text.toString()
            )

            if(check){
                var intent = Intent(
                    this,
                    ActivityWithBinding::class.java
                )

                intent.putExtra("var1", binding.textView6.text.toString())

                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.button2,
                    "Usuario o contraseña incorrectos",
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.rgb(232, 87, 87)).show()
            }

        }
    }

}