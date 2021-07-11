package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.data.model.Manager
import com.awesome.amumanager.data.model.remote.ManagerApi

class ManagerViewModel : ViewModel() {
    private val managerFactory = ManagerApi()
    val status = MutableLiveData<Int>()

    fun addManager(manager: Manager) {
        managerFactory.addManager(manager, status)
    }
}