package com.example.dispositivosmoviles.data.entities.marvel.characters.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dispositivosmoviles.logic.data.MarvelChars
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MarvelCharsDB(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var nombre: String,
    var comic: String,
    var imagen: String,
) : Parcelable

fun MarvelCharsDB.getMarvelChars(): MarvelChars {
    return MarvelChars(
        id,
        nombre,
        comic,
        imagen
    )
}

