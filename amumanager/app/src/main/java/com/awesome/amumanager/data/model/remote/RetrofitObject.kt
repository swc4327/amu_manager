package com.awesome.amumanager.data.model.remote

import com.awesome.amumanager.data.api.service.*
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

    val getStoreListService: GetStoreListService = retrofit.create(GetStoreListService::class.java)
    val addStoreService : AddStoreService = retrofit.create(AddStoreService::class.java)
    val addMenuService : AddMenuService = retrofit.create(AddMenuService::class.java)
    val addPromotionService : AddPromotionService = retrofit.create(AddPromotionService::class.java)
    val getClientInfoService : GetClientInfoService = retrofit.create(GetClientInfoService::class.java)
    val getMenuListService : GetMenuListService = retrofit.create(GetMenuListService::class.java)
    val getPromotionListService : GetPromotionListService = retrofit.create(GetPromotionListService::class.java)
    val getReserveListService : GetReserveListService = retrofit.create(GetReserveListService::class.java)
    val getReviewListService : GetReviewListService = retrofit.create(GetReviewListService::class.java)
    val reviewFilteringService : ReviewFilteringService = retrofit.create(ReviewFilteringService::class.java)
    val signUpService : SignUpService = retrofit.create(SignUpService::class.java)
}