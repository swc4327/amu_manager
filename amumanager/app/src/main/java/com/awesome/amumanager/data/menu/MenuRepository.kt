package com.awesome.amumanager.data.menu

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.domain.model.Menu
import javax.inject.Inject

interface MenuRepository {
    fun getMenu(
        menus: MutableLiveData<ArrayList<Menu>>,
        storeId: String,
        lastId: String
    )
    fun addMenu(menu: Menu, status: MutableLiveData<Int>)
    fun deleteMenu(menuId: String, status : MutableLiveData<Int>)
}

class AmuManagerMenuRepository @Inject constructor(
    private val menuDataSource: MenuDataSource
) : MenuRepository {
    override fun getMenu(menus: MutableLiveData<ArrayList<Menu>>, storeId: String, lastId: String) {
        menuDataSource.getMenu(menus, storeId, lastId)
    }

    override fun addMenu(menu: Menu, status: MutableLiveData<Int>) {
        menuDataSource.addMenu(menu, status)
    }

    override fun deleteMenu(menuId: String, status: MutableLiveData<Int>) {
        menuDataSource.deleteMenu(menuId, status)
    }

}