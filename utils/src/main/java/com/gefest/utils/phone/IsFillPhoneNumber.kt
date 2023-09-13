package com.gefest.utils.phone

import com.gefest.utils.Constants


object IsFillPhoneNumber {
    fun isFill(phoneNumber: String) = phoneNumber != Constants.Error.EMPTY_STRING &&
                                        !phoneNumber.contains(Constants.Phone.KEY_SYMBOL)
}