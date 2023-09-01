package com.maxbay.domain.booking.usecases

import com.maxbay.domain.booking.models.BookingDataDomain
import com.maxbay.domain.booking.repository.BookingRepository

class GetBookingPrice(private val bookingRepository: BookingRepository) {
    suspend fun get(): List<BookingDataDomain>? {
        return bookingRepository.getBookingPrice()
    }
}