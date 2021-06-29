package com.awesome.amumanager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.awesome.amumanager.R
import com.awesome.amumanager.api.Constants
import com.awesome.amumanager.api.response.PromotionListResponse
import com.awesome.amumanager.api.service.GetPromotionListService
import com.awesome.amumanager.model.Promotion
import com.awesome.amumanager.ui.adapter.PromotionListAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_promotion_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PromotionListActivity : AppCompatActivity() {

    var store_id : String = ""
    var name : String = ""

    private var promotions : ArrayList<Promotion> = ArrayList<Promotion>()
    private var promotionListAdapter: PromotionListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotion_list)

        store_id = intent.getStringExtra("store_id").toString()
        name = intent.getStringExtra("name").toString()


        getPromotionList()

        close_promotion_list.setOnClickListener {
            finish()
        }

        add_promotion.setOnClickListener {
            val intent = Intent(this, AddPromotionActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("store_id", store_id)
            intent.putExtra("name", name)
            startActivity(intent)
        }


    }

    private fun getPromotionList() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.serverUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val joinApi = retrofit.create(GetPromotionListService::class.java)

        joinApi.getPromotionList(store_id.toString())
                .enqueue(object : Callback<PromotionListResponse> {

                    override fun onFailure(call: Call<PromotionListResponse>, t: Throwable) {
                        Log.e("get promotion list", "실패")
                        Log.e("Check", t.toString())
                    }

                    override fun onResponse(
                            call: Call<PromotionListResponse>,
                            response: Response<PromotionListResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("get promotion list", "success")

                            promotions.addAll(response.body()!!.promotions)
                            promotionListAdapter = PromotionListAdapter(
                                    this@PromotionListActivity, promotions
                            )
                            promotion_list.adapter = promotionListAdapter

                        } else {

                        }
                    }
                })
    }
}