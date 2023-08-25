package com.example.dispositivosmoviles.logic.marvel_logic

import android.util.Log
import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndpoint
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB
import com.example.dispositivosmoviles.data.entities.marvel.characters.getMarvelChars
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.data.getMarvelCharsDB
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles
import java.lang.Exception
import java.lang.RuntimeException

class MarvelLogic {

    suspend fun getMarvelChars(name: String, limit: Int): ArrayList<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()

        val call = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        )

        if (call != null) {
            val response = call.getCharactersThatStartWith(name, limit)

            if (response.isSuccessful) {
                response.body()!!.data.results.forEach() {
                    itemList.add(it.getMarvelChars())
                }
            } else {
                Log.d("UCE", response.toString())
            }
        }

        return itemList

    }

    suspend fun getAllMarvelChars(offset: Int, limit: Int): ArrayList<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()

        val call = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        )

        if (call != null) {
            val response = call.getAllMarvelChars(offset, limit)

            if (response.isSuccessful) {
                response.body()!!.data.results.forEach() {
                    itemList.add(it.getMarvelChars())
                }
            } else {
                Log.d("UCE", response.toString())
            }
        }

        return itemList

    }

    suspend fun getAllMarvelCharDB(): MutableList<MarvelChars> {

        val items: ArrayList<MarvelChars> = arrayListOf()

        DispositivosMoviles.getDbInstance().marvelDao().getAllCharacters().forEach {
            items.add(
                MarvelChars(
                    it.id,
                    it.nombre,
                    it.comic,
                    it.imagen
                )
            )
        }

        return items

    }

    suspend fun getInitChar(offset:Int, limit:Int): MutableList<MarvelChars> {
        var items = mutableListOf<MarvelChars>()
        try {
            items = MarvelLogic()
                .getAllMarvelCharDB()
                .toMutableList()

            if (items.isEmpty()) {
                items = (MarvelLogic().getAllMarvelChars(
                    offset,
                    limit
                ))
                //MarvelLogic().insertMarvelCharsToDB(items)
            }
        } catch (ex: Exception) {
            throw RuntimeException(ex.message)
        } finally {
            return items
        }
    }

    suspend fun insertMarvelCharsToDB(items: List<MarvelChars>) {
        var itemsDB = arrayListOf<MarvelCharsDB>()
        items.forEach {
            itemsDB.add(it.getMarvelCharsDB())
        }
        DispositivosMoviles.getDbInstance().marvelDao().insertMarvelChars(itemsDB)
    }


}