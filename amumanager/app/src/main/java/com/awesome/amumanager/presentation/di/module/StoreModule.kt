package com.awesome.amumanager.presentation.di.module

import androidx.lifecycle.ViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.StoreViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StoreModule {
    @Binds
    @IntoMap
    @ViewModelKey(StoreViewModel::class)
    abstract fun bindStoreViewModel(storeViewModel: StoreViewModel): ViewModel
}