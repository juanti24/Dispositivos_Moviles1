package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_BIOMETRIC_ENROLL
import android.provider.Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.dispositivosmoviles.databinding.ActivityBiometricBinding
import com.google.android.material.snackbar.Snackbar

class BiometricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBiometricBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiometricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAutenticator.setOnClickListener {
            autenticateBiometric()
        }
    }

    private fun autenticateBiometric() {
        if(checkBiometric()){
            val executor = ContextCompat.getMainExecutor(this)

            val biometricPromp = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación Requerida")
                .setSubtitle("Ingrese su huella digital")
                .setDescription("Necesitamos su atenticacion para poder ingresar a las funcionalidades.")
                .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                .build()
            val biometricManager = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(
                            baseContext,
                            "Autenticacion Biometrica Erronea.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        startActivity(Intent(this@BiometricActivity, MainActivity::class.java))
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(
                            baseContext,
                            "Authenticacion Biometrica Falló.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                })
            biometricManager.authenticate(biometricPromp)
        }else{
            Snackbar.make(binding.btnAutenticator,"Noexistenrequisitos",Snackbar.LENGTH_LONG).show()
        }


    }
    private fun checkBiometric():Boolean{
        val biometricManager= BiometricManager.from(this)
        var returnValid=false
        Log.d("Error2","error")
        when(biometricManager.canAuthenticate(
            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        )){
            BiometricManager.BIOMETRIC_SUCCESS ->{
                returnValid= true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->{
                Log.d("Error1","error")
                returnValid= false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->{
                Log.d("Error2","error")
                returnValid= false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->{
                Log.d("Error3","error")
                val intentPromp= Intent(ACTION_BIOMETRIC_ENROLL)
                intentPromp.putExtra(EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                startActivity(intentPromp)
                returnValid=false
            }
        }
        return returnValid
    }

}