package com.maxbay.domain.booking.usecases.phone

import com.maxbay.domain.other.Constants

object IsFillPhoneNumber {
    fun isFill(phoneNumber: String) = phoneNumber != Constants.Error.EMPTY_STRING &&
                                        !phoneNumber.contains(Constants.Phone.KEY_SYMBOL)
}