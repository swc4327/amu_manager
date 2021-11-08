package com.awesome.amumanager.presentation.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.domain.model.Menu
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuViewHolder(itemView: View, private val itemClick: (Menu)->Unit): RecyclerView.ViewHolder(itemView) {

    fun bind(menu : Menu, requestManager : RequestManager) {
        itemView.menu_name.text = menu.name
        requestManager.load(menu.image).circleCrop().into(itemView.menu_image)
        itemView.menu_comment.text = menu.comment
        itemView.menu_price.text = menu.price

        itemView.setOnClickListener{itemClick(menu)}
    }
}
//.diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true).
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////class ReviewAdapter(val context: Context, val reviewLists : ArrayList<ReviewList>): BaseAdapter() {
////
////    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
////        val view : View = LayoutInflater.from(context).inflate(R.layout.reviewlist_item, null)
////
////        if(reviewLists != null) {
////            Glide
////                .with(context)
////                .load(reviewLists[position].review.review_image)
////                .into(view.review_image)
////
////            view.comment.setText(reviewLists[position].review.comment)
////
////            view.ratingBar.rating = reviewLists[position].review.point!!.toFloat()
////
////            view.time.setText(reviewLists[position].review.time)
////
////            Glide
////                .with(context)
////                .load(reviewLists[position].client.image)
////                .circleCrop()
////                .into(view.client_image)
////
////            view.client_name.setText(reviewLists[position].client.nickname)
////            //notifyDataSetChanged()
////        }
////        return view
////    }
////
////    fun getReview(position: Int) : Review {
////        return reviewLists[position].review
////    }
////
////    fun getClient(position: Int) : Client {
////        return reviewLists[position].client
////    }
////
////    override fun getItem(position: Int): Any {
////        return 0
////    }
////
////    override fun getItemId(position: Int): Long {
////        return 0
////    }
////
////    override fun getCount(): Int {
////        return reviewLists.size
////    }
////
////}