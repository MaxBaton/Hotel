package com.maxbay.data.booking.models

data class BookingData(
    val id: Int,
    val hotel_name: String,
    val hotel_adress: String,
    val horating: Int,
    val rating_name: String,
    val departure: String,
    val arrival_country: String,
    val tour_date_start: String,
    val tour_date_stop: String,
    val number_of_nights: Int,
    val room: String,
    val nutrition: String
)