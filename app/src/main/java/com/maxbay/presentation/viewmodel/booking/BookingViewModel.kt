package com.maxbay.presentation.viewmodel.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxbay.domain.booking.models.BookingDataDomain
import com.maxbay.domain.booking.usecases.GetBookingPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val getBookingPrice: GetBookingPrice
): ViewModel() {
    // BookingData
    private val bookingMutableLiveData = MutableLiveData<List<BookingDataDomain>?>()
    val bookingLiveData: LiveData<List<BookingDataDomain>?>
        get() = bookingMutableLiveData

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
}