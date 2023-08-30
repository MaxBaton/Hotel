package com.maxbay.di.domain

import com.maxbay.domain.hotel.repository.HotelRepository
import com.maxbay.domain.hotel.usecases.GetHotelInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class HotelDomainModule {
    @Provides
    @ViewModelScoped
    fun provideGetHotelInfo(hotelRepository: HotelRepository): GetHotelInfo {
        return GetHotelInfo(hotelRepository = hotelRepository)
    }
}