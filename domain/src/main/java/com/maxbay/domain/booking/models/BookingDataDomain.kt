package com.maxbay.domain.booking.models

sealed class BookingDataDomain {
    data class Booking(
        val departure: String,
        val arrivalCountry: String,
        val tourDateStart: String,
        val tourDateStop: String,
        val numberOfNights: Int,
        val room: String,
        val nutrition: String,
        val hotelName: String
    ): BookingDataDomain()

    data class Hotel(
        val id: Int,
        val hotelName: String,
        val hotelAddress: String,
        val hotRating: Int,
        val ratingName: String,
    ): BookingDataDomain()

    data class Price(
        val tourPrice: Int,
        val serviceCharge: Int,
        val fuelCharge: Int
    ): BookingDataDomain()
}
