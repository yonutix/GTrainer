package com.example.GestureLayer;

public interface GestureListener {
	public void onGesture(RawGesture g);
	public void onDelimiter();
	public void onRecording();
}
