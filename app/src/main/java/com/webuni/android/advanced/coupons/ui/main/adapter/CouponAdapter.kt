package com.webuni.android.advanced.coupons.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.webuni.android.advanced.coupons.R
import com.webuni.android.advanced.coupons.data.Coupon
import com.webuni.android.advanced.coupons.databinding.ListItemCouponBinding
import com.webuni.android.advanced.coupons.ui.main.viewmodel.MainViewModel

class CouponAdapter(options: FirestoreRecyclerOptions<Coupon>,
                    private val mainViewModel: MainViewModel
) : FirestoreRecyclerAdapter<Coupon, CouponAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ListItemCouponBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_coupon,
            parent,
            false)
        binding.adapter = this
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Coupon) {
        holder.binding.coupon = model
        if (model.uid == mainViewModel.currentUserID) {
            holder.binding.ivDeleteCoupon.visibility = View.VISIBLE
        } else {
            holder.binding.ivDeleteCoupon.visibility = View.GONE
        }

    }

    fun deleteCoupon(coupon: Coupon){
        mainViewModel.deleteCoupon(coupon)
    }

    class ViewHolder(val binding: ListItemCouponBinding) : RecyclerView.ViewHolder(binding.root)
}