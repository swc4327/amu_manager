package com.awesome.amumanager.ui.base

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.data.model.Menu
import com.awesome.amumanager.data.model.Store
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_menu.view.*
import kotlinx.android.synthetic.main.item_store.view.*

class BaseViewHolder <T> (itemView : View, private val itemClick: (T)-> Unit) : RecyclerView.ViewHolder(itemView) {

    fun bind(t : T, requestManager : RequestManager) {
        itemView.setOnClickListener { itemClick(t)}
        if(t is Store) {
            bindStore(t, requestManager)
        } else if (t is Menu) {
            bindMenu(t, requestManager)
        }
    }

    private fun bindStore(store : Store, requestManager : RequestManager) {
        itemView.store_name.text = store.name
        requestManager.load(store.image).into(itemView.store_image)
        itemView.point.text = store.point?.toFloat().toString()
        itemView.count.text = "("+ store.count.toString() + ")"

        if(store.is_opened == "0") {
            itemView.upper_image.setBackgroundColor(Color.parseColor("#26000000"))
            itemView.upper_text.text = "준비중"
        } else { //영업중
            itemView.upper_image.setBackgroundColor(Color.parseColor("#00000000"))
            itemView.upper_text.text = ""
        }
    }

    private fun bindMenu(menu : Menu, requestManager : RequestManager) {
        itemView.menu_name.text = menu.name
        requestManager.load(menu.image).circleCrop().diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true).into(itemView.menu_image)
        itemView.menu_comment.text = menu.comment
        itemView.menu_price.text = menu.price

    }

}
