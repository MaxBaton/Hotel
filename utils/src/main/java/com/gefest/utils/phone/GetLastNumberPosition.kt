package com.gefest.utils.phone

import com.gefest.utils.Constants


object GetLastNumberPosition {
    fun get(str: String): Int {
        try {
            for (i in str.length - 1 downTo 2) {
                try {
                    str[i].digitToInt()
                    return i
                }catch (e: Exception) {
                    continue
                }
            }

            return Constants.Error.ERROR_INT
        }catch (e: IndexOutOfBoundsException) {
            return Constants.Error.ERROR_INT
        }
    }
}