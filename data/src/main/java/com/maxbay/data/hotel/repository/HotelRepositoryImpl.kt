package com.maxbay.data.hotel.repository

import com.maxbay.data.hotel.mappers.mapToHotel
import com.maxbay.data.hotel.storage.HotelStorage
import com.maxbay.domain.hotel.models.Hotel
import com.maxbay.domain.hotel.repository.HotelRepository

class HotelRepositoryImpl(private val hotelStorage: HotelStorage): HotelRepository {
    override suspend fun getHotelInfo(): Hotel? {
        return try {
            hotelStorage.getHotelInfo()?.mapToHotel()
        }catch (e: Exception) {
            null
        }
    }
}