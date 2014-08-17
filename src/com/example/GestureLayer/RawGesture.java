package com.example.GestureLayer;
import java.util.Vector;

import android.util.Log;

import com.example.Constants.DumpLog;
import com.example.GeometricEngine.Vector3;



public class RawGesture {
	static final double size = 0.06;
	public Vector<Vector3> acceleration;
	public Vector<Double>          normalizedTime;
	
	public RawGesture(RawGesture g){
		normalizedTime = new Vector<Double>();
		acceleration = new Vector<Vector3>();
		for(Vector3 v : g.acceleration){
			acceleration.add(new Vector3(v));
		}
		//removeBias();
		//normalize();
		normalizeTime();
	}

	public RawGesture(Vector<Vector3> gesture) {
		normalizedTime = new Vector<Double>();
		this.acceleration = new Vector<Vector3>();
		for(Vector3 g : gesture){
			this.acceleration.add(new Vector3(g));
		}
		//removeBias();
		//normalize();
		normalizeTime();
	}
	
	public void removeBias(){
		Vector3 med = new Vector3();
		for(int i = 0; i < acceleration.size(); ++i){
			med = med.add(acceleration.elementAt(i));
		}
		med.x = med.x/acceleration.size();
		med.y = med.y/acceleration.size();
		med.z = med.z/acceleration.size();
		
		for(int i = 0; i < acceleration.size(); ++i){
			acceleration.elementAt(i).x -= med.x;
			acceleration.elementAt(i).y -= med.y;
			acceleration.elementAt(i).z -= med.z;
		}
		
	}
	
	public void normalize(){
		
		
		Vector3 mins = new Vector3();
		mins.x = Double.MAX_VALUE;
		mins.y = Double.MAX_VALUE;
		mins.z = Double.MAX_VALUE;
		
		Vector3 maxs = new Vector3();
		maxs.x = Double.MIN_VALUE;
		maxs.y = Double.MIN_VALUE;
		maxs.z = Double.MIN_VALUE;
		
		for(Vector3 v: acceleration){
			maxs.x = (maxs.x < v.x)?v.x:maxs.x;
			maxs.y = (maxs.y < v.x)?v.y:maxs.y;
			maxs.z = (maxs.z < v.x)?v.z:maxs.z;
			
			mins.x = (mins.x > v.x)?v.x:mins.x;
			mins.y = (mins.y > v.y)?v.y:mins.y;
			mins.z = (mins.z > v.z)?v.z:mins.z;
		}
		double absMin = mins.x;
		
		if(absMin > mins.y)
			absMin = mins.y;
		if(absMin > mins.z)
			absMin = mins.z;
		
		double absMax = maxs.x;
		if(absMax < maxs.y)
			absMax = maxs.y;
		if(absMax < maxs.z)
			absMax = maxs.z;
		
		double scale = 1/(absMax - absMin);
		Vector3 scaleV = new Vector3();
		scaleV.x = scale;
		scaleV.y = scale;
		scaleV.z = scale;
		
		for(int i = 0; i < acceleration.size(); ++i){
			Vector3 v = acceleration.elementAt(i);
			v.x = v.x - mins.x;
			v.y = v.y - mins.y;
			v.z = v.z - mins.z;
			

			v.x = v.x * scale;
			v.y = v.y * scale;
			v.z = v.z * scale;

			acceleration.setElementAt(new Vector3(v), i);
		}
		
	}
	
	public void normalizeTime(){
		long maxT = -1;
		long minT = Long.MAX_VALUE;
		
		for(Vector3 v: acceleration){
			if(v.timestamp < minT){
				minT = v.timestamp;
			}
			if(v.timestamp > maxT){
				maxT = v.timestamp;
			}
		}
		
		long deltaT = maxT - minT;
		
		for(Vector3 v: acceleration){
			v.timestamp -= minT;
			normalizedTime.add(Double.valueOf(((double)v.timestamp)/((double)deltaT)));
		}
	}
	
	
	public void dump(){
		Log.v("yonutix", "acceleration " + acceleration.size());
		DumpLog.startSesssion();
		for(Vector3 v: acceleration){
			DumpLog._log(v);
		}
		DumpLog.endSession();
	}
	
	public void dump(String filename){
		Log.v("yonutix", "acceleration " + acceleration.size());
		DumpLog.startSesssion(filename);
		for(Vector3 v: acceleration){
			DumpLog._log(v);
		}
		DumpLog.endSession();
	}
	
	
	
}
