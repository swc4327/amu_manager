package com.awesome.amumanager.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Promotion
import kotlinx.android.synthetic.main.item_promotion.view.*

class PromotionAdapter(val context: Context, val promotions : ArrayList<Promotion>): BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_promotion, null)

        view.store_name.setText(promotions[position].store_name)
        view.message.setText(promotions[position].message)
        view.date.setText(promotions[position].date)

        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return promotions.size
    }
}