package com.example.Constants;

import android.hardware.Sensor;
import android.hardware.SensorManager;

public class Globals {

	static Globals singleton = new Globals();

	public static long refTime;
	
	public static SensorManager sensorManager;
	public static Sensor accSensor;
	public static Sensor barometerSensor;
	public static Sensor gravitySensor;
	public static Sensor gyroSensor;
	public static Sensor lightSensor;
	public static Sensor magneticSensor;

	public static final float TRESHOLD = 2.2f;
	public static final float DELIMITER_ENTROPY = 0.9f;

	private Globals() {
		refTime = -1;
	}

	public static Globals getInstance() {
		return singleton;
	}

	public static void setRefTime(long newRefTime) {
		refTime = newRefTime;
	}
}
