package com.example.personaldiaryapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.personaldiaryapp.databinding.FragmentLoginBinding
import com.example.personaldiaryapp.databinding.FragmentSplashBinding
import com.example.personaldiaryapp.room.DiaryVM


class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    public val binding get() = _binding
    var viewModel: DiaryVM? = null

    private var usernameInDb: String? = null
    private var passwordInDb: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DiaryVM::class)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {
        var valuesAreNull: Boolean = false
        viewModel?.getUsername?.observe(viewLifecycleOwner) {
            if (it == null) {
                valuesAreNull = true

                Log.i("Null check", "We are nulll credentials")
                viewModel?.setDefaultSettings()
            }
            usernameInDb = it ?: ""
        }
        viewModel?.getPassword?.observe(viewLifecycleOwner) {
            passwordInDb = it ?: ""
        }
    }

    private fun registerClicks() {
        binding?.btnLoginFragmentLogin?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin_fragment_login -> {
                val usernameValue = binding?.etUsernameFragmentLogin?.text.toString()
                val passwordValue = binding?.etPasswordFragmentLogin?.text.toString()

                if (usernameValue == usernameInDb && passwordValue == passwordInDb) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
//                    viewModel?.setLoggedInStatus(true)
                }

                else{
                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}