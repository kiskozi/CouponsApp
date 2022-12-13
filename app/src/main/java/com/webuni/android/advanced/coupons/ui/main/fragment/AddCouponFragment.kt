package com.webuni.android.advanced.coupons.ui.main.fragment

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
import com.webuni.android.advanced.coupons.databinding.FragmentAddCouponBinding
import com.webuni.android.advanced.coupons.ui.main.viewmodel.MainViewModel

class AddCouponFragment : Fragment() {

    private lateinit var binding: FragmentAddCouponBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_add_coupon, container, false
        )
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        mainViewModel.couponDescriptionError.observe(viewLifecycleOwner) {
            binding.tilDescription.error = it
        }

        mainViewModel.couponDiscountError.observe(viewLifecycleOwner) {
            binding.tilDiscount.error = it
        }

        mainViewModel.couponAvailabilityError.observe(viewLifecycleOwner) {
            binding.tilAvailability.error = it
        }

        mainViewModel.setCouponCoID(
            AddCouponFragmentArgs.fromBundle(requireArguments()).couponCoID
        )

        mainViewModel.saveCouponResult.observe(viewLifecycleOwner) {
            if (it) {
                this.findNavController().navigate(
                    AddCouponFragmentDirections
                        .actionAddCouponFragmentToCouponsFragment(
                            mainViewModel.currentUserID,
                            mainViewModel.couponCompanyID!!
                        )
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.add_coupon_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return binding.root
    }
}