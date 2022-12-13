package com.webuni.android.advanced.coupons.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.webuni.android.advanced.coupons.R
import com.webuni.android.advanced.coupons.data.Company
import com.webuni.android.advanced.coupons.databinding.ListItemProviderBinding
import com.webuni.android.advanced.coupons.ui.main.fragment.ProvidersFragmentDirections
import com.webuni.android.advanced.coupons.ui.main.viewmodel.MainViewModel

class ProviderAdapter(
    private val navController: NavController,
    options: FirestoreRecyclerOptions<Company>,
    private val mainViewModel: MainViewModel
) : FirestoreRecyclerAdapter<Company, ProviderAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemProviderBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_item_provider,
            parent,
            false
        )
        binding.adapter = this
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Company) {
        holder.binding.company = model
        if (model.uid == mainViewModel.currentUserID) {
            holder.binding.ivDeleteCoupon.visibility = View.VISIBLE
        } else {
            holder.binding.ivDeleteCoupon.visibility = View.GONE
        }
    }

    fun showCoupons(company: Company) {
        navController.navigate(
            ProvidersFragmentDirections
                .actionProvidersFragmentToCouponsFragment(company.uid, company.companyID)
        )
    }

    fun deleteCompany(company: Company){
        mainViewModel.deleteCompany(company)
    }

    class ViewHolder (val binding: ListItemProviderBinding) : RecyclerView.ViewHolder(binding.root)
}