package com.awesome.amumanager.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.ui.base.BaseActivity
import com.awesome.amumanager.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amumanager.ui.main.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_join.*
import javax.inject.Inject

class JoinActivity : BaseActivity() {


    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    private lateinit var firebaseViewModel : FirebaseViewModel

    companion object {
        fun startActivity(activity : BaseActivity) {
            val intent = Intent(activity, JoinActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        firebaseViewModel = ViewModelProvider(this, viewModelFactory).get(FirebaseViewModel::class.java)

        initListener()
        observe()

    }
    private fun observe() {
        firebaseViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                JoinInfoActivity.startActivity(this)
            }
            else {
                //에러처리
            }
        })
    }

    private fun initListener() {
        join_button.setOnClickListener {
            firebaseViewModel.signUp(join_email.text.toString(), join_password.text.toString())
//            firebaseViewModel.getAuth().createUserWithEmailAndPassword(join_email.text.toString(),
//                    join_password.text.toString()).addOnCompleteListener(this){ task ->
//                if(task.isSuccessful) {
//                    JoinInfoActivity.startActivity(this)
//                } else {
//                    Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
//                }
//            }

        }
    }
}