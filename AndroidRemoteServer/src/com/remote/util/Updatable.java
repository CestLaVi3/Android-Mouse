package com.remote.util;


public interface Updatable {
	public void update(float elapsed);
	public void onEnter();
	public void onExit();
	public void onPlay();
	public void onPause();
}