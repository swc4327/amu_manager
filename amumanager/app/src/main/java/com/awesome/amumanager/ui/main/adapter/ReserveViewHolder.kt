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
    private val reserveDate = itemView.date
    private val reserveStatus = itemView.reserve_status

    fun bind(reserveList: ReserveList, requestManager: RequestManager) {
        clientName.text = reserveList.client.nickname
        requestManager.load(reserveList.client.image).circleCrop().into(clientImage)
        reserveDate.text = reserveList.reserve.date

        when {
            reserveList.reserve.is_completed == "1" -> {
                reserveStatus.text = "완료된 예약"
            }
            reserveList.reserve.is_confirmed == "1" -> {
                reserveStatus.text = "확정된 예약"
            }
            else -> {
                reserveStatus.text = "예약 요청"
            }
        }

        itemView.setOnClickListener{ itemClick(reserveList)}

    }
}