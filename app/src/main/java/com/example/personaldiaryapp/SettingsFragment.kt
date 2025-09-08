package com.example.personaldiaryapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.personaldiaryapp.databinding.FragmentSettingsBinding
import com.example.personaldiaryapp.room.DiaryVM

class SettingsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSettingsBinding? = null
    public val binding get() = _binding
    var viewModel: DiaryVM? = null

    private var usernameInDb: String? = null
    private var passwordInDb: String? = null
    private var themeModeInDb: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
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
            usernameInDb = it
        }
        viewModel?.getPassword?.observe(viewLifecycleOwner) {
            passwordInDb = it
        }
        Log.i("HERE", "Username: $usernameInDb")
        Log.i("HERE", "Password: $passwordInDb")
    }

    private fun registerClicks() {
        binding?.ivBackButtonFragmentSettings?.setOnClickListener(this)
        binding?.btnSetCredentialsFragmentSettings?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackButtonFragmentSettings -> {
                findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToHomeFragment())
            }
            R.id.btnSetCredentialsFragmentSettings -> {
                val usernameValue = binding?.etUsernameFragmentSettings?.text.toString()
                val passwordValue = binding?.etPasswordFragmentSettings?.text.toString()

                if (usernameInDb == null && passwordInDb == null) {
                    viewModel?.createCredentials(usernameValue, passwordValue)
                }
                else {
                    viewModel?.setUsername(usernameValue)
                    viewModel?.setPassword(passwordValue)
                }

                findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToHomeFragment())
            }
        }
    }
}