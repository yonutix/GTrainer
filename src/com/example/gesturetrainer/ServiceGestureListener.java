package com.example.gesturetrainer;

import android.graphics.Color;
import android.util.Log;

import com.example.Constants.DumpLog;
import com.example.GeometricEngine.GeometricEngine;
import com.example.GestureLayer.GestureListener;
import com.example.GestureLayer.RawGesture;

public class ServiceGestureListener implements GestureListener {
	GestureService context;
	
	RawGesture currentGesture;

	public ServiceGestureListener(GestureService context) {
		super();
		this.context = context;
		currentGesture = null;
	}

	@Override
	public void onGesture(RawGesture g) {
		/*
		g.normalize();
		g.acceleration = GeometricEngine.remapDiscreteCourve(g.acceleration);
		if (context.refGesture == null)
			context.refGesture = new RawGesture(g);
		else {
			Log.v("yonutix",
					""
							+ GeometricEngine.DinamicTimeWrapping(
									context.refGesture.acceleration,
									g.acceleration));
		}
*/
		if(RecordGesture.recordingState){
			currentGesture = g;
			RecordGesture.recordingState = false;
			DumpLog._log(g);
		}
	}

	@Override
	public void onDelimiter() {
		if(RecordGesture.runningState)
			RecordGesture.delimiterStatusText.setTextColor(Color.GREEN);
	}

	@Override
	public void onRecording() {
		if(RecordGesture.runningState)
			RecordGesture.delimiterStatusText.setTextColor(Color.RED);
	}

}
