package com.maxbay.data.room.storage

import com.maxbay.data.room.models.RoomData

interface RoomStorage {
    suspend fun getRooms(): List<RoomData>?
}