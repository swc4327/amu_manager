package com.awesome.amumanager.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Menu
import com.awesome.amumanager.ui.main.viewmodel.MenuViewModel
import com.awesome.amumanager.ui.main.viewmodel.factory.MenuViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_menu_detail.*

class MenuDetailActivity : AppCompatActivity() {

    private var menu : Menu? = null
    private var storeId : String? = null
    private lateinit var menuViewModel : MenuViewModel

    companion object {
        fun startActivity(activity : AppCompatActivity, menu : Menu, storeId : String) {
            val intent = Intent(activity, MenuDetailActivity::class.java)
            intent.putExtra("Menu", menu)
            intent.putExtra("storeId", storeId)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)

        menu = intent.getParcelableExtra("Menu")
        storeId = intent.getStringExtra("storeId")
        println(storeId)
        println("^^^^^^^^^--")

        menuViewModel = ViewModelProvider(this,
            MenuViewModelFactory(
                storeId.toString()
            )
        ).get(MenuViewModel::class.java)

        initLayout()
        initListener()

        observe()

    }

    private fun initLayout() {
        Glide.with(this).load(menu?.image).circleCrop().into(detail_menu_image)
        detail_menu_name.text = menu?.name.toString()
        detail_menu_comment.text = menu?.comment.toString()
        detail_menu_price.text = menu?.price.toString()
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