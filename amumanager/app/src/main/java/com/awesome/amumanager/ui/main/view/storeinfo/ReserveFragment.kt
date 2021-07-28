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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.ReserveList
import com.awesome.amumanager.ui.main.adapter.ReserveAdapter
import com.awesome.amumanager.ui.main.view.ReserveDetailActivity
import com.awesome.amumanager.ui.main.viewmodel.ReserveViewModel
import com.awesome.amumanager.ui.main.viewmodel.ReserveViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_reserve.*
import kotlinx.android.synthetic.main.fragment_reserve.view.*

class ReserveFragment() : Fragment() {

    private var reserveAdapter: ReserveAdapter? = null
    private var storeId: String? = ""
    private lateinit var reserveViewModel : ReserveViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reserveViewModel.reserveLists.observe(viewLifecycleOwner, Observer<ArrayList<ReserveList>> {reserveLists->
            if(reserveAdapter == null) {
                reserveAdapter = ReserveAdapter(arrayListOf(), Glide.with(this)) {reserveList->
                    val intent = Intent(requireContext(), ReserveDetailActivity::class.java)
                    intent.putExtra("reserve", reserveList.reserve)
                    intent.putExtra("client", reserveList.client)
                    intent.putExtra("storeId", storeId)
                    startActivityForResult(intent, 200)
                }
                reserve_list.adapter = reserveAdapter
            }
            reserveAdapter!!.update(reserveLists)
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reserve, container, false)
        storeId = arguments?.getString("store_id")

        initRecyclerView(view)

        var factory = ReserveViewModelFactory(storeId.toString())
        reserveViewModel = ViewModelProvider(this, factory).get(ReserveViewModel::class.java)

        reserveViewModel.getReserveList("0")

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode ==200) {
            if(resultCode == AppCompatActivity.RESULT_OK) {
                reserveViewModel.getReserveList("0")
            }
        }
    }

    private fun initRecyclerView(view: View) {
        view.reserve_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                if (lastVisibleItemPosition == itemTotalCount) {
                    reserveViewModel.getReserveList(recyclerView.adapter!!.itemCount.toString())
                }
            }
        })

    }
}