package com.maxbay.presentation.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.maxbay.hotel.R


class MyDividerItemDecoration(context: Context, orientation: Int): DividerItemDecoration(context, orientation) {
    private var desiredWidth: Int = 0

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // Draw the custom divider
        // Draw the custom divider
        val divider = ContextCompat.getDrawable(parent.context, R.drawable.divider_line)
        val paddingLeft = parent.paddingLeft
        val paddingRight = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom: Int = top + desiredWidth // desiredWidth from getItemOffsets()
            divider!!.setBounds(paddingLeft, top, paddingRight, bottom)
            divider!!.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // Set the desired width for the divider
        // Set the desired width for the divider
        desiredWidth = 16 // Change this value to your desired width in pixels


        if (parent.getChildAdapterPosition(view) === parent.adapter!!.itemCount - 1) {
            // For the last item, don't apply the divider
            outRect.setEmpty()
        } else {
            // Apply the divider with the desired width
            outRect[0, 0, 0] = desiredWidth // Change the values if you want to add padding
        }
    }
}