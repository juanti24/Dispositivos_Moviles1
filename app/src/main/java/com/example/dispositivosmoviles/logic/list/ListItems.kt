package com.example.dispositivosmoviles.logic.list

import com.example.dispositivosmoviles.R

data class MarvelChars(var id: Int, var nombre: String, var comic: String, var imagen: String) {
    override fun toString(): String {
        return "$nombre aparece en el comic $comic"
    }
}

class ListItems {

    fun returnMarvelList(): List<MarvelChars> {
        val items = listOf(
            MarvelChars(
                1,
                "Spiderman",
                "The Amazing Spiderman (1963)",
                "https://comicvine.gamespot.com/a/uploads/square_medium/11170/111705043/8843794-neverready.jpg"
            ),
            MarvelChars(
                2,
                "Daredevil",
                "Daredevil (1964)",
                "https://comicvine.gamespot.com/a/uploads/square_medium/6/63459/5595347-6645510049-f1ae9.jpg"
            ),
            MarvelChars(
                3,
                "Punisher",
                "The Punisher (1987)",
                "https://comicvine.gamespot.com/a/uploads/square_medium/14/147237/7927302-rco011_1583587732.jpg"
            ),
            MarvelChars(
                4,
                "Deadpool",
                "Deadpool (1997)",
                "https://comicvine.gamespot.com/a/uploads/square_medium/11131/111317321/5939443-5.jpg"
            ), MarvelChars(
                5,
                "Mr. Fantastic",
                "Fantastic Four (1961)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11112/111123579/7316596-fantastic_four_vol_6_1_mr._fantastic_variant_textless.jpg"
            )

        )
        return items
    }

}