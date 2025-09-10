package com.example.personaldiaryapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.personaldiaryapp.databinding.FragmentAddBinding
import com.example.personaldiaryapp.room.DiaryEntry
import com.example.personaldiaryapp.room.DiaryVM
import java.time.LocalDateTime
import java.time.ZoneId


class AddFragment : Fragment(), View.OnClickListener {

    private val args: AddFragmentArgs by navArgs()
    private var _binding: FragmentAddBinding? = null
    public val binding get() = _binding
    var viewModel: DiaryVM? = null
    var usernameInDb: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DiaryVM::class)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {
        viewModel?.getUsername?.observe(viewLifecycleOwner) {
            usernameInDb = it ?: ""
        }
    }

    private fun registerClicks() {
        binding?.ivBackButtonFragmentAdd?.setOnClickListener(this)
        binding?.btnAddEntryFragmentAdd?.setOnClickListener(this)
        binding?.includedCard?.tvbtnBoldFragmentEdit?.setOnClickListener {

            val theEditText = binding?.etContentFragmentAdd!!
            if (!(theEditText.isFocused)) {
                // This EditText has focus (cursor should be here)
                return@setOnClickListener
            }
            theEditText.setText(theEditText.text.toString() + "****")
            theEditText.setSelection((theEditText.text?.length ?: 2) - 2)
        }
        binding?.includedCard?.tvbtnItalicFragmentEdit?.setOnClickListener {

            val theEditText = binding?.etContentFragmentAdd!!
            if (!(theEditText.isFocused)) {
                // This EditText has focus (cursor should be here)
                return@setOnClickListener
            }
            theEditText.setText(theEditText.text.toString() + "__")
            theEditText.setSelection(theEditText.text?.length ?: 1 - 1)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackButtonFragmentAdd -> {
                if (args.fromHome == 1) {
                    findNavController().navigate(AddFragmentDirections.actionAddFragmentToHomeFragment(usernameInDb!!))
                }
                else {
                    findNavController().navigate(AddFragmentDirections.actionAddFragmentToEntrysFragment())
                }
            }
            R.id.btnAddEntryFragmentAdd -> {

                val titleValue = binding?.etTitleFragmentAdd?.text.toString()
                val contentValue = binding?.etContentFragmentAdd?.text.toString()

                if (titleValue.isNotEmpty() &&
                    contentValue.isNotEmpty()
                    )
                {
                    viewModel?.addDiaryEntry(
                        DiaryEntry(
                            0,
                            titleValue,
                            contentValue,
                        )
                    )

                    findNavController().navigate(AddFragmentDirections.actionAddFragmentToEntrysFragment())
                }

                else {
                    Toast.makeText(context, "Empty fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}