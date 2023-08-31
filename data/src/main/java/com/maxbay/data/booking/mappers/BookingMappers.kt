package com.maxbay.data.booking.mappers

import com.maxbay.data.booking.models.BookingData
import com.maxbay.data.booking.models.BookingPriceData
import com.maxbay.data.booking.models.PriceData
import com.maxbay.domain.booking.models.Booking
import com.maxbay.domain.booking.models.Price

fun BookingPriceData.mapToBookingData(): BookingData {
    return BookingData(
        id = this.id,
        hotel_name = this.hotel_name,
        hotel_adress = this.hotel_adress,
        horating = this.horating,
        rating_name = this.rating_name,
        departure = this.departure,
        arrival_country = this.arrival_country,
        tour_date_start = this.tour_date_start,
        tour_date_stop = this.tour_date_stop,
        number_of_nights = this.number_of_nights,
        room = this.room,
        nutrition = this.nutrition
    )
}

fun BookingPriceData.mapToPriceData(): PriceData {
    return PriceData(
        tour_price = this.tour_price,
        service_charge = this.service_charge,
        fuel_charge = this.fuel_charge
    )
}

fun BookingData.mapToBooking(): Booking {
    return Booking(
        id = this.id,
        hotelName = this.hotel_name,
        hotelAdress = this.hotel_adress,
        horating = this.horating,
        ratingName = this.rating_name,
        departure = this.departure,
        arrivalCountry = this.arrival_country,
        tourDateStart = this.tour_date_start,
        tourDateStop = this.tour_date_stop,
        numberOfNights = this.number_of_nights,
        room = this.room,
        nutrition = this.nutrition
    )
}

fun PriceData.mapToPrice(): Price {
    return Price(
        tourPrice = this.tour_price,
        serviceCharge = this.service_charge,
        fuelCharge = this.fuel_charge
    )
}

fun Pair<BookingData, PriceData>.mapToPairBookingPrice(): Pair<Booking, Price> {
    return this.first.mapToBooking() to this.second.mapToPrice()
}