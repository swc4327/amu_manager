package com.awesome.amumanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.awesome.amumanager.api.Constants
import com.awesome.amumanager.api.response.StoreListResponse
import com.awesome.amumanager.api.service.GetStoreListService
import com.awesome.amumanager.model.Store
import com.awesome.amumanager.ui.*
import com.awesome.amumanager.ui.adapter.StoreListAdapter
import com.awesome.amumanager.util.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_bottom.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var stores : ArrayList<Store> = ArrayList<Store>()
    private var storeListAdapter: StoreListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        //auth.signOut()

        getStoreList()

        my_page.setOnClickListener{
            if(auth.currentUser == null) { //로그인 no
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else { //로그인 ok
                val intent = Intent(this, MyPageActivity::class.java)
                startActivity(intent)
            }
        }

        add_store.setOnClickListener {
            if(auth.currentUser == null) { //로그인 no
                Toast.makeText(this, "로그인 후 이용하세요!!", Toast.LENGTH_LONG).show()
            } else { //로그인 ok
                val intent = Intent(this, AddStoreActivity::class.java)
                startActivityForResult(intent, 0)
            }
        }

        store_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, StoreInfoActivity::class.java)
            intent.putExtra("store", storeListAdapter!!.getItem(position))
            startActivity(intent)
        }
    }
    fun getStoreList() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val joinApi = retrofit.create(GetStoreListService::class.java)
        val uid = FirebaseUtils.getUid()


        joinApi.getStoreList(uid)
            .enqueue(object : Callback<StoreListResponse> {

                override fun onFailure(call: Call<StoreListResponse>, t: Throwable) {
                    Log.e("Main Retrofit getStore", "실패")
                }

                override fun onResponse(
                    call: Call<StoreListResponse>,
                    response: Response<StoreListResponse>
                )  {
                    println(response)
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        if(stores.isNotEmpty()) {
                            stores.clear()
                        }
                        stores.addAll(response.body()!!.stores)
                        storeListAdapter =
                            StoreListAdapter(
                                this@MainActivity,
                                stores
                            )
                        store_list.adapter = storeListAdapter
                    } else {

                    }
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode ==0) {
            if(resultCode == RESULT_OK) {
                getStoreList()
            }
        }
    }
}