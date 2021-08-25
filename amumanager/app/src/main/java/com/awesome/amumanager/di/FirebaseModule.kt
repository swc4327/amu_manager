package com.awesome.amumanager.di

import androidx.lifecycle.ViewModel
import com.awesome.amumanager.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amumanager.ui.main.viewmodel.MenuViewModel
import com.awesome.amumanager.ui.main.viewmodel.StoreViewModel
import com.awesome.amumanager.ui.main.viewmodel.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class FirebaseModule {
    @Provides
    @IntoMap
    @ViewModelKey(FirebaseViewModel::class)
    fun bindFirebaseViewModel(firebaseViewModel: FirebaseViewModel): ViewModel {
        return firebaseViewModel
    }
}