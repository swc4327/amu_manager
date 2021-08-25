package com.awesome.amumanager.di.module

import androidx.lifecycle.ViewModel
import com.awesome.amumanager.ui.main.viewmodel.*
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