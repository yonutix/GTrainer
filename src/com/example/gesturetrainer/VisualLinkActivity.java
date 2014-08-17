package com.example.gesturetrainer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class VisualLinkActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visual_link);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visual_link, menu);
		return true;
	}

}
