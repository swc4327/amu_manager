package com.awesome.amumanager.ui.main.adapter

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.data.model.ReserveList
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_reserve.view.*

class ReserveViewHolder(itemView: View, private val itemClick: (ReserveList) -> Unit): RecyclerView.ViewHolder(itemView) {

    private val clientName = itemView.client_name
    private val clientImage = itemView.client_image
    private val reservePhone = itemView.phone
    private val reserveDate = itemView.date

    fun bind(reserveList: ReserveList, requestManager: RequestManager) {
        clientName.text = reserveList.client.nickname
        requestManager.load(reserveList.client.image).circleCrop().into(clientImage)
        reservePhone.text = reserveList.reserve.phone
        reserveDate.text = reserveList.reserve.date

        when {
            reserveList.reserve.is_completed == "1" -> {
                itemView.setBackgroundColor(Color.parseColor("#E16A9D"))
            }
            reserveList.reserve.is_confirmed == "1" -> {
                itemView.setBackgroundColor(Color.parseColor("#B4FBFF"))
            }
            else -> {
                itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }

        itemView.setOnClickListener{ itemClick(reserveList)}

    }
}