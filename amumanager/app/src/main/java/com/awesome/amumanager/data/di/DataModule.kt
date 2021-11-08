package com.awesome.amumanager.data.di

import com.awesome.amumanager.data.api.service.*
import com.awesome.amumanager.data.api.test.StoresTestData
import com.awesome.amumanager.data.manager.AmuManagerManagerDataSource
import com.awesome.amumanager.data.manager.AmuManagerManagerRepository
import com.awesome.amumanager.data.manager.ManagerDataSource
import com.awesome.amumanager.data.manager.ManagerRepository
import com.awesome.amumanager.data.menu.AmuManagerMenuDataSource
import com.awesome.amumanager.data.menu.AmuManagerMenuRepository
import com.awesome.amumanager.data.menu.MenuDataSource
import com.awesome.amumanager.data.menu.MenuRepository
import com.awesome.amumanager.data.promotion.AmuManagerPromotionDataSource
import com.awesome.amumanager.data.promotion.AmuManagerPromotionRepository
import com.awesome.amumanager.data.promotion.PromotionDataSource
import com.awesome.amumanager.data.promotion.PromotionRepository
import com.awesome.amumanager.data.reserve.AmuManagerReserveDataSource
import com.awesome.amumanager.data.reserve.AmuManagerReserveRepository
import com.awesome.amumanager.data.reserve.ReserveDataSource
import com.awesome.amumanager.data.reserve.ReserveRepository
import com.awesome.amumanager.data.review.AmuManagerReviewDataSource
import com.awesome.amumanager.data.review.AmuManagerReviewRepository
import com.awesome.amumanager.data.review.ReviewDataSource
import com.awesome.amumanager.data.review.ReviewRepository
import com.awesome.amumanager.data.store.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideStoreDataSource(storeApi: StoreApi): StoreDataSource {
        return AmuManagerStoreDataSource(storeApi)
    }

    @Provides
    @Singleton
    fun provideStoreRepository(amuManagerStoreDataSource: AmuManagerStoreDataSource): StoreRepository {
        return AmuManagerStoreRepository(amuManagerStoreDataSource)
    }

    @Provides
    @Singleton
    fun provideStoreTestDataSource(storesTestData: StoresTestData): StoreDataSource {
        return AmuManagerStoreTestDataSource(storesTestData)
    }

    @Provides
    @Singleton
    fun provideStoreTestRepository(amuManagerStoreTestDataSource: AmuManagerStoreTestDataSource): StoreRepository {
        return AmuManagerStoreTestRepository(amuManagerStoreTestDataSource)
    }

    @Provides
    @Singleton
    fun provideManagerDataSource(managerApi: ManagerApi): ManagerDataSource {
        return AmuManagerManagerDataSource(managerApi)
    }

    @Provides
    @Singleton
    fun provideManagerRepository(managerDataSource: ManagerDataSource): ManagerRepository {
        return AmuManagerManagerRepository(managerDataSource)
    }

    @Provides
    @Singleton
    fun provideMenuDataSource(menuApi: MenuApi): MenuDataSource {
        return AmuManagerMenuDataSource(menuApi)
    }

    @Provides
    @Singleton
    fun provideMenuRepository(menuDataSource: MenuDataSource): MenuRepository {
        return AmuManagerMenuRepository(menuDataSource)
    }

    @Provides
    @Singleton
    fun providePromotionDataSource(promotionApi: PromotionApi): PromotionDataSource {
        return AmuManagerPromotionDataSource(promotionApi)
    }

    @Provides
    @Singleton
    fun providePromotionRepository(promotionDataSource: PromotionDataSource): PromotionRepository {
        return AmuManagerPromotionRepository(promotionDataSource)
    }

    @Provides
    @Singleton
    fun provideReserveDataSource(reserveApi: ReserveApi, clientApi: ClientApi): ReserveDataSource {
        return AmuManagerReserveDataSource(reserveApi, clientApi)
    }

    @Provides
    @Singleton
    fun provideReserveRepository(reserveDataSource: ReserveDataSource): ReserveRepository {
        return AmuManagerReserveRepository(reserveDataSource)
    }

    @Provides
    @Singleton
    fun provideReviewDataSource(reviewApi: ReviewApi, clientApi: ClientApi): ReviewDataSource {
        return AmuManagerReviewDataSource(reviewApi, clientApi)
    }

    @Provides
    @Singleton
    fun provideReviewRepository(reviewDataSource: ReviewDataSource): ReviewRepository {
        return AmuManagerReviewRepository(reviewDataSource)
    }
}