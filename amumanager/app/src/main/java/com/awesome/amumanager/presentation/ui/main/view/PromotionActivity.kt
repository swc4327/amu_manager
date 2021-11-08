package com.awesome.amumanager.presentation.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.domain.model.Constants.ADD_PROMOTION_ACTIVITY
import com.awesome.amumanager.domain.model.Constants.FIRST_CALL
import com.awesome.amumanager.domain.model.Promotion
import com.awesome.amumanager.domain.model.Store
import com.awesome.amumanager.presentation.ui.base.BaseActivity
import com.awesome.amumanager.presentation.ui.main.adapter.PromotionAdapter
import com.awesome.amumanager.presentation.ui.main.viewmodel.PromotionViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_promotion.*
import javax.inject.Inject

class PromotionActivity : BaseActivity() {

    private var store : Store? = null

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private lateinit var promotionViewModel : PromotionViewModel
    private var promotionAdapter: PromotionAdapter? = null

    companion object {
        fun startActivity(activity : BaseActivity, store : Store) {
            val intent = Intent(activity, PromotionActivity::class.java)
            intent.putExtra("store", store)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotion)

        store = intent.getParcelableExtra("store")

        promotionViewModel = ViewModelProvider(this, viewModelFactory).get(PromotionViewModel::class.java)

        initListener()
        observe()

        promotionViewModel.getPromotion(FIRST_CALL, store?.id.toString())


    }

    private fun initListener() {
        close_promotion_list.setOnClickListener {
            finish()
        }

        add_promotion.setOnClickListener {
            AddPromotionActivity.startActivityForResult(
                this,
                store?.id.toString(),
                store?.name.toString(),
                ADD_PROMOTION_ACTIVITY
            )
        }
        promotion_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()

                if (!recyclerView.canScrollVertically((1)) && lastVisibleItemPosition >= 0) {
                    promotionAdapter?.getLastPromotionId(lastVisibleItemPosition)?.let {lastId-> promotionViewModel.getPromotion(lastId, store?.id.toString()) }
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
                promotionViewModel.getPromotion(FIRST_CALL, store?.id.toString())
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_PROMOTION_ACTIVITY) {
            if(resultCode == RESULT_OK) {
                promotionAdapter?.clearPromotions()
                promotionViewModel.getPromotion(FIRST_CALL, store?.id.toString())
            }
        }
    }

    private fun endPromotion(promotionId : String) {
        promotionViewModel.endPromotion(promotionId)
    }
}