package com.awesome.amumanager.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Store
import com.awesome.amumanager.ui.main.adapter.StoreAdapter
import com.awesome.amumanager.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amumanager.ui.main.viewmodel.StoreViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_bottom.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var storeAdapter: StoreAdapter? = null

    private lateinit var storeViewModel : StoreViewModel
    private lateinit var firebaseViewModel: FirebaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class.java)
        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

        observe()
        initListener()

        storeViewModel.getStore(firebaseViewModel.getUid(), "0")
    }


    private fun observe() {
        storeViewModel.stores.observe(this, Observer<ArrayList<Store>> { stores ->
            if (storeAdapter == null) {
                storeAdapter = StoreAdapter(arrayListOf() , Glide.with(this)) { store ->
                    val intent = Intent(this, StoreInfoActivity::class.java)
                    intent.putExtra("store", store)
                    startActivity(intent)
                }
                store_list.adapter = storeAdapter
            }
            println("AAAA"+storeAdapter!!.itemCount.toString())
            storeAdapter!!.update(stores)
            })
    }

    private fun initListener() {
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

        store_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                println(lastVisibleItemPosition.toString()+"~!~!~!")

                if (lastVisibleItemPosition == itemTotalCount) {
                    //storeViewModel.getStore(firebaseViewModel.getUid(), recyclerView.adapter!!.itemCount.toString())
                    storeViewModel.getStore(firebaseViewModel.getUid(), storeAdapter!!.getLastStoreId(lastVisibleItemPosition))
                    println(storeAdapter!!.getLastStoreName(lastVisibleItemPosition))
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode ==0) {
            if(resultCode == RESULT_OK) {
                storeViewModel.getStore(firebaseViewModel.getUid(), "0")
            }
        }
    }
}