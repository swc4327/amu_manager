package com.awesome.amumanager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import com.awesome.amumanager.R
import com.awesome.amumanager.ui.storeinfo.MenuFragment
import com.awesome.amumanager.ui.storeinfo.InfoFragment
import com.awesome.amumanager.ui.storeinfo.ReserveListFragment
import com.awesome.amumanager.ui.storeinfo.ReviewFragment
import kotlinx.android.synthetic.main.activity_store_info.*

class StoreInfoActivity : AppCompatActivity() {

    private var name = ""
    private var managerUid = ""
    private var storeId = ""
    private var lat: String? = null
    private var lng: String? = null
    private var place = ""
    private var place_detail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_info)

        name = intent.getStringExtra("name").toString()
        managerUid = intent.getStringExtra("manager_uid").toString()
        storeId = intent.getStringExtra("store_id").toString()
        lat = intent.getStringExtra("lat").toString()
        lng = intent.getStringExtra("lng").toString()
        place = intent.getStringExtra("place").toString()
        place_detail = intent.getStringExtra("place_detail").toString()

        store_info_name.setText(name)


        store_info_name.setOnClickListener {
//            val intent = Intent(this, AddPromotionActivity::class.java)
//            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            intent.putExtra("store_id", storeId)
//            intent.putExtra("name", name)
//            startActivity(intent)
            val intent = Intent(this, PromotionListActivity::class.java)
            intent.putExtra("store_id", storeId)
            intent.putExtra("name", name)
            startActivity(intent)
        }

        add_menu.setOnClickListener {
            val intent = Intent(this, AddMenuActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("store_id", storeId)
            intent.putExtra("name", name)
            startActivity(intent)
        }

        close_store_info.setOnClickListener {
            finish()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_area, MenuFragment().apply {
                arguments = Bundle().apply {
                    putString("store_id", storeId)
                }
            })
            .commit()

        menu_bar_1.setOnClickListener {

            menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
            menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            menu_bar_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_area, MenuFragment().apply {
                    arguments = Bundle().apply {
                        putString("store_id", storeId)
                    }
                })
                .commit()
        }

        menu_bar_2.setOnClickListener {


            menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
            menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            menu_bar_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_area, InfoFragment().apply {
                    arguments = Bundle().apply {
                        putString("store_id", storeId)
                        putString("lat", lat)
                        putString("lng", lng)
                        putString("place", place)
                        putString("place_detail", place_detail)
                    }
                })
                .commit()
        }

        menu_bar_3.setOnClickListener {


            menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
            menu_bar_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_area, ReviewFragment().apply {
                    arguments = Bundle().apply {
                        putString("store_id", storeId)
                    }
                })
                .commit()
        }

        menu_bar_4.setOnClickListener {


            menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            menu_bar_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_area, ReserveListFragment().apply {
                    arguments = Bundle().apply {
                        putString("store_id", storeId)
                    }
                })
                .commit()
        }
    }
}