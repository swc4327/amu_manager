package com.awesome.amumanager.ui.main.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Store
import com.awesome.amumanager.ui.main.viewmodel.StoreViewModel
import kotlinx.android.synthetic.main.activity_store_info_setting.*

class StoreInfoSettingActivity : AppCompatActivity() {

    private var store : Store? = null

    private lateinit var storeViewModel : StoreViewModel

    companion object {
        fun startActivityForResult(activity : AppCompatActivity, store : Store, requestCode : Int) {
            val intent = Intent(activity, StoreInfoSettingActivity::class.java)
            intent.putExtra("store", store)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_info_setting)

        store = intent.getParcelableExtra("store")

        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

        initListener()
        observe()
    }

    private fun initListener() {
        close_store_setting.setOnClickListener {
            finish()
        }

        open.setOnClickListener {

        }

        close.setOnClickListener {

        }

        delete_store.setOnClickListener {
            //cascade 적용해야함
            storeViewModel.deleteStore(store?.id.toString())
        }
    }

    private fun observe() {
        storeViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        })
    }
}