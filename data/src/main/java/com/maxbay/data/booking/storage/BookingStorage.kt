package com.maxbay.data.booking.storage

import com.maxbay.data.booking.models.BookingData
import com.maxbay.data.booking.models.BookingDatas
import com.maxbay.data.booking.models.BookingPriceData

interface BookingStorage {
    suspend fun getBookingPrice(): List<BookingDatas>?
}