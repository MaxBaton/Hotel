package com.maxbay.data.hotel.models

data class HotelData(
    val id: Int,
    val name: String,
    val aboutTheHotelData: AboutTheHotelData,
    val address: String,
    val imageUrls: List<String>,
    val minimalPrice: Int,
    val priceForIt: String,
    val rating: Int,
    val ratingName: String
)
