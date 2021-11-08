package com.awesome.amumanager.domain.usecase.manager

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.manager.ManagerRepository
import com.awesome.amumanager.data.menu.MenuRepository
import com.awesome.amumanager.data.store.StoreRepository
import com.awesome.amumanager.domain.model.Manager
import com.awesome.amumanager.domain.model.Menu
import com.awesome.amumanager.domain.model.Store
import javax.inject.Inject

class AddManagerUseCase @Inject constructor(private val repository: ManagerRepository) {
    operator fun invoke(manager: Manager, status: MutableLiveData<Int>) {
        repository.addManager(manager, status)
    }
}