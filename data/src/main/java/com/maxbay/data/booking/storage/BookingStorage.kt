package com.maxbay.data.booking.storage

import com.maxbay.data.booking.models.BookingDatas

interface BookingStorage {
    suspend fun getBookingPrice(): List<BookingDatas>?
}