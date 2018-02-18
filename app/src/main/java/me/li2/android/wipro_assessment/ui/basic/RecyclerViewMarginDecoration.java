/**
 * RecyclerViewMarginDecoration
 * {@link RecyclerView} support the concept of {@link android.support.v7.widget.RecyclerView.ItemDecoration}:
 *
 * It allows the application to add a special drawing and layout offset
 * to specific item views from the adapter's data set. This can be useful for drawing dividers
 * between items, highlights, visual grouping boundaries and more.
 *
 * Created by weiyi.li on 2017-01-31.
 */
package me.li2.android.wipro_assessment.ui.basic;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewMarginDecoration extends RecyclerView.ItemDecoration
{
    private int top;
    private int bottom;
    private int left;
    private int right;

    public RecyclerViewMarginDecoration(int margin) {
      this(margin, margin, margin, margin);
    }
    
    public RecyclerViewMarginDecoration(int top, int bottom, int left, int right) {
      super();
      this.top = top;
      this.bottom = bottom;
      this.left = left;
      this.right = right;
    }
    
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = top;
        }
        outRect.bottom = bottom;
        outRect.left = left;
        outRect.right = right;
    }
}
