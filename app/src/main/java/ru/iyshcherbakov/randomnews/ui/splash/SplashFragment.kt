package ru.iyshcherbakov.randomnews.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import ru.iyshcherbakov.randomnews.R
import ru.iyshcherbakov.randomnews.databinding.FragmentDetailsBinding
import ru.iyshcherbakov.randomnews.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity)
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

}