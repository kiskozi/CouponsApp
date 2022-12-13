package com.webuni.android.advanced.coupons.ui.main.fragment

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
import com.webuni.android.advanced.coupons.databinding.FragmentAddProviderBinding
import com.webuni.android.advanced.coupons.ui.main.viewmodel.MainViewModel

class AddProviderFragment : Fragment() {

    private lateinit var binding: FragmentAddProviderBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_provider, container, false
        )

        binding.viewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        mainViewModel.companyNameError.observe(viewLifecycleOwner) {
            binding.tilCompanyName.error = it
        }

        mainViewModel.companyAddressError.observe(viewLifecycleOwner) {
            binding.tilCompanyAddress.error = it
        }

        mainViewModel.saveCompanyResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                this.findNavController().navigate(
                    AddProviderFragmentDirections.actionAddProviderFragmentToProvidersFragment()
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.add_company_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return binding.root
    }

}