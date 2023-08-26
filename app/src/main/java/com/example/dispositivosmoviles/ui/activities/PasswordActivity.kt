package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityPasswordBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class PasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRecover.setOnClickListener {
            validate()
        }
    }

    private fun validate() {
        var email = binding.email.text.toString().trim()

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.error = "Correo Invalido"
            return
        } else {
            sendEmail(email)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, EjercicioPracticoActivity::class.java))
        finish()
    }

    private fun sendEmail(email: String) {
        auth = FirebaseAuth.getInstance()
        var emailAddress: String = email

        auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener {
            if (it.isSuccessful) {
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Alert")
                    setMessage("Correo de recuperacion enviado correctamente")
                    setCancelable(true)
                }
                startActivity(Intent(this, EjercicioPracticoActivity::class.java))

            } else {
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Alert")
                    setMessage("El correo fue invalido")
                    setCancelable(true)
                }
            }
        }

    }
}