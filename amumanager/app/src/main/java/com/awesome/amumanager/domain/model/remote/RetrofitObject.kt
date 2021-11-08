//package com.awesome.amumanager.domain.model.remote
//
//import com.awesome.amumanager.data.api.service.*
//import com.awesome.amumanager.domain.model.Constants
//import com.google.gson.GsonBuilder
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitObject {
//    private val retrofit = Retrofit.Builder()
//            .baseUrl(Constants.serverUrl)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//            .build()
//
//    val STORE_API : com.awesome.amumanager.data.api.service.StoreApi = retrofit.create(StoreApi::class.java)
//    val MENU_API : com.awesome.amumanager.data.api.service.MenuApi = retrofit.create(MenuApi::class.java)
//    val REVIEW_API : com.awesome.amumanager.data.api.service.ReviewApi = retrofit.create(ReviewApi::class.java)
//    val RESERVE_API : com.awesome.amumanager.data.api.service.ReserveApi = retrofit.create(ReserveApi::class.java)
//    val PROMOTION_API : com.awesome.amumanager.data.api.service.PromotionApi = retrofit.create(PromotionApi::class.java)
//    val SIGN_UP_API : SignUpApi = retrofit.create(SignUpApi::class.java)
//    val CLIENT_API : ClientApi = retrofit.create(ClientApi::class.java)
//}