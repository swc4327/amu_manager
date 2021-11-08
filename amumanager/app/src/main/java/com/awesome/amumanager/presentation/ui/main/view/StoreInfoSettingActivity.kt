package com.awesome.amumanager.presentation.ui.main.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.domain.model.Store
import com.awesome.amumanager.presentation.ui.base.BaseActivity
import com.awesome.amumanager.presentation.ui.main.viewmodel.StoreViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_store_info_setting.*
import javax.inject.Inject

class StoreInfoSettingActivity : BaseActivity() {

    private var store : Store? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var storeViewModel : StoreViewModel

    companion object {
        fun startActivityForResult(activity : BaseActivity, store : Store, requestCode : Int) {
            val intent = Intent(activity, StoreInfoSettingActivity::class.java)
            intent.putExtra("store", store)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_info_setting)

        store = intent.getParcelableExtra("store")

        storeViewModel = ViewModelProvider(this, viewModelFactory).get(StoreViewModel::class.java)

        initLayout()

        initListener()
        observe()
    }

    private fun initLayout() {
        if(store?.isOpened == "0") {
            close.visibility = GONE

        } else {
            open.visibility = GONE
        }
    }

    private fun initListener() {
        close_store_setting.setOnClickListener {
            finish()
        }

        open.setOnClickListener {
            storeViewModel.openStore(store?.id.toString())
        }

        close.setOnClickListener {
            storeViewModel.closeStore(store?.id.toString())
        }

        delete_store.setOnClickListener {
            storeViewModel.deleteStore(store?.id.toString())
        }
    }

    private fun observe() {
        storeViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                //가게 삭제 or 영업시작 or 영업종료 완료
                setResult(Activity.RESULT_OK)
                finish()
            }
        })
    }
}