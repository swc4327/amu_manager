package com.awesome.amumanager.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.awesome.amumanager.R
import com.awesome.amumanager.model.Client
import com.awesome.amumanager.model.Review
import com.awesome.amumanager.model.ReviewList
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.reviewlist_item.view.*

class ReviewListAdapter(val context: Context, val reviewLists : ArrayList<ReviewList>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view : View = LayoutInflater.from(context).inflate(R.layout.reviewlist_item, null)

        Glide
                .with(context)
                .load(reviewLists[position].review.review_image)
                .into(view.review_image)

        view.comment.setText(reviewLists[position].review.comment)

        view.ratingBar.rating = reviewLists[position].review.point!!.toFloat()

        view.time.setText(reviewLists[position].review.time)

        Glide
                .with(context)
                .load(reviewLists[position].client.image)
                .circleCrop()
                .into(view.client_image)

        view.client_name.setText(reviewLists[position].client.nickname)

        return view
    }

    fun getReview(position: Int) : Review {
        return reviewLists[position].review
    }

    fun getClient(position: Int) : Client {
        return reviewLists[position].client
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return reviewLists.size
    }

}