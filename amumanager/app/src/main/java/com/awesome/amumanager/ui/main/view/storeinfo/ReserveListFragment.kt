package com.awesome.amumanager.ui.main.view.storeinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.ReserveList
import com.awesome.amumanager.ui.main.adapter.ReserveListAdapter
import com.awesome.amumanager.ui.main.view.ReserveDetailActivity
import com.awesome.amumanager.ui.main.viewmodel.ReserveViewModel
import com.awesome.amumanager.ui.main.viewmodel.ReserveViewModelFactory
import kotlinx.android.synthetic.main.fragment_reserve_list.*
import kotlinx.android.synthetic.main.fragment_reserve_list.view.*

class ReserveListFragment() : Fragment() {

    private var reserveListAdapter: ReserveListAdapter? = null
    private var storeId: String? = ""
    private lateinit var reserveViewModel : ReserveViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reserveViewModel.reserveLists.observe(viewLifecycleOwner, Observer<ArrayList<ReserveList>> {
            reserveListAdapter = ReserveListAdapter(requireContext(), it)
            reserve_list.adapter = reserveListAdapter
        })

        view.reserve_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(), ReserveDetailActivity::class.java)
            intent.putExtra("reserve", reserveListAdapter!!.getReserve(position))
            intent.putExtra("client", reserveListAdapter!!.getClient(position))
            startActivity(intent)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reserve_list, container, false)
        storeId = arguments?.getString("store_id")

        var factory = ReserveViewModelFactory(storeId.toString())
        reserveViewModel = ViewModelProvider(this, factory).get(ReserveViewModel::class.java)

        reserveViewModel.getReserveList()

        return view
    }
}