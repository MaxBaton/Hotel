package com.maxbay.domain.booking.usecases.phone

import com.maxbay.domain.other.Constants

object GetFirstPhoneKeySymbolIndex {
    fun get(str: String): Int {
        return try {
            str.indexOf(Constants.Phone.KEY_SYMBOL)
        }catch (e: IndexOutOfBoundsException) {
            Constants.Error.ERROR_INT
        }
    }
}