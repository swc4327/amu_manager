package com.awesome.amumanager.view.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.awesome.amumanager.R
import com.awesome.amumanager.model.Store
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_store.view.*

class StoreListAdapter(val context: Context, val stores: ArrayList<Store>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_store, null)

        Glide
            .with(context)
            .load(stores[p0].image)
            .circleCrop()
            .into(view.store_image)

        view.store_name.setText(stores[p0].name)
        return view
    }

    override fun getItem(position: Int): Store {
        return stores[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return stores.size
    }
}

