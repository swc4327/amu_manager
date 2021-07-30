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
    private lateinit var auth: FirebaseAuth

    private var reviewAdapter: ReviewAdapter? = null
    private var storeId: String? = ""
    private lateinit var reviewViewModel : ReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_review, container, false)

        auth = FirebaseAuth.getInstance()
        storeId = arguments?.getString("store_id")
        //initRecyclerView(view)
        var factory = ReviewViewModelFactory(storeId.toString())
        reviewViewModel = ViewModelProvider(this, factory).get(ReviewViewModel::class.java)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view)
        reviewViewModel.getReviewList("-1")

        reviewViewModel.reviewLists.observe(
            viewLifecycleOwner,
            Observer<ArrayList<ReviewList>> { reviewLists ->
                if (reviewAdapter == null) {
                    println("OBSERVE!!!")
                    reviewAdapter = ReviewAdapter(arrayListOf(), Glide.with(this)) { reviewList ->
                        val intent = Intent(requireContext(), ReviewDetailActivity::class.java)
                        intent.putExtra("review", reviewList.review)
                        intent.putExtra("client", reviewList.client)
                        intent.putExtra("storeId", this.storeId)
                        startActivityForResult(intent, 100)
                    }
                    review_list.adapter = reviewAdapter

                }
                println("OBSERVE!~!@~")
                reviewAdapter!!.update(reviewLists)
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode ==100) {
            if(resultCode == RESULT_OK) {
                reviewViewModel.getReviewList("-1")
            }
        }
    }

    private fun initRecyclerView(view: View) {
        view.review_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                println("lastVisibleItemPosition :$lastVisibleItemPosition")

                if(!recyclerView.canScrollVertically((1))) {
                    reviewViewModel.getReviewList(reviewAdapter!!.getLastReviewId(lastVisibleItemPosition))
                }
//                if (lastVisibleItemPosition+a == itemTotalCount) {
//                    reviewViewModel.getReviewList(reviewAdapter!!.getLastReviewId(lastVisibleItemPosition))
//                    println(reviewAdapter!!.getLastReviewId(lastVisibleItemPosition).toString())
//                }
            }
        })
    }
}
