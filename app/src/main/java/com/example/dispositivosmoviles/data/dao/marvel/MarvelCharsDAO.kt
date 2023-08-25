package com.example.dispositivosmoviles.data.dao.marvel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB

@Dao
interface MarvelCharsDAO {

    @Query("select * from MarvelCharsDB")
    fun getAllCharacters(): List<MarvelCharsDB>

    // con los : podemos acceder a los parametros del metodo en el query
    @Query("select * from MarvelCharsDB where id=:id")
    fun getOneCharacter(id: Int): MarvelCharsDB

    @Insert
    fun insertMarvelChars(ch: List<MarvelCharsDB>)

    /*
        @Delete
        @Update
     */

}