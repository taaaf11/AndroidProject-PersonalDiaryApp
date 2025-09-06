package com.example.personaldiaryapp

import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaldiaryapp.databinding.FragmentSearchBinding
import com.example.personaldiaryapp.room.DiaryEntry
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
        binding?.rvEntrysListFragmentSearch?.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding?.rvEntrysListFragmentSearch?.layoutManager = LinearLayoutManager(requireContext())

        adapter = DiaryAdapter(requireContext())
        binding?.rvEntrysListFragmentSearch?.adapter = adapter

        adapter?.setSearchingRequirements(viewModel, binding?.etSearchValueFragmentSearch)

        adapter?.viewDetailLambda = {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                it
            ))
        }

        adapter?.popupMenuShowerLambda = {
            registerForContextMenu(it)
            requireActivity().openContextMenu(it)
            true
        }
//        adapter?.deleteEntryLambda = {
//            viewModel?.deleteDiaryEntry(it)
//
//        }
//        adapter?.editEntryLambda = {
//            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToEditFragment(
//                it
//            ))
//        }

        viewModel?.readAllDiaryEntrys?.observe(viewLifecycleOwner) { notes ->
            adapter?.setData(notes)
        }
    }

    override fun onCreateContextMenu (menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo? ){
        val entry = v.getTag(R.id.theEntry) as DiaryEntry

        menu.add(Menu.NONE, MenuItems.EDIT, Menu.NONE, "Edit").setOnMenuItemClickListener {
            findNavController().navigate(EntrysFragmentDirections.actionEntrysFragmentToEditFragment(
                entry
            ))
            true
        }

        menu.add(Menu.NONE, MenuItems.DELETE, Menu.NONE, "Delete").setOnMenuItemClickListener {
            viewModel?.deleteDiaryEntry(entry)
            true
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