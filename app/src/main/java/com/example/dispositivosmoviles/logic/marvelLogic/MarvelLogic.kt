package com.example.dispositivosmoviles.logic.marvelLogic

import android.util.Log
import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndpoint
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.getMarvelChars
import com.example.dispositivosmoviles.data.entities.marvel.characters.getMarvelChars
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.data.getMarvelCharsDB
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles

class MarvelLogic {

    suspend fun getMarvelChars(name: String, limit:Int):ArrayList<MarvelChars>{

        var itemList = arrayListOf<MarvelChars>()
        val call = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        )

        if(call != null){
            val response = call.getCharactersStartWith(name, limit)

            if(response.isSuccessful){
                response.body()!!.data.results.forEach(){

                    val m = it.getMarvelChars()
                    itemList.add(m)
                }
            }else{
                Log.d("UCE", response.toString())
            }
        }

        return itemList

    }


    suspend fun getAllMarvelChars(offset: Int, limit:Int):ArrayList<MarvelChars>{

        var itemList = arrayListOf<MarvelChars>()
        val call = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        )

        if(call != null){
//            val response =
//                call.getCharactersStartWith(offset, limit)

            val response =
                call.getAllMarvelChars(offset, limit)

            if(response.isSuccessful){
                response.body()!!.data.results.forEach(){

                    val m = it.getMarvelChars()
                    itemList.add(m)
                }
            }else{
                Log.d("UCE", response.toString())
            }
        }

        return itemList

    }

    suspend fun getAllMarvelCharDb() : List<MarvelChars>{

        var items: ArrayList<MarvelChars> = arrayListOf()
        val itemsAux = DispositivosMoviles.getDbInstance().marvelDao().getAllCharacters()
        itemsAux.forEach{
            items.add(
                it.getMarvelChars()
            )
        }

        return items
    }

    suspend fun insertMarvelCharstoDB(items: List<MarvelChars>){
        var itemsDB = arrayListOf<MarvelCharsDB>()
        items.forEach{
            itemsDB.add(it.getMarvelCharsDB())
        }

        DispositivosMoviles
            .getDbInstance()
            .marvelDao()
            .insertMarvelChar(itemsDB)


    }


}