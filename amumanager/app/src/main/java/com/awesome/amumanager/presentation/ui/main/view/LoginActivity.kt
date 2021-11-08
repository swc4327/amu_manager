package com.awesome.amumanager.presentation.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.presentation.ui.base.BaseActivity
import com.awesome.amumanager.presentation.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var firebaseViewModel : FirebaseViewModel

    companion object {
        fun startActivity(activity : BaseActivity) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseViewModel = ViewModelProvider(this, viewModelFactory).get(FirebaseViewModel::class.java)

        initListener()
        observe()

    }

    private fun observe() {
        firebaseViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                MainActivity.startActivity(this)
            }
            else {
                //에러처리
            }
        })
    }

    private fun initListener() {
        login_button.setOnClickListener {
            firebaseViewModel.signIn(login_email.text.toString(), login_password.text.toString())
//            firebaseViewModel.getAuth().signInWithEmailAndPassword(login_email.text.toString(), login_password.text.toString())
//                .addOnCompleteListener(this){task ->
//                    if(task.isSuccessful) {
//                        MainActivity.startActivity(this)
//                    } else {
//                        Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
//                    }
//                }
        }

        login_join_button.setOnClickListener{
            JoinActivity.startActivity(this)
        }
    }
}