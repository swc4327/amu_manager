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
import com.awesome.amumanager.data.model.Constants.ADD_STORE_ACTIVITY
import com.awesome.amumanager.data.model.Constants.FIRST_CALL
import com.awesome.amumanager.data.model.Constants.STORE_INFO_ACTIVITY
import com.awesome.amumanager.ui.main.adapter.StoreAdapter
import com.awesome.amumanager.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amumanager.ui.main.viewmodel.StoreViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_bottom.*

class MainActivity : AppCompatActivity() {

    private var storeAdapter: StoreAdapter? = null

    private lateinit var storeViewModel : StoreViewModel
    private lateinit var firebaseViewModel: FirebaseViewModel

    companion object {
        fun startActivity(activity : AppCompatActivity) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class.java)
        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

        //firebaseViewModel.logout()

        observe()
        initListener()

        storeViewModel.getStore(firebaseViewModel.getUid(), FIRST_CALL)

    }


    private fun observe() {
        storeViewModel.stores.observe(this, Observer<ArrayList<Store>> { stores ->
            if (storeAdapter == null) {
                storeAdapter = StoreAdapter(arrayListOf() , Glide.with(this)) { store ->
                    StoreInfoActivity.startActivityForResult(this, store, STORE_INFO_ACTIVITY)
                }
                store_list.adapter = storeAdapter
            }
            storeAdapter?.update(stores)
            })
    }

    private fun initListener() {
        my_page.setOnClickListener{
            if(!firebaseViewModel.isLoggedIn()) { //로그인 no
                LoginActivity.startActivity(this)
            } else { //로그인 ok
                MyPageActivity.startActivity(this)
            }
        }

        add_store.setOnClickListener {
            if(!firebaseViewModel.isLoggedIn()) { //로그인 no
                Toast.makeText(this, "로그인 후 이용하세요!!", Toast.LENGTH_LONG).show()
            } else { //로그인 ok
                AddStoreActivity.startActivityForResult(this, ADD_STORE_ACTIVITY)
            }
        }

        store_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()

                if (!recyclerView.canScrollVertically((1)) && lastVisibleItemPosition >= 0) {
                    //storeViewModel.getStore(firebaseViewModel.getUid(), recyclerView.adapter!!.itemCount.toString())


                    storeAdapter?.getLastStoreId(lastVisibleItemPosition)?.let { storeViewModel.getStore(firebaseViewModel.getUid(), it) }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_STORE_ACTIVITY) { //가게추가
            if(resultCode == RESULT_OK) {
                storeAdapter?.clearStores()
                storeViewModel.getStore(firebaseViewModel.getUid(), FIRST_CALL)
            }
        } else if(requestCode == STORE_INFO_ACTIVITY) { //StoreInfo - 삭제, 영업시작
            if(resultCode == RESULT_OK) {
                storeAdapter?.clearStores()
                storeViewModel.getStore(firebaseViewModel.getUid(), FIRST_CALL)
            }
        }
    }
}