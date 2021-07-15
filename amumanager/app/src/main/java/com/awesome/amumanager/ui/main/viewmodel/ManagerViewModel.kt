package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Manager
import com.awesome.amumanager.data.model.remote.ManagerApi

class ManagerViewModel : ViewModel() {
    private val managerApi = ManagerApi()
    val status = MutableLiveData<Int>()

    fun addManager(manager: Manager) {
        managerApi.addManager(manager, status)
    }
}