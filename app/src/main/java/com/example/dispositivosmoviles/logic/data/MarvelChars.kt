package com.example.dispositivosmoviles.logic.data

import android.os.Parcelable
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB
import kotlinx.parcelize.Parcelize

@Parcelize // Con esta anotacion, el IDE genera los metodos necesarios
data class MarvelChars(
    var id: Int,
    var nombre: String,
    var comic: String,
    var imagen: String,
) : Parcelable

fun MarvelChars.getMarvelCharsDB(): MarvelCharsDB {
    return MarvelCharsDB(
        id,
        nombre,
        comic,
        imagen
    )
}

