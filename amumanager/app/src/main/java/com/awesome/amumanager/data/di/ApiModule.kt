package com.awesome.amumanager.data.di

import com.awesome.amumanager.data.api.service.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun getClientApi(@Named("AmuManagerApi") retrofit: Retrofit): ClientApi {
        return retrofit.create(ClientApi::class.java)
    }

    @Provides
    @Singleton
    fun getManagerApi(@Named("AmuManagerApi") retrofit: Retrofit): ManagerApi {
        return retrofit.create(ManagerApi::class.java)
    }

    @Provides
    @Singleton
    fun getMenuApi(@Named("AmuManagerApi") retrofit: Retrofit): MenuApi {
        return retrofit.create(MenuApi::class.java)
    }

    @Provides
    @Singleton
    fun getPromotionApi(@Named("AmuManagerApi") retrofit: Retrofit): PromotionApi {
        return retrofit.create(PromotionApi::class.java)
    }

    @Provides
    @Singleton
    fun getReserveApi(@Named("AmuManagerApi") retrofit: Retrofit): ReserveApi {
        return retrofit.create(ReserveApi::class.java)
    }

    @Provides
    @Singleton
    fun getReviewApi(@Named("AmuManagerApi") retrofit: Retrofit): ReviewApi {
        return retrofit.create(ReviewApi::class.java)
    }

    @Provides
    @Singleton
    fun getStoreApi(@Named("AmuManagerApi") retrofit: Retrofit): StoreApi {
        return retrofit.create(StoreApi::class.java)
    }
}