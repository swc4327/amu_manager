package com.awesome.amumanager.presentation.di

import com.awesome.amumanager.App
import com.awesome.amumanager.data.di.ApiModule
import com.awesome.amumanager.data.di.DataModule
import com.awesome.amumanager.data.di.RetrofitModule
import com.awesome.amumanager.data.di.TestModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        DataModule::class,
        ApiModule::class,
        RetrofitModule::class,
        TestModule::class]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: App): AppComponent
    }

}