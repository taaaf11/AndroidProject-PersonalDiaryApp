package com.example.personaldiaryapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaldiaryapp.databinding.FragmentHomeBinding
import com.example.personaldiaryapp.room.DiaryVM

class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    public val binding get() = _binding
    var viewModel: DiaryVM? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DiaryVM::class)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {
    }

    private fun registerClicks() {
        binding?.btnCheckEntriesFragmentHome?.setOnClickListener(this)
        binding?.btnSettingsFragmentHome?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCheckEntriesFragmentHome -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEntrysFragment())
            }
            R.id.btnSettingsFragmentHome -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
            }
        }
    }
}