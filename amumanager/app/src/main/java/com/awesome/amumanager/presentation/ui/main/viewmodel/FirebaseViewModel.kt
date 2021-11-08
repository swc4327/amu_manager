package com.awesome.amumanager.presentation.ui.main.viewmodel

import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.presentation.util.firebase.FirebaseAuthManager
import com.awesome.amumanager.presentation.util.firebase.FirebaseDatabaseManager
import com.awesome.amumanager.presentation.util.firebase.FirebaseStorageManager
import javax.inject.Inject

class FirebaseViewModel @Inject constructor() : ViewModel() {
    val taskToString = MutableLiveData<String>()
    val status = MutableLiveData<Int>()
    val nickname = MutableLiveData<String>()

    fun signUp(email : String, password: String) {
        FirebaseAuthManager.signUp(email, password, status)
    }

    fun signIn(email : String, password: String) {
        FirebaseAuthManager.signIn(email, password, status)
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


    fun addDatabase(collectionPath : String, filed : String, value : String) {
        FirebaseDatabaseManager.addDatabase(collectionPath, filed, value, status)
    }

    fun getDatabase(collectionPath: String) {
        FirebaseDatabaseManager.getDatabase(collectionPath, nickname)
    }

    /////////////////////////////////////////////

    fun uploadTask(bitmapDrawable: BitmapDrawable, stringValue: String) {
        FirebaseStorageManager.uploadTask(bitmapDrawable, stringValue, taskToString)
    }


}