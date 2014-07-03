package com.example.gesturetrainer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class RecordGesture extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_gesture);
		
		TextView recordGestureMainText = (TextView)findViewById(R.id.record_gesture_text);
		SharedPreferences prefs = PreferenceManager
			    .getDefaultSharedPreferences(RecordGesture.this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_gesture, menu);
		return true;
	}

}
