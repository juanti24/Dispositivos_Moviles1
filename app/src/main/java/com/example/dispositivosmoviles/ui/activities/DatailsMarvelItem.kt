package com.example.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.databinding.ActivityDatailsMarvelItemBinding
import com.squareup.picasso.Picasso

class DatailsMarvelItem : AppCompatActivity() {

    private lateinit var binding: ActivityDatailsMarvelItemBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datails_marvel_item)

        binding = ActivityDatailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
//        var name : String? = ""
//        intent.extras?.let {
//            name = it.getString("name")
//        }
//        if(!name.isNullOrEmpty()){
//            binding.txtName.text = name
//        }

        val item = intent.getParcelableExtra<MarvelChars>("name")

        if(item != null){
            binding.txtName.text = item.name
            Picasso.get().load(item.image).into(binding.imgMarvel)
        }

    }
}