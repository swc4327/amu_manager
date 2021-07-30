package com.awesome.amumanager.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.ReviewList
import com.bumptech.glide.RequestManager

class ReviewAdapter(private val reviewLists : ArrayList<ReviewList>,
                    private val requestManager : RequestManager,
                    private val itemClick: (ReviewList) -> Unit)
    : RecyclerView.Adapter<ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return reviewLists.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviewLists[position], requestManager)
    }

    fun getLastReviewId(position: Int) : String {
        return this.reviewLists[position].review.id.toString()
    }

    fun update(reviewLists: ArrayList<ReviewList>) {
        val endPosition = this.reviewLists.size

        //계속 불러오는중
        if(endPosition < reviewLists.size) {
            loadMore(reviewLists, endPosition)
        } else { //삭제했거나, detail 변경 됬을때
            if(this.reviewLists.isNotEmpty())
                this.reviewLists.clear()
            this.reviewLists.addAll(reviewLists)
            notifyDataSetChanged()
        }
    }

    private fun loadMore(reviewLists: ArrayList<ReviewList>, endPosition: Int) {
        if (this.reviewLists.isEmpty()) {
            this.reviewLists.addAll(reviewLists)
        } else {
            for (index in endPosition until reviewLists.size) {
                this.reviewLists.add(index, reviewLists[index])
            }
        }
        notifyItemRangeInserted(endPosition, this.reviewLists.size - endPosition)
    }
}

























//class ReviewAdapter(val context: Context, val reviewLists : ArrayList<ReviewList>): BaseAdapter() {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
//        val view : View = LayoutInflater.from(context).inflate(R.layout.reviewlist_item, null)
//
//        if(reviewLists != null) {
//            Glide
//                .with(context)
//                .load(reviewLists[position].review.review_image)
//                .into(view.review_image)
//
//            view.comment.setText(reviewLists[position].review.comment)
//
//            view.ratingBar.rating = reviewLists[position].review.point!!.toFloat()
//
//            view.time.setText(reviewLists[position].review.time)
//
//            Glide
//                .with(context)
//                .load(reviewLists[position].client.image)
//                .circleCrop()
//                .into(view.client_image)
//
//            view.client_name.setText(reviewLists[position].client.nickname)
//            //notifyDataSetChanged()
//        }
//        return view
//    }
//
//    fun getReview(position: Int) : Review {
//        return reviewLists[position].review
//    }
//
//    fun getClient(position: Int) : Client {
//        return reviewLists[position].client
//    }
//
//    override fun getItem(position: Int): Any {
//        return 0
//    }
//
//    override fun getItemId(position: Int): Long {
//        return 0
//    }
//
//    override fun getCount(): Int {
//        return reviewLists.size
//    }
//
//}