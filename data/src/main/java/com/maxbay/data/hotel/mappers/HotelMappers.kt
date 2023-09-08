package com.maxbay.data.hotel.mappers

import com.maxbay.data.hotel.models.AboutTheHotelData
import com.maxbay.data.hotel.models.HotelData
import com.maxbay.domain.hotel.models.AboutTheHotel
import com.maxbay.domain.hotel.models.Hotel

fun AboutTheHotel.mapToAboutTheHotelData(): AboutTheHotelData {
    return AboutTheHotelData(
        description = this.description,
        peculiarities = this.peculiarities
    )
}

fun AboutTheHotelData.mapToAboutTheHotel(): AboutTheHotel {
    return AboutTheHotel(
        description = this.description,
        peculiarities = this.peculiarities
    )
}

fun HotelData.mapToHotel(): Hotel {
    return Hotel(
        id = this.id,
        name = this.name,
        aboutTheHotel = this.about_the_hotel.mapToAboutTheHotel(),
        address = this.adress,
        imageUrls = this.image_urls,
        minimalPrice = this.minimal_price,
        priceForIt = this.price_for_it,
        rating = this.rating,
        ratingName = this.rating_name
    )
}