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

     // source: https://stackoverflow.com/a/44384149/19619895
     override fun onCreateContextMenu (menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo? ){
        val entry = v.getTag(R.id.theEntry) as DiaryEntry

        menu.add(Menu.NONE, CardViewContextMenuItems.EDIT, Menu.NONE, "Edit").setOnMenuItemClickListener {
            findNavController().navigate(
                EntrysFragmentDirections.actionEntrysFragmentToEditFragment(entry)
            )
            true
        }

        menu.add(Menu.NONE, CardViewContextMenuItems.DELETE, Menu.NONE, "Delete").setOnMenuItemClickListener {
            viewModel?.deleteDiaryEntry(entry)
            true
        }
    }

    private fun initialize() {
        val adapter = DiaryAdapter(requireContext())
        binding?.rvEntrysList?.adapter = adapter
        binding?.rvEntrysList?.layoutManager = LinearLayoutManager(requireContext())

        // source: https://stackoverflow.com/a/57886251/19619895
        binding?.rvEntrysList?.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        // this is called when the view i.e. the cardView is clicked
        // this will be called inside the setOnClickListener of the cardView
        adapter.viewDetailLambda = {
            findNavController().navigate(
                EntrysFragmentDirections.actionEntrysFragmentToDetailsFragment(it)
            )
        }

        // this is called when the view i.e. the cardView is long-clicked
        // this will be called inside the setOnLongClickListener of the cardView
        adapter.popupMenuShowerLambda = {
            registerForContextMenu(it)
            requireActivity().openContextMenu(it)
            true
        }

        // this block creates an "observer", meaning that whenever
        // the dataset changes, this block will be called.
        // inside the block, we are calling adapter?.setData(notes)
        // the setData function calls the notifyDataSetChanged ,
        // which then rebuilds the recyclerView's list.
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
                findNavController().navigate(
                    EntrysFragmentDirections.actionEntrysFragmentToHomeFragment()
                )
            }
            R.id.ivSearchButtonFragmentEntrys -> {
                findNavController().navigate(
                    EntrysFragmentDirections.actionEntrysFragmentToSearchFragment()
                )
            }
            R.id.btnAddEntry_fragment_entrys -> {
                findNavController().navigate(
                    EntrysFragmentDirections.actionEntrysFragmentToAddFragment()
                )
            }
        }
    }
}