package com.maxbay.di.data

import com.maxbay.data.hotel.api.HotelApi
import com.maxbay.data.hotel.repository.HotelRepositoryImpl
import com.maxbay.data.hotel.storage.HotelNetworkStorage
import com.maxbay.data.hotel.storage.HotelStorage
import com.maxbay.data.network.AppRetrofit
import com.maxbay.domain.hotel.repository.HotelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HotelDataModule {
    @Provides
    @Singleton
    fun provideHotelRepository(hotelStorage: HotelStorage): HotelRepository {
        return HotelRepositoryImpl(hotelStorage = hotelStorage)
    }

    @Provides
    @Singleton
    fun provideHotelNetworkStorage(hotelApi: HotelApi): HotelStorage {
        return HotelNetworkStorage(hotelApi = hotelApi)
    }

    @Provides
    @Singleton
    fun provideHotelApi(retrofit: Retrofit): HotelApi {
        return retrofit.create(HotelApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return AppRetrofit.get()
    }
}