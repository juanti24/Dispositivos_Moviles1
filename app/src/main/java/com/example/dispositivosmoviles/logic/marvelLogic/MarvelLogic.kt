package com.example.dispositivosmoviles.logic.marvelLogic

import android.util.Log
import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndpoint
import com.example.dispositivosmoviles.data.entities.marvel.characters.getMarvelChars
import com.example.dispositivosmoviles.logic.data.MarvelChars

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


}