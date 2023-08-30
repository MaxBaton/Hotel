package com.maxbay.domain.hotel.usecases

import com.maxbay.domain.hotel.models.Hotel
import com.maxbay.domain.hotel.repository.HotelRepository

class GetHotelInfo(private val hotelRepository: HotelRepository) {
    suspend fun get(): Hotel? {
        return hotelRepository.getHotelInfo()
    }
}