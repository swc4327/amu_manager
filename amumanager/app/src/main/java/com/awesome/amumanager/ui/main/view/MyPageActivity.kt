package com.awesome.amumanager.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.ui.base.BaseActivity
import com.awesome.amumanager.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amumanager.ui.main.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_my_page.*
import javax.inject.Inject

class MyPageActivity : BaseActivity() {

    companion object {
        fun startActivity(activity : BaseActivity) {
            val intent = Intent(activity, MyPageActivity::class.java)
            activity.startActivity(intent)
        }
    }

    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    private lateinit var firebaseViewModel : FirebaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        firebaseViewModel = ViewModelProvider(this, viewModelFactory).get(FirebaseViewModel::class.java)

        setProfileName()
        setProfileImage()
        initListener()
        observe()


    }

    private fun observe() {
        firebaseViewModel.nickname.observe(this, Observer<String> { nickname ->
            nickname_area.text = nickname
        })
    }

    private fun setProfileName() {
        firebaseViewModel.getDatabase("managers")
    }

    private fun setProfileImage() {
        val ref = FirebaseStorage.getInstance()
            .getReference(firebaseViewModel.getUid()+"_profile")

        Log.e("image file url check", ref.toString())

        ref.downloadUrl
            .addOnCompleteListener(OnCompleteListener { task ->
                Log.e("task check", task.result.toString())
                if(task.isSuccessful) {
                    Glide.with(this)
                        .load(task.result)
                        .into(profile_img)
                } else {
                    Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun initListener() {
        close_my_page.setOnClickListener {
            finish()
        }

        mypage_logout_button.setOnClickListener {
            firebaseViewModel.logout()
            MainActivity.startActivity(this)
        }

    }
}