package pl.itto.packageinspector.ui.pkgmanager.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import pl.itto.packageinspector.R;

/**
 * Created by PL_itto on 8/28/2017.
 */

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDrawable;
    public ItemDecoration(Context context) {
        mDrawable = context.getResources().getDrawable(R.drawable.line_divider);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left, right, top, bottom;
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + params.bottomMargin;
            bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }

    }
}
