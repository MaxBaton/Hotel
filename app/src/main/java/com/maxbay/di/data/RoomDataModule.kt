package com.maxbay.di.data

import com.maxbay.data.room.api.RoomApi
import com.maxbay.data.room.repository.RoomRepositoryImpl
import com.maxbay.data.room.storage.RoomNetworkStorage
import com.maxbay.data.room.storage.RoomStorage
import com.maxbay.domain.room.repository.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDataModule {
    @Provides
    @Singleton
    fun provideRoomRepository(roomStorage: RoomStorage): RoomRepository {
        return RoomRepositoryImpl(roomStorage = roomStorage)
    }

    @Provides
    @Singleton
    fun provideRoomNetworkStorage(roomApi: RoomApi): RoomStorage {
        return RoomNetworkStorage(roomApi = roomApi)
    }

    @Provides
    @Singleton
    fun provideRoomApi(retrofit: Retrofit): RoomApi {
        return retrofit.create(RoomApi::class.java)
    }
}