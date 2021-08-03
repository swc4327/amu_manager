package com.awesome.amumanager.ui.main.viewmodel

import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.firebase.FirebaseAuthManager
import com.awesome.amumanager.firebase.FirebaseDatabaseManager
import com.awesome.amumanager.firebase.FirebaseStorageManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseViewModel() : ViewModel() {
    //private val firebaseUtils = FirebaseUtils()
    val taskToString = MutableLiveData<String>()
    val status = MutableLiveData<Int>()

    fun getAuth(): FirebaseAuth {
        return FirebaseAuthManager.getAuth()
    }

    fun getUid() : String {
        return FirebaseAuthManager.getUid()
    }

    fun logout() {
        FirebaseAuthManager.logout()
    }

    fun isLoggedIn(): Boolean {
        return FirebaseAuthManager.isLoggedIn()
    }

    ////////////////////////////////////////////

    fun getDb(): FirebaseFirestore {
        return FirebaseDatabaseManager.getDb()
    }

    fun addDatabase(collectionPath : String, filed : String, value : String) {
        FirebaseDatabaseManager.addDatabase(collectionPath, filed, value, status)
    }

    /////////////////////////////////////////////

    fun uploadTask(bitmapDrawable: BitmapDrawable, stringValue: String) {
        FirebaseStorageManager.uploadTask(bitmapDrawable, stringValue, taskToString)
    }


}