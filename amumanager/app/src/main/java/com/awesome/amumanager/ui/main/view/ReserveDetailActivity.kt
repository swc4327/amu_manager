package com.awesome.amumanager.ui.main.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Client
import com.awesome.amumanager.data.model.Reserve
import com.awesome.amumanager.ui.main.viewmodel.ReserveViewModel
import com.awesome.amumanager.ui.main.viewmodel.ReserveViewModelFactory
import kotlinx.android.synthetic.main.activity_reserve_detail.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class ReserveDetailActivity : AppCompatActivity() {


    private var mapView : MapView? = null
    private var reserve : Reserve? = null
    private var client : Client? = null
    private var storeId : String? = null

    private lateinit var reserveViewModel : ReserveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_detail)

        reserve = intent.getParcelableExtra("reserve")
        client = intent.getParcelableExtra("client")

        var factory = ReserveViewModelFactory(storeId.toString())
        reserveViewModel = ViewModelProvider(this, factory).get(ReserveViewModel::class.java)

        setMap()
        initLayout()
        initListener()

        observe()
    }

    private fun observe() {
        reserveViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        })
    }

    private fun initLayout() {
        detail_client_name.setText(client!!.nickname)
        detail_client_phone.setText(reserve!!.phone)
        detail_client_arrive.setText(reserve!!.arrive)
        detail_client_request.setText(reserve!!.request)
        detail_date.setText(reserve!!.date)
        setClientLocation()

        if(reserve!!.is_confirmed == "0") {
            detail_reserve_complete.visibility = GONE
        } else {
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
            reserveViewModel.cancelReserve(reserve!!.id.toString())
        }

        //예약확정
        detail_reserve_confirm.setOnClickListener {
            reserveViewModel.confirmReserve(reserve!!.id.toString())
        }

        //완료
        detail_reserve_complete.setOnClickListener {
            //이거 1이어야 리뷰 작성 가능
            reserveViewModel.completeReserve(reserve!!.id.toString())
        }
    }

    private fun setClientLocation() {
        mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(reserve!!.lat!!.toDouble(), reserve!!.lng!!.toDouble()),true)
        var marker = MapPOIItem()
        marker.setItemName("고객의 예약위치")
        marker.setTag(0)
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(reserve!!.lat!!.toDouble(), reserve!!.lng!!.toDouble()))
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin)
        mapView!!.addPOIItem(marker)
    }

    private fun setMap() {
        mapView = MapView(this)
        mapView!!.setMapViewEventListener(object : MapView.MapViewEventListener {
            override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
                println("onMapViewDoubleTapped")
            }

            override fun onMapViewInitialized(p0: MapView?) {
                println("onMapViewInitialized")
            }

            override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
                println("onMapViewDragStarted")
            }

            override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
                println("onMapViewMoveFinished")
            }

            override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
                println("onMapViewCenterPointMoved")
            }

            override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
                println("onMapViewDragEnded")
            }

            override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
                println("onMapViewSingleTapped")
            }

            override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
                println("onMapViewZoomLevelChanged")
            }

            override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
                println("onMapViewLongPressed")
            }

        })
        info_map_view.addView(mapView)
    }
}