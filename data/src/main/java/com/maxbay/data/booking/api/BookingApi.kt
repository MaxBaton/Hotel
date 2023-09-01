package com.maxbay.data.booking.api

import com.maxbay.data.booking.models.BookingCommonData
import retrofit2.http.GET

interface BookingApi {
    @GET("e8868481-743f-4eb2-a0d7-2bc4012275c8")
    suspend fun getBookingPrice(): BookingCommonData
}