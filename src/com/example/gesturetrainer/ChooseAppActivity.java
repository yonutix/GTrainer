package com.example.gesturetrainer;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


class OnAppListListener implements OnItemClickListener{

    Activity context;

    public OnAppListListener(Activity context) {
       this.context = context;
    }

    @Override 
    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
    { 
    	Intent intent = new Intent(context, RecordGesture.class);
		Bundle b = new Bundle();
		b.putInt("position", position);
		intent.putExtras(b);
		context.startActivity(intent);
		
    }

 }

public class ChooseAppActivity extends Activity {
	PackageManager pm;
	final String TAG = "yonutix";
	static String[] values;
	static Vector<Drawable> icons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_app);
		pm = getPackageManager();

		ListView l = (ListView) findViewById(R.id.listview);
		
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
	    intent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED);

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
