package com.webuni.android.advanced.coupons.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthDataSource {

    fun getLoggedInUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun login(email: String, password: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
    }

    fun registration(email: String, password: String): Task<AuthResult> {
        return  FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
    }

}