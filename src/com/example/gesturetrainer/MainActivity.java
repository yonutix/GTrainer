package com.example.gesturetrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends Activity {

	static Animation animRotate = null;
	static Animation animAlpha = null;
	static Animation animTranslate = null;
	
	static Button btnTrainNewGesture;
	static Button btnEditGesture;
	static Button btnDeleteGesture;
	static Button btnSettings;
	static Button btnExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		animRotate    = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
		animAlpha     = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
		animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
		
		
		btnTrainNewGesture = (Button)findViewById(R.id.train_new_gesture);
		btnEditGesture     = (Button)findViewById(R.id.edit_gesture);
		btnDeleteGesture   = (Button)findViewById(R.id.delete_gesture);
		btnSettings        = (Button)findViewById(R.id.settings);
		btnExit            = (Button)findViewById(R.id.exit);
	}
	
	public void mainAnimation(){
		((View)btnTrainNewGesture).startAnimation(animAlpha);
		((View)btnEditGesture).startAnimation(animAlpha);
		((View)btnDeleteGesture).startAnimation(animAlpha);
		((View)btnSettings).startAnimation(animAlpha);
		((View)btnSettings).startAnimation(animAlpha);
	}
	
	
	public void onTrainButtonPressed(View v){
		mainAnimation();
		Intent intent = new Intent(this, ChooseAppActivity.class);
		startActivity(intent);
		
	}
	
	
	
	public void onSettings(View v){
		mainAnimation();
		
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	
	public void onExit(View v){
		finish();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
