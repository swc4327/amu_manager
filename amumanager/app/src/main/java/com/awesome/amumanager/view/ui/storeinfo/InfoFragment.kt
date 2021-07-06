package com.awesome.amumanager.view.ui.storeinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.awesome.amumanager.R
import kotlinx.android.synthetic.main.fragment_info.view.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class InfoFragment() : Fragment() {

    private var lat: String? = ""
    private var lng: String? = ""
    private var place_detail: String? = ""
    private var place : String? = ""
    private var mapView : MapView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        lat = arguments?.getString("lat")
        lng = arguments?.getString("lng")
        place = arguments?.getString("place")
        place_detail = arguments?.getString("place_detail")


        mapView = MapView(requireContext())
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
        view.info_map_view.addView(mapView)
        view.info_place.setText(place)
        view.info_place_detail.setText(place_detail)


        mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat!!.toDouble(), lng!!.toDouble()),true)
        var marker = MapPOIItem()
        marker.setItemName("업체위치")
        marker.setTag(0)
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat!!.toDouble(), lng!!.toDouble()))
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin)
        //marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView!!.addPOIItem(marker);
        return view
    }

}