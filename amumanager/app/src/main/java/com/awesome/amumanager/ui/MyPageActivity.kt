package com.awesome.amumanager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.awesome.amumanager.MainActivity
import com.awesome.amumanager.R
import com.awesome.amumanager.util.FirebaseUtils
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.main_bottom.*

class MyPageActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        auth = FirebaseAuth.getInstance()
        val docRef = db.collection("managers")
            .document(auth.currentUser?.uid.toString())

        docRef.get().addOnSuccessListener { documentSnapshot ->
            nickname_area.setText(documentSnapshot.get("nickname").toString())
        }

        close_my_page.setOnClickListener {
            finish()
        }

        mypage_logout_button.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val ref = FirebaseStorage.getInstance()
            .getReference(FirebaseUtils.getUid()+"_profile")

        Log.e("image file url check", ref.toString())

        ref.downloadUrl
            .addOnCompleteListener(OnCompleteListener { task ->
                Log.e("task check", task.result.toString())
                if(task.isSuccessful) {
                    Glide.with(this)
                        .load(task.result)
                        .into(profile_img)
                } else {
                    Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
                }
            })
    }
}









//    private fun pickImageFromGallery() {
//        //Intent to pick image
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent,
//            IMAGE_PICK_CODE
//        )
//    }

//    companion object {
//        //image pick code
//        private val IMAGE_PICK_CODE = 1000;
//        //Permission code
//        private val PERMISSION_CODE = 1001;
//    }

//handle requested permission result
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when(requestCode){
//            PERMISSION_CODE -> {
//                if (grantResults.size > 0 && grantResults[0] ==
//                    PackageManager.PERMISSION_GRANTED){
//                    //permission from popup granted
//                    pickImageFromGallery()
//                }
//                else{
//                    //permission from popup denied
//                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

//handle result of picked image
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
//            profile_img.setImageURI(data?.data)
//        }
//    }

//        profile_img.setOnClickListener {
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
//                    PackageManager.PERMISSION_DENIED) {
//                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
//                    requestPermissions(permissions,
//                        PERMISSION_CODE
//                    )
//                }
//                else {
//                    pickImageFromGallery()
//                }
//            }
//            else {
//                pickImageFromGallery()
//            }
//        }



//        profile_img_edit.setOnClickListener {
//            val bitmap = (profile_img.drawable as BitmapDrawable).bitmap
//            val baos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//            val data = baos.toByteArray()
//
//            //"uid_profile"
//            var uploadTask = FirebaseStorage.getInstance().getReference()
//                .child(FirebaseUtils.getUid() + "_profile").putBytes(data)
//
//            uploadTask.addOnFailureListener {
//                Toast.makeText(this, "업로드실패", Toast.LENGTH_LONG).show()
//            }
//                .addOnSuccessListener {
//                    Toast.makeText(this, "업로드성공", Toast.LENGTH_LONG).show()
//                }
//        }
