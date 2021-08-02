package com.awesome.amumanager.ui.main.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Constants.ADD_PROMOTION_ACTIVITY
import com.awesome.amumanager.data.model.Constants.FIRST_CALL_GET_PROMOTION
import com.awesome.amumanager.data.model.Promotion
import com.awesome.amumanager.ui.main.adapter.PromotionAdapter
import com.awesome.amumanager.ui.main.viewmodel.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_promotion.*

class PromotionActivity : AppCompatActivity() {

    var storeId : String = ""
    var storeName : String = ""

    private lateinit var promotionViewModel : PromotionViewModel
    private var promotionAdapter: PromotionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotion)

        storeId = intent.getStringExtra("storeId").toString()
        storeName = intent.getStringExtra("storeName").toString()

        var factory = PromotionViewModelFactory(storeId.toString())
        promotionViewModel = ViewModelProvider(this, factory).get(PromotionViewModel::class.java)


        initListener()
        observe()

        promotionViewModel.getPromotion(FIRST_CALL_GET_PROMOTION)


    }

    private fun initListener() {
        close_promotion_list.setOnClickListener {
            finish()
        }

        add_promotion.setOnClickListener {
            val intent = Intent(this, AddPromotionActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("storeId", storeId)
            intent.putExtra("storeName", storeName)
            startActivityForResult(intent, ADD_PROMOTION_ACTIVITY)
        }
        promotion_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()

                if (!recyclerView.canScrollVertically((1)) && lastVisibleItemPosition >= 0) {
                    promotionAdapter?.getLastPromotionId(lastVisibleItemPosition)?.let { promotionViewModel.getPromotion(it) }
                }
            }
        })
    }

    private fun observe() {
        promotionViewModel.promotions.observe(this, Observer<ArrayList<Promotion>>{promotions ->
            if(promotionAdapter == null) {
                promotionAdapter = PromotionAdapter(arrayListOf(), Glide.with(this)) {promotionId->
                    endPromotion(promotionId)
                }
                promotion_list.adapter = promotionAdapter
            }
            promotionAdapter?.update(promotions)
        })

        promotionViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                promotionAdapter?.clearPromotions()
                promotionViewModel.getPromotion(FIRST_CALL_GET_PROMOTION)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_PROMOTION_ACTIVITY) {
            if(resultCode == RESULT_OK) {
                promotionAdapter?.clearPromotions()
                promotionViewModel.getPromotion(FIRST_CALL_GET_PROMOTION)
            }
        }
    }

    private fun endPromotion(promotionId : String) {
        promotionViewModel.endPromotion(promotionId)
    }
}