package com.awesome.amumanager.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.ui.main.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {


    private lateinit var firebaseViewModel : FirebaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

        initListener()

    }
    private fun initListener() {
        join_button.setOnClickListener {
            firebaseViewModel.getAuth().createUserWithEmailAndPassword(join_email.text.toString(),
                    join_password.text.toString()).addOnCompleteListener(this){ task ->
                if(task.isSuccessful) {
                    val intent = Intent(this, JoinInfoActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}