package com.awesome.amumanager.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Client
import com.awesome.amumanager.data.model.Reserve
import com.awesome.amumanager.data.model.ReserveList
import com.awesome.amumanager.map.MapManager
import com.awesome.amumanager.ui.main.viewmodel.ReserveViewModel
import com.awesome.amumanager.ui.main.viewmodel.ReserveViewModelFactory
import kotlinx.android.synthetic.main.activity_reserve_detail.*

class ReserveDetailActivity : AppCompatActivity() {


    private var reserve : Reserve? = null
    private var client : Client? = null
    private var storeId : String? = null

    private var mapManager : MapManager? = null

    private lateinit var reserveViewModel : ReserveViewModel

    companion object {
        fun startActivity(activity : AppCompatActivity, reserveList : ReserveList, storeId: String) {
            val intent = Intent(activity, ReserveDetailActivity::class.java)
            intent.putExtra("reserve", reserveList.reserve)
            intent.putExtra("client", reserveList.client)
            intent.putExtra("storeId", storeId)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_detail)

        reserve = intent.getParcelableExtra("reserve")
        client = intent.getParcelableExtra("client")

        mapManager = MapManager(this)

        reserveViewModel = ViewModelProvider(this, ReserveViewModelFactory(storeId.toString())).get(ReserveViewModel::class.java)

        mapManager?.addMapListener(info_map_view)
        reserve?.lat?.toDouble()?.let {lat->
            reserve?.lng?.toDouble()?.let {lng ->
                mapManager?.setMap("고객의 예약위치", lat, lng) }
        }
        initLayout()
        initListener()

        observe()
    }

    private fun observe() {
        reserveViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                finish()
            }
        })
    }

    private fun initLayout() {
        detail_client_name.text = client?.nickname
        detail_client_phone.text = reserve?.phone
        detail_client_arrive.text = reserve?.arrive
        detail_client_request.text = reserve?.request
        detail_date.text = reserve?.date

        if(reserve?.is_confirmed == "0") {
            detail_reserve_complete.visibility = GONE
        } else {
            detail_reserve_cancel.visibility = GONE
            detail_reserve_confirm.visibility = GONE
        }

        if(reserve?.is_completed == "1") {
            detail_reserve_complete.visibility = GONE
            detail_reserve_cancel.visibility = GONE
            detail_reserve_confirm.visibility = GONE
        }
    }

    private fun initListener() {
        close_reserve_detail.setOnClickListener {
            finish()
        }

        //예약거부
        detail_reserve_cancel.setOnClickListener {
            reserveViewModel.cancelReserve(reserve?.id.toString())
        }

        //예약확정
        detail_reserve_confirm.setOnClickListener {
            reserveViewModel.confirmReserve(reserve?.id.toString())
        }

        //완료
        detail_reserve_complete.setOnClickListener {
            //이거 1이어야 리뷰 작성 가능
            reserveViewModel.completeReserve(reserve?.id.toString())
        }
    }
}