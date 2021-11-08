package com.awesome.amumanager.data.promotion

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.domain.model.Promotion
import javax.inject.Inject

interface PromotionRepository {
    fun getPromotion(
        promotions: MutableLiveData<ArrayList<Promotion>>,
        storeId: String,
        lastId: String
    )
    fun addPromotion(promotion : Promotion , status : MutableLiveData<Int>)
    fun endPromotion(promotionId : String , status : MutableLiveData<Int>)
}

class AmuManagerPromotionRepository @Inject constructor(
    private val promotionDataSource: PromotionDataSource
) : PromotionRepository {
    override fun getPromotion(
        promotions: MutableLiveData<ArrayList<Promotion>>,
        storeId: String,
        lastId: String
    ) {
        promotionDataSource.getPromotion(promotions, storeId, lastId)
    }

    override fun addPromotion(promotion: Promotion, status: MutableLiveData<Int>) {
        promotionDataSource.addPromotion(promotion, status)
    }

    override fun endPromotion(promotionId: String, status: MutableLiveData<Int>) {
        promotionDataSource.endPromotion(promotionId, status)
    }
}