package com.webuni.android.advanced.coupons.ui.loginregistration.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.webuni.android.advanced.coupons.R
import com.webuni.android.advanced.coupons.databinding.FragmentRegistrationBinding
import com.webuni.android.advanced.coupons.ui.loginregistration.viewmodel.LoginRegistrationViewModel

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    private val loginRegistrationViewModel: LoginRegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_registration, container, false
        )

        binding.logRegViewModel = loginRegistrationViewModel

        loginRegistrationViewModel.nameError.observe(viewLifecycleOwner) {
            binding.tilRegName.error = it
        }

        loginRegistrationViewModel.emailError.observe(viewLifecycleOwner) {
            binding.tilRegEmail.error = it
        }

        loginRegistrationViewModel.passwordError.observe(viewLifecycleOwner) {
            binding.tilRegPassword.error = it
        }

        loginRegistrationViewModel.confirmPasswordError.observe(viewLifecycleOwner) {
            binding.tilRegConfirmPassword.error = it
        }

        loginRegistrationViewModel.registrationResult.observe(viewLifecycleOwner) { isSuccess ->
            if (!isSuccess) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.registration_failed),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        loginRegistrationViewModel.loginResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                this.findNavController().navigate(
                    RegistrationFragmentDirections.actionRegistrationFragmentToProvidersFragment()
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.login_failed),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return binding.root
    }
}
