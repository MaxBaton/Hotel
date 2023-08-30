package com.maxbay.domain.hotel.models

data class Hotel(
    val id: Int,
    val name: String,
    val aboutTheHotel: AboutTheHotel,
    val address: String,
    val imageUrls: List<String>,
    val minimalPrice: Int,
    val priceForIt: String,
    val rating: Int,
    val ratingName: String
)