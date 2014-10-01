package com.example.gesturetrainer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.GeometricEngine.Vector3;
import com.example.GestureLayer.RawGesture;

public class RecordGesture extends Activity {
	public static TextView delimiterStatusText;

	public static boolean runningState = false;
	public static boolean recordingState = false;
	public static ImageButton recordingBtn = null;

	public static Queue<RawGesture> currentGestures = null;
	
	
	public static String targetApp;
	
	public static AlertDialog.Builder builder = null;
	public static AlertDialog ad = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_record_gesture);
		
		targetApp = getIntent().getStringExtra("name")+ "|" + getIntent().getStringExtra("packagename");
		
		//Log.v("yonutix target received", targetApp);
		
		//TextView recordGestureMainText = (TextView) findViewById(R.id.record_gesture_text);
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
		recordingBtn = (ImageButton) findViewById(R.id.start_recording);
		if (currentGestures == null)
			currentGestures = new LinkedList<RawGesture>();
	}

	@Override
	public void onPause() {
		if (MainActivity.s != null) {
			GestureService.runningState = GestureService.RUNNING;
		}
		currentGestures.clear();
		runningState = false;
		recordingBtn = null;
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_gesture, menu);
		return true;
	}

	public void onStartRecording(View v) {
		Log.v("yonutix", "Recording starteddddddd!!!!!!");
		
		CharSequence colors[] = new CharSequence[] {};
		
		builder = new AlertDialog.Builder(this);
		
		ad = builder.create();
		
		ad.setTitle("Gesture is recorded....start");
		ad.show();
		
		recordingState = true;
		
	}

	public void onDeleteLast(View v) {
		
	}
	// TODO: Change try again to delete last gesture
	public void onTryAgain(View v) {
		if (!currentGestures.isEmpty())
			currentGestures.poll();
	}

	public void onNext(View v) {
		if (currentGestures != null) {
			if (!currentGestures.isEmpty()) {
				ServiceGestureListener.allGestures.put(targetApp, new GestureCluster(currentGestures));
			}
		}
	}

	// TODO: is it needed?
	public void onEnforce(View v) {

	}

}
