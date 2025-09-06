package com.example.personaldiaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.personaldiaryapp.databinding.FragmentEditBinding
import com.example.personaldiaryapp.room.DiaryEntry
import com.example.personaldiaryapp.room.DiaryVM

class EditFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentEditBinding? = null
    public val binding get() = _binding
    var viewModel: DiaryVM? = null
    private val args: EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DiaryVM::class)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {
        binding?.etTitleFragmentEdit?.setText( args.entryInstance.title)
        binding?.etContentFragmentEdit?.setText(args.entryInstance.content)
    }

    private fun registerClicks() {
        binding?.ivBackButtonFragmentEdit?.setOnClickListener(this)
        binding?.btnUpdateEntryFragmentEdit?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackButtonFragmentEdit -> {
                findNavController().navigate(EditFragmentDirections.actionEditFragmentToEntrysFragment())
            }
            R.id.btnUpdateEntryFragmentEdit -> {
                val titleValue = binding?.etTitleFragmentEdit?.text.toString()
                val contentValue = binding?.etContentFragmentEdit?.text.toString()

                if (titleValue.isNotEmpty() &&
                    contentValue.isNotEmpty()
                )
                {
                    val editedEntry = args.entryInstance.copy(
                        title = titleValue,
                        content = contentValue
                    )
                    viewModel?.updateDiaryEntry(
                        editedEntry
                    )

                    findNavController().navigate(EditFragmentDirections.actionEditFragmentToEntrysFragment())
                }

                else {
                    Toast.makeText(context, "Empty fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}