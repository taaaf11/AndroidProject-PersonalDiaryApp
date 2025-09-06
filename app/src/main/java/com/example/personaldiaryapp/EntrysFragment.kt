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
import com.example.personaldiaryapp.databinding.FragmentEntrysBinding
import com.example.personaldiaryapp.room.DiaryVM


class EntrysFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentEntrysBinding? = null
    public val binding get() = _binding
    var viewModel: DiaryVM? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEntrysBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DiaryVM::class)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {
        binding?.rvEntrysList?.layoutManager = LinearLayoutManager(requireContext())
        val adapter = DiaryAdapter(requireContext())
        binding?.rvEntrysList?.adapter = adapter

        adapter.viewDetailLambda = {
            findNavController().navigate(EntrysFragmentDirections.actionEntrysFragmentToDetailsFragment(
                it
            ))
        }
        adapter.deleteEntryLambda = {
            viewModel?.deleteDiaryEntry(it)

        }
        adapter.editEntryLambda = {
            findNavController().navigate(EntrysFragmentDirections.actionEntrysFragmentToEditFragment(
                it
            ))
        }

        viewModel?.readAllDiaryEntrys?.observe(viewLifecycleOwner) { notes ->
            adapter.setData(notes)
        }
    }

    private fun registerClicks() {
        binding?.ivBackButtonFragmentEntrys?.setOnClickListener(this)
        binding?.ivSearchButtonFragmentEntrys?.setOnClickListener(this)
        binding?.btnAddEntryFragmentEntrys?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackButtonFragmentEntrys -> {
                findNavController().navigate(EntrysFragmentDirections.actionEntrysFragmentToHomeFragment())
            }
            R.id.ivSearchButtonFragmentEntrys -> {
                findNavController().navigate(EntrysFragmentDirections.actionEntrysFragmentToSearchFragment())
            }
            R.id.btnAddEntry_fragment_entrys -> {
                findNavController().navigate(EntrysFragmentDirections.actionEntrysFragmentToAddFragment())
            }
        }
    }
}