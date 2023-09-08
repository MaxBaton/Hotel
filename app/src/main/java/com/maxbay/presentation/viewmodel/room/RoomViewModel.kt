package com.maxbay.presentation.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxbay.domain.room.models.Room
import com.maxbay.domain.room.usecases.GetRooms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val getRooms: GetRooms
): ViewModel() {
    private val roomsMutableLiveData = MutableLiveData<List<Room>>()
    val roomsLiveData: LiveData<List<Room>>
        get() = roomsMutableLiveData

    init {
        getRooms()
    }

    fun getRooms() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val rooms = getRooms.getRooms() ?: emptyList()
                roomsMutableLiveData.postValue(rooms)
            }
        }
    }
}