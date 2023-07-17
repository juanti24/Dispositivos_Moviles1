package com.example.dispositivosmoviles.ui.activities





import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


import androidx.lifecycle.lifecycleScope

import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityLoginBinding
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnIngresar.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.txtNombre.text.toString(),
                binding.txtContasena.text.toString()
            )

            if(check){

                lifecycleScope.launch(Dispatchers.IO){
                    saveDataStore(binding.txtNombre.text.toString())
                }

                var intent = Intent(
                    this,
                    PrincipalActivity::class.java
                )

                intent.putExtra("var1", binding.txtNombre.text.toString())

                startActivity(intent)


            }else{
                Snackbar.make(binding.txtNombre,"Usuario o contraseña invalidos", Snackbar.LENGTH_LONG).show()
            }

        }


    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "dimoviles@uce.edu.ec"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}