package com.awesome.amumanager.presentation.ui.main.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.domain.model.Store
import com.awesome.amumanager.presentation.util.map.MapManager
import com.awesome.amumanager.presentation.ui.base.BaseActivity
import com.awesome.amumanager.presentation.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.StoreViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_add_store.*
import javax.inject.Inject

class AddStoreActivity : BaseActivity() {
    private var lat : Double? = null
    private var lng : Double? = null
    private var kind : String? = null
    private var mapManager : MapManager? = null

    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    private lateinit var firebaseViewModel : FirebaseViewModel
    private lateinit var storeViewModel : StoreViewModel

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000;
        //Permission code
        private const val PERMISSION_CODE = 1001;

        fun startActivityForResult(activity : BaseActivity, requestCode: Int) {
            val intent = Intent(activity, AddStoreActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_store)
        mapManager = MapManager(this)
        initLayout()
        initListener()
        firebaseViewModel = ViewModelProvider(this, viewModelFactory).get(FirebaseViewModel::class.java)
        storeViewModel = ViewModelProvider(this, viewModelFactory).get(StoreViewModel::class.java)
        observe()
    }

    private fun initLayout() {
        val spinnerItem = listOf("업체구분","노래방","스크린야구장","볼링장")
        val spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerItem)
        store_spinner.adapter = spinnerAdapter

        //setMap()
        mapManager?.addMapListener(map_view)
    }

    private fun initListener() {
        close_add_store.setOnClickListener {
            finish()
        }
        search_place.setOnClickListener {
            val location = mapManager?.getLocation(store_place.text.toString(), "업체위치")
            this.lat = location?.first
            this.lng = location?.second
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
                            PERMISSION_CODE
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
                    kind?.let { kind ->
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
                                null,
                            "0"
                        )
                    }
                }
            }
            if (store != null) {
                storeViewModel.addStore(store)
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

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
                    pickImageFromGallery()
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