package com.awesome.amumanager.presentation.di.module

import androidx.lifecycle.ViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.PromotionViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class PromotionModule {
    @Provides
    @IntoMap
    @ViewModelKey(PromotionViewModel::class)
    fun bindPromotionViewModel(promotionViewModel: PromotionViewModel): ViewModel {
        return promotionViewModel
    }
}