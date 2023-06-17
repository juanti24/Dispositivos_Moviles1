package com.example.dispositivosmoviles.logic.lists

import com.example.dispositivosmoviles.data.marvel.MarvelChars
import com.example.dispositivosmoviles.logic.entities.LoginUser

class ListItems {

    fun returnitems(): List<LoginUser> {
        var items = listOf<LoginUser>(

            LoginUser("1", "1"),
            LoginUser("2", "1"),
            LoginUser("3", "1"),
            LoginUser("4", "1"),
            LoginUser("5", "1")


        )
        return items
    }

    fun returnMarvelChars(): List<MarvelChars> {
        val items = listOf(
            MarvelChars(
                1,
                "Deadpool",
                "Avengers vs X-Men",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8926324-large-2680196.jpg"
            ),
            MarvelChars(
                2,
                "Captian America",
                "Captain America lives...or dies.",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8459983-rco031_1650495781.jpg"
            ),
            MarvelChars(
                3,
                "Thor",
                "Avengers vs X-Men",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8926324-large-2680196.jpg"
            ),
            MarvelChars(
                4,
                "Iceman",
                "Gli Incredibili X-Men",
                "https://comicvine.gamespot.com/a/uploads/scale_small/1/14487/8582303-6562c2d0-2069-4a20-af02-4861621c0b9a.jpeg"
            ),
            MarvelChars(
                5,
                "Professor X",
                "X-Men Vol 5 ",
                "https://comicvine.gamespot.com/a/uploads/scale_small/10/100647/7261595-hox1pichelli.jpg"
            )


        )
        return items
    }

}