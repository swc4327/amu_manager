package com.awesome.amumanager.di.module

import androidx.lifecycle.ViewModel
import com.awesome.amumanager.ui.main.viewmodel.MenuViewModel
import com.awesome.amumanager.ui.main.viewmodel.StoreViewModel
import com.awesome.amumanager.ui.main.viewmodel.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class MenuModule {
    @Provides
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    fun bindMenuViewModel(menuViewModel: MenuViewModel): ViewModel {
        return menuViewModel
    }
}