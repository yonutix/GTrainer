package com.example.gesturetrainer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class GestureService extends Service{

	@Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    return Service.START_NOT_STICKY;
	  }
	
	@Override
	public IBinder onBind(Intent arg0) {

		return null;
	}
	
}
