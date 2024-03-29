package com.remote.joshsera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class SlidingDarwerImp1 extends SlidingDrawer {
	private boolean mVertical;
	private int mTopOffset;

	public void setOnDrawerOpenListener(
			OnDrawerOpenListener onDrawerOpenListener) {
		super.setOnDrawerOpenListener(onDrawerOpenListener);
	}

	public SlidingDarwerImp1(Context context, AttributeSet attrs) {
		super(context, attrs);
		int orientation = attrs.getAttributeIntValue("android", "orientation",ORIENTATION_VERTICAL);
		mTopOffset = attrs.getAttributeIntValue("android", "topOffset", 0);
		mVertical = (orientation == SlidingDrawer.ORIENTATION_VERTICAL);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

		final View handle = getHandle();
		final View content = getContent();
		measureChild(handle, widthMeasureSpec, heightMeasureSpec);

		if (mVertical) {
			int height = heightSpecSize - handle.getMeasuredHeight()
					- mTopOffset;
			content.measure(widthMeasureSpec,
					MeasureSpec.makeMeasureSpec(height, heightSpecMode));
			heightSpecSize = handle.getMeasuredHeight() + mTopOffset
					+ content.getMeasuredHeight();
			widthSpecSize = content.getMeasuredWidth();
			if (handle.getMeasuredWidth() > widthSpecSize)
				widthSpecSize = handle.getMeasuredWidth();
		} else {
			int width = widthSpecSize - handle.getMeasuredWidth() - mTopOffset;
			getContent().measure(
					MeasureSpec.makeMeasureSpec(width, widthSpecMode),
					heightMeasureSpec);
			widthSpecSize = handle.getMeasuredWidth() + mTopOffset
					+ content.getMeasuredWidth();
			heightSpecSize = content.getMeasuredHeight();
			if (handle.getMeasuredHeight() > heightSpecSize)
				heightSpecSize = handle.getMeasuredHeight();
		}
		setMeasuredDimension(widthSpecSize, heightSpecSize);
	}
}