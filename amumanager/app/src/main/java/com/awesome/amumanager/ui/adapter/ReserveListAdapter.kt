package com.awesome.amumanager.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.awesome.amumanager.R
import com.awesome.amumanager.model.Client
import com.awesome.amumanager.model.Reserve
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_reserve.view.*

class ReserveListAdapter(val context: Context, val reserves: ArrayList<Reserve>, val clients: ArrayList<Client>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_reserve, null)

        Glide
            .with(context)
            .load(clients[p0].image)
            .circleCrop()
            .into(view.client_image)

        view.client_name.setText(clients[p0].nickname)

        view.phone.setText(reserves[p0].phone)
        view.date.setText(reserves[p0].date)

        return view
    }

    fun getItemClientName(position : Int): Any {
        return clients[position].nickname!!
    }

    fun getItemLng(position : Int) : Any {
        return reserves[position].lng!!
    }

    fun getItemLat(position : Int) : Any {
        return reserves[position].lat!!
    }

    fun getItemPhone(position: Int): Any {
        return reserves[position].phone
    }

    fun getItemArrive(position: Int): Any {
        return reserves[position].arrive
    }

    fun getItemRequest(position: Int): Any {
        return reserves[position].request
    }

    fun getItemDate(position: Int): Any {
        return reserves[position].date!!
    }

    override fun getItem(position: Int): Any {
        return 0
    }


    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return reserves.size
    }
}