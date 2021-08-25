package com.awesome.amumanager.di.module

import androidx.lifecycle.ViewModel
import com.awesome.amumanager.ui.main.viewmodel.StoreViewModel
import com.awesome.amumanager.ui.main.viewmodel.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class StoreModule {
    @Provides
    @IntoMap
    @ViewModelKey(StoreViewModel::class)
    fun bindStoreViewModel(storeViewModel: StoreViewModel): ViewModel {
        return storeViewModel
    }
}