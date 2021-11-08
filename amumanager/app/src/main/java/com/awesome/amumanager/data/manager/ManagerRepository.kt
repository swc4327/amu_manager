package com.awesome.amumanager.data.manager

import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.domain.model.Manager
import javax.inject.Inject

interface ManagerRepository {
    fun addManager(manager: Manager, status: MutableLiveData<Int>)
}

class AmuManagerManagerRepository @Inject constructor(
    private val managerDataSource: ManagerDataSource
) : ManagerRepository {
    override fun addManager(manager: Manager, status: MutableLiveData<Int>) {
        managerDataSource.addManager(manager, status)
    }
}