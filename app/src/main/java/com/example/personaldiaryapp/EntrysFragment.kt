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
import com.example.personaldiaryapp.databinding.FragmentEntrysBinding
import com.example.personaldiaryapp.room.DiaryEntry
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

    private fun initialize() {
        binding?.rvEntrysList?.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding?.rvEntrysList?.layoutManager = LinearLayoutManager(requireContext())
        val adapter = DiaryAdapter(requireContext())
        binding?.rvEntrysList?.adapter = adapter

        adapter.viewDetailLambda = {
            findNavController().navigate(EntrysFragmentDirections.actionEntrysFragmentToDetailsFragment(
                it
            ))
        }

        adapter.popupMenuShowerLambda = {
            registerForContextMenu(it)
            requireActivity().openContextMenu(it)
            true
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