package com.maxbay.presentation.viewmodel.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxbay.domain.booking.models.BookingDataDomain
import com.maxbay.domain.booking.models.TouristInfo
import com.maxbay.domain.booking.models.UserInfo
import com.maxbay.domain.booking.usecases.GetBookingPrice
import com.maxbay.domain.booking.usecases.tourist.GetSumPrice
import com.maxbay.domain.other.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val getBookingPrice: GetBookingPrice,
    private val getSumPrice: GetSumPrice
): ViewModel() {
    // BookingData
    private val bookingMutableLiveData = MutableLiveData<List<BookingDataDomain>?>()
    val bookingLiveData: LiveData<List<BookingDataDomain>?>
        get() = bookingMutableLiveData
    // UserInfo
    private val userInfoMutableLiveData = MutableLiveData<UserInfo?>()
    val userInfoLiveData: LiveData<UserInfo?>
        get() = userInfoMutableLiveData
    // TouristInfo
    private val touristInfoMutableLiveData = MutableLiveData<TouristInfo?>()
    val touristInfoLiveData: LiveData<TouristInfo?>
        get() = touristInfoMutableLiveData

    init {
        getBookingPrice()
    }

    private fun getBookingPrice() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val listBookingData = getBookingPrice.get()
                if (listBookingData != null) {
                    bookingMutableLiveData.postValue(listBookingData)
                }else {
                    bookingMutableLiveData.postValue(null)
                }
            }
        }
    }

    fun getSumPrice(price: BookingDataDomain.Price?): Int {
        return if (price != null) {
            getSumPrice.get(price = price)
        }else {
            Constants.Error.ERROR_INT
        }
    }

    fun saveUserInfo(userInfo: UserInfo) {
        userInfoMutableLiveData.postValue(userInfo)
    }

    fun resetUserInfo() {
        userInfoMutableLiveData.postValue(null)
    }

    fun saveTouristInfo(touristInfo: TouristInfo) {
        touristInfoMutableLiveData.postValue(touristInfo)
    }

    fun resetTouristInfo() {
        touristInfoMutableLiveData.postValue(null)
    }
}