package com.example.personaldiaryapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.personaldiaryapp.databinding.FragmentDetailsBinding
import com.example.personaldiaryapp.room.DiaryVM

class DetailsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailsBinding? = null
    public val binding get() = _binding
    var viewModel: DiaryVM? = null
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DiaryVM::class)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {
        binding?.tvTitleValueFragmentDetails?.text = args.entryInstance.title
        binding?.tvContentValueFragmentDetails?.text = args.entryInstance.content
    }

    private fun registerClicks() {
        binding?.ivBackButtonFragmentDetails?.setOnClickListener(this)
        binding?.ivEditButtonFragmentDetails?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackButtonFragmentDetails -> {
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToEntrysFragment())
            }
            R.id.ivEditButton_fragment_details -> {
                findNavController().navigate(
                    DetailsFragmentDirections.actionDetailsFragmentToEditFragment(
                        args.entryInstance, 1
                    )
                )
            }
        }
    }
}