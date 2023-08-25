package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.data.getMarvelCharsDB
import com.example.dispositivosmoviles.logic.marvel_logic.MarvelLogic
import com.example.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.ui.activities.NotificationActivity
import com.example.dispositivosmoviles.ui.activities.dataStore
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.example.dispositivosmoviles.ui.data.UserDataStore
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles
import com.example.dispositivosmoviles.ui.utilities.Metodos
import com.example.dispositivosmoviles.ui.viewmodels.MarvelViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gManager: GridLayoutManager

    private val marvelViewModel by viewModels<MarvelViewModel>()

    private lateinit var rvAdapter: MarvelAdapter

    private val limit = 5
    private var offset = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(
            layoutInflater,
            container,
            false
        )

        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        gManager = GridLayoutManager(
            requireActivity(),
            2
        )

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch(Dispatchers.IO) {
            getDataStore()
                .collect() {
                    Log.d("UCE", it.name)
                    Log.d("UCE", it.email)
                    Log.d("UCE", it.session)
                }
        }

        marvelViewModel.items.observe(
            this
        )
        {
            rvAdapter = MarvelAdapter(
                it,
                { mc -> sendMarvelItem(mc) },
                { mc -> saveMarveltem(mc) }
            )

            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
        }


        chargeDataRVInit(offset, limit)

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRv(offset, limit)
            binding.rvSwipe.isRefreshing = false
        }


        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        val v = lmanager.childCount
                        val p = lmanager.findFirstVisibleItemPosition()
                        val t = lmanager.itemCount

                        if ((v + p) >= t) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                //val newItems = JikanAnimeLogic().getAllAnimes()
                                val newItems = MarvelLogic().getAllMarvelChars(
                                    offset,
                                    limit
                                )

                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItem(newItems)
                                    //this@FirstFragment.offset = offset + limit

                                }
                            }
                            offset += limit
                        }
                    }
                }
            }
        )

        binding.txtFilter.addTextChangedListener { filteredText ->
            val newItems = marvelViewModel.items.value!!.filter { items ->
                items.nombre.lowercase().contains(filteredText.toString().lowercase())
            }
            rvAdapter.replaceListAdapter(newItems)
        }
    }


    fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun saveMarveltem(item: MarvelChars): Boolean {
        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                if (DispositivosMoviles.getDbInstance().marvelDao()
                        .getOneCharacter(item.id) == null
                ) {
                    DispositivosMoviles
                        .getDbInstance()
                        .marvelDao()
                        .insertMarvelChars(listOf(item.getMarvelCharsDB()))
                }
            }
        }
        return true
    }

    private fun getDataStore(): Flow<UserDataStore> {
        return requireActivity().dataStore.data.map { prefs ->
            UserDataStore(
                name = prefs[stringPreferencesKey("usuario")].orEmpty(),
                email = prefs[stringPreferencesKey("email")].orEmpty(),
                session = prefs[stringPreferencesKey("password")].orEmpty()
            )
        }
    }


    fun chargeDataRvAPI(search: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            marvelViewModel.getMarvelChars(search, limit)
        }
        this.offset += this.limit
    }

    fun chargeDataRv(offset: Int, limit: Int) {
        //hilo principal
        lifecycleScope.launch(Dispatchers.Main) {
            marvelViewModel.getAllMarvelChars(offset, limit)
        }
        this.offset += this.limit
    }

    fun chargeDataRVInit(offset: Int, limit: Int) {
        if (Metodos().isOnline(requireActivity())) {
            lifecycleScope.launch(Dispatchers.Main) {
                marvelViewModel.getInitChar(offset, limit)
            }
            this.offset += this.limit
        } else {
            Snackbar.make(binding.cardView, "No hay conexion", Snackbar.LENGTH_LONG).show()
        }
    }

}