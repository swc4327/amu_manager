package com.awesome.amumanager.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Store
import com.awesome.amumanager.ui.main.view.ui.*
import com.awesome.amumanager.ui.main.adapter.StoreListAdapter
import com.awesome.amumanager.ui.main.viewmodel.StoreViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_bottom.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var storeListAdapter: StoreListAdapter? = null
    private lateinit var storeViewModel : StoreViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        //auth.signOut()

        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class.java)
        storeViewModel.getStoreList()

        storeViewModel.storeList.observe(this, Observer<ArrayList<Store>> {
            storeListAdapter = StoreListAdapter(this, it)
            store_list.adapter = storeListAdapter
        })

        my_page.setOnClickListener{
            if(auth.currentUser == null) { //로그인 no
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else { //로그인 ok
                val intent = Intent(this, MyPageActivity::class.java)
                startActivity(intent)
            }
        }

        add_store.setOnClickListener {
            if(auth.currentUser == null) { //로그인 no
                Toast.makeText(this, "로그인 후 이용하세요!!", Toast.LENGTH_LONG).show()
            } else { //로그인 ok
                val intent = Intent(this, AddStoreActivity::class.java)
                startActivityForResult(intent, 0)
            }
        }

        store_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, StoreInfoActivity::class.java)
            intent.putExtra("store", storeListAdapter!!.getItem(position))
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode ==0) {
            if(resultCode == RESULT_OK) {
                storeViewModel.getStoreList()
            }
        }
    }
}