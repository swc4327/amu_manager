package com.awesome.amumanager.di.module

import androidx.lifecycle.ViewModel
import com.awesome.amumanager.ui.main.viewmodel.ManagerViewModel
import com.awesome.amumanager.ui.main.viewmodel.ReserveViewModel
import com.awesome.amumanager.ui.main.viewmodel.ReviewViewModel
import com.awesome.amumanager.ui.main.viewmodel.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ManagerModule {
    @Provides
    @IntoMap
    @ViewModelKey(ManagerViewModel::class)
    fun bindManagerViewModel(managerViewModel: ManagerViewModel): ViewModel {
        return managerViewModel
    }
}