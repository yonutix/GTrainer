package com.example.Constants;


public class Globals {
	
	static Globals singleton = new Globals();
	
	
	public static long refTime;
	
	private Globals(){
		refTime = -1;
	}
	
	public static Globals getInstance(){
        return singleton;
    }
	
	public static void setRefTime(long newRefTime){
		refTime = newRefTime;
	}
}
