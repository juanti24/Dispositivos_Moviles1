package com.example.dispositivosmoviles.data.entities

// De esta forma ya tiene un admin y un password designado
data class LoginUser(val name: String = "root",
                     val pass: String = "root"
)