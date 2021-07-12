package com.awesome.amumanager.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.data.model.Store
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_store.view.*

class StoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val storeName = itemView.store_name
    private val storeImage = itemView.store_image

    fun bind(store : Store, requestManager : RequestManager) {
        storeName.text = store.name
        requestManager.load(store.image).circleCrop().into(storeImage)
    }
}