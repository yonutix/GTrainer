package com.example.gesturetrainer;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppListAdapter extends ArrayAdapter<String>{
	private final Activity context;
	private final String[] web;
	//private final Integer[] imageId;
	
	Vector<Drawable> icons;

	public AppListAdapter(Activity context,
			String[] web, Vector<Drawable> icons) {
			super(context, R.layout.list_item, web);
			this.context = context;
			this.web = web;
			this.icons = new Vector<Drawable>();
			for(int i = 0; i < icons.size(); ++i){
				this.icons.add(icons.elementAt(i));
			}
	}

	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_item, null, true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.textView);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
		txtTitle.setText(web[position]);
		imageView.setImageBitmap(((BitmapDrawable)icons.elementAt(position)).getBitmap());
		
		return rowView;
	}
}
