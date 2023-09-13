package com.gefest.utils.phone

import com.gefest.utils.Constants


object GetFirstPhoneKeySymbolIndex {
    fun get(str: String): Int {
        return try {
            str.indexOf(Constants.Phone.KEY_SYMBOL)
        }catch (e: IndexOutOfBoundsException) {
            Constants.Error.ERROR_INT
        }
    }
}