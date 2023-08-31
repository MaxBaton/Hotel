package com.maxbay.data.booking.repository

import android.util.Log
import com.maxbay.data.booking.mappers.mapToPairBookingPrice
import com.maxbay.data.booking.storage.BookingStorage
import com.maxbay.domain.booking.models.Booking
import com.maxbay.domain.booking.models.Price
import com.maxbay.domain.booking.repository.BookingRepository
import com.maxbay.domain.other.Constants

class BookingRepositoryImpl(private val bookingStorage: BookingStorage): BookingRepository {
    override suspend fun getBookingPrice(): Pair<Booking, Price>? {
        return try {
            bookingStorage.getBookingPrice()?.mapToPairBookingPrice()
        }catch (e: Exception) {
            Log.d(Constants.Logs.OTHER_ERROR, e.message.toString())
            null
        }
    }
}