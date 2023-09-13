package com.maxbay.data.room.repository

import android.util.Log
import com.maxbay.data.room.mappers.mapToListRoom
import com.maxbay.data.room.storage.RoomStorage
import com.gefest.utils.other.Constants
import com.maxbay.domain.room.models.Room
import com.maxbay.domain.room.repository.RoomRepository

class RoomRepositoryImpl(private val roomStorage: RoomStorage): RoomRepository {
    override suspend fun getRooms(): List<Room>? {
        return try {
            roomStorage.getRooms()?.mapToListRoom()
        }catch (e: Exception) {
            Log.d(Constants.Logs.OTHER_ERROR, e.message.toString())
            null
        }
    }
}