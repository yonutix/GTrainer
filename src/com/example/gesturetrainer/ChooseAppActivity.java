package com.example.gesturetrainer;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


class OnAppListListener implements OnItemClickListener{

    Activity context;

    public OnAppListListener(Activity context) {
       this.context = context;
    }

    @Override 
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3)
    { 

    	ResolveInfo launchable=ChooseAppActivity.list.get(position);
    	ActivityInfo activity=launchable.activityInfo;
    	Log.v("yonutix ", "Reach here");
    	Intent intent = new Intent(context, RecordGesture.class);
		Bundle b = new Bundle();
		b.putString("name", activity.name);
		b.putString("packagename", activity.applicationInfo.packageName);
		intent.putExtras(b);
		
		context.startActivity(intent);
    }

 }

public class ChooseAppActivity extends Activity {
	PackageManager pm;
	final String TAG = "yonutix";
	public static String[] values;
	static Vector<Drawable> icons;
	
	public static List<ResolveInfo> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_choose_app);
		pm = getPackageManager();

		ListView l = (ListView) findViewById(R.id.listview);
		
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
	    intent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		list = pm.queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED);

		values = new String[list.size()];
		
		icons = new Vector<Drawable>();

		int i = 0;
		for (ResolveInfo rInfo : list){
	       Drawable icon =  rInfo.activityInfo.applicationInfo.loadIcon(pm);
	       icons.add(icon);
	       values[i++] = rInfo.activityInfo.applicationInfo.loadLabel(pm).toString();
	    }
	    
		AppListAdapter appListAdapter = new AppListAdapter(this, values, icons);

		l.setAdapter(appListAdapter);
		l.setOnItemClickListener(new OnAppListListener(this));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_app, menu);
		return true;
	}

}
