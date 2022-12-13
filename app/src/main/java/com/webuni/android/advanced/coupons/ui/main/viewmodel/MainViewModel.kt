package com.webuni.android.advanced.coupons.ui.main.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import com.webuni.android.advanced.coupons.R
import com.webuni.android.advanced.coupons.data.Company
import com.webuni.android.advanced.coupons.data.Coupon
import com.webuni.android.advanced.coupons.data.User
import com.webuni.android.advanced.coupons.repository.AuthenticationRepository
import com.webuni.android.advanced.coupons.repository.FireStoreRepository
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val FIELD_EMPTY_ERROR = getApplication<Application>()
        .resources
        .getString(R.string.field_empty_error)

    private val authenticationRepository = AuthenticationRepository()
    private val fireStoreRepository = FireStoreRepository()

    private var currentUser: User? = null

    val currentUserID = authenticationRepository.getLoggedInUserID()

//    providersFragment staff
    private val _btnAddCompanyVisibility = MutableLiveData<Int>()
    val btnAddCompanyVisibility: LiveData<Int> get() = _btnAddCompanyVisibility

    private val _eventAddCompanyBtnPressed = MutableLiveData<Boolean>()
    val eventAddCompanyBtnPressed: LiveData<Boolean> get() = _eventAddCompanyBtnPressed

    val providerListQuery: Query

//    addProviderFragment staff
    private val _companyNameError = MutableLiveData<String>()
    val companyNameError: LiveData<String> get() = _companyNameError

    private val _companyAddressError = MutableLiveData<String>()
    val companyAddressError: LiveData<String> get() = _companyAddressError

    private val _saveCompanyResult = MutableLiveData<Boolean>()
    val saveCompanyResult: LiveData<Boolean> get() = _saveCompanyResult

//    couponsFragment staff
    private val _btnAddCouponVisibility = MutableLiveData<Int>()
    val btnAddCouponVisibility: LiveData<Int> get() = _btnAddCouponVisibility

    private val _eventAddCouponBtnPressed = MutableLiveData<Boolean>()
    val eventAddCouponBtnPressed: LiveData<Boolean> get() = _eventAddCouponBtnPressed

    private var couponUID: String? = null
    var couponCompanyID: String? = null

    lateinit var couponListQuery: Query

//    addCouponFragment staff
    private val _couponDescriptionError = MutableLiveData<String>()
    val couponDescriptionError: LiveData<String> get() = _couponDescriptionError

    private val _couponDiscountError = MutableLiveData<String>()
    val couponDiscountError: LiveData<String> get() = _couponDiscountError

    private val _couponAvailabilityError = MutableLiveData<String>()
    val couponAvailabilityError: LiveData<String> get() = _couponAvailabilityError

    private val _saveCouponResult = MutableLiveData<Boolean>()
    val saveCouponResult: LiveData<Boolean> get() = _saveCouponResult


    init {
        fireStoreRepository.getCurrentUser(currentUserID).addOnSuccessListener {
            currentUser = it.toObject(User::class.java)
            if (currentUser!!.isProvider) {
                _btnAddCompanyVisibility.value = View.VISIBLE
            } else {
                _btnAddCompanyVisibility.value = View.GONE
            }
        }

        providerListQuery = fireStoreRepository.getProvidersQuery()

    }

    fun onAddCompany() {
        _eventAddCompanyBtnPressed.value = true
    }
    fun onAddCompanyCompleted() {
        _eventAddCompanyBtnPressed.value = false
    }

    fun onAddCoupon() {
        _eventAddCouponBtnPressed.value = true
    }
    fun onAddCouponCompleted() {
        _eventAddCouponBtnPressed.value = false
    }

    fun setCouponUserID(uid: String) {
        couponUID = uid
    }

    fun setCouponCoID(couponCoID: String) {
        couponCompanyID = couponCoID
    }

    fun setCouponListQuery(couponCoID: String) {
        couponCompanyID = couponCoID
        couponCompanyID?.let {
            couponListQuery = fireStoreRepository.getCouponsQuery(it)
        }
    }

    fun setAddCouponVisibility() {
        if (couponUID != null && couponUID == currentUserID) {
            _btnAddCouponVisibility.value = View.VISIBLE
        } else {
            _btnAddCouponVisibility.value = View.GONE
        }
    }

    fun saveCompany(companyName: String?, companyAddress: String?) {
        if (!isAddProviderFormValid(companyName, companyAddress)) return

        val company = Company(
            UUID.randomUUID().toString(),
            currentUserID,
            companyName!!,
            companyAddress!!
        )

        fireStoreRepository.saveCompanyToDB(company)
            .addOnSuccessListener {
                _saveCompanyResult.value = true
            }.addOnFailureListener {
                _saveCompanyResult.value = false
        }
    }

    fun saveCoupon(description: String?, discount: String?, availability: String?) {
        if (!isAddCouponFormValid(description, discount, availability)) return

        val coupon = Coupon(
            UUID.randomUUID().toString(),
            currentUserID,
            couponCompanyID!!,
            description!!,
            discount!!,
            availability!!
        )

        fireStoreRepository.saveCouponToDB(coupon)
            .addOnSuccessListener {
                _saveCouponResult.value = true
            }.addOnFailureListener {
                _saveCouponResult.value = false
        }
    }

    private fun isAddProviderFormValid(companyName: String?, companyAddress: String?): Boolean{

        _companyNameError.value = null
        _companyAddressError.value = null

        var isValid = true
        if (companyName.isNullOrEmpty()) {
            _companyNameError.value = FIELD_EMPTY_ERROR
            isValid = false
        }
        if (companyAddress.isNullOrEmpty()) {
            _companyAddressError.value = FIELD_EMPTY_ERROR
            isValid = false
        }

        return isValid
    }

    private fun isAddCouponFormValid(description: String?, discount: String?, availability: String?): Boolean{

        _couponDescriptionError.value = null
        _couponDiscountError.value = null
        _couponAvailabilityError.value = null

        var isValid = true
        if (description.isNullOrEmpty()) {
            _couponDescriptionError.value = FIELD_EMPTY_ERROR
            isValid = false
        }
        if (discount.isNullOrEmpty()) {
            _couponDiscountError.value = FIELD_EMPTY_ERROR
            isValid = false
        }
        if (availability.isNullOrEmpty()) {
            _couponAvailabilityError.value = FIELD_EMPTY_ERROR
            isValid = false
        }

        return isValid
    }

    fun deleteCoupon(coupon: Coupon) {
        fireStoreRepository.deleteCoupon(coupon)
    }

    fun deleteCompany(company: Company) {
        fireStoreRepository.deleteCompany(company)
    }

}