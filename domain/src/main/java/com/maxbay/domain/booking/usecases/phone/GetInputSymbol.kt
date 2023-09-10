package com.maxbay.domain.booking.usecases.phone

import com.maxbay.domain.other.Constants

object GetInputSymbol {
    fun get(oldStr: String, newStr: String): String {
        newStr.forEachIndexed { index, c ->
            try {
                val oldChar = oldStr[index]
                if (c != oldChar) {
                    return c.toString()
                }
            }catch (e: IndexOutOfBoundsException) {
                return c.toString()
            }
        }

        return Constants.Error.EMPTY_STRING
    }
}