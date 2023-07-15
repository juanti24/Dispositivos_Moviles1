package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityLoginBinding
import com.example.dispositivosmoviles.databinding.ActivityPrincipalBinding
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar

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

            val check = LoginValidator().checkLogin(binding.txtNombre.text.toString(),binding.txtContasena.text.toString())

            if(check){

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

    override fun onDestroy() {
        super.onDestroy()
    }
}