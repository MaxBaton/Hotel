package com.maxbay.data.room.storage

import android.util.Log
import com.maxbay.data.room.api.RoomApi
import com.maxbay.data.room.models.RoomData
import com.gefest.utils.Constants

class RoomNetworkStorage(private val roomApi: RoomApi): RoomStorage {
    override suspend fun getRooms(): List<RoomData>? {
        return try {
            roomApi.getRooms().rooms
        }catch (e: Exception) {
            Log.d(com.gefest.utils.Constants.Logs.NETWORK_ERROR, e.message.toString())
            null
        }
    }
}