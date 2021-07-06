package com.awesome.amumanager.view.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.awesome.amumanager.R
import com.awesome.amumanager.api.Constants
import com.awesome.amumanager.api.response.DefaultResponse
import com.awesome.amumanager.api.service.AddMenuService
import com.awesome.amumanager.model.Menu
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_add_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

class AddMenuActivity : AppCompatActivity() {

    var store_id : String = ""
    var name : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        store_id = intent.getStringExtra("store_id").toString()
        name = intent.getStringExtra("name").toString()

        println("Store Id Check!!" + store_id)
        println(name)

        add_menu_image.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions,
                        AddMenuActivity.PERMISSION_CODE
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

        close_add_menu.setOnClickListener {
            finish()
        }

        add_menu_button.setOnClickListener {
            val bitmap = (add_menu_image.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()


            var task = FirebaseStorage.getInstance().getReference()
                .child(store_id.toString()+"_"+menu_name.text.toString())
            val uploadTask = task.putBytes(data)

            uploadTask.addOnFailureListener {
                Toast.makeText(this, "업로드실패", Toast.LENGTH_LONG).show()
            }
                .addOnSuccessListener {
                    Toast.makeText(this, "업로드성공", Toast.LENGTH_LONG).show()
                    task.downloadUrl
                        .addOnCompleteListener(OnCompleteListener { task ->
                            Log.e("Add Menu Url Check", task.result.toString())
                            addMenu(task)
//                            val intent = Intent(this, StoreInfoActivity::class.java)
//                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                            intent.putExtra("name", name)
//                            intent.putExtra("store_id", store_id)
//                            startActivity(intent)
                        })
                }

        }
    }

    private fun addMenu(
        task: Task<Uri>
    ) {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val joinApi = retrofit.create(AddMenuService::class.java)


        val menu = Menu(
            null,
            menu_name.text.toString(),
            task.result.toString(),
            menu_price.text.toString(),
            menu_comment.text.toString(),
            store_id
        )

        joinApi.addMenu(menu)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("Retrofit Add Menu", "실패")
                    Log.e("Check", t.toString())
                }
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                )  {
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("AddMenuActivity", "success")
                        setResult(Activity.RESULT_OK)
                        finish()


                    } else {
                        Log.e("AddMenuActivity", "실패")
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
            add_menu_image.setImageURI(data?.data)
        }
    }
}