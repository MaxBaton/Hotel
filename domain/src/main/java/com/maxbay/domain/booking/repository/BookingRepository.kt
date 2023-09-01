package com.maxbay.domain.booking.repository

import com.maxbay.domain.booking.models.BookingDataDomain

interface BookingRepository {
    suspend fun getBookingPrice(): List<BookingDataDomain>?
}