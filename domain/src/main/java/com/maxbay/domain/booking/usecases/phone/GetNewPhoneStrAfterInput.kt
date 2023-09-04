package com.maxbay.domain.booking.usecases.phone

import com.maxbay.domain.other.Constants
import java.lang.StringBuilder

object GetNewPhoneStrAfterInput {
    private val strBuilder = StringBuilder()

    fun get(currStr: String, inputNumberStr: String): String {
        return try {
            val indexInput = GetFirstPhoneKeySymbolIndex.get(str = currStr)
            if (indexInput != Constants.Error.ERROR_INT) {
                strBuilder.clear()
                strBuilder.append(currStr)
                // Insert
                strBuilder.replace(indexInput, indexInput + 1, inputNumberStr)
                val str = strBuilder.toString()
                str.substring(0, str.length - 1)
            }else {
                Constants.Error.EMPTY_STRING
            }
        }catch (e: IndexOutOfBoundsException) {
            Constants.Error.EMPTY_STRING
        }
    }
}