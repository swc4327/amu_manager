package com.awesome.amumanager.presentation.di

import com.awesome.amumanager.presentation.di.module.*
import com.awesome.amumanager.presentation.ui.main.view.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [StoreModule::class, FirebaseModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules =[])
    abstract fun bindStoreInfoActivity() : StoreInfoActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [StoreModule::class, FirebaseModule::class])
    abstract  fun bindAddStoreActivity(): AddStoreActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MenuModule::class, FirebaseModule::class])
    abstract  fun bindAddMenuActivity() : AddMenuActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MenuModule::class])
    abstract fun bindMenuDetailActivity() : MenuDetailActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PromotionModule::class])
    abstract fun bindAddPromotionActivity() : AddPromotionActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PromotionModule::class])
    abstract fun bindPromotionActivity() : PromotionActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [FirebaseModule::class])
    abstract fun bindJoinActivity() : JoinActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ManagerModule::class, FirebaseModule::class])
    abstract fun bindJoinInfoActivity() : JoinInfoActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [FirebaseModule::class])
    abstract fun bindLoginActivity() : LoginActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [FirebaseModule::class])
    abstract fun bindMyPageActivity() : MyPageActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ReserveModule::class])
    abstract fun bindReserveDetailActivity() : ReserveDetailActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ReviewModule::class])
    abstract fun bindReviewDetailActivity() : ReviewDetailActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [StoreModule::class])
    abstract fun bindStoreInfoSettingActivity() : StoreInfoSettingActivity

}