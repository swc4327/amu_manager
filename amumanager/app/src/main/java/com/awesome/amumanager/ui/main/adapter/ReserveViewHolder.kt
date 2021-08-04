package com.awesome.amumanager.ui.main.adapter

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.data.model.ReserveList
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_reserve.view.*

class ReserveViewHolder(itemView: View, private val itemClick: (ReserveList) -> Unit): RecyclerView.ViewHolder(itemView) {

    fun bind(reserveList: ReserveList, requestManager: RequestManager) {
        itemView.client_name.text = reserveList.client.nickname
        requestManager.load(reserveList.client.image).circleCrop().into(itemView.client_image)
        itemView.date.text = reserveList.reserve.date

        when {
            reserveList.reserve.is_completed == "1" -> {
                itemView.reserve_status.text = "완료된 예약"
            }
            reserveList.reserve.is_confirmed == "1" -> {
                itemView.reserve_status.text = "확정된 예약"
            }
            else -> {
                itemView.reserve_status.text = "예약 요청"
            }
        }

        itemView.setOnClickListener{ itemClick(reserveList)}

    }
}