package com.example.dispositivosmoviles.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransitionImpl
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.FragmentSecondBinding
import com.example.dispositivosmoviles.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {

    private lateinit var binding : FragmentThirdBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentThirdBinding.inflate(
            layoutInflater, container, false
        )

        // Inflate the layout for this fragment
        return binding.root

    }

}