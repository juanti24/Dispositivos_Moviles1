package com.example.dispositivosmoviles.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.marvel_logic.MarvelLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarvelViewModel : ViewModel() {

    val items = MutableLiveData<MutableList<MarvelChars>>()
    suspend fun getMarvelChars(name: String, limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItems = MarvelLogic()
                .getMarvelChars(name, limit)
            items.postValue(newItems)
        }
    }

    suspend fun getAllMarvelChars(offset: Int, limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItems = MarvelLogic()
                .getAllMarvelChars(offset, limit)
            items.postValue(newItems)
        }
    }

    suspend fun getInitChar(offset: Int, limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItems = MarvelLogic()
                .getInitChar(offset, limit)
            items.postValue(newItems)
        }
    }

}