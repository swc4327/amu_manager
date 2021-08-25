package com.awesome.amumanager.ui.main.view.storeinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Constants.FIRST_CALL
import com.awesome.amumanager.data.model.ReviewList
import com.awesome.amumanager.ui.base.BaseActivity
import com.awesome.amumanager.ui.base.BaseFragment
import com.awesome.amumanager.ui.main.adapter.ReviewAdapter
import com.awesome.amumanager.ui.main.view.ReviewDetailActivity
import com.awesome.amumanager.ui.main.viewmodel.ReviewViewModel
import com.awesome.amumanager.ui.main.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_review.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ReviewFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private var reviewAdapter: ReviewAdapter? = null
    private var storeId: String? = ""
    private lateinit var reviewViewModel : ReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeId = arguments?.getString("store_id")
        reviewViewModel = ViewModelProvider(this, viewModelFactory
        ).get(ReviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observe()
    }

    override fun onResume() {
        super.onResume()
        reviewAdapter?.clearReviewLists()
        storeId?.let {storeId-> reviewViewModel.getReviewList(FIRST_CALL, storeId) }
    }

    private fun observe() {
        reviewViewModel.reviewLists.observe(
                viewLifecycleOwner,
                Observer<ArrayList<ReviewList>> { reviewLists ->
                    if (reviewAdapter == null) {
                        reviewAdapter = ReviewAdapter(arrayListOf(), Glide.with(this)) { reviewList ->
                            storeId?.let {storeId -> ReviewDetailActivity.startActivity(requireContext() as BaseActivity, reviewList, storeId) }
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
                    reviewAdapter?.getLastReviewId(lastVisibleItemPosition)?.let {lastId-> storeId?.let {storeId->
                        reviewViewModel.getReviewList(lastId, storeId)
                    } }
                }
            }
        })
    }
}
