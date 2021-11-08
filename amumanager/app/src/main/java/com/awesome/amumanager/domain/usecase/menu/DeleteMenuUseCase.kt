package com.awesome.amumanager.domain.usecase.menu

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.menu.MenuRepository
import com.awesome.amumanager.data.store.StoreRepository
import com.awesome.amumanager.domain.model.Menu
import com.awesome.amumanager.domain.model.Store
import javax.inject.Inject

class DeleteMenuUseCase @Inject constructor(private val repository: MenuRepository) {
    operator fun invoke(menuId: String, status : MutableLiveData<Int>) {
        repository.deleteMenu(menuId, status)
    }
}