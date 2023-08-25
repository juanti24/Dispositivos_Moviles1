package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R

import com.example.dispositivosmoviles.databinding.ActivityWithBindingBinding
import com.example.dispositivosmoviles.ui.fragments.FirstFragment
import com.example.dispositivosmoviles.ui.fragments.SecondFragment
import com.example.dispositivosmoviles.ui.fragments.ThirdFragment
import com.example.dispositivosmoviles.ui.utilities.FragmentsManager
import com.google.android.material.snackbar.Snackbar

class ActivityWithBinding : AppCompatActivity() {

    private lateinit var binding : ActivityWithBindingBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UCE", "Entrando a Create")

        binding = ActivityWithBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        var name: String = ""



        Log.d("UCE", "Entrando a Start")
        super.onStart()
        FragmentsManager().replaceFragment(
            supportFragmentManager,
            binding.frmContainer.id,
            FirstFragment()
        )
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun initClass() {
        binding.imageButton2.setOnClickListener {


            var intent = Intent(
                this,
                EjercicioPracticoActivity::class.java
            )


            startActivity(intent)

        }

        binding.bottomNavigation.setOnItemSelectedListener {it->
            when(it.itemId) {
                R.id.home -> {

                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )
                    true
                }
                R.id.fav -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        SecondFragment()
                    )
                    true
                }
                R.id.chat_gpt -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        ThirdFragment()
                    )
                    true
                }

                else -> {false}
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}