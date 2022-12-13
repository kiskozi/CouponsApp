package com.webuni.android.advanced.coupons.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.webuni.android.advanced.coupons.R
import com.webuni.android.advanced.coupons.data.Company
import com.webuni.android.advanced.coupons.databinding.FragmentProvidersBinding
import com.webuni.android.advanced.coupons.ui.main.adapter.ProviderAdapter
import com.webuni.android.advanced.coupons.ui.main.viewmodel.MainViewModel

class ProvidersFragment : Fragment() {

    private lateinit var binding: FragmentProvidersBinding

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var providerAdapter: ProviderAdapter

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_providers, container, false
        )

        binding.viewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        navController = this.findNavController()

        initAdapter()

        mainViewModel.eventAddCompanyBtnPressed.observe(viewLifecycleOwner) {
            if (it) {
                navController.navigate(
                    ProvidersFragmentDirections
                        .actionProvidersFragmentToAddProviderFragment()
                )
                mainViewModel.onAddCompanyCompleted()
            }
        }

        return binding.root
    }

    private fun initAdapter() {

        val fireStoreRecyclerOptions = FirestoreRecyclerOptions.Builder<Company>()
            .setQuery(mainViewModel.providerListQuery, Company::class.java)
            .setLifecycleOwner(viewLifecycleOwner)
            .build()

        providerAdapter = ProviderAdapter(navController, fireStoreRecyclerOptions, mainViewModel)
        binding.recyclerProviderItem.adapter = providerAdapter
    }
}