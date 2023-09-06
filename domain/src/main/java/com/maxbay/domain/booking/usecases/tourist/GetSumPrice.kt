package com.maxbay.domain.booking.usecases.tourist

import com.maxbay.domain.booking.models.BookingDataDomain

class GetSumPrice {
    fun get(price: BookingDataDomain.Price) = price.tourPrice + price.fuelCharge + price.serviceCharge
}