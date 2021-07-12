package com.awesome.amumanager.ui.main.view.storeinfo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.ReviewList
import com.awesome.amumanager.ui.main.adapter.ReviewAdapter
import com.awesome.amumanager.ui.main.view.ReviewDetailActivity
import com.awesome.amumanager.ui.main.viewmodel.ReviewViewModel
import com.awesome.amumanager.ui.main.viewmodel.ReviewViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_review.*
import kotlinx.android.synthetic.main.fragment_review.view.*
import kotlin.collections.ArrayList

class ReviewFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private var reviewAdapter: ReviewAdapter? = null
    private var storeId: String? = ""
    private lateinit var reviewViewModel : ReviewViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewViewModel.reviewLists.observe(viewLifecycleOwner, Observer<ArrayList<ReviewList>> {
            reviewAdapter = ReviewAdapter(requireContext(), it)
            review_list.adapter = reviewAdapter
        })


        view.review_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(), ReviewDetailActivity::class.java)
            intent.putExtra("review", reviewAdapter!!.getReview(position))
            intent.putExtra("client", reviewAdapter!!.getClient(position))
            intent.putExtra("storeId", this.storeId)
            startActivityForResult(intent, 100)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_review, container, false)

        auth = FirebaseAuth.getInstance()
        storeId = arguments?.getString("store_id")

        var factory = ReviewViewModelFactory(storeId.toString())
        reviewViewModel = ViewModelProvider(this, factory).get(ReviewViewModel::class.java)

        reviewViewModel.getReviewList()

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode ==100) {
            if(resultCode == AppCompatActivity.RESULT_OK) {
                reviewViewModel.getReviewList()
            }
        }
    }
}