package com.webuni.android.advanced.coupons.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.webuni.android.advanced.coupons.data.Company
import com.webuni.android.advanced.coupons.data.Coupon
import com.webuni.android.advanced.coupons.data.User
import com.webuni.android.advanced.coupons.datasource.FireStoreDataSource

class FireStoreRepository {

    private val fireStoreDataSource = FireStoreDataSource()

    fun addUser(user: User) {
        fireStoreDataSource.addUserToDatabase(user)
    }

    fun getCurrentUser(uid: String): Task<DocumentSnapshot> {
        return fireStoreDataSource.getCurrentUser(uid)
    }

    fun getProvidersQuery(): Query {
        return fireStoreDataSource.getProvidersQuery()
    }

    fun getCouponsQuery(couponCompanyID: String): Query {
        return fireStoreDataSource.getCouponsQuery(couponCompanyID)
    }

    fun saveCompanyToDB(company: Company): Task<Void>{
        return fireStoreDataSource.saveCompanyToDatabase(company)
    }

    fun saveCouponToDB(coupon: Coupon): Task<Void>{
        return fireStoreDataSource.saveCouponToDatabase(coupon)
    }

    fun deleteCoupon(coupon: Coupon) {
        fireStoreDataSource.deleteCoupon(coupon)
    }

    fun deleteCompany(company: Company) {
        fireStoreDataSource.deleteCompany(company)
    }
}