package com.wf.test.recycle.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * SimpleRecycleDecoration -> com.wf.test.recycle.decoration -> SimpleRecycleDecoration
 *
 * @Author: wf-pc
 * @Date: 2020-06-15 16:36
 */
public class SimpleRecyclerDecoration extends RecyclerView.ItemDecoration {

    private Drawable mHorizontalDivider;
    private Drawable mVerticalDivider;
    private int mHorizontalSpan;
    private int mVerticalSpan;

    /**
     * 线性布局
     *
     * @param color 间隔线颜色
     * @param span  间隔线宽度
     */
    public SimpleRecyclerDecoration(int color, int span) {
        mHorizontalDivider = new ColorDrawable(color);
        mVerticalDivider = new ColorDrawable(color);
        mHorizontalSpan = span;
        mVerticalSpan = span;
    }

    /**
     * 网格布局
     *
     * @param hColor 横向间隔线颜色
     * @param vColor 纵向间隔线颜色
     * @param hSpan  横向间隔线宽度
     * @param vSpan  纵向间隔线宽度
     */
    public SimpleRecyclerDecoration(int hColor, int vColor, int hSpan, int vSpan) {
        mHorizontalDivider = new ColorDrawable(hColor);
        mVerticalDivider = new ColorDrawable(vColor);
        mHorizontalSpan = hSpan;
        mVerticalSpan = vSpan;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        if (isGridLayout(parent)) {
            offsetGrid(outRect, view, parent);
        } else {
            offsetLinear(outRect, view, parent);
        }
    }

    private void offsetGrid(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int position = parent.getChildAdapterPosition(view);
        if (position < 0) return;
        int column = position % spanCount;//计算处于哪一列
        int left = column * mVerticalSpan / spanCount;
        int right = mVerticalSpan - (column + 1) * mVerticalSpan / spanCount;
        int bottom = isLastRow(position, parent) ? 0 : mHorizontalSpan;
        outRect.set(left, 0, right, bottom);
    }

    private void offsetLinear(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent) {
        if (parent.getAdapter() != null && parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
            return;
        }
        if (isVertical(parent)) {
            outRect.set(0, 0, 0, mHorizontalSpan);
        } else {
            outRect.set(0, 0, mVerticalSpan, 0);
        }
    }


    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (isGridLayout(parent)) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        } else {
            if (isVertical(parent)) {
                drawHorizontal(c, parent);
            } else {
                drawVertical(c, parent);
            }
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            //最后一行底部横线不绘制
            if (isLastRow(i, parent)) {
                continue;
            }
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin;
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mHorizontalSpan;

            mHorizontalDivider.setBounds(left, top, right, bottom);
            mHorizontalDivider.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            //最后一列右边不绘制
            if (isLastColumn(i, parent)) {
                continue;
            }
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin + mHorizontalSpan;
            int left = child.getRight() + params.rightMargin;
            int right = left + mVerticalSpan;
            //满足条件( 最后一行 && 不绘制 ) 将vertical多出的一部分去掉;
            /*if (i==childCount-1) {
                right -= mVerticalSpan;
            }*/
            mVerticalDivider.setBounds(left, top, right, bottom);
            mVerticalDivider.draw(c);
        }
    }


    private boolean isGridLayout(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        return (layoutManager instanceof GridLayoutManager)
                || (layoutManager instanceof StaggeredGridLayoutManager);
    }

    private boolean isVertical(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            return orientation == LinearLayoutManager.VERTICAL;
        }
        return false;
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            return lm.getSpanCount();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) layoutManager;
            return lm.getSpanCount();
        }
        return 1;
    }

    //判断是否为第一行
    private boolean isFirstRow(int position, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int spanCount = getSpanCount(parent);
        if (layoutManager instanceof GridLayoutManager) {
            return position < spanCount;
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (((StaggeredGridLayoutManager) layoutManager).getOrientation()
                    == StaggeredGridLayoutManager.VERTICAL) {
                return position < spanCount;
            } else {
                return position % spanCount == 0;
            }
        }
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation()
                    == LinearLayoutManager.VERTICAL) {
                return position == 0;
            } else {
                return true;
            }
        }
        return false;
    }

    //判断是否为最后一行
    private boolean isLastRow(int position, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int childCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();
        int spanCount = getSpanCount(parent);
        if (layoutManager instanceof GridLayoutManager) {
            int index;
            if (childCount % spanCount == 0) {
                index = childCount - spanCount;
            } else {
                index = childCount - childCount % spanCount;
            }
            return position >= index;
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (((StaggeredGridLayoutManager) layoutManager).getOrientation()
                    == StaggeredGridLayoutManager.VERTICAL) {
                int index;
                if (childCount % spanCount == 0) {
                    index = childCount - spanCount;
                } else {
                    index = childCount - childCount % spanCount;
                }
                return position >= index;
            } else {
                return (position + 1) % spanCount == 0;
            }
        }
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation()
                    == LinearLayoutManager.VERTICAL) {
                return position == childCount - 1;
            } else {
                return true;
            }
        }
        return false;
    }

    //判断是否为第一列
    private boolean isFirstColumn(int position, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int spanCount = getSpanCount(parent);
        if (layoutManager instanceof GridLayoutManager) {
            return position % spanCount == 0;
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (((StaggeredGridLayoutManager) layoutManager).getOrientation()
                    == StaggeredGridLayoutManager.VERTICAL) {
                return position % spanCount == 0;
            } else {
                return position < spanCount;
            }
        }
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation()
                    == LinearLayoutManager.VERTICAL) {
                return true;
            } else {
                return position == 0;
            }
        }
        return false;
    }

    //判断是否为最后一列
    private boolean isLastColumn(int position, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int childCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();
        int spanCount = getSpanCount(parent);
        if (layoutManager instanceof GridLayoutManager) {
            return (position + 1) % spanCount == 0;
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (((StaggeredGridLayoutManager) layoutManager).getOrientation()
                    == StaggeredGridLayoutManager.VERTICAL) {
                return (position + 1) % spanCount == 0;
            } else {
                int index;
                if (childCount % spanCount == 0) {
                    index = childCount - spanCount;
                } else {
                    index = childCount - childCount % spanCount;
                }
                return position >= index;
            }
        }
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation()
                    == LinearLayoutManager.VERTICAL) {
                return true;
            } else {
                return position == childCount - 1;
            }
        }
        return false;
    }

}
