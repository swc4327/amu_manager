package com.awesome.amumanager.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.firebase.FirebaseAuthManager.getUid
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseDatabaseManager {
    private val db = FirebaseFirestore.getInstance()

    fun getDb(): FirebaseFirestore {
        return this.db
    }

    fun addDatabase(collectionPath : String, field : String, value : String, status : MutableLiveData<Int>) {
        db.collection(collectionPath)
                .document(getUid())
                .set(hashMapOf(
                        field to value
                ))
                .addOnSuccessListener {
                    Log.e("Join To Manager", "성공")
                    status.value = 200

                }.addOnFailureListener{
                    Log.e("Join To Manager", "실패")
                    println(it)
                }
    }
}