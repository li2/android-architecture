/**
 * RecyclerViewMarginDecoration
 * [RecyclerView] support the concept of [android.support.v7.widget.RecyclerView.ItemDecoration]:
 *
 * It allows the application to add a special drawing and layout offset
 * to specific item views from the adapter's data set. This can be useful for drawing dividers
 * between items, highlights, visual grouping boundaries and more.
 *
 * Created by weiyi.li on 2017-01-31.
 */
package me.li2.android.architecture.ui.widget

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerViewMarginDecoration(private val top: Int, private val bottom: Int, private val left: Int, private val right: Int) : RecyclerView.ItemDecoration() {

    constructor(margin: Int) : this(margin, margin, margin, margin)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = top
        }
        outRect.bottom = bottom
        outRect.left = left
        outRect.right = right
    }
}
