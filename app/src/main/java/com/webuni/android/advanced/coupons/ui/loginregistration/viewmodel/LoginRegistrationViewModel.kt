package com.webuni.android.advanced.coupons.ui.loginregistration.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webuni.android.advanced.coupons.R
import com.webuni.android.advanced.coupons.data.User
import com.webuni.android.advanced.coupons.repository.AuthenticationRepository
import com.webuni.android.advanced.coupons.repository.FireStoreRepository

class LoginRegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val FIELD_EMPTY_ERROR = getApplication<Application>()
        .resources
        .getString(R.string.field_empty_error)

    private val CONFIRM_PASSWORD_ERROR = getApplication<Application>()
        .resources
        .getString(R.string.confirm_password_error)
    private val MIN_SIX_CHAR_ERROR = getApplication<Application>()
        .resources
        .getString(R.string.min_six_char_error)

    private val _nameError = MutableLiveData<String>()
    val nameError: LiveData<String> get() = _nameError

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> get() = _emailError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get() = _passwordError

    private val _confirmPasswordError = MutableLiveData<String>()
    val confirmPasswordError: LiveData<String> get() = _confirmPasswordError


    private val authenticationRepository = AuthenticationRepository()
    private val fireStoreRepository = FireStoreRepository()

    private var currentUserID: String? = null

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> get() = _registrationResult

    fun login(email: String?, password: String?) {
        if (!isLoginFormValid(email, password)) return
        authenticationRepository.login(email!!, password!!)
            .addOnSuccessListener {
                currentUserID = authenticationRepository.getLoggedInUserID()
                _loginResult.value = true
            }.addOnFailureListener{
                _loginResult.value = false
            }
    }

    fun registration(name: String?,
                     email: String?,
                     password: String?,
                     confirmPassword: String?,
                     isProvider: Boolean
    ) {

        if (!isRegistrationFormValid(name, email, password, confirmPassword))
            return

        authenticationRepository.registration(email!!, password!!)
            .addOnSuccessListener {
                val registeredUser = User(
                    it.user!!.uid,
                    name!!,
                    email,
                    isProvider
                )
                saveUserToDB(registeredUser)
                login(email, password)
                _registrationResult.value = true
            }.addOnFailureListener {
                _registrationResult.value = false
            }
    }

    private fun saveUserToDB(user: User) {
        fireStoreRepository.addUser(user)
    }

    private fun isRegistrationFormValid(name: String?,
                                        email: String?,
                                        password: String?,
                                        confirmPassword: String?
    ): Boolean{

        _nameError.value = null
        _emailError.value = null
        _passwordError.value = null
        _confirmPasswordError.value = null

        var isValid = true

        if (name.isNullOrEmpty()) {
            _nameError.value = FIELD_EMPTY_ERROR
            isValid = false
        }
        if (email.isNullOrEmpty()) {
           _emailError.value = FIELD_EMPTY_ERROR
            isValid = false
        }
        if (password.isNullOrEmpty()) {
            _passwordError.value = MIN_SIX_CHAR_ERROR
            isValid = false
        }
        if (password != confirmPassword) {
            _confirmPasswordError.value = CONFIRM_PASSWORD_ERROR
            isValid = false
        }

        return isValid
    }

    private fun isLoginFormValid(email: String?, password: String?): Boolean{

        _emailError.value = null
        _passwordError.value = null

        var isValid = true
        if (email.isNullOrEmpty()) {
            _emailError.value = FIELD_EMPTY_ERROR
            isValid = false
        }
        if (password.isNullOrEmpty()) {
            _passwordError.value = FIELD_EMPTY_ERROR
            isValid = false
        }

        return isValid
    }

}