package com.example.dispositivosmoviles.ui.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.MarvelFavoriteCharsDB
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles
import com.example.dispositivosmoviles.ui.utilities.Metodos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val limit = 99
    private var offset = 0
    private val marvelCharsItems: MutableLiveData<MutableList<MarvelChars>?> = MutableLiveData()
    private val snackbarMessage: MutableLiveData<String?> = MutableLiveData()

    init {
        marvelCharsItems.value = mutableListOf()
    }

    fun getMarvelCharsItems() = marvelCharsItems

    fun getSnackbarMessage() = snackbarMessage

    fun resetSnackbarMessage() {
        snackbarMessage.value = null
    }

    fun chargeDataRVAPI() {
        viewModelScope.launch(Dispatchers.Main) {
            val newItems = withContext(Dispatchers.IO) {
                return@withContext MarvelLogic().getAllMarvelChars(offset, limit)
            }
            marvelCharsItems.value?.addAll(newItems)
            marvelCharsItems.postValue(marvelCharsItems.value)
            offset += limit
        }
    }

    fun chargeDataRVDBInit() {
        if (Metodos().isOnline(getApplication())) {
            viewModelScope.launch(Dispatchers.Main) {
                marvelCharsItems.value = withContext(Dispatchers.IO) {
                    return@withContext MarvelLogic().getInitChars(limit, offset)
                }
                offset += limit
            }
        } else {
            snackbarMessage.value = "No hay conexión a Internet"
        }
    }

    fun loadMoreData() {
        viewModelScope.launch(Dispatchers.Main) {
            val newItems = withContext(Dispatchers.IO) {
                return@withContext MarvelLogic().getAllMarvelChars(offset, limit)
            }
            marvelCharsItems.value?.addAll(newItems)
            marvelCharsItems.postValue(marvelCharsItems.value)
            offset += limit
        }
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

        var success = false

        viewModelScope.launch(Dispatchers.IO) {
            val exist = dao.getOneCharacters(item.id)
            if (exist != null) {
                dao.deleteMarvelChar(exist)
                snackbarMessage.postValue("Se eliminó de favoritos")
                success = true
            } else {
                dao.insertMarvelChar(marvelSavedChar)
                snackbarMessage.postValue("Se agregó a favoritos")
                success = true
            }
        }

        return success
    }

    fun setupScrollListener(recyclerView: RecyclerView, layoutManager: GridLayoutManager) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val v = layoutManager.childCount
                    val p = layoutManager.findFirstVisibleItemPosition()
                    val t = layoutManager.itemCount

                    if ((v + p) >= t) {
                        loadMoreData()
                    }
                }
            }
        })
    }

}