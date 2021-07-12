package com.awesome.amumanager.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Client
import com.awesome.amumanager.data.model.Reserve
import com.awesome.amumanager.data.model.ReserveList
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_reserve.view.*

class ReserveAdapter(val context: Context, val reserveLists: ArrayList<ReserveList>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_reserve, null)

        Glide
            .with(context)
            .load(reserveLists[p0].client.image)
            .circleCrop()
            .into(view.client_image)

        view.client_name.setText(reserveLists[p0].client.nickname)

        view.phone.setText(reserveLists[p0].reserve.phone)
        view.date.setText(reserveLists[p0].reserve.date)

        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return reserveLists.size
    }

    fun getReserve(position: Int) : Reserve {
        return reserveLists[position].reserve
    }

    fun getClient(position: Int) : Client {
        return reserveLists[position].client
    }
}