package com.example.GeometricEngine;

import android.hardware.SensorEvent;
import android.util.Log;

import com.example.Constants.Globals;
import com.example.SignalProcessing.AccelerometerListener;

public class Vector3 {
	public double x;
	public double y;
	public double z;
	
	public long timestamp;
	
	public Vector3(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		timestamp = AccelerometerListener.getTime() - Globals.refTime;
		//Log.v("yonutix", )
	}
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		timestamp = AccelerometerListener.getTime() - Globals.refTime;
	}
	
	
	public Vector3(SensorEvent se) {
		super();
		this.x = se.values[0];
		this.y = se.values[1];
		this.z = se.values[2];
		timestamp = (se.timestamp - Globals.refTime);
	}
	
	public Vector3(Vector3 n) {
		x = n.x;
		y = n.y;
		z = n.z;
		timestamp = n.timestamp;
	}
	
	public Vector3() {
		super();
	}
	
	public Vector3 add(Vector3 adder){
		Vector3 res = new Vector3(x + adder.x, y + adder.y, z + adder.z);
		res.timestamp = adder.timestamp;
		return res;
	}
	
	public Vector3 diff(Vector3 adder){
		return new Vector3(x - adder.x, y - adder.y, z - adder.z);
	}
	
	public Vector3 multiply(float scalar){
		return new Vector3(x * scalar, y * scalar, z * scalar);
	}
	
	public Vector3 multiply(double scalar){
		return new Vector3(x * scalar, y * scalar, z * scalar);
	}
	
	public double dot(Vector3 b){
		return x*b.x + y*b.y + z*b.z;
	}
	
	public Vector3 cross(Vector3 b){
		Vector3 res = new Vector3();
		
		res.x = (y*b.z - z*b.y);
		res.y = (b.x*z - b.z*x);
		res.z = (x*b.y - y*b.x);
		
		return res;
	}
	
	public Vector3 normalize(){
		
		double length = Math.sqrt(x*x + y*y + z*z);
		Log.v("length: " + this + " " + length, "yonutixy");
		if(length == 0){
			return new Vector3();
		}
		
		Vector3 res = new Vector3();
		res.x = x/length;
		res.y = y/length;
		res.z = z/length;
		
		return res;
	}
	
	//around z
	public Vector3 yawRotation(double angle){
		Vector3 res = new Vector3();
		res.x =  x * Math.cos(angle) + y * Math.sin(angle);
		res.y = -x * Math.sin(angle) + y * Math.cos(angle);
		res.z = z;
		return res;
	}
	
	//around x
	public Vector3 pitchRotation(double angle){
		Vector3 res = new Vector3();
		res.x = x;
		res.y =  y * Math.cos(angle) + z * Math.sin(angle);
		res.z = -y * Math.sin(angle) + z * Math.cos(angle);
		
		return res;
	}
	
	//around y
	public Vector3 rollRotation(double angle){
		Vector3 res = new Vector3();
		res.x = x * Math.cos(angle) - z * Math.sin(angle);
		res.y = y;
		res.z = x * Math.sin(angle) + z * Math.cos(angle);
		return res;
	}
	
	public Vector3 rotateByAxis(Vector3 axis, double angle){
		
		double cosAngle = Math.cos(angle);
		double sinAngle = Math.sin(angle);
		
		Vector3 r1 = new Vector3();
		Vector3 r2 = new Vector3();
		Vector3 r3 = new Vector3();
		
		r1.x = cosAngle + (axis.x*axis.x)*(1-cosAngle);
		r2.x = (axis.y*axis.x)*(1-cosAngle) + axis.z*sinAngle;
		r3.x = (axis.z*axis.x)*(1-cosAngle) - axis.y*sinAngle;
		
		r1.y = axis.x * axis.y*(1-cosAngle) - axis.z*sinAngle;
		r2.y = cosAngle + (axis.y*axis.y)*(1-cosAngle);
		r3.y = axis.z*axis.y*(1-cosAngle) + axis.x*sinAngle;
		
		r1.z = axis.x*axis.z*(1-cosAngle) + axis.y*sinAngle;
		r2.z = axis.y*axis.z*(1-cosAngle) - axis.x*sinAngle;
		r3.z = cosAngle + (axis.z*axis.z)*(1-cosAngle);
		
		Vector3 res = new Vector3();
		res.x = r1.dot(this);
		res.y = r2.dot(this);
		res.z = r3.dot(this);
		
		return res;
	}
	
	public Vector3 scale(Vector3 newScale){
		Vector3 res = new Vector3();
		
		Vector3 s1 = new Vector3();
		Vector3 s2 = new Vector3();
		Vector3 s3 = new Vector3();
		s1.x = newScale.x;
		s2.y = newScale.y;
		s3.z = newScale.z;
		
		res.x = s1.dot(this);
		res.y = s2.dot(this);
		res.z = s3.dot(this);
		
		return res;
		
	}
	
	
	
	public float getTimeDelta(Vector3 tf){
		return ((float)(tf.timestamp - timestamp))/1000;
	}
	
	public float getDist(Vector3 p2){
		return (float)Math.sqrt((x - p2.x)*(x - p2.x) + (y - p2.y)*(y - p2.y) + (z - p2.z)*(z - p2.z));
	}
	
	public double length(){
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public boolean equal(Vector3 b){
		return (b.timestamp == timestamp);
			
	}
	
	@Override
	public boolean equals(Object o) {
		Vector3 b = (Vector3)o;
		return (x == b.x && y == b.y && z == b.z);
	}
	
	
	//A->B, C->D
	public static double angle(Vector3 A, Vector3 B, Vector3 C, Vector3 D){
		Vector3 v1 = B.diff(A);
		Vector3 v2 = D.diff(A);
		
		double dotProduct = v1.dot(v2);
		double lengthV1 = v1.length();
		double lengthV2 = v2.length();
		
		double denominator = lengthV1 * lengthV2;
		
		double product = denominator != 0.0 ? dotProduct / denominator : 0.0;

		double angle = Math.acos (product);

		return angle;
	}
	
	public static double angle(Vector3 v1, Vector3 v2){
		
		double dotProduct = v1.dot(v2);
		double lengthV1 = v1.length();
		double lengthV2 = v2.length();
		
		double denominator = lengthV1 * lengthV2;
		
		double product = denominator != 0.0 ? dotProduct / denominator : 0.0;

		double angle = Math.acos (product);

		return angle;
	}
	
	public Object clone(){  
	    try{  
	        return super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}
	
	/*
	@Override
	public String toString() {
		return "Vector3 [x=" + x + ", y=" + y + ", z=" + z + ", timestamp="
				+ timestamp + "]";
	}
*/
/*
	@Override
	public String toString() {
		return "Vector3 [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	*/
}
