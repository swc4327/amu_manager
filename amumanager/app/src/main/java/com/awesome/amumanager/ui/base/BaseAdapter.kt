package com.awesome.amumanager.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager


abstract class BaseAdapter <T>(private val list: ArrayList<T>,
                               private val requestManager: RequestManager,
                               private val container: Int,
                               private val itemClick: (T) -> Unit)
    : RecyclerView.Adapter<BaseViewHolder<T>>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(container, parent, false)
        return BaseViewHolder<T>(view, itemClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(list[position], requestManager)
    }

    abstract fun getLastId(position: Int) : String

    fun clearList() {
        if(this.list.isNotEmpty()) {
            this.list.clear()
            notifyDataSetChanged()
        }
    }

    fun update(list: ArrayList<T>) {
        val endPosition = this.list.size

        if(endPosition < list.size) {
            loadMore(list, endPosition)
        }
    }

    private fun loadMore(
        list: ArrayList<T>,
        endPosition: Int
    ) {
        if (this.list.isEmpty()) {
            this.list.addAll(list)
        } else {
            for (index in endPosition until list.size) {
                this.list.add(index, list[index])
            }
        }
        notifyItemRangeInserted(endPosition, this.list.size - endPosition)
    }
}