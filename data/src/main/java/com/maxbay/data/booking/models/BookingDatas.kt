package com.maxbay.data.booking.models

sealed class BookingDatas {
    data class BookingData(
        val departure: String,
        val arrival_country: String,
        val tour_date_start: String,
        val tour_date_stop: String,
        val number_of_nights: Int,
        val room: String,
        val nutrition: String,
        val hotel_name: String
    ): BookingDatas()

    data class BookingHotelData(
        val id: Int,
        val hotel_name: String,
        val hotel_adress: String,
        val horating: Int,
        val rating_name: String,
    ): BookingDatas()

    data class BookingPriceData(
        val tour_price: Int,
        val fuel_charge: Int,
        val service_charge: Int
    ): BookingDatas()
}