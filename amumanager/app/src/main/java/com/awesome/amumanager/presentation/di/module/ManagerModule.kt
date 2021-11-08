package com.awesome.amumanager.presentation.di.module

import androidx.lifecycle.ViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ManagerViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ViewModelKey
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