package com.awesome.amumanager.ui.main.adapter

import com.awesome.amumanager.data.model.Menu
import com.awesome.amumanager.ui.base.BaseAdapter
import com.bumptech.glide.RequestManager
import java.util.ArrayList

class MenuAdapter(private val list: ArrayList<Menu>, requestManager: RequestManager, container: Int, private val itemClick: (Menu)->Unit)
    : BaseAdapter<Menu>(list, requestManager, container, itemClick) {

    override fun getLastId(position: Int): String {
        return this.list[position].id.toString()
    }

//    inner class MenuViewHolder(itemView: View, itemClick: (Menu)->Unit) : BaseViewHolder<Menu>(itemView, itemClick) {
//
//        override fun bind(menu : Menu, requestManager: RequestManager) {
//            super.bind(menu, requestManager)
//            itemView.menu_name.text = menu.name
//            requestManager.load(menu.image).circleCrop().diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true).into(itemView.menu_image)
//            itemView.menu_comment.text = menu.comment
//            itemView.menu_price.text = menu.price
//
//
//            itemView.setOnClickListener { itemClick(menu)}
//
//        }
//    }
}












//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.awesome.amumanager.R
//import com.awesome.amumanager.data.model.Menu
//import com.bumptech.glide.RequestManager
//
//class MenuAdapter(private val menus : ArrayList<Menu>,
//                  private val requestManager : RequestManager,
//                    private val itemClick : (Menu)->Unit)
//    : RecyclerView.Adapter<MenuViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
//        return MenuViewHolder(view, itemClick)
//    }
//
//    override fun getItemCount(): Int {
//        return menus.size
//    }
//
//    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
//        holder.bind(menus[position], requestManager)
//    }
//
//    fun clearMenus() {
//        if(this.menus.isNotEmpty()) {
//            this.menus.clear()
//            notifyDataSetChanged()
//        }
//    }
//
//    fun getLastMenuId(position: Int) : String {
//        return this.menus[position].id.toString()
//    }
//
//    fun update(menus: ArrayList<Menu>) {
//        val endPosition = this.menus.size
//
//        if(endPosition < menus.size) {
//            loadMore(menus, endPosition)
//        }
//    }
//
//    private fun loadMore(
//        menus: ArrayList<Menu>,
//        endPosition: Int
//    ) {
//        if (this.menus.isEmpty()) {
//            this.menus.addAll(menus)
//        } else {
//            for (index in endPosition until menus.size) {
//                this.menus.add(index, menus[index])
//            }
//        }
//        notifyItemRangeInserted(endPosition, this.menus.size - endPosition)
//
//    }
//}