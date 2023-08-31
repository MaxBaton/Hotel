package com.maxbay.di.data

import com.maxbay.data.booking.api.BookingApi
import com.maxbay.data.booking.repository.BookingRepositoryImpl
import com.maxbay.data.booking.storage.BookingNetworkStorage
import com.maxbay.data.booking.storage.BookingStorage
import com.maxbay.domain.booking.repository.BookingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BookingDataModule {
    @Provides
    @Singleton
    fun provideBookingRepository(bookingStorage: BookingStorage): BookingRepository {
        return BookingRepositoryImpl(bookingStorage = bookingStorage)
    }

    @Provides
    @Singleton
    fun provideBookingNetworkStorage(bookingApi: BookingApi): BookingStorage {
        return BookingNetworkStorage(bookingApi = bookingApi)
    }

    @Provides
    @Singleton
    fun provideBookingApi(retrofit: Retrofit): BookingApi {
        return retrofit.create(BookingApi::class.java)
    }
}