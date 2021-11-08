package com.awesome.amumanager.presentation.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.domain.model.Menu
import com.awesome.amumanager.domain.usecase.menu.AddMenuUseCase
import com.awesome.amumanager.domain.usecase.menu.DeleteMenuUseCase
import com.awesome.amumanager.domain.usecase.menu.GetMenuUseCase
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val addMenuUseCase: AddMenuUseCase,
    private val deleteMenuUseCase: DeleteMenuUseCase,
    private val getMenuUseCase: GetMenuUseCase
) : ViewModel() {
    val menus = MutableLiveData<ArrayList<Menu>>()
    val status = MutableLiveData<Int>()


    fun getMenu(lastId : String, storeId : String) {
        getMenuUseCase(menus, storeId, lastId)
    }

    fun addMenu(menu : Menu) {
        addMenuUseCase(menu, status)
    }

    fun deleteMenu(menuId: String) {
        deleteMenuUseCase(menuId, status)
    }
}