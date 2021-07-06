package com.awesome.amumanager.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReviewViewModelFactory(private val param: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            ReviewViewModel(param) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}