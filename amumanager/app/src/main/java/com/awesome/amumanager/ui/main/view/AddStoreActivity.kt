package com.awesome.amumanager.ui.main.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Store
import com.awesome.amumanager.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amumanager.ui.main.viewmodel.StoreViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_add_store.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class AddStoreActivity : AppCompatActivity() {
    private var lat : Double? = null
    private var lng : Double? = null
    private var mapView : MapView? = null
    private var kind : String? = null

    private lateinit var firebaseViewModel : FirebaseViewModel
    private lateinit var storeViewModel : StoreViewModel

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000;
        //Permission code
        private const val PERMISSION_CODE = 1001;

        fun startActivityForResult(activity : AppCompatActivity, requestCode: Int) {
            val intent = Intent(activity, AddStoreActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_store)
        //auth = FirebaseAuth.getInstance()

        initLayout()
        initListener()

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

        observe()
    }

    private fun initLayout() {
        val spinnerItem = listOf("업체구분","노래방","스크린야구장","볼링장")
        val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerItem)
        store_spinner.adapter = spinnerAdapter

        setMap()
    }

    private fun initListener() {
        close_add_store.setOnClickListener {
            finish()
        }
        search_place.setOnClickListener {
            searchPlace()
        }

        add_store_button.setOnClickListener {
            firebaseViewModel.uploadTask(add_store_image.drawable as BitmapDrawable, store_name.text.toString())
        }

        store_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {}
                    1 -> kind = "노래방"
                    2 -> kind = "스크린야구장"
                    3 -> kind = "볼링장"
                }
            }
        }

        add_store_image.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions,
                            AddStoreActivity.PERMISSION_CODE
                    )
                }
                else {
                    pickImageFromGallery()
                }
            }
            else {
                pickImageFromGallery()
            }
        }
    }

    private fun observe() {
        storeViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        })

        firebaseViewModel.taskToString.observe(this, Observer<String> {
            val manager_uid = firebaseViewModel.getUid()
            val name = store_name.text.toString()
            val store = lat?.toString()?.let { lat ->
                lng?.toString()?.let { lng ->
                    Store(
                            null,
                            name,
                            it,
                            manager_uid,
                            lat,
                            lng,
                            store_place.text.toString(),
                            store_place_detail.text.toString(),
                            kind,
                            null,
                            null
                    )
                }
            }
            if (store != null) {
                storeViewModel.addStore(store)
            }
        })
    }

    private fun searchPlace() {
        var geoCoder = Geocoder(this)
        var result : List<Address> = geoCoder.getFromLocationName(store_place.text.toString(),1)
        lat = result[0].latitude
        lng = result[0].longitude
        Log.e("lat check", lat.toString() + lng.toString())
        mapView?.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat!!, lng!!),true)
        var marker = MapPOIItem()
        marker.itemName = "업체위치"
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(lat!!, lng!!)
        marker.markerType = MapPOIItem.MarkerType.BluePin
        mapView?.addPOIItem(marker);
    }

    private fun setMap() {
        mapView = MapView(this)
        this.mapView?.setMapViewEventListener(object : MapView.MapViewEventListener {
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
        map_view?.addView(mapView)
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            add_store_image.setImageURI(data?.data)
        }
    }
}