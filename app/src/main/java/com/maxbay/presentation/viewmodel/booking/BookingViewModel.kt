package com.maxbay.presentation.viewmodel.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxbay.domain.booking.models.Booking
import com.maxbay.domain.booking.models.Price
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
    // Booking
    private val bookingMutableLiveData = MutableLiveData<Booking?>()
    val bookingLiveData: LiveData<Booking?>
        get() = bookingMutableLiveData
    // Price
    private val priceMutableLiveData = MutableLiveData<Price?>()
    val priceLiveData: LiveData<Price?>
        get() = priceMutableLiveData

    init {
        getBookingPrice()
    }

    private fun getBookingPrice() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val bookingPrice = getBookingPrice.get()
                if (bookingPrice != null) {
                    bookingMutableLiveData.postValue(bookingPrice.first)
                    priceMutableLiveData.postValue(bookingPrice.second)
                }else {
                    bookingMutableLiveData.postValue(null)
                    priceMutableLiveData.postValue(null)
                }
            }
        }
    }
}