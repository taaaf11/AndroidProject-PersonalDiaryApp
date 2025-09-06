package com.example.personaldiaryapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.personaldiaryapp.databinding.FragmentSplashBinding
import com.example.personaldiaryapp.room.DiaryVM


class SplashFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSplashBinding? = null
    var viewModel: DiaryVM? = null
    public val binding get() = _binding

    private var themeModeInDb: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DiaryVM::class)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {}

    private fun registerClicks() {
        binding?.btnGoFragmentSplash?.setOnClickListener(this)
        binding?.btnGoToAboutFragmentSplash?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnGoFragmentSplash -> {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
            R.id.btnGoToAboutFragmentSplash -> {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAboutFragment())
            }
        }
    }
}