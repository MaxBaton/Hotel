package com.maxbay.di.domain

import com.maxbay.domain.room.repository.RoomRepository
import com.maxbay.domain.room.usecases.GetRooms
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RoomDomainModule {
    @Provides
    @ViewModelScoped
    fun provideGetRooms(roomRepository: RoomRepository): GetRooms {
        return GetRooms(roomRepository = roomRepository)
    }
}