package com.awesome.amumanager.util

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class FirebaseUtils {

    private var auth = FirebaseAuth.getInstance()
    //private var db = FirebaseFirestore.getInstance
    private val db = FirebaseFirestore.getInstance()

    fun getAuth() : FirebaseAuth {
        return this.auth
    }

    fun getUid(): String {
        return auth.currentUser?.uid.toString()
    }

    fun getDb(): FirebaseFirestore {
        return this.db
    }

    fun logout() {
        this.auth.signOut()
    }

    fun checkCurrentUser(): Boolean {
        return auth.currentUser != null
    }

    fun uploadTask(bitmapDrawable: BitmapDrawable,
                   stringValue: String,
                   taskToString: MutableLiveData<String>) {
        val bitmap = bitmapDrawable.bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var task = FirebaseStorage.getInstance().getReference()
                .child(getUid() + stringValue)

        val uploadTask = task.putBytes(data)

        uploadTask.addOnFailureListener {

        }.addOnSuccessListener {
            task.downloadUrl
                    .addOnCompleteListener { task ->
                        taskToString.value = task.result.toString()
                    }
        }
    }
}