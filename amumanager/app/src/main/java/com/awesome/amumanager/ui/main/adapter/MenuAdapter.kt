package com.awesome.amumanager.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Menu
import com.bumptech.glide.RequestManager

class MenuAdapter(private val menus : ArrayList<Menu>,
                  private val requestManager : RequestManager,
                  private val itemClick : (Menu)->Unit)
    : RecyclerView.Adapter<MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menus[position], requestManager)
    }

    fun clearMenus() {
        if(this.menus.isNotEmpty()) {
            this.menus.clear()
            notifyDataSetChanged()
        }
    }

    fun getLastMenuId(position: Int) : String {
        return this.menus[position].id.toString()
    }

    fun update(menus: ArrayList<Menu>) {
        val endPosition = this.menus.size
        loadMore(menus, endPosition)
    }

    private fun loadMore(
        menus: ArrayList<Menu>,
        endPosition: Int
    ) {
        this.menus.addAll(menus)
        notifyItemRangeInserted(endPosition, this.menus.size - endPosition)

    }
}