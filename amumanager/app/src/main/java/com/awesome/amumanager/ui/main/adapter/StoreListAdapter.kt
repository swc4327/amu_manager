package com.awesome.amumanager.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Store
import com.bumptech.glide.RequestManager


class StoreListAdapter(stores : ArrayList<Store>, requestManager : RequestManager): RecyclerView.Adapter<StoreViewHolder>() {

    private val stores = stores
    private val requestManager = requestManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent,false))
    }

    override fun getItemCount(): Int {
        return this.stores.size
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(this.stores[position], requestManager)
    }
}






//package com.awesome.amumanager.ui.main.adapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//import com.awesome.amumanager.R
//import com.awesome.amumanager.data.model.Store
//import com.bumptech.glide.Glide
//import kotlinx.android.synthetic.main.item_store.view.*
//
//class StoreListAdapter(val context: Context, val stores: ArrayList<Store>) : BaseAdapter() {
//    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
//        val view: View = LayoutInflater.from(context).inflate(R.layout.item_store, null)
//
//        Glide
//            .with(context)
//            .load(stores[p0].image)
//            .circleCrop()
//            .into(view.store_image)
//
//        view.store_name.setText(stores[p0].name)
//        return view
//    }
//
//    override fun getItem(position: Int): Store {
//        return stores[position]
//    }
//
//    override fun getItemId(position: Int): Long {
//        return 0
//    }
//
//    override fun getCount(): Int {
//        return stores.size
//    }
//}
//
