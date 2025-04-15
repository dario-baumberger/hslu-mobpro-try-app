package com.example.tryapp.di

import com.example.tryapp.business.bands.BandsRepository
import com.example.tryapp.business.bands.BandsRepositoryImpl
import com.example.tryapp.data.BandsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BandsModule {

    @Provides
    @Singleton
    fun provideBandsRepository(
        api: BandsApiService
    ): BandsRepository = BandsRepositoryImpl(api)
}