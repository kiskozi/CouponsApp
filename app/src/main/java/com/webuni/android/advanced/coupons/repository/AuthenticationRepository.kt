package com.webuni.android.advanced.coupons.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.webuni.android.advanced.coupons.datasource.FirebaseAuthDataSource

class AuthenticationRepository {

    private val firebaseAuthDataSource = FirebaseAuthDataSource()

    fun getLoggedInUserID(): String =
        firebaseAuthDataSource.getLoggedInUserID()

    fun login(email: String, password: String): Task<AuthResult> =
        firebaseAuthDataSource.login(email, password)

    fun registration(email: String, password: String): Task<AuthResult> =
        firebaseAuthDataSource.registration(email, password)
}