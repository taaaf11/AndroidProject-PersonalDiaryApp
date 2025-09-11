package com.example.personaldiaryapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.personaldiaryapp.databinding.FragmentAddBinding
import com.example.personaldiaryapp.room.DiaryEntry
import com.example.personaldiaryapp.room.DiaryVM
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date


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

    fun setContainerEnabled(container: ViewGroup, enabled: Boolean) {
        container.isEnabled = enabled
        container.alpha = if (enabled) 1f else 0.5f

        for (i in 0 until container.childCount) {
            val child = container.getChildAt(i)
            child.isEnabled = enabled

            // If the child is a ViewGroup, recursively disable its children
            if (child is ViewGroup) {
                setContainerEnabled(child, enabled)
            }
        }
    }

    fun fillSpinnerValues() {
//        val spinnerHours = binding?.timeGetterCard?.spinnerHours
//        val spinnerMinutes = binding?.timeGetterCard?.spinnerMinutes
//
//        val hours = (0..23).map { String.format("%02d", it) }
//        val minutes = (0..59).map { String.format("%02d", it) }
//
//        spinnerHours?.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, hours)
//        spinnerMinutes?.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, minutes)
        val amPmSpinner = binding?.timeGetterCard?.spinnerAmPm
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("AM", "PM")
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        amPmSpinner?.adapter = adapter
    }

    private fun initialize() {
        viewModel?.getUsername?.observe(viewLifecycleOwner) {
            usernameInDb = it ?: ""
        }

        fillSpinnerValues()

        val cbSchedule = binding?.timeGetterCard?.cbScheduleNotification
        val datetimeContainer = binding?.timeGetterCard?.dateTimeContainer

        setContainerEnabled(datetimeContainer as ViewGroup, false)

        // Checkbox toggle
        cbSchedule?.setOnCheckedChangeListener { _, isChecked ->
            setContainerEnabled(datetimeContainer as ViewGroup, isChecked)
            datetimeContainer.alpha = if (isChecked) 1f else 0.5f
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
                    val diaryEntry = DiaryEntry(
                        0,
                        titleValue,
                        contentValue,
                    )

                    if (binding?.timeGetterCard?.cbScheduleNotification?.isChecked == true) {
                        val day = binding?.timeGetterCard?.etDay?.text.toString().toIntOrNull() ?: -1
                        val month = binding?.timeGetterCard?.etMonth?.text.toString().toIntOrNull() ?: -1

                        var hour = binding?.timeGetterCard?.etHours?.text.toString().toIntOrNull() ?: 0
                        val minute = binding?.timeGetterCard?.etMins?.text.toString().toIntOrNull() ?: 0

                        if (binding?.timeGetterCard?.spinnerAmPm?.selectedItem!!.toString() == "PM") {
                            hour += 12
                        }

                        Log.i("HERE", "Values: $month, $day, $hour, $minute")
                        scheduleNotification(
                            requireContext(),
                            titleValue,
                            contentValue,
                            month=month,
                            day=day,
                            hour=hour,
                            minute=minute,
                            entry=diaryEntry,
                        )
                    }

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