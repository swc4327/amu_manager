package com.awesome.amumanager.ui.storeinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.awesome.amumanager.R
import com.awesome.amumanager.api.Constants
import com.awesome.amumanager.api.response.ClientResponse
import com.awesome.amumanager.api.response.ReserveListResponse
import com.awesome.amumanager.api.service.GetClientInfoService
import com.awesome.amumanager.api.service.GetReserveListService
import com.awesome.amumanager.model.Client
import com.awesome.amumanager.model.Reserve
import com.awesome.amumanager.ui.ReserveDetailActivity
import com.awesome.amumanager.ui.adapter.ReserveListAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_reserve_list.*
import kotlinx.android.synthetic.main.fragment_reserve_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReserveListFragment() : Fragment() {

    private var reserves : ArrayList<Reserve> = ArrayList<Reserve>()
    private var reserveListAdapter: ReserveListAdapter? = null
    private var storeId: String? = ""
    private var clients : ArrayList<Client> = ArrayList<Client>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.reserve_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(), ReserveDetailActivity::class.java)
            intent.putExtra("client_name", reserveListAdapter!!.getItemClientName(position).toString())
            intent.putExtra("arrive", reserveListAdapter!!.getItemArrive(position).toString())
            intent.putExtra("phone", reserveListAdapter!!.getItemPhone(position).toString())
            intent.putExtra("request", reserveListAdapter!!.getItemRequest(position).toString())
            intent.putExtra("date", reserveListAdapter!!.getItemDate(position).toString())

            intent.putExtra("lat", reserveListAdapter!!.getItemLat(position).toString())
            intent.putExtra("lng", reserveListAdapter!!.getItemLng(position).toString())
            startActivity(intent)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reserve_list, container, false)
        storeId = arguments?.getString("store_id")
        getReserveList()

        return view
    }

    private fun getReserveList() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val joinApi = retrofit.create(GetReserveListService::class.java)
        Log.e("store_id check", storeId.toString())

        joinApi.getReserveList(storeId.toString())
            .enqueue(object : Callback<ReserveListResponse> {

                override fun onFailure(call: Call<ReserveListResponse>, t: Throwable) {
                    Log.e("Retrofit Reserve", "실패")
                    Log.e("Check", t.toString())
                }

                override fun onResponse(
                    call: Call<ReserveListResponse>,
                    response: Response<ReserveListResponse>
                )  {
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("ReserveList Retrofit" , "success")

                        reserves.addAll(response.body()!!.reserves)
                        getClientInfo()

                    } else {

                    }
                }
            })
    }
    private fun getClientInfo() {
        if(reserves.size != 0) {
            for(i in 0 until reserves.size) {
                clients.add(Client("","","","",""))
            }
            for (i in 0 until reserves.size) {
                val gson = GsonBuilder().setLenient().create()
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.serverUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

                val joinApi = retrofit.create(GetClientInfoService::class.java)

                Log.e("client id check", reserves[i].client_id.toString())
                joinApi.getClient(reserves[i].client_id.toString())
                    .enqueue(object : Callback<ClientResponse> {

                        override fun onFailure(
                            call: Call<ClientResponse>,
                            t: Throwable
                        ) {
                            Log.e("Retrofit GetClient", "실패")
                            Log.e("Check", t.toString())
                        }

                        override fun onResponse(
                            call: Call<ClientResponse>,
                            response: Response<ClientResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                                Log.e("Get Client Retrofit", "success")
                                clients[i]=response.body()!!.client
                                if(!clients.contains(Client("","","","",""))) {
                                    reserveListAdapter =
                                            ReserveListAdapter(
                                                    requireContext(), reserves,
                                                    clients
                                            )
                                    reserve_list.adapter = reserveListAdapter
                                }
                            } else {

                            }
                        }
                    })
            }
        }
    }
}