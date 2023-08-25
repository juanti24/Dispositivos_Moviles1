package com.example.dispositivosmoviles.ui.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.marvel_logic.MarvelLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressViewModel : ViewModel() {

    var progressState = MutableLiveData<Int>()
    val items = MutableLiveData<MutableList<MarvelChars>>()

    fun progressBackground(time: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = View.VISIBLE
            progressState.postValue(state)
            delay(time)
            val state1 = View.GONE
            progressState.postValue(state1)
        }
    }

    fun sumInBackground(cant: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = View.VISIBLE
            progressState.postValue(state)

            var total = 0
            for (i in 1 .. cant){
                total += i
            }

            val state1 = View.GONE
            progressState.postValue(state1)

            Log.d("UCE", "${total}")
        }
    }

    suspend fun getMarvelChars(offset: Int, limit: Int){
        progressState.postValue(View.VISIBLE)
        val newItems = MarvelLogic()
            .getAllMarvelChars(offset, limit)
        items.postValue(newItems)
        progressState.postValue(View.GONE)
    }

}