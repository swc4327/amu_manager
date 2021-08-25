package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Manager
import com.awesome.amumanager.data.model.remote.ManagerApi
import javax.inject.Inject

class ManagerViewModel @Inject constructor() : ViewModel() {
    private val managerApi = ManagerApi()
    val status = MutableLiveData<Int>()

    fun addManager(manager: Manager) {
        managerApi.addManager(manager, status)
    }
}