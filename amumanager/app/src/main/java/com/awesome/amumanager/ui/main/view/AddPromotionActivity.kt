package com.awesome.amumanager.ui.main.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Promotion
import com.awesome.amumanager.ui.main.viewmodel.PromotionViewModel
import com.awesome.amumanager.ui.main.viewmodel.PromotionViewModelFactory
import kotlinx.android.synthetic.main.activity_add_promotion.*

class AddPromotionActivity : AppCompatActivity() {

    var storeId : String = ""
    var storeName : String = ""
    private lateinit var promotionViewModel : PromotionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_promotion)

        storeId = intent.getStringExtra("storeId").toString()
        storeName = intent.getStringExtra("storeName").toString()

        initListener()

        var factory = PromotionViewModelFactory(storeId.toString())
        promotionViewModel = ViewModelProvider(this, factory).get(PromotionViewModel::class.java)

        observe()


    }
    private fun initListener() {
        close_add_promotion.setOnClickListener {
            finish()
        }

        complete_button.setOnClickListener {
            val promotion = Promotion(
                    null,
                    null,
                    storeName,
                    promotion_message.text.toString(),
                    storeId
            )
            promotionViewModel.addPromotion(promotion)
        }
    }

    private fun observe() {
        promotionViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                setResult(RESULT_OK)
                finish()
            }
        })
    }
}