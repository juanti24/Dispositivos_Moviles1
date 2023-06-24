package com.example.dispositivosmoviles.data.marvel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize // Con esta anotacion, el IDE genera los metodos necesarios
data class MarvelChars(
    var id: Int,
    var nombre: String,
    var comic: String,
    var imagen: String,
) : Parcelable
