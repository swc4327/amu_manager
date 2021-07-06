package com.awesome.amumanager.view.ui.storeinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.model.Menu
import com.awesome.amumanager.view.ui.adapter.MenuListAdapter
import com.awesome.amumanager.viewmodel.MenuViewModel
import com.awesome.amumanager.viewmodel.MenuViewModelFactory
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment() : Fragment() {

    private var menuListAdapter: MenuListAdapter? = null
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

        menuListAdapter = MenuListAdapter(requireContext(), menuViewModel.menuList.value!!)

        menuViewModel.menuList.observe(viewLifecycleOwner, Observer<ArrayList<Menu>> {
            menuListAdapter!!.notifyDataSetChanged()
        })



        //getMenuList()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menu_list.adapter = menuListAdapter
    }
}



//private fun getMenuList() {
//    val gson = GsonBuilder().setLenient().create()
//    val retrofit = Retrofit.Builder()
//            .baseUrl(Constants.serverUrl)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//
//    val joinApi = retrofit.create(GetMenuListService::class.java)
//    Log.e("store_id check", storeId.toString())
//
//    joinApi.getMenuList(storeId.toString())
//            .enqueue(object : Callback<MenuListResponse> {
//
//                override fun onFailure(call: Call<MenuListResponse>, t: Throwable) {
//                    Log.e("Menu Retrofit getMenu", "실패")
//                    Log.e("Check", t.toString())
//                }
//
//                override fun onResponse(
//                        call: Call<MenuListResponse>,
//                        response: Response<MenuListResponse>
//                )  {
//                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                        Log.e("Getmenu Retrofit", "success")
//                        menus.addAll(response.body()!!.menus)
//                        menuListAdapter =
//                                MenuListAdapter(
//                                        requireContext(),
//                                        menus
//                                )
//                        menu_list.adapter = menuListAdapter
//                    } else {
//
//                    }
//                }
//            })
//
//}