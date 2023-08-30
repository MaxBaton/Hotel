package com.maxbay.data.hotel.storage

import com.maxbay.data.hotel.models.HotelData

interface HotelStorage {
    suspend fun getHotelInfo(): HotelData?
}