package com.example.dispositivosmoviles.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispositivosmoviles.databinding.FragmentSecondBinding
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.marvel_logic.MarvelLogic
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter2
import com.example.dispositivosmoviles.ui.viewmodels.MarvelViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var lmanager: LinearLayoutManager

    private lateinit var rvAdapter: MarvelAdapter2

    private var marvelCharItems: MutableList<MarvelChars> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
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


        binding.editText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.editText.windowToken, 0)

                chargeDataRv(textView.text.toString())


                return@setOnEditorActionListener true
            }
            false
        }

    }

    fun chargeDataRv(search: String) {

        lifecycleScope.launch(Dispatchers.Main) {

            marvelCharItems = withContext(Dispatchers.IO) {

                return@withContext MarvelLogic().getMarvelChars(
                    search,
                    20
                )
            }

            rvAdapter = MarvelAdapter2(
                marvelCharItems
            )

            binding.recycler.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }

        }

    }

}