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

    fun clearPromotions() {
        this.promotions.clear()
    }

    fun getLastPromotionId(position: Int) : String {
        return this.promotions[position].id.toString()
    }

    fun update(promotions: ArrayList<Promotion>) {
        val endPosition = this.promotions.size

        println("endPosition:$endPosition")
        println("promotions.size:"+ promotions.size.toString())

        if(endPosition < promotions.size) {
            loadMore(promotions, endPosition)
        }
    }

    private fun loadMore(promotions: ArrayList<Promotion>, endPosition: Int) {
        if (this.promotions.isEmpty()) {
            this.promotions.addAll(promotions)
        } else {
            for (index in endPosition until promotions.size) {
                this.promotions.add(index, promotions[index])
            }
        }
        notifyItemRangeInserted(endPosition, this.promotions.size - endPosition)
    }
}