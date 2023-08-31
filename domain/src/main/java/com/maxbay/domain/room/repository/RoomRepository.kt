package com.maxbay.domain.room.repository

import com.maxbay.domain.room.models.Room

interface RoomRepository {
    suspend fun getRooms(): List<Room>?
}