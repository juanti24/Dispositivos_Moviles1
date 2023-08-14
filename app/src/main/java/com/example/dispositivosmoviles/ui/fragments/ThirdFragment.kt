package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransitionImpl
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.MarvelFavoriteCharsDB
import com.example.dispositivosmoviles.databinding.FragmentSecondBinding
import com.example.dispositivosmoviles.databinding.FragmentThirdBinding
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.activities.DatailsMarvelItem
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ThirdFragment : Fragment() {

    private lateinit var binding : FragmentThirdBinding
    private lateinit var rvAdapter: MarvelAdapter
    private lateinit var gManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentThirdBinding.inflate(
            inflater, container, false
        )

        gManager = GridLayoutManager(requireContext(), 2)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        chargeDataRV()

        binding.rvSwipe.isRefreshing = false
    }

    fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireContext(), DatailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun saveMarvelItem(item: MarvelChars): Boolean {
        val marvelSavedChar = MarvelFavoriteCharsDB(
            id = item.id,
            name = item.name,
            comic = item.comic,
            image = item.image
        )

        val dao = DispositivosMoviles
            .getDbInstance()
            .marvelFavoriteDao()

        lifecycleScope.launch(Dispatchers.IO) {
            val exist = dao.getOneCharacters(item.id)
            if (exist != null) {
                dao.deleteMarvelChar(exist)
                Snackbar.make(
                    binding.cardView,
                    "Se elimino de favoritos",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                dao.insertMarvelChar(marvelSavedChar)
                Snackbar.make(
                    binding.cardView,
                    "Se agrego a favoritos",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        return false
    }

    fun chargeDataRV() {
        lifecycleScope.launch(Dispatchers.Main) {
            val favoriteChars = getAllFavoriteMarvelChars()
            rvAdapter = MarvelAdapter(
                favoriteChars,
                fnClick = { sendMarvelItem(it) },
                fnSave = { saveMarvelItem(it) }
            )
            binding.rvMarvelChars.apply {
                adapter = rvAdapter
                layoutManager = gManager
            }
        }
    }

    private suspend fun getAllFavoriteMarvelChars(): List<MarvelChars> {
        return withContext(Dispatchers.IO) {
            MarvelLogic().getAllFavoriteMarvelCharDb()
        }
    }
}