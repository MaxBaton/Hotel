package com.maxbay.data.booking.storage

import com.maxbay.data.booking.models.BookingData
import com.maxbay.data.booking.models.PriceData

interface BookingStorage {
    suspend fun getBookingPrice(): Pair<BookingData, PriceData>?
}