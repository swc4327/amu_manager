package com.awesome.amumanager.di.module

import androidx.lifecycle.ViewModel
import com.awesome.amumanager.ui.main.viewmodel.ReviewViewModel
import com.awesome.amumanager.ui.main.viewmodel.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ReviewModule {
    @Provides
    @IntoMap
    @ViewModelKey(ReviewViewModel::class)
    fun bindReviewViewModel(reviewViewModel: ReviewViewModel): ViewModel {
        return reviewViewModel
    }
}