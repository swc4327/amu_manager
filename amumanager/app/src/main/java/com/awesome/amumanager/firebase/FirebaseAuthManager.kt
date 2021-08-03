package com.awesome.amumanager.firebase

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthManager {

    private var auth = FirebaseAuth.getInstance()

    fun getAuth() : FirebaseAuth {
        return this.auth
    }

    fun getUid(): String {
        return auth.currentUser?.uid.toString()
    }

    fun logout() {
        this.auth.signOut()
    }

    fun isLoggedIn() : Boolean {
        return auth.currentUser != null
    }
}