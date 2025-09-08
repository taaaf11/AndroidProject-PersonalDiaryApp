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
import com.example.personaldiaryapp.databinding.LoginCardBinding
import com.example.personaldiaryapp.room.DiaryVM
import kotlin.math.log


class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private var _loginCardBinding: LoginCardBinding? = null
    public val binding get() = _binding
    val loginCardBinding get() = _loginCardBinding
    var viewModel: DiaryVM? = null

    private var usernameInDb: String? = null
    private var passwordInDb: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        _loginCardBinding = LoginCardBinding.inflate(inflater, container, false)
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
//        binding?.btnLoginFragmentLogin?.setOnClickListener(this)
        binding?.includedCard?.btnLoginLoginCard?.setOnClickListener(this)
//        binding?.btnSignupFragmentLogin?.setOnClickListener(this)
//        loginCardBinding?.btnLoginLoginCard?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin_login_card -> {
                Log.i("HERE", "login card login called")
//                val usernameValue = binding?.etUsernameFragmentLogin?.text.toString()
//                val passwordValue = binding?.etPasswordFragmentLogin?.text.toString()
                val usernameValue = binding?.includedCard?.etUsernameLoginCard?.text.toString()
                val passwordValue = binding?.includedCard?.etPasswordLoginCard?.text.toString()

                if (usernameValue == usernameInDb && passwordValue == passwordInDb) {
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment(usernameValue)
                    )

//                    viewModel?.setLoggedInStatus(true)
                }

                else{
                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }
//            R.id.btnSignup_fragment_login -> {
//                findNavController().navigate(
//                    LoginFragmentDirections.actionLoginFragmentToSignupFragment()
//                )
//            }
        }
    }
}