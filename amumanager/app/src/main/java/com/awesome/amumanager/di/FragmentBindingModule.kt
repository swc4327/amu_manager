package com.awesome.amumanager.di

import com.awesome.amumanager.di.module.MenuModule
import com.awesome.amumanager.di.module.ReserveModule
import com.awesome.amumanager.di.module.ReviewModule
import com.awesome.amumanager.ui.main.view.storeinfo.InfoFragment
import com.awesome.amumanager.ui.main.view.storeinfo.MenuFragment
import com.awesome.amumanager.ui.main.view.storeinfo.ReserveFragment
import com.awesome.amumanager.ui.main.view.storeinfo.ReviewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @FragmentScoped
    @ContributesAndroidInjector(modules = [MenuModule::class])
    abstract fun menuFragment(): MenuFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [])
    abstract fun infoFragment() : InfoFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [ReviewModule::class])
    abstract fun reviewFragment(): ReviewFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [ReserveModule::class])
    abstract fun reserveFragment(): ReserveFragment
}