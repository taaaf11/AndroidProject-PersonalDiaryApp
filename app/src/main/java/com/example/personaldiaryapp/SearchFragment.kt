package com.example.personaldiaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaldiaryapp.databinding.FragmentSearchBinding
import com.example.personaldiaryapp.room.DiaryVM

class SearchFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSearchBinding? = null
    val binding get() = _binding
    var viewModel: DiaryVM? = null
    private var adapter: DiaryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DiaryVM::class)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {
        binding?.rvEntrysListFragmentSearch?.layoutManager = LinearLayoutManager(requireContext())

        adapter = DiaryAdapter(requireContext())
        binding?.rvEntrysListFragmentSearch?.adapter = adapter

        adapter?.setSearchingRequirements(viewModel, binding?.etSearchValueFragmentSearch)

        adapter?.viewDetailLambda = {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                it
            ))
        }
        adapter?.deleteEntryLambda = {
            viewModel?.deleteDiaryEntry(it)

        }
        adapter?.editEntryLambda = {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToEditFragment(
                it
            ))
        }

        viewModel?.readAllDiaryEntrys?.observe(viewLifecycleOwner) { notes ->
            adapter?.setData(notes)
        }
    }

    private fun registerClicks() {
        binding?.ivBackButtonFragmentSearch?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackButtonFragmentSearch -> {
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToEntrysFragment())
            }
        }
    }
}