package com.webuni.android.advanced.coupons.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.webuni.android.advanced.coupons.data.Company
import com.webuni.android.advanced.coupons.data.Coupon
import com.webuni.android.advanced.coupons.data.User

class FireStoreDataSource {

    companion object {
        private const val COLLECTION_USERS = "users"
        private const val COLLECTION_COMPANIES = "companies"
        private const val COLLECTION_COUPONS = "coupons"
    }

    private val usersCollection = FirebaseFirestore.getInstance().collection(COLLECTION_USERS)
    private val companiesCollection = FirebaseFirestore.getInstance().collection(COLLECTION_COMPANIES)
    private val couponsCollection = FirebaseFirestore.getInstance().collection(COLLECTION_COUPONS)

    fun addUserToDatabase(user: User) {
        usersCollection.document(user.uid).set(user)
    }

    fun getCurrentUser(uid: String): Task<DocumentSnapshot> {
        return usersCollection.document(uid).get()
    }

    fun getProvidersQuery() : Query {
        return companiesCollection.orderBy("name")
    }

    fun getCouponsQuery(couponCompanyID: String) : Query {
        return couponsCollection.whereEqualTo("companyID", couponCompanyID)
    }

    fun saveCompanyToDatabase(company: Company): Task<Void> {
        return companiesCollection.document(company.companyID).set(company)
    }

    fun saveCouponToDatabase(coupon: Coupon): Task<Void> {
        return couponsCollection.document(coupon.cid).set(coupon)
    }

    fun deleteCoupon(coupon: Coupon) {
        couponsCollection.document(coupon.cid).delete()
    }

    fun deleteCompany(company: Company) {
        getCouponsQuery(company.companyID).get().addOnSuccessListener { documents ->
            for (document in documents){
                couponsCollection.document(document.id).delete()
            }
        }
        companiesCollection.document(company.companyID).delete()
    }
}
