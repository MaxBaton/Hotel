package com.maxbay.domain.booking.repository

import com.maxbay.domain.booking.models.Booking
import com.maxbay.domain.booking.models.Price

interface BookingRepository {
    suspend fun getBookingPrice(): Pair<Booking, Price>?
}