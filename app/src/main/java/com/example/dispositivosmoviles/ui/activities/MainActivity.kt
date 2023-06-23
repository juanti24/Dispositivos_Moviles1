package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    // En todas las clases pero cambia el tipo de dato (despues de los ':'
    private lateinit var binding: ActivityMainBinding // Invertido el nombre de la clase con terminacion Binding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Las siguientes dos lineas se definen en todas las clases
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // No es necesario invocarlo por el ciclo de vida de un Activity
        //onStart()

        // Se invoca el metodo creado para realizar funcionalidades especificas
        //initClass()

        // ViewBinding
        //var button2 = binding.button2

        // FindViewByID
        //var button2 = findViewById<Button>(R.id.button2)
        //var txtBuscar = findViewById<TextView>(R.id.txt_buscar)

        // Con ViewBinding
        /*binding.button2.setOnClickListener{
            binding.txtBuscar.text = "El evento se ha ejecutado"

            Toast.makeText(this, "AM MC xdxdxd",
                Toast.LENGTH_SHORT
            ).show()

            Snackbar.make(
                binding.button2,
                "Este es otro mensaje",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(Color.BLACK).show()
        }*/

        // con FindViewById
        /*button2.text = "INGRESAR"
        button2.setOnClickListener{
            txtBuscar.text = "El evento se ha ejecutado"

            Toast.makeText(this, "AM MC xdxdxd",
                Toast.LENGTH_SHORT
            ).show()

            Snackbar.make(
                button2,
                "Este es otro mensaje",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(Color.BLACK).show()
        }*/

    }

    // Para iniciar la funcion y evitar problemas con la inicializacion del Binding
    // Se ejecuta inmediatamente despues del onCreate
    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    // Podemos crear nuestras funcionalidades en una funcion apartada e invocarla en el onCreate
    fun initClass() {
        binding.btnLogin.setOnClickListener {
            //binding.txtBuscar.text = "El evento se ha ejecutado"

            /*
            Toast.makeText(
                this, "AM MC xdxdxd",
                Toast.LENGTH_SHORT
            ).show()
            */

            /*
            Snackbar.make(
                binding.button2,
                "Este es otro mensaje",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(Color.BLACK).show()
             */
/*
            // Asi se define un intent y se menciona a que Activity se trasladara
            var intent = Intent(
                this,
                ActivityWithBinding::class.java
            )
            // Añadimos contenido que queremos enviar a la otra activity
            intent.putExtra("var1", binding.txtUser.text.toString())

            // Con esto iniciamos el otro Activity
            startActivity(intent)
*/
            val check = LoginValidator().checkLogin(
                binding.txtUser.text.toString(),
                binding.txtPassword.text.toString()
            )
            if(check){

                var intent = Intent(
                    this,
                    ActivityWithBinding::class.java
                )

                intent.putExtra("var1", binding.txtUser.text.toString())

                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.btnLogin,
                    "Usuario o contraseña incorrectos",
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.BLACK).show()
            }

        }
    }
}