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

    fun update(reserveLists: ArrayList<ReserveList>) {
        if(this.reserveLists.isNotEmpty())
            this.reserveLists.clear()
        this.reserveLists.addAll(reserveLists)
        notifyDataSetChanged()
    }
}
