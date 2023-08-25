package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityEjercicioPracticoBinding
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles
import com.example.dispositivosmoviles.ui.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    val appResultLocal =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultActivity ->

            val sn = Snackbar.make(binding.funcion6, "", Snackbar.LENGTH_LONG)

            var message = when (resultActivity.resultCode) {
                RESULT_OK -> {
                    sn.setBackgroundTint(resources.getColor(R.color.azul3))
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }
                RESULT_CANCELED -> {
                    sn.setBackgroundTint(resources.getColor(R.color.fragment2))
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }
                else -> {
                    "Resultado Erroneo"
                }
            }
            sn.setText(message)
            sn.show()
        }

    fun initClass() {

        binding.funcion1.setOnClickListener {
            var intent = Intent(
                this,
                BiometricActivity::class.java
            )
            startActivity(intent)
        }
        binding.funcion2.setOnClickListener {
            var intent = Intent(
                this,
                CameraActivity::class.java
            )
            startActivity(intent)
        }
        binding.funcion3.setOnClickListener {
            var intent = Intent(
                this,
                ActivityWithBinding::class.java
            )
            startActivity(intent)
        }
        binding.funcion4.setOnClickListener {
            var intent = Intent(
                this,
                NotificationActivity::class.java
            )
            startActivity(intent)
        }

        binding.funcion6.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }
        binding.funcion7.setOnClickListener {
            finishAffinity()
        }

    }

}