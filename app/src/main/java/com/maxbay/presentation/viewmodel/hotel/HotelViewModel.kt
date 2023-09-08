package com.maxbay.presentation.viewmodel.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxbay.domain.hotel.models.Hotel
import com.maxbay.domain.hotel.usecases.GetHotelInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor(
    private val getHotelInfo: GetHotelInfo
): ViewModel() {
    private val hotelMutableLiveData = MutableLiveData<Hotel?>()
    val hotelLiveData: LiveData<Hotel?>
        get() = hotelMutableLiveData

    init {
        getHotelInfo()
    }

    fun getHotelInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val hotel = getHotelInfo.get()
                withContext(Dispatchers.Main) {
                    hotelMutableLiveData.postValue(hotel)
                }
            }
        }
    }
}