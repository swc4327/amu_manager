package com.awesome.amumanager.presentation.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amumanager.domain.model.Manager
import com.awesome.amumanager.domain.usecase.manager.AddManagerUseCase
import javax.inject.Inject

class ManagerViewModel @Inject constructor(
    private val addManagerUseCase: AddManagerUseCase
) : ViewModel() {
    val status = MutableLiveData<Int>()

    fun addManager(manager: Manager) {
        addManagerUseCase(manager, status)
    }
}