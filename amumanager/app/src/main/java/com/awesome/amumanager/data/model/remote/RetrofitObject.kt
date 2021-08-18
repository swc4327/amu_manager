package com.awesome.amumanager.data.model.remote

import com.awesome.amumanager.data.api.service.*
import com.awesome.amumanager.data.model.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

    val storeService : StoreService = retrofit.create(StoreService::class.java)
    val menuService : MenuService = retrofit.create(MenuService::class.java)
    val reviewService : ReviewService = retrofit.create(ReviewService::class.java)

    //val getStoreService: GetStoreService = retrofit.create(GetStoreService::class.java)
    //val addStoreService : AddStoreService = retrofit.create(AddStoreService::class.java)
    //val addMenuService : AddMenuService = retrofit.create(AddMenuService::class.java)
    val addPromotionService : AddPromotionService = retrofit.create(AddPromotionService::class.java)
    val getClientService : GetClientService = retrofit.create(GetClientService::class.java)
    //val getMenuService : GetMenuService = retrofit.create(GetMenuService::class.java)
    val getPromotionService : GetPromotionService = retrofit.create(GetPromotionService::class.java)
    val getReserveService : GetReserveService = retrofit.create(GetReserveService::class.java)
    //val getReviewService : GetReviewService = retrofit.create(GetReviewService::class.java)
    //val reviewFilteringService : ReviewFilteringService = retrofit.create(ReviewFilteringService::class.java)
    val signUpService : SignUpService = retrofit.create(SignUpService::class.java)
    val confirmReserveService : ConfirmReserveService = retrofit.create(ConfirmReserveService::class.java)
    val cancelReserveService : CancelReserveService = retrofit.create(CancelReserveService::class.java)
    val completeReserveService : CompleteReserveService = retrofit.create(CompleteReserveService::class.java)
    val endPromotionService : EndPromotionService = retrofit.create(EndPromotionService::class.java)
    //val deleteMenuService : DeleteMenuService = retrofit.create(DeleteMenuService::class.java)
    //val deleteStoreService : DeleteStoreService = retrofit.create(DeleteStoreService::class.java)
    val getConfirmedReserveService : GetConfirmedReserveService = retrofit.create(GetConfirmedReserveService::class.java)
    //val openStoreService : OpenStoreService = retrofit.create(OpenStoreService::class.java)
    //val closeStoreService : CloseStoreService = retrofit.create(CloseStoreService::class.java)
}