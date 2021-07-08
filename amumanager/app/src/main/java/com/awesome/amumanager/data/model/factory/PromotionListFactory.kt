package com.awesome.amumanager.data.model.factory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.api.Constants
import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.PromotionListResponse
import com.awesome.amumanager.data.api.response.StoreListResponse
import com.awesome.amumanager.data.api.service.AddPromotionService
import com.awesome.amumanager.data.api.service.AddStoreService
import com.awesome.amumanager.data.api.service.GetPromotionListService
import com.awesome.amumanager.data.api.service.GetStoreListService
import com.awesome.amumanager.data.model.Promotion
import com.awesome.amumanager.data.model.Store
import com.awesome.amumanager.util.FirebaseUtils
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PromotionListFactory {
    private val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()



    fun getPromotionList(
            promotionList: MutableLiveData<ArrayList<Promotion>>,
            storeId : String
    ) {

        var promotionListTemp = ArrayList<Promotion>()

        val joinApi = retrofit.create(GetPromotionListService::class.java)

        joinApi.getPromotionList(storeId)
                .enqueue(object : Callback<PromotionListResponse> {

                    override fun onFailure(call: Call<PromotionListResponse>, t: Throwable) {
                        Log.e("Retrofit get Promotion", "실패")
                    }

                    override fun onResponse(
                            call: Call<PromotionListResponse>,
                            response: Response<PromotionListResponse>
                    )  {
                        println(response)
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            promotionListTemp.addAll(response.body()!!.promotions)
                            promotionList.value = promotionListTemp
                        } else {

                        }
                    }
                })
    }


    fun addPromotion(promotion : Promotion , status : MutableLiveData<Int>) {
        val joinApi = retrofit.create(AddPromotionService::class.java)

        joinApi.addPromotion(promotion)
                .enqueue(object : Callback<DefaultResponse> {

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Log.e("Retrofit Add Promotion", "실패")
                        Log.e("Check", t.toString())
                    }
                    override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                    )  {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("AddPromotionActivity", "success")
                            status.value = 200
                        } else {
                            Log.e("AddPromotionActivity", "실패")
                        }
                    }
                })
    }
}