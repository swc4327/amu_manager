package com.awesome.amumanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.awesome.amumanager.R
import com.awesome.amumanager.model.Client
import com.awesome.amumanager.model.Reserve
import kotlinx.android.synthetic.main.activity_reserve_detail.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class ReserveDetailActivity : AppCompatActivity() {


    private var mapView : MapView? = null
    private var reserve : Reserve? = null
    private var client : Client? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_detail)

        reserve = intent.getParcelableExtra("reserve")
        client = intent.getParcelableExtra("client")


        detail_client_name.setText(client!!.nickname)
        detail_client_phone.setText(reserve!!.phone)
        detail_client_arrive.setText(reserve!!.arrive)
        detail_client_request.setText(reserve!!.request)
        detail_date.setText(reserve!!.date)


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
        mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(reserve!!.lat!!.toDouble(), reserve!!.lng!!.toDouble()),true)
        var marker = MapPOIItem()
        marker.setItemName("고객의 예약위치")
        marker.setTag(0)
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(reserve!!.lat!!.toDouble(), reserve!!.lng!!.toDouble()))
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin)
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