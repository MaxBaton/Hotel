package com.maxbay.data.hotel.storage

import android.util.Log
import com.maxbay.data.hotel.api.HotelApi
import com.maxbay.data.hotel.models.HotelData
import com.gefest.utils.other.Constants

class HotelNetworkStorage(private val hotelApi: HotelApi): HotelStorage {
    override suspend fun getHotelInfo(): HotelData? {
        return try {
            hotelApi.getHotelInfo()
        }catch (e: Exception) {
            Log.d(Constants.Logs.NETWORK_ERROR, e.message.toString())
            null
        }
    }
}