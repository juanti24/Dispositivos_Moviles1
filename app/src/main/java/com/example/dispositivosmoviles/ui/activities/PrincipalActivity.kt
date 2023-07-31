package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.databinding.ActivityPrincipalBinding
import com.example.dispositivosmoviles.ui.fragments.FirstFragment
import com.example.dispositivosmoviles.ui.fragments.SecondFragment
import com.example.dispositivosmoviles.ui.fragments.ThirdFragment
import com.example.dispositivosmoviles.ui.utilities.FragmentsManager
import com.google.android.material.snackbar.Snackbar

class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UCE","Entrada a Create")
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onStart() {
        super.onStart()

        FragmentsManager().replaceFragment(
            supportFragmentManager,
            binding.frmContainer.id,
            FirstFragment())

        var name : String = ""
        /*intent.extras.let {
            name = it?.getString("var1")!!
        }*/

        //Log.d("UCE","Hola ${name}")
        //binding.textName.text = "Bienvenido "+name.toString()

        //Log.d("UCE","Entrada a Start")

        binding.botonUno.setOnClickListener {
            //iniciar el objeto intent
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

        }


        binding.bottomNavigation.setOnItemSelectedListener() { item ->
            when(item.itemId) {
                R.id.inicio -> {
                    //instanciar fragment

                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )


                    true
                }
                R.id.favoritos -> {
                    // Respond to navigation item 2 click

                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        SecondFragment()
                    )

                    true
                }
                R.id.apis -> {
                    // Respond to navigation item 2 click

                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        ThirdFragment()
                    )

                    true
                }
                else -> false
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
    }




}