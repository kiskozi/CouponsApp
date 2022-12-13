package com.webuni.android.advanced.coupons.ui.loginregistration.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.webuni.android.advanced.coupons.R
import com.webuni.android.advanced.coupons.databinding.FragmentLoginBinding
import com.webuni.android.advanced.coupons.ui.loginregistration.viewmodel.LoginRegistrationViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val loginRegistrationViewModel: LoginRegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_login, container, false
        )

        binding.logRegViewModel = loginRegistrationViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        loginRegistrationViewModel.emailError.observe(viewLifecycleOwner) {
            binding.tilEmail.error = it
        }

        loginRegistrationViewModel.passwordError.observe(viewLifecycleOwner) {
            binding.tilPassword.error = it
        }

        loginRegistrationViewModel.loginResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                this.findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToProvidersFragment()
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.login_failed),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.tvRegistration.setOnClickListener {
            this.findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
        }

        return binding.root
    }
}
