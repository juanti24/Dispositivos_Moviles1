package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.jikan_logic.JikanAnimeLogic
import com.example.dispositivosmoviles.logic.marvel_logic.MarvelLogic
import com.example.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
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

    private lateinit var rvAdapter: MarvelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val names = arrayListOf<String>(
            "Ariel",
            "Michael",
            "Erick",
            "Willian",
            "Edwin",
            "Santiago",
            "Layla"
        )

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_spinner,
            names
        )

        chargeDataRv("cap")

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRv("cap")
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

                        // v
                        // p es la posicion en la que esta
                        // t es el total de items
                        if ((v + p) >= t) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                val newItems = JikanAnimeLogic().getAllAnimes()
                                /*val newItems = MarvelLogic().getMarvelChars(
                                    name = "spi",
                                    limit = 20
                                )
                                */
                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItem(newItems)
                                }
                            }
                        }
                    }
                }
            }
        )

    }

    fun sendMarvelItem(item: MarvelChars) { //: Unit {
        //Los intents solo se encuentran en los fragments y en las Activities
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        // Esta forma de enviar informacion es ineficiente cuando se tienen mas atributos
        i.putExtra("name", item)
        //i.putExtra("comic", item.comic)
        //i.putExtra("imagen", item.imagen)
        startActivity(i)
    }

    // Serializacion: proceso de pasar de un objeto a un string para poder enviarlo por medio de la web
    // Parceables: Mucho mas eficiente que la serializacion, pues ejecutan de mejor manera el mismo proceso
    // Serializacion -> Utiliza objetos JSON
    // Parcelables   -> Son mas rapidos pero su implementacion es compleja, afortunadamente existen plugins que nos ayudan

    /*
    fun corroutine() {
        lifecycleScope.launch(Dispatchers.Main) {
            var name = "Ariel" // no tiene ningun efecto de cambio porque son distintos hilos
            // Por medio de dispatchers elegimos un hilo
            name = withContext((Dispatchers.IO)) {
                name = "Michael"
                return@withContext name // De esta forma si se actualiza en el hilo en el que este esa variable
            }

            binding.cardView.radius
        }
    }
    */

    fun chargeDataRv(search: String) {

        lifecycleScope.launch(Dispatchers.IO) {

            rvAdapter = MarvelAdapter(
                JikanAnimeLogic().getAllAnimes()
                //MarvelLogic().getMarvelChars(search, 20)
            ) { sendMarvelItem(it) }

            withContext(Dispatchers.Main) {
                with(binding.rvMarvelChars) {
                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                }
            }


        }


    }
}