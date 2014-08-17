package com.example.gesturetrainer;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.GestureLayer.GestureListener;
import com.example.GestureLayer.RawGesture;
import com.example.SignalProcessing.SignalProcessingEngine;

public class GestureService extends Service {

	private final IBinder mBinder = new LocalBinder();

	public static final int RUNNING = 1;
	public static final int TRAINING = 2;

	public static int runningState;

	public RawGesture refGesture = null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v("yonutix", "Service started");
		runningState = GestureService.RUNNING;

		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {

		SignalProcessingEngine.getInstance().setGestureListener(
				new ServiceGestureListener(this));
		SignalProcessingEngine.getInstance().init(this);

		return mBinder;
	}

	public void msg() {
		Log.v("yonutix", "Reach herex");
	}

	public class LocalBinder extends Binder {
		GestureService getService() {
			return GestureService.this;
		}
	}

}
