package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.datastore.dataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.MarvelFavoriteCharsDB
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.activities.DatailsMarvelItem
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles
import com.example.dispositivosmoviles.ui.utilities.Metodos
import com.example.dispositivosmoviles.ui.viewmodels.FragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Flow


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: FragmentViewModel
    private lateinit var rvAdapter: MarvelAdapter
    private lateinit var gManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FragmentViewModel::class.java)
        gManager = GridLayoutManager(requireActivity(), 2)

        binding.rvMarvelChars.layoutManager = gManager

        viewModel.getMarvelCharsItems().observe(viewLifecycleOwner) { marvelCharsItems ->
            if (marvelCharsItems != null) {
                rvAdapter = MarvelAdapter(
                    marvelCharsItems,
                    fnClick = { sendMarvelItem(it) },
                    fnSave = { viewModel.saveMarvelItem(it) }
                )
                binding.rvMarvelChars.adapter = rvAdapter
            }
        }

        viewModel.getSnackbarMessage().observe(viewLifecycleOwner) { message ->
            message?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.resetSnackbarMessage()
            }
        }

        binding.rvSwipe.setOnRefreshListener {
            viewModel.chargeDataRVAPI()
            binding.rvSwipe.isRefreshing = false
            gManager.scrollToPositionWithOffset(5, 20)
        }

        // Configurar el addOnScrollListener
        viewModel.setupScrollListener(binding.rvMarvelChars, gManager)

        viewModel.chargeDataRVDBInit()
    }
    fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DatailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }
}