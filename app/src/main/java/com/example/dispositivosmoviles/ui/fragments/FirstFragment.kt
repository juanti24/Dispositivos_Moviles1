package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.activities.DatailsMarvelItem
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FirstFragment : Fragment() {

    private lateinit var binding : FragmentFirstBinding

    private lateinit var lManager: LinearLayoutManager

    private lateinit var rvAdapter: MarvelAdapter

    private var page = 1

    private var marvelCharsItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()


    private lateinit var gManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentFirstBinding.inflate(
            layoutInflater, container, false)

        //da la disposicion de orientacion
        //sabe cuantos elementos han pasado
        lManager = LinearLayoutManager(
            requireActivity(), //contexto -> se pasa el contexto de la activity
            LinearLayoutManager.VERTICAL,
            false
        )

        gManager = GridLayoutManager(requireActivity(),2)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val names = arrayListOf<String>("Carlos", "Xavier", "Andrés", "Pepe", "Mariano", "Rosa")

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_layout,
            names
        )

        binding.spinner.adapter = adapter
        //binding.listView.adapter = adapter

        chargeDataRV()

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV()
            binding.rvSwipe.isRefreshing = false
            gManager.scrollToPositionWithOffset(5,20)
        }

        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {

                        val v = gManager.childCount
                        val p = gManager.findFirstVisibleItemPosition()
                        val t = gManager.itemCount

                        // v
                        // p es la posicion en la que esta
                        // t es el total de items
                        if ((v + p) >= t) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                //val newItems = JikanAnimeLogic().getAllAnimes()
                                val newItems = MarvelLogic().getAllMarvelChars(0,99)

                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItem(newItems)
                                }
                            }
                        }
                    }
                }
            }
        )

        //evento para filtrar la informaicon
        binding.txtFilter.addTextChangedListener {filteredText ->
            //devuelve una lista
            val newItems = marvelCharsItems.filter { items ->
                items.name.lowercase().contains(filteredText.toString().lowercase())
            }
            rvAdapter.replaceListAdapter(newItems)
        }

    }

    //cambiar de activity desde un fragment
    //esta funcion lleva contenido o informacion
    fun sendMarvelItem(item: MarvelChars){
        val i = Intent(requireActivity(), DatailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun corrotine(){
        lifecycleScope.launch(Dispatchers.Main){

            var name = "Kevin"

            name = withContext(Dispatchers.IO){
                //hacer un cambio y retornar solo se puede con uno a la vez
                name = "Paul"
                return@withContext name
            }

            binding.cardView.radius
        }
    }

    //cambios
    fun chargeDataRV(){

        lifecycleScope.launch(Dispatchers.Main) {

            marvelCharsItems = withContext(Dispatchers.IO){
                    return@withContext (MarvelLogic().getAllMarvelChars(0,99))
                }

            rvAdapter = MarvelAdapter(
                marvelCharsItems,
                fnClick = { sendMarvelItem(it)}
            )

            binding.rvMarvelChars.apply{
                this.adapter = rvAdapter
                this.layoutManager = gManager
            }
        }
    }



}