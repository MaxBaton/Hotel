package com.maxbay.data.booking.storage

import android.util.Log
import com.maxbay.data.booking.api.BookingApi
import com.maxbay.data.booking.mappers.mapToBookingData
import com.maxbay.data.booking.mappers.mapToPriceData
import com.maxbay.data.booking.models.BookingData
import com.maxbay.data.booking.models.PriceData
import com.maxbay.domain.other.Constants

class BookingNetworkStorage(private val bookingApi: BookingApi): BookingStorage {
    override suspend fun getBookingPrice(): Pair<BookingData, PriceData>? {
        return try {
            val bookingPriceData = bookingApi.getBookingPrice()
            bookingPriceData.mapToBookingData() to bookingPriceData.mapToPriceData()
        }catch (e: Exception) {
            Log.d(Constants.Logs.NETWORK_ERROR, e.message.toString())
            null
        }
    }
}