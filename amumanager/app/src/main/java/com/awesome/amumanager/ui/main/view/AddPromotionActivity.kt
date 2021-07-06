package com.awesome.amumanager.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.awesome.amumanager.R
import com.awesome.amumanager.data.api.Constants
import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.service.AddPromotionService
import com.awesome.amumanager.data.model.Promotion
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_add_promotion.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddPromotionActivity : AppCompatActivity() {

    var store_id : String = ""
    var name : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_promotion)

        close_add_promotion.setOnClickListener {
            finish()
        }

        store_id = intent.getStringExtra("store_id").toString()
        name = intent.getStringExtra("name").toString()

        complete_button.setOnClickListener {
            addPromotion()
        }


    }

    private fun addPromotion() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val joinApi = retrofit.create(AddPromotionService::class.java)


        val promotion = Promotion(
            null,
            null,
            name,
            promotion_message.text.toString(),
            store_id
        )

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
                        //바로 반영 안됨.
                        finish()


                    } else {
                        Log.e("AddPromotionActivity", "실패")
                    }
                }
            })
    }
}