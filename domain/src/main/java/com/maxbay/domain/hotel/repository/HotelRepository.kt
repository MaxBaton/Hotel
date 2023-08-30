package com.maxbay.domain.hotel.repository

import com.maxbay.domain.hotel.models.Hotel

interface HotelRepository {
    suspend fun getHotelInfo(): Hotel?
}