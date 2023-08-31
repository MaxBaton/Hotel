package com.maxbay.di.domain

import com.maxbay.domain.booking.repository.BookingRepository
import com.maxbay.domain.booking.usecases.GetBookingPrice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class BookingDomainModule {
    @Provides
    @ViewModelScoped
    fun provideGetBookingPrice(bookingRepository: BookingRepository): GetBookingPrice {
        return GetBookingPrice(bookingRepository = bookingRepository)
    }
}