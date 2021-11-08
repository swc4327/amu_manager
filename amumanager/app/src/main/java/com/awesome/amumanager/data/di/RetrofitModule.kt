package com.awesome.amumanager.data.di

import com.awesome.amumanager.domain.model.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides
    @Named("AmuManagerApi")
    @Singleton
    fun provideAmuManagerApi() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
}