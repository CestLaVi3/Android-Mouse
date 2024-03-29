package com.remote.start;

import java.util.ArrayList;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

class GuidePageAdapter extends PagerAdapter {
	public ArrayList<View> pageViews = null;

	GuidePageAdapter(ArrayList<View> pageViews) {
		this.pageViews = pageViews;
	}

	public int getCount() {
		return pageViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
	
		return super.getItemPosition(object);
	}

	public void destroyItem(View arg0, int arg1, Object arg2) {

		((ViewPager) arg0).removeView(pageViews.get(arg1));
	}

	public Object instantiateItem(View arg0, int arg1) {

		((ViewPager) arg0).addView(pageViews.get(arg1));
		return pageViews.get(arg1);
	}

	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	

	}

	public Parcelable saveState() {

		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}
}
