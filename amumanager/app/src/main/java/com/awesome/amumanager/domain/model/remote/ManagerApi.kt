//package com.awesome.amumanager.domain.model.remote
//
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import com.awesome.amumanager.data.api.response.DefaultResponse
//import com.awesome.amumanager.domain.model.Manager
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class ManagerApi {
//
//    fun addManager(manager: Manager, status: MutableLiveData<Int>) {
//
//        val joinApi = RetrofitObject.SIGN_UP_API
//
//
//        joinApi.addManager(manager)
//                .enqueue(object : Callback<DefaultResponse> {
//
//                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                        Log.e("retrofit add manager", "실패")
//                        Log.e("Check", t.toString())
//                    }
//                    override fun onResponse(
//                            call: Call<DefaultResponse>,
//                            response: Response<DefaultResponse>
//                    ) {
//                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                            Log.e("JoinInfoActivity", "success")
//                            status.value = 200
//
//                        } else {
//                            Log.e("JoinInfoActivity", "실패")
//                        }
//                    }
//                })
//}
//}