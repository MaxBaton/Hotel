package com.maxbay.domain.room.usecases

import com.maxbay.domain.room.models.Room
import com.maxbay.domain.room.repository.RoomRepository

class GetRooms(private val roomRepository: RoomRepository) {
    suspend fun getRooms(): List<Room>? {
        return roomRepository.getRooms()
    }
}