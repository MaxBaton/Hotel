package com.maxbay.data.booking.models

data class BookingData(
    val departure: String,
    val arrival_country: String,
    val tour_date_start: String,
    val tour_date_stop: String,
    val number_of_nights: Int,
    val room: String,
    val nutrition: String
)