package com.awesome.amumanager.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.ReserveList
import com.bumptech.glide.RequestManager

class ReserveAdapter(private val reserveLists : ArrayList<ReserveList>,
                     private val requestManager : RequestManager,
                     private val itemClick: (ReserveList) -> Unit)
    : RecyclerView.Adapter<ReserveViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReserveViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reserve, parent, false)
        return ReserveViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return reserveLists.size
    }

    override fun onBindViewHolder(holder: ReserveViewHolder, position: Int) {
        holder.bind(reserveLists[position], requestManager)
        }

    fun clearReserveLists() {
        if(this.reserveLists.isNotEmpty()) {
            this.reserveLists.clear()
            notifyDataSetChanged()
        }
    }

    fun getLastReserveId(position : Int) : String {
        return this.reserveLists[position].reserve.id.toString()
    }

    fun update(reserveLists: ArrayList<ReserveList>) {
        val endPosition = this.reserveLists.size

        //계속 불러오는중
        if(endPosition < reserveLists.size) {
            loadMore(reserveLists, endPosition)
        }
//        else { //삭제됬거나, 디테일 변경 됬을 때
//            if(this.reserveLists.isNotEmpty())
//                this.reserveLists.clear()
//            this.reserveLists.addAll(reserveLists)
//            notifyDataSetChanged()
//        }
    }

    private fun loadMore(reserveLists: ArrayList<ReserveList>, endPosition: Int) {
        if (this.reserveLists.isEmpty()) {
            this.reserveLists.addAll(reserveLists)
        } else {
            for (index in endPosition until reserveLists.size) {
                this.reserveLists.add(index, reserveLists[index])
            }
        }
        notifyItemRangeInserted(endPosition, this.reserveLists.size - endPosition)
    }
}

//취소, 예약확정, 완료
//취소, 예약확정
//예약확정 -> 완료