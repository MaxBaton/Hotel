package com.maxbay.data.room.api

import com.maxbay.data.room.models.RoomsData
import retrofit2.http.GET

interface RoomApi {
    @GET("f9a38183-6f95-43aa-853a-9c83cbb05ecd")
    suspend fun getRooms(): RoomsData
}