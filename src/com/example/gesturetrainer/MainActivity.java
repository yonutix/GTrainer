package com.example.gesturetrainer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	static Animation animRotate = null;
	static Animation animAlpha = null;
	static Animation animTranslate = null;
	
	static Button btnTrainNewGesture;
	static Button btnEditGesture;
	static Button btnDeleteGesture;
	static Button btnSettings;
	static Button btnExit;
	
	static Intent serviceLink;
	
	static MainActivity context;
	
	public static GestureService s = null;
	
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
		
		context = this;
		
		//serviceLink = new Intent(this, GestureService.class);
		// potentially add data to the intent
		//serviceLink.putExtra("KEY1", "Value to be used by the service");
		//startService(serviceLink); 
		
		
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
	
	
	private ServiceConnection mConnection = new ServiceConnection() {

	    public void onServiceConnected(ComponentName className, 
	        IBinder binder) {
	    	Log.v("yonutix", "Service connected");
	    	GestureService.LocalBinder b = (GestureService.LocalBinder) binder;
	      s = b.getService();
	      Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
	          .show();
	    }

	    public void onServiceDisconnected(ComponentName className) {
	    	Log.v("yonutix", "Service disconnected");
	    	s = null;
	    }
	  };
	
	@Override
	public void onPause(){
		unbindService(mConnection);
		GestureService.runningState = GestureService.TRAINING;
		Log.v("yonutix", "pause");
		super.onPause();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Intent intent= new Intent(this, GestureService.class);
	    boolean test = bindService(intent, mConnection,
	        Context.BIND_AUTO_CREATE);
	    
	    if(test){
	    	Log.v("yonutix", "True");
	    }
	    else{
	    	Log.v("yonutix", "False");
	    }
		
		GestureService.runningState = GestureService.RUNNING;
		
		if(s != null){
			s.msg();
			Log.v("yonutix", "Message should appear");
		}
		else{
			Log.v("yonutix", "Message should not apprear");
		}
		Log.v("yonutix", "resume");
		
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
