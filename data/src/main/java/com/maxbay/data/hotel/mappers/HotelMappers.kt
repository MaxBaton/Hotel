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

fun Hotel.mapToHotelData(): HotelData {
    return HotelData(
        id = this.id,
        name = this.name,
        aboutTheHotelData = this.aboutTheHotel.mapToAboutTheHotelData(),
        address = this.address,
        imageUrls = this.imageUrls,
        minimalPrice = this.minimalPrice,
        priceForIt = this.priceForIt,
        rating = this.rating,
        ratingName = this.ratingName
    )
}

fun HotelData.mapToHotel(): Hotel {
    return Hotel(
        id = this.id,
        name = this.name,
        aboutTheHotel = this.aboutTheHotelData.mapToAboutTheHotel(),
        address = this.address,
        imageUrls = this.imageUrls,
        minimalPrice = this.minimalPrice,
        priceForIt = this.priceForIt,
        rating = this.rating,
        ratingName = this.ratingName
    )
}