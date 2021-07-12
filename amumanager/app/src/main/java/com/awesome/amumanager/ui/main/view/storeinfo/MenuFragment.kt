package com.awesome.amumanager.ui.main.view.storeinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Menu
import com.awesome.amumanager.ui.main.adapter.MenuAdapter
import com.awesome.amumanager.ui.main.viewmodel.MenuViewModel
import com.awesome.amumanager.ui.main.viewmodel.MenuViewModelFactory
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment() : Fragment() {

    private var menuAdapter: MenuAdapter? = null
    private var storeId: String? = ""
    private lateinit var menuViewModel : MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_menu, container, false)
        storeId = arguments?.getString("store_id")

        var factory = MenuViewModelFactory(storeId.toString())
        menuViewModel = ViewModelProvider(this, factory).get(MenuViewModel::class.java)

        menuViewModel.getMenuList()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuViewModel.menuList.observe(viewLifecycleOwner, Observer<ArrayList<Menu>> {
            menuAdapter = MenuAdapter(requireContext(), it)
            menu_list.adapter = menuAdapter
        })

    }
}