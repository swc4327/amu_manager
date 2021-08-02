package com.awesome.amumanager.ui.main.viewmodel

import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.util.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseViewModel() : ViewModel() {
    private val firebaseUtils = FirebaseUtils()
    val taskToString = MutableLiveData<String>()

    fun getAuth(): FirebaseAuth {
        return firebaseUtils.getAuth()
    }

    fun getUid() : String {
        return firebaseUtils.getUid()
    }

    fun getDb(): FirebaseFirestore {
        return firebaseUtils.getDb()
    }

    fun logout() {
        firebaseUtils.logout()
    }

    fun checkCurrentUser(): Boolean {
        return firebaseUtils.checkCurrentUser()
    }

    fun uploadTask(bitmapDrawable: BitmapDrawable, stringValue: String) {
        firebaseUtils.uploadTask(bitmapDrawable, stringValue, taskToString)
    }
}