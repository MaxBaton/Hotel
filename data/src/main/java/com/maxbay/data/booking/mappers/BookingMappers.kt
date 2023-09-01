package com.maxbay.data.booking.mappers

import com.maxbay.data.booking.models.BookingData
import com.maxbay.data.booking.models.BookingCommonData
import com.maxbay.data.booking.models.BookingDatas
import com.maxbay.data.booking.models.BookingPriceData
import com.maxbay.domain.booking.models.Booking
import com.maxbay.domain.booking.models.BookingDataDomain
import com.maxbay.domain.booking.models.BookingPrice

fun BookingCommonData.mapToBookingData(): BookingDatas.BookingData {
    return BookingDatas.BookingData(
        departure = this.departure,
        arrival_country = this.arrival_country,
        tour_date_start = this.tour_date_start,
        tour_date_stop = this.tour_date_stop,
        number_of_nights = this.number_of_nights,
        room = this.room,
        nutrition = this.nutrition,
        hotel_name = this.hotel_name
    )
}

fun BookingCommonData.mapToBookingHotelData(): BookingDatas.BookingHotelData {
    return BookingDatas.BookingHotelData(
        id = this.id,
        hotel_name = this.hotel_name,
        hotel_adress = this.hotel_adress,
        horating = this.horating,
        rating_name = this.rating_name
    )
}

fun BookingCommonData.mapToBookingPriceData(): BookingDatas.BookingPriceData {
    return BookingDatas.BookingPriceData(
        tour_price = this.tour_price,
        service_charge = this.service_charge,
        fuel_charge = this.fuel_charge
    )
}

fun List<BookingDatas>.mapToListBookingDataDomain(): List<BookingDataDomain> {
    val list = mutableListOf<BookingDataDomain>()
    this.forEach { bookingDatas ->
        when(bookingDatas) {
            is BookingDatas.BookingHotelData -> {
                list.add(bookingDatas.mapToBookingHotel())
            }
            is BookingDatas.BookingData -> {
                list.add(bookingDatas.mapToBooking())
            }
            is BookingDatas.BookingPriceData -> {
                list.add(bookingDatas.mapToBookingPrice())
            }
        }
    }

    return list.toList()
}

fun BookingDatas.BookingHotelData.mapToBookingHotel(): BookingDataDomain.BookingHotel {
    return BookingDataDomain.BookingHotel(
        id = this.id,
        hotelName = this.hotel_name,
        hotelAddress = this.hotel_adress,
        hotRating = this.horating,
        ratingName = this.rating_name
    )
}

fun BookingDatas.BookingData.mapToBooking(): BookingDataDomain.Booking {
    return BookingDataDomain.Booking(
        departure = this.departure,
        arrivalCountry = this.arrival_country,
        tourDateStart = this.tour_date_start,
        tourDateStop = this.tour_date_stop,
        numberOfNights = this.number_of_nights,
        room = this.room,
        nutrition = this.nutrition,
        hotelName = this.hotel_name
    )
}

fun BookingDatas.BookingPriceData.mapToBookingPrice(): BookingDataDomain.Price {
    return BookingDataDomain.Price(
        tourPrice = this.tour_price,
        serviceCharge = this.service_charge,
        fuelCharge = this.fuel_charge
    )
}