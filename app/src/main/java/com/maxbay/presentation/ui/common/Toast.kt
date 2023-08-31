package com.maxbay.presentation.ui.common

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.maxbay.hotel.R
import io.github.muddz.styleabletoast.StyleableToast

fun Context.showShortToast(message: String) {
    with(StyleableToast.Builder(this)) {
        text(message)
        textColor(Color.WHITE)
        backgroundColor(ContextCompat.getColor(this@showShortToast, R.color.toast_background_color))
        gravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)

        show()
    }
}