package com.awesome.amumanager.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.awesome.amumanager.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = FirebaseAuth.getInstance()

        initListener()

    }
    private fun initListener() {
        join_button.setOnClickListener {
            auth.createUserWithEmailAndPassword(join_email.text.toString(),
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