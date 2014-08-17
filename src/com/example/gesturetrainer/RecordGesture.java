package com.example.gesturetrainer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class RecordGesture extends Activity {
	public static TextView delimiterStatusText;

	public static boolean runningState   = false;
	public static boolean recordingState = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_gesture);

		TextView recordGestureMainText = (TextView) findViewById(R.id.record_gesture_text);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(RecordGesture.this);

		delimiterStatusText = (TextView) findViewById(R.id.record_gesture_text);
		delimiterStatusText.setTextColor(Color.RED);
		
	}

	@Override
	public void onResume() {
		super.onResume();
		if (MainActivity.s != null) {
			GestureService.runningState = GestureService.TRAINING;
		}
		runningState = true;
	}

	@Override
	public void onPause() {
		if (MainActivity.s != null) {
			GestureService.runningState = GestureService.RUNNING;
		}
		runningState = false;
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_gesture, menu);
		return true;
	}
	
	public void onStartRecording(View v){
		recordingState = true;
	}
	
	public void onTryAgain(View v){
		
	}
	
	public void onNext(View v){
		
	}
	
	public void onEnforce(View v){
		
	}

}
