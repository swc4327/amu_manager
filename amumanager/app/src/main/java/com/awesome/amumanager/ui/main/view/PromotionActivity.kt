package com.awesome.amumanager.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Constants.ADD_PROMOTION_ACTIVITY
import com.awesome.amumanager.data.model.Constants.FIRST_CALL
import com.awesome.amumanager.data.model.Promotion
import com.awesome.amumanager.data.model.Store
import com.awesome.amumanager.ui.main.adapter.PromotionAdapter
import com.awesome.amumanager.ui.main.viewmodel.*
import com.awesome.amumanager.ui.main.viewmodel.factory.PromotionViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_promotion.*

class PromotionActivity : AppCompatActivity() {

    private var store : Store? = null

    private lateinit var promotionViewModel : PromotionViewModel
    private var promotionAdapter: PromotionAdapter? = null

    companion object {
        fun startActivity(activity : AppCompatActivity, store : Store) {
            val intent = Intent(activity, PromotionActivity::class.java)
            intent.putExtra("store", store)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotion)

        store = intent.getParcelableExtra("store")

        promotionViewModel = ViewModelProvider(this,
            PromotionViewModelFactory(
                store?.id.toString()
            )
        ).get(PromotionViewModel::class.java)


        initListener()
        observe()

        promotionViewModel.getPromotion(FIRST_CALL)


    }

    private fun initListener() {
        close_promotion_list.setOnClickListener {
            finish()
        }

        add_promotion.setOnClickListener {
            AddPromotionActivity.startActivityForResult(this, store?.id.toString(), store?.name.toString(), ADD_PROMOTION_ACTIVITY)
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
                promotionViewModel.getPromotion(FIRST_CALL)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_PROMOTION_ACTIVITY) {
            if(resultCode == RESULT_OK) {
                promotionAdapter?.clearPromotions()
                promotionViewModel.getPromotion(FIRST_CALL)
            }
        }
    }

    private fun endPromotion(promotionId : String) {
        promotionViewModel.endPromotion(promotionId)
    }
}