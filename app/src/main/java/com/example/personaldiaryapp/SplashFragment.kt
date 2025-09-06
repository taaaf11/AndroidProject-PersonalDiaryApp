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

    // when i want to insert some data using @Query, i want it such that if a row already exists, it updates that row, and if the row does not exist, it
    private fun initialize() {
//        fun getColorFromResources(id: Int): Int {
//            return ContextCompat.getColor(requireContext(), id)
//        }
//
//        viewModel?.getThemeMode?.observe(viewLifecycleOwner) { isLightThemeInDb ->
//            if (isLightThemeInDb == null) {
//                viewModel?.setDefaultSettings()
//                Log.i("HERE", "Settin defaults")
//            }
//
//            val isLightTheme = isLightThemeInDb ?: true
//            if (isLightTheme) {
//                binding?.rootLayoutFragmentSplash?.setBackgroundColor(
//                    getColorFromResources(R.color.background)
//                )
//
//            }
//            else {
//                binding?.rootLayoutFragmentSplash?.setBackgroundColor(
//                    getColorFromResources(R.color.backgroundDark)
//                )
//            }
//        }
    }

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