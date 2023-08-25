package com.example.dispositivosmoviles.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityEjercicioPracticoBinding
import com.example.dispositivosmoviles.ui.utilities.MyLocationManager
import com.example.dispositivosmoviles.ui.validator.LoginValidator
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class EjercicioPracticoActivity : AppCompatActivity() {


    private lateinit var binding: ActivityEjercicioPracticoBinding

    private lateinit var auth: FirebaseAuth

    private val TAG = "UCE"

    // Ubicacion GPS
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBack: LocationCallback
    private lateinit var client: SettingsClient
    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private var currentLocation: Location? = null

    private val speechToText =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val sn = Snackbar.make(
                binding.root,
                "",
                Snackbar.LENGTH_LONG
            )

            var message = ""
            when (activityResult.resultCode) {
                RESULT_OK -> {
                    val msg =
                        activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                            .toString()
                    if (msg.isNotEmpty()) {
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, msg.toString())
                        startActivity(intent)
                    }
                }


                RESULT_CANCELED -> {
                    message = "Proceso Cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.rojo_pasion))
                }

                else -> {
                    message = "Resultado Erroneo"
                    sn.setBackgroundTint(resources.getColor(R.color.rojo_pasion))
                }

            }
            sn.setText(message)
            sn.show()

        }

    @SuppressLint("MissingPermission")
    private val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {
                true -> {

                    client.checkLocationSettings(locationSettingsRequest).apply {
                        addOnSuccessListener {
                            val task = fusedLocationProviderClient.lastLocation

                            task.addOnSuccessListener { location ->
                                fusedLocationProviderClient.requestLocationUpdates(
                                    locationRequest,
                                    locationCallBack,
                                    Looper.getMainLooper()
                                )
                            }
                        }

                        addOnFailureListener { ex ->
                            if (ex is ResolvableApiException) {
                                ex.startResolutionForResult(
                                    this@EjercicioPracticoActivity,
                                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                                )
                            }

                        }
                    }

                }

                shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    Snackbar.make(
                        binding.textView3,
                        "Ayude con el permiso por fa",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                false -> {
                    Snackbar.make(
                        binding.textView3,
                        "Permiso denegado",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEjercicioPracticoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000
        ).setMaxUpdates(1).build()

        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                if (locationResult != null) {
                    locationResult.locations.forEach { location ->
                        currentLocation = location
                        Toast.makeText(
                            baseContext,
                            "Ubicacion: ${location.latitude}, ${location.longitude}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }

        client = LocationServices.getSettingsClient(this)
        locationSettingsRequest =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()

        auth = Firebase.auth
        binding.button2.setOnClickListener {

            /*     autWithFirebaseEmail(binding.txtName.text.toString(),
                 binding.txtPassword.text.toString())
             */
            singInWhitEmailAndPassword(
                binding.txtName.text.toString(),
                binding.txtPassword.text.toString()
            )
        }
    }

    private fun singInWhitEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser

                    // updateUI(user)
                    startActivity(Intent(this, BiometricActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI(null)
                }
            }
    }

    private fun recoveryPasswordWithEmail(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            //Es una tarea
            if (task.isSuccessful) {
                Toast.makeText(
                    this,
                    "Correo de recuperacion enviado correctamente",
                    Toast.LENGTH_SHORT,
                ).show()
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Alert")
                    setMessage("Correo de recuperacion enviado correctamente")
                    setCancelable(true)
                }

            } else {

            }

        }
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()

        fusedLocationProviderClient.removeLocationUpdates(locationCallBack)
    }

    fun initClass() {



        binding.micro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        val appResultLocal =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultActivity ->

                val sn = Snackbar.make(binding.micro, "", Snackbar.LENGTH_LONG)

                var message = when (resultActivity.resultCode) {
                    RESULT_OK -> {
                        sn.setBackgroundTint(resources.getColor(R.color.azul))
                        resultActivity.data?.getStringExtra("result").orEmpty()
                    }

                    RESULT_CANCELED -> {
                        sn.setBackgroundTint(resources.getColor(R.color.rojo_pasion))
                        resultActivity.data?.getStringExtra("result").orEmpty()
                    }

                    else -> {
                        "Resultado Erroneo"
                    }

                }
                sn.setText(message)
                sn.show()
            }


        binding.textView9.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }


        binding.textView9.setOnClickListener {
            locationContract.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        }

        binding.textView10.setOnClickListener {
            startActivity(Intent(this, PasswordActivity::class.java))
        }

        binding.button5.setOnClickListener {
            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo")
            speechToText.launch(intentSpeech)
        }


        binding.textView9.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }

    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("email")] = "dimoviles@uce.edu.ec"
            prefs[stringPreferencesKey("password")] = UUID.randomUUID().toString()
        }
    }

    private fun test() {
        var location = MyLocationManager(this)
        location.getUserLocation()
    }

}