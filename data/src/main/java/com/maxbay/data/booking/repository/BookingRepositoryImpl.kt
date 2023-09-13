package com.maxbay.data.booking.repository

import android.util.Log
import com.gefest.utils.Constants
import com.maxbay.data.booking.mappers.mapToListBookingDataDomain
import com.maxbay.data.booking.storage.BookingStorage
import com.maxbay.domain.booking.models.BookingDataDomain
import com.maxbay.domain.booking.repository.BookingRepository

class BookingRepositoryImpl(private val bookingStorage: BookingStorage): BookingRepository {
    override suspend fun getBookingPrice(): List<BookingDataDomain>? {
        return try {
            bookingStorage.getBookingPrice()?.mapToListBookingDataDomain()
        }catch (e: Exception) {
            Log.d(Constants.Logs.OTHER_ERROR, e.message.toString())
            null
        }
    }
}