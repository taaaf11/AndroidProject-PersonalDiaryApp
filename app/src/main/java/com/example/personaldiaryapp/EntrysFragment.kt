package com.example.personaldiaryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
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

    private fun initialize() {
        val adapter = DiaryAdapter(requireContext())
        binding?.rvEntrysList?.adapter = adapter
        binding?.rvEntrysList?.layoutManager = LinearLayoutManager(requireContext())

        // source: https://stackoverflow.com/a/57886251/19619895
//        binding?.rvEntrysList?.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        // this is called when the view i.e. the cardView is clicked
        // this will be called inside the setOnClickListener of the cardView
        adapter.viewDetailLambda = {
            findNavController().navigate(
                EntrysFragmentDirections.actionEntrysFragmentToDetailsFragment(it)
            )
        }

        // this is called when the three dots button is clicked
        // this will be called inside the setOnClickListener of the three dots buttons
        // is clicked
        adapter.popupMenuShowerLambda = {
            showPopup(it)
            true
        }

        // this block creates an "observer", meaning that whenever
        // the dataset changes, this block will be called. (verified using logging)
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

    // source: https://stackoverflow.com/a/48628108/19619895
    private fun showPopup(view: View) {
        // popup menu ka background color or menu options ke text color ko
        // change karnay ke lie ContextThemeWrapper istamal kia hai
        // R.style.CustomPopUpStyle men colors given hain
        // is ka sourec:
        // https://stackoverflow.com/a/70210405/19619895
        val wrapper = ContextThemeWrapper(context, R.style.CustomPopUpStyle)
        val popup = PopupMenu(wrapper, view)
        popup.inflate(R.menu.card_view_popup_menu)

        // ham ne DiaryAdapter wali class men, onBindViewHolder
        // walay function men, vertical-three-dots walay component
        // par setTag ke zariya ham ne is component
        // men wo specific DiaryEntry ki instance store ki thi

        // ab ham us instance ko hasil kar rahay hain, id ke zariye
        // `as` wala keyword type casting kar raha hai
        // kionke getTag wala function Any type ka object
        // return karta hai. ab hamen pata hai ke wo object
        // DiaryEntry ka object hai, is lie ham is ko
        // safely to-DiaryEntry type-cast kar saktay hain
        val entry = view.getTag(R.id.theEntry) as DiaryEntry

        // ye function tab call ho ga jab menu men se koi
        // item (koi option) select / click ki jaye gi
        popup.setOnMenuItemClickListener { item: MenuItem? ->

            // ye item ki id ko check karta hai
            when (item!!.itemId) {
                R.id.menuitemEditCardViewPopMenu -> {
                    findNavController().navigate(
                        EntrysFragmentDirections.actionEntrysFragmentToEditFragment(entry)
                    )
                }

                R.id.menuitemDeleteCardViewPopMenu -> {
                    viewModel?.deleteDiaryEntry(entry)
                }
            }

            true
        }

        popup.show()
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