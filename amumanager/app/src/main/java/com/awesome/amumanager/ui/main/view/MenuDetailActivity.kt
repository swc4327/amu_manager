package com.awesome.amumanager.ui.main.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Menu
import com.awesome.amumanager.ui.main.viewmodel.MenuViewModel
import com.awesome.amumanager.ui.main.viewmodel.MenuViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_menu_detail.*

class MenuDetailActivity : AppCompatActivity() {

    private var menu : Menu? = null
    private var storeId : String? = null
    private lateinit var menuViewModel : MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)

        menu = intent.getParcelableExtra("Menu")
        storeId = intent.getStringExtra("storeId")

        var factory = MenuViewModelFactory(storeId.toString())
        menuViewModel = ViewModelProvider(this, factory).get(MenuViewModel::class.java)

        initLayout()
        initListener()

        observe()

    }

    private fun initLayout() {
        Glide.with(this).load(menu?.image).circleCrop().into(detail_menu_image)
        detail_menu_name.setText(menu?.name.toString())
        detail_menu_comment.setText(menu?.comment.toString())
        detail_menu_price.setText(menu?.price.toString())
    }

    private fun initListener() {
        close_menu_detail.setOnClickListener {
            finish()
        }

        delete_menu.setOnClickListener {
            //메뉴삭제
            menuViewModel?.deleteMenu(menu?.id.toString())
        }
    }

    private fun observe() {
        menuViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                //setResult(Activity.RESULT_OK)
                finish()
            }
        })
    }
}