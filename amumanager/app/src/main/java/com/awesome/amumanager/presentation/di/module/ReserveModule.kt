package com.awesome.amumanager.presentation.di.module

import androidx.lifecycle.ViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ReserveViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ReserveModule {
    @Provides
    @IntoMap
    @ViewModelKey(ReserveViewModel::class)
    fun bindReserveViewModel(reserveViewModel: ReserveViewModel): ViewModel {
        return reserveViewModel
    }
}