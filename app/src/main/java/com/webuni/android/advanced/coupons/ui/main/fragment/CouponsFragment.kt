package com.webuni.android.advanced.coupons.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.webuni.android.advanced.coupons.R
import com.webuni.android.advanced.coupons.data.Coupon
import com.webuni.android.advanced.coupons.databinding.FragmentCouponsBinding
import com.webuni.android.advanced.coupons.ui.main.adapter.CouponAdapter
import com.webuni.android.advanced.coupons.ui.main.viewmodel.MainViewModel

class CouponsFragment : Fragment() {

    private lateinit var binding: FragmentCouponsBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_coupons, container, false
        )
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        mainViewModel.setCouponUserID(
            CouponsFragmentArgs.fromBundle(requireArguments()).providerUserID
        )

        mainViewModel.setCouponListQuery(
            CouponsFragmentArgs.fromBundle(requireArguments()).couponCoID
        )

        mainViewModel.setAddCouponVisibility()

        initAdapter()

        mainViewModel.eventAddCouponBtnPressed.observe(viewLifecycleOwner) {
            if (it) {
                this.findNavController().navigate(
                    CouponsFragmentDirections
                        .actionCouponsFragmentToAddCouponFragment(mainViewModel.couponCompanyID!!)
                )
                mainViewModel.onAddCouponCompleted()
            }
        }

        return binding.root
    }

    private fun initAdapter() {

        val fireStoreRecyclerOptions = FirestoreRecyclerOptions.Builder<Coupon>()
            .setQuery(mainViewModel.couponListQuery, Coupon::class.java)
            .setLifecycleOwner(viewLifecycleOwner)
            .build()

        val couponAdapter = CouponAdapter(fireStoreRecyclerOptions, mainViewModel)
        binding.recyclerCouponItem.adapter = couponAdapter
    }
}