package com.example.dispositivosmoviles.logic.marvel_logic

import android.util.Log
import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndpoint
import com.example.dispositivosmoviles.data.marvel.MarvelChars

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
                    var comic: String = "No available"
                    if (it.comics.items.size > 0) {
                        comic = it.comics.items[0].name
                    }
                    val m = MarvelChars(
                        it.id,
                        it.name,
                        comic,
                        it.thumbnail.path + "." + it.thumbnail.extension
                    )
                    itemList.add(m)
                }
            } else {
                Log.d("UCE", response.toString())
            }
        }


        return itemList

    }

}