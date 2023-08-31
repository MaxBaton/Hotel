package com.maxbay.domain.booking.usecases

import com.maxbay.domain.booking.models.Booking
import com.maxbay.domain.booking.models.Price
import com.maxbay.domain.booking.repository.BookingRepository

class GetBookingPrice(private val bookingRepository: BookingRepository) {
    suspend fun get(): Pair<Booking, Price>? {
        return bookingRepository.getBookingPrice()
    }
}