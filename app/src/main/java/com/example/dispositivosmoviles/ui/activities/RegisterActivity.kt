package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    private val TAG = "UCE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            if (binding.txtName.text.toString().isNotEmpty() && binding.txtPassword.text.toString()
                    .isNotEmpty()
            ) {
                autWithFirebaseEmail(
                    binding.txtName.text.toString(),
                    binding.txtPassword.text.toString()
                )
                Snackbar.make(
                    binding.btnRegister,
                    "Registrado con exito ${binding.txtName.text}",
                    Snackbar.LENGTH_LONG
                ).show()

                startActivity(Intent(this, EjercicioPracticoActivity::class.java))
            } else {
                Snackbar.make(
                    binding.btnRegister,
                    "Ingrese su informacion",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun autWithFirebaseEmail(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(Constants.TAG, "createUserWithEmailAndPassword:success")

                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication success.",
                        Toast.LENGTH_SHORT,
                    ).show()

                } else {

                    Log.w(Constants.TAG, "createUserWithEmailAndPassword:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }


}