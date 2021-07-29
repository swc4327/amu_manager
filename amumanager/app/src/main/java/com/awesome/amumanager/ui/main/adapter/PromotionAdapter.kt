package com.awesome.amumanager.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Promotion
import com.bumptech.glide.RequestManager

class PromotionAdapter(private val promotions : ArrayList<Promotion>,
                   private val requestManager : RequestManager)
    : RecyclerView.Adapter<PromotionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_promotion, parent, false)
        return PromotionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return promotions.size
    }

    override fun onBindViewHolder(holder: PromotionViewHolder, position: Int) {
        holder.bind(promotions[position], requestManager)
    }

    fun update(promotions: ArrayList<Promotion>) {
        if(this.promotions.isNotEmpty())
            this.promotions.clear()
        this.promotions.addAll(promotions)
        notifyDataSetChanged()
    }
}