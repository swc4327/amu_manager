package com.awesome.amumanager.ui.main.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.ui.main.viewmodel.PromotionViewModel

class PromotionViewModelFactory(private val param: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PromotionViewModel::class.java)) {
            PromotionViewModel(param) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}