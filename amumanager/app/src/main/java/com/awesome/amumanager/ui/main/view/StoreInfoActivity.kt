package com.awesome.amumanager.ui.main.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Constants.STORE_INFO_SETTING_ACTIVITY
import com.awesome.amumanager.data.model.Store
import com.awesome.amumanager.ui.base.BaseActivity
import com.awesome.amumanager.ui.main.view.fragment.ReserveFragment
import com.awesome.amumanager.ui.main.view.fragment.ReviewFragment
import com.awesome.amumanager.ui.main.view.fragment.MenuFragment
import com.awesome.amumanager.ui.main.view.fragment.InfoFragment
import kotlinx.android.synthetic.main.activity_store_info.*

class StoreInfoActivity : BaseActivity() {

    private var store : Store? = null

    companion object {
        fun startActivityForResult(activity : BaseActivity, store : Store, requestCode: Int) {
            val intent = Intent(activity, StoreInfoActivity::class.java)
            intent.putExtra("store", store)
            activity.startActivityForResult(intent, requestCode)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_info)

        store = intent.getParcelableExtra("store")

        initLayout()
        initListener()

        goMenuFragment()
    }

    private fun initListener() {
        store_info_name.setOnClickListener {
            store?.let { store -> PromotionActivity.startActivity(this, store) }
        }

        setting.setOnClickListener {
            store?.let { store -> StoreInfoSettingActivity.startActivityForResult(this, store, STORE_INFO_SETTING_ACTIVITY) }
            //가게삭제, 영업시작, 영업종료
        }

        close_store_info.setOnClickListener {
            finish()
        }

        menu_bar_1.setOnClickListener {
            goMenuFragment()
        }

        menu_bar_2.setOnClickListener {
            goInfoFragment()
        }

        menu_bar_3.setOnClickListener {
            goReviewFragment()
        }

        menu_bar_4.setOnClickListener {
            goReserveListFragment()
        }
    }

    private fun initLayout() {
        store_info_name.text = store?.name
        point.text = store?.point?.toString()
        count.text = "("+store?.count.toString()+")"
    }

    private fun goMenuFragment() {
        menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
        menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_area, MenuFragment().apply {
                    arguments = Bundle().apply {
                        putString("store_id", store?.id.toString())
                    }
                })
                .commit()
    }

    private fun goInfoFragment() {
        menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
        menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_area, InfoFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("store", store)
                    }
                })
                .commit()
    }

    private fun goReviewFragment() {
        menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
        menu_bar_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_area, ReviewFragment().apply {
                    arguments = Bundle().apply {
                        putString("store_id", store?.id.toString())
                    }
                })
                .commit()
    }

    private fun goReserveListFragment() {
        menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_area, ReserveFragment().apply {
                    arguments = Bundle().apply {
                        putString("store_id", store?.id.toString())
                    }
                })
                .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == STORE_INFO_SETTING_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}