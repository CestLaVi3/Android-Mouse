
package com.remote.joshsera;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.view.MotionEvent;


public class WrappedMotionEvent {

	private static Method mMotionEvent_GetPointerCount;
	private static Method mMotionEvent_GetPointerId;
	private static Method mMotionEvent_GetX;
	private static Method mMotionEvent_GetY;

	private static boolean mIsMultitouchCapable;

	//������������
	private static Object[] mEmptyObjectArray = new Object[] {};

	static {
		initCompatibility();
	};

	private static void initCompatibility() {
		try {
			mMotionEvent_GetPointerCount = MotionEvent.class.getMethod("getPointerCount",
					new Class[] {});
			mMotionEvent_GetPointerId = MotionEvent.class.getMethod("getPointerId",
					new Class[] { int.class });
			mMotionEvent_GetX = MotionEvent.class.getMethod("getX", new Class[] { int.class });
			mMotionEvent_GetY = MotionEvent.class.getMethod("getY", new Class[] { int.class });
			//�ɹ�����һ���µ�����
			
			mIsMultitouchCapable = true;
			
		} catch (NoSuchMethodException nsme) {
			
			mIsMultitouchCapable = false;
		}
	}

	public static boolean isMutitouchCapable() {
		return mIsMultitouchCapable;
	}
	
	public static int getPointerCount(MotionEvent event) {
		try {
			int pointerCount = (Integer) mMotionEvent_GetPointerCount.invoke(event,
					mEmptyObjectArray);
			return pointerCount;
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("��㴥��ʧ�ܣ�", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("��㴥��ʧ��!", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("��㴥��ʧ��!", e);
		}
	}

	
	public static int getPointerId(MotionEvent event, int pointerIndex) {
		try {
			int pointerCount = (Integer) mMotionEvent_GetPointerId.invoke(event,
					new Object[] { pointerIndex });
			return pointerCount;
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("��㴥��ʧ�ܣ�", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("��㴥��ʧ�ܣ�", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("��㴥��ʧ��!", e);
		}
	}

	public static float getX(MotionEvent event, int pointerIndex) {
		try {
			float pointerCount = (Float) mMotionEvent_GetX.invoke(event,
					new Object[] { pointerIndex });
			return pointerCount;
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("��㴥��ʧ��!", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("��㴥��ʧ��!", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("��㴥��ʧ��!", e);
		}
	}

	public static float getY(MotionEvent event, int pointerIndex) {
		try {
			float pointerCount = (Float) mMotionEvent_GetY.invoke(event,
					new Object[] { pointerIndex });
			return pointerCount;
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("��㴥��ʧ��!", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("��㴥��ʧ��!", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("��㴥��ʧ��!", e);
		}
	}

}
