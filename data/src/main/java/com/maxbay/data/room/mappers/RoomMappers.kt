package com.maxbay.data.room.mappers

import com.maxbay.data.room.models.RoomData
import com.maxbay.domain.room.models.Room

fun RoomData.mapToRoom(): Room {
    return Room(
        id = this.id,
        imageUrls = this.image_urls,
        name = this.name,
        peculiarities = this.peculiarities,
        price = this.price,
        pricePer = this.price_per
    )
}

fun List<RoomData>.mapToListRoom(): List<Room> {
    val rooms = mutableListOf<Room>()

    this.forEach { roomData ->
        val room = roomData.mapToRoom()
        rooms.add(room)
    }

    return rooms.toList()
}