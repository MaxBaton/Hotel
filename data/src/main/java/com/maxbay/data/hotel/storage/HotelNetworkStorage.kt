package com.maxbay.data.hotel.storage

import com.maxbay.data.hotel.api.HotelApi
import com.maxbay.data.hotel.models.HotelData

class HotelNetworkStorage(private val hotelApi: HotelApi): HotelStorage {
    override suspend fun getHotelInfo(): HotelData? {
        return try {
            hotelApi.getHotelInfo()
        }catch (e: Exception) {
            null
        }
    }
}