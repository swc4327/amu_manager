package com.awesome.amumanager.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseUtils {
    companion object {
        private var auth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()

        fun getUid() : String {
            return auth.currentUser?.uid.toString()
        }
    }
}