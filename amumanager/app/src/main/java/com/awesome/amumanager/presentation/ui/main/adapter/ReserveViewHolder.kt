package com.awesome.amumanager.presentation.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.domain.model.ReserveList
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_reserve.view.*

class ReserveViewHolder(itemView: View, private val itemClick: (ReserveList) -> Unit): RecyclerView.ViewHolder(itemView) {

    fun bind(reserveList: ReserveList, requestManager: RequestManager) {
        itemView.client_name.text = reserveList.client.nickname
        requestManager.load(reserveList.client.image).circleCrop().into(itemView.client_image)
        itemView.date.text = reserveList.reserve.date

        when {
            reserveList.reserve.isCompleted == "1" -> {
                itemView.reserve_status.text = "완료된 예약"
            }
            reserveList.reserve.isConfirmed == "1" -> {
                itemView.reserve_status.text = "확정된 예약"
            }
            else -> {
                itemView.reserve_status.text = "예약 요청"
            }
        }

        itemView.setOnClickListener{ itemClick(reserveList)}

    }
}