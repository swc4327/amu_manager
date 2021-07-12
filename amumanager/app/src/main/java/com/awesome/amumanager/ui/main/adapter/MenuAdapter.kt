package com.awesome.amumanager.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Menu
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.menulist_item.view.*

class MenuAdapter(val context: Context, val menus : ArrayList<Menu>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.menulist_item, null)

        view.menu_list_name.setText(menus[position].name)
        view.menu_list_price.setText(menus[position].price)
        view.menu_list_comment.setText(menus[position].comment)

        Glide
            .with(context)
            .load(menus[position].image)
            .circleCrop()
            .into(view.menu_list_image)

        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return menus.size
    }

}