package com.maxbay.domain.booking.usecases.phone

import com.maxbay.domain.other.Constants

object GetLastInputNumberFromCharSequence {
    fun get(charSequence: CharSequence?): Int {
        val textInput = charSequence?.toString()?.trim() ?: Constants.Error.EMPTY_STRING
        return if (textInput != Constants.Error.EMPTY_STRING) {
            try {
                textInput[textInput.length - 1].digitToInt()
            }catch (e: NumberFormatException) {
                Constants.Error.ERROR_INT
            }catch (e: IndexOutOfBoundsException) {
                Constants.Error.ERROR_INT
            }
        }else {
            Constants.Error.ERROR_INT
        }
    }
}