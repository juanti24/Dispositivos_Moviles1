package com.example.dispositivosmoviles.ui.utilities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentsManager {

    fun replaceFragment(manager: FragmentManager, container: Int, fragment: Fragment){
        // con este objeto hacer estas operaciones
        // De esta forma no tenemos que instanciarlo
        with(manager.beginTransaction()){
            replace(container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    fun addFragment(manager: FragmentManager, container: Int, fragment: Fragment){
        // con este objeto hacer estas operaciones
        // De esta forma no tenemos que instanciarlo
        with(manager.beginTransaction()){
            add(container, fragment)
            addToBackStack(null)
            commit()
        }
    }

}