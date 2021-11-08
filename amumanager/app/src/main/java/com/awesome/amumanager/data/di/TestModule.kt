package com.awesome.amumanager.data.di

import com.awesome.amumanager.data.api.test.StoresTestData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestModule {
    @Provides
    @Singleton
    fun getStoresTestData(): StoresTestData {
        return StoresTestData()
    }
}