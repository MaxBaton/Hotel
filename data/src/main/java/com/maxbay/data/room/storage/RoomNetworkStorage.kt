package com.maxbay.data.room.storage

import com.maxbay.data.room.api.RoomApi
import com.maxbay.data.room.models.RoomData

class RoomNetworkStorage(private val roomApi: RoomApi): RoomStorage {
    override suspend fun getRooms(): List<RoomData>? {
        return try {
            roomApi.getRooms().roomsData
        }catch (e: Exception) {
            null
        }
    }
}