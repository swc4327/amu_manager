package com.awesome.amumanager.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.data.model.Promotion
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_promotion.view.*


class PromotionViewHolder(itemView: View, private val endButtonClick: (String) -> Unit): RecyclerView.ViewHolder(itemView) {

    fun bind(promotion : Promotion , requestManager : RequestManager) {
        itemView.store_name.text = promotion.storeName
        itemView.date.text = promotion.date
        itemView.message.text = promotion.message
        itemView.end_promotion.setOnClickListener { endButtonClick(promotion.id.toString())}
    }
}