package com.awesome.amumanager.ui.main.view.storeinfo

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Constants.FIRST_CALL_GET_REVIEW
import com.awesome.amumanager.data.model.Constants.REVIEW_DETAIL_ACTIVITY
import com.awesome.amumanager.data.model.ReviewList
import com.awesome.amumanager.ui.main.adapter.ReviewAdapter
import com.awesome.amumanager.ui.main.view.ReviewDetailActivity
import com.awesome.amumanager.ui.main.viewmodel.ReviewViewModel
import com.awesome.amumanager.ui.main.viewmodel.ReviewViewModelFactory
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_review.*
import kotlinx.android.synthetic.main.fragment_review.view.*
import kotlin.collections.ArrayList

class ReviewFragment : Fragment() {

    private var reviewAdapter: ReviewAdapter? = null
    private var storeId: String? = ""
    private lateinit var reviewViewModel : ReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reviewViewModel = ViewModelProvider(this, ReviewViewModelFactory(storeId.toString())).get(ReviewViewModel::class.java)
        observe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_review, container, false)
        storeId = arguments?.getString("store_id")
        reviewViewModel.getReviewList(FIRST_CALL_GET_REVIEW)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REVIEW_DETAIL_ACTIVITY) {
            if(resultCode == RESULT_OK) {
                reviewAdapter?.clearReviewLists()
                reviewViewModel.getReviewList(FIRST_CALL_GET_REVIEW)

            }
        }
    }

    private fun observe() {
        reviewViewModel.reviewLists.observe(
                viewLifecycleOwner,
                Observer<ArrayList<ReviewList>> { reviewLists ->
                    if (reviewAdapter == null) {
                        reviewAdapter = ReviewAdapter(arrayListOf(), Glide.with(this)) { reviewList ->
                            val intent = Intent(requireContext(), ReviewDetailActivity::class.java)
                            intent.putExtra("review", reviewList.review)
                            intent.putExtra("client", reviewList.client)
                            intent.putExtra("storeId", this.storeId)
                            startActivityForResult(intent, REVIEW_DETAIL_ACTIVITY)
                        }
                        review_list.adapter = reviewAdapter
                    }
                    reviewAdapter?.update(reviewLists)
                })
    }

    private fun initRecyclerView() {
        review_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                println("lastVisibleItemPosition :$lastVisibleItemPosition")

                if(!recyclerView.canScrollVertically((1)) && lastVisibleItemPosition >= 0) {
                    reviewAdapter?.getLastReviewId(lastVisibleItemPosition)?.let { reviewViewModel.getReviewList(it) }
                }
            }
        })
    }
}
