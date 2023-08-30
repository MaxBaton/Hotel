package com.maxbay.data.hotel.api

import com.maxbay.data.hotel.models.HotelData
import retrofit2.http.GET

interface HotelApi {
    @GET("e8868481-743f-4eb2-a0d7-2bc4012275c8")
    suspend fun getHotelInfo(): HotelData
}