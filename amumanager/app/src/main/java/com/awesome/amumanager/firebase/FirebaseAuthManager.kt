package com.awesome.amumanager.firebase

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.ui.main.view.JoinInfoActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_join.*

object FirebaseAuthManager {

    private var auth = FirebaseAuth.getInstance()

//    fun getAuth() : FirebaseAuth {
//        return this.auth
//    }

    fun getUid(): String {
        return auth.currentUser?.uid.toString()
    }

    fun logout() {
        this.auth.signOut()
    }

    fun isLoggedIn() : Boolean {
        return auth.currentUser != null
    }

    fun signUp(email : String, password : String, status : MutableLiveData<Int>) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(){ task ->
                if(task.isSuccessful) {
                    status.value = 200
                } else {
                    //에러처리
                }
            }
    }

    fun signIn(email : String, password : String, status : MutableLiveData<Int>) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){task ->
                if(task.isSuccessful) {
                    status.value = 200
                } else {
                    //에러처리
                }
            }
    }
}