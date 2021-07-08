package com.awesome.amumanager.ui.main.viewmodel

import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.util.FirebaseUtils

class FirebaseViewModel() : ViewModel() {
    private val firebaseUtils = FirebaseUtils()
    //val status = MutableLiveData<Int>()
    val taskToString = MutableLiveData<String>()

    fun getUid() : String {
        return firebaseUtils.getUid()
    }

    fun uploadTask(bitmapDrawable: BitmapDrawable, stringValue: String) {
        firebaseUtils.uploadTask(bitmapDrawable, stringValue, taskToString)
    }
}