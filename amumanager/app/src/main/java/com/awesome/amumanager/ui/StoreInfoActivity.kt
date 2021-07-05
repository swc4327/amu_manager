package com.awesome.amumanager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import com.awesome.amumanager.R
import com.awesome.amumanager.model.Store
import com.awesome.amumanager.ui.storeinfo.MenuFragment
import com.awesome.amumanager.ui.storeinfo.InfoFragment
import com.awesome.amumanager.ui.storeinfo.ReserveListFragment
import com.awesome.amumanager.ui.storeinfo.ReviewFragment
import kotlinx.android.synthetic.main.activity_store_info.*

class StoreInfoActivity : AppCompatActivity() {

    private var store : Store? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_info)


        store = intent.getParcelableExtra("store")


        store_info_name.setText(store!!.name)

        store_info_name.setOnClickListener {
            val intent = Intent(this, PromotionListActivity::class.java)
            intent.putExtra("store_id", store!!.id)
            intent.putExtra("name", store!!.name)
            startActivity(intent)
        }

        add_menu.setOnClickListener {
            val intent = Intent(this, AddMenuActivity::class.java)
            intent.putExtra("store_id", store!!.id)
            intent.putExtra("name", store!!.name)
            startActivity(intent)
        }

        close_store_info.setOnClickListener {
            finish()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_area, MenuFragment().apply {
                arguments = Bundle().apply {
                    putString("store_id", store!!.id.toString())
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
                        putString("store_id", store!!.id.toString())
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
                        putString("store_id", store!!.id.toString())
                        putString("lat", store!!.lat)
                        putString("lng", store!!.lng)
                        putString("place", store!!.place)
                        putString("place_detail", store!!.place_detail)
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
                        putString("store_id", store!!.id.toString())
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
                        putString("store_id", store!!.id.toString())
                    }
                })
                .commit()
        }
    }
}