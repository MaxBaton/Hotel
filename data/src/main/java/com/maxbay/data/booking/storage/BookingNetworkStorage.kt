package com.maxbay.data.booking.storage

import android.util.Log
import com.maxbay.data.booking.api.BookingApi
import com.maxbay.data.booking.mappers.mapToBookingData
import com.maxbay.data.booking.mappers.mapToBookingHotelData
import com.maxbay.data.booking.mappers.mapToBookingPriceData
import com.maxbay.data.booking.models.BookingDatas
import com.gefest.utils.other.Constants

class BookingNetworkStorage(private val bookingApi: BookingApi): BookingStorage {
    override suspend fun getBookingPrice(): List<BookingDatas>? {
        return try {
            val bookingCommonData = bookingApi.getBookingPrice()
            listOf(
                bookingCommonData.mapToBookingHotelData(),
                bookingCommonData.mapToBookingData(),
                bookingCommonData.mapToBookingPriceData()
            )
        }catch (e: Exception) {
            Log.d(Constants.Logs.NETWORK_ERROR, e.message.toString())
            null
        }
    }
}