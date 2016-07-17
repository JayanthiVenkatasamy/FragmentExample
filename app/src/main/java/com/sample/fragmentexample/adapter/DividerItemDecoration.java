package com.sample.fragmentexample.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class DividerItemDecoration extends RecyclerView.ItemDecoration{
    private static final int[] ATTR = new int[]{android.R.attr.listDivider};

    private static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    private static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDrawableDivider;
    private int mOrientation;

    public  DividerItemDecoration(Context mContext,int mOrientation){
        final TypedArray typedArray =  mContext.obtainStyledAttributes(ATTR);
        mDrawableDivider = typedArray.getDrawable(0);
        typedArray.recycle();
        setOrientation(mOrientation);


    }

    private void setOrientation(int mOrientation){
        if(mOrientation!=HORIZONTAL_LIST && mOrientation!=VERTICAL_LIST)
            throw  new IllegalArgumentException("Invalid Orientation ");
        this.mOrientation = mOrientation;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(this.mOrientation==VERTICAL_LIST)
            drawVerticalDivider(c,parent);
        else
            drawHorizontalDivider(c,parent);
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent) {
         int left = parent.getPaddingLeft();
         int right = parent.getWidth() - parent.getPaddingRight();
         int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDrawableDivider.getIntrinsicHeight();
            mDrawableDivider.setBounds(left, top, right, bottom);
            mDrawableDivider.draw(c);
        }
    }
    private void drawHorizontalDivider(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + mDrawableDivider.getIntrinsicHeight();
            mDrawableDivider.setBounds(left, top, right, bottom);

            mDrawableDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDrawableDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDrawableDivider.getIntrinsicWidth(), 0);
        }
    }
}
