package com.awesome.amumanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.awesome.amumanager.R
import kotlinx.android.synthetic.main.activity_reserve_detail.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class ReserveDetailActivity : AppCompatActivity() {

    private var client_name = ""
    private var phone = ""
    private var arrive = ""
    private var request = ""
    private var date: String? = null

    private var lat: Double? = null
    private var lng: Double? = null
    private var mapView : MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_detail)

        client_name = intent.getStringExtra("client_name").toString()
        phone = intent.getStringExtra("phone").toString()
        arrive = intent.getStringExtra("arrive").toString()
        request = intent.getStringExtra("request").toString()
        date = intent.getStringExtra("date").toString()

        lat = intent.getStringExtra("lat")!!.toDouble()
        lng = intent.getStringExtra("lng")!!.toDouble()

        detail_client_name.setText(client_name)
        detail_client_phone.setText(phone)
        detail_client_arrive.setText(arrive)
        detail_client_request.setText(request)
        detail_date.setText(date)


        mapView = MapView(this)
        mapView!!.setMapViewEventListener(object : MapView.MapViewEventListener {
            override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewInitialized(p0: MapView?) {
                println("onMapViewInitialized")
            }

            override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
                println("onMapViewMoveFinished")
            }

            override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

        })
        info_map_view.addView(mapView)
        mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat!!.toDouble(), lng!!.toDouble()),true)
        var marker = MapPOIItem()
        marker.setItemName("고객의 예약위치")
        marker.setTag(0)
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat!!.toDouble(), lng!!.toDouble()))
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin)
        //marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView!!.addPOIItem(marker)


        close_reserve_detail.setOnClickListener {
            finish()
        }

        detail_reserve_cancel.setOnClickListener {
            //취소처리
            //transaction, reservelist 에서 삭제
            finish()
        }

        detail_reserve_complete.setOnClickListener {
            //완료처리
            //reservelist에서 삭제
            finish()
        }
    }
}