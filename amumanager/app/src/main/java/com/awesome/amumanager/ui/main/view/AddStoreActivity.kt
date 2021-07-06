package com.awesome.amumanager.ui.main.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.awesome.amumanager.R
import com.awesome.amumanager.data.api.Constants
import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.service.AddStoreService
import com.awesome.amumanager.data.model.Store
import com.awesome.amumanager.util.FirebaseUtils
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_add_store.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

class AddStoreActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private var lat : Double? = null
    private var lng : Double? = null
    private var mapView : MapView? = null
    private var kind : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_store)

        val spinnerItem = listOf("업체구분","노래방","스크린야구장","볼링장")
        val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerItem)
        store_spinner.adapter = spinnerAdapter

        store_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {
                        //에러처리
                    }
                    1 -> {
                        kind = "노래방"
                        println(kind)
                    }
                    2 -> {
                        kind = "스크린야구장"
                        println(kind)
                    }
                    3 -> {
                        kind = "볼링장"
                        println(kind)
                    }
                }
            }
        }


        auth = FirebaseAuth.getInstance()

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
        map_view.addView(mapView)


        close_add_store.setOnClickListener {
            finish()
        }

        search_place.setOnClickListener {
            searchPlace()
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



        add_store_button.setOnClickListener {
            val bitmap = (add_store_image.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            //"uid_profile"
            var task = FirebaseStorage.getInstance().getReference()
                .child(FirebaseUtils.getUid() + store_name.text.toString())
            val uploadTask = task.putBytes(data)

            uploadTask.addOnFailureListener {
                Toast.makeText(this, "업로드실패", Toast.LENGTH_LONG).show()
            }
                .addOnSuccessListener {
                    Toast.makeText(this, "업로드성공", Toast.LENGTH_LONG).show()
                    task.downloadUrl
                        .addOnCompleteListener{ task ->
                            Log.e("Add Store Url Check", task.result.toString())
                            addStore(task)
                        }
                }
        }
    }

    private fun searchPlace() {
        var geoCoder = Geocoder(this)
        var result : List<Address> = geoCoder.getFromLocationName(store_place.text.toString(),1)
        lat = result[0].latitude
        lng = result[0].longitude
        Log.e("lat check", lat.toString() + lng.toString())
        mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat!!, lng!!),true)
        var marker = MapPOIItem()
        marker.setItemName("업체위치")
        marker.setTag(0)
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat!!, lng!!))
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin)
        mapView!!.addPOIItem(marker);
    }

    private fun addStore(task: Task<Uri>) {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val joinApi = retrofit.create(AddStoreService::class.java)


        val manager_uid = FirebaseUtils.getUid()
        val name = store_name.text.toString()
        //업체주소(위도경도), 상세주소,

        val store = Store(
            null,
            name,
            task.result.toString(),
            manager_uid,
                lat!!.toString(),
                lng!!.toString(),
                store_place.text.toString(),
                store_place_detail.text.toString(),
                kind,
                null,
                null
        )

        joinApi.addStore(store)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("Retrofit Add Store", "실패")
                    Log.e("Check", t.toString())
                }
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                )  {
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("AddStoreActivity", "success")
                        setResult(Activity.RESULT_OK)
                        finish()

                    } else {
                        Log.e("AddStoreActivity", "실패")
                    }
                }
            })

    }
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
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
