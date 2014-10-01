package com.example.gesturetrainer;

import java.util.Queue;
import java.util.Vector;

import android.util.Log;

import com.example.GeometricEngine.GeometricEngine;
import com.example.GeometricEngine.Vector3;
import com.example.GestureLayer.RawGesture;

public class GestureCluster {
	public Vector<Vector3> median = new Vector<Vector3>();
	public Vector3 dtwDistance = new Vector3();
	public Vector3 maxDist = new Vector3();

	public GestureCluster() {
		super();
	}

	public GestureCluster(Queue<RawGesture> data) {
		super();

		for (int i = 0; i < data.peek().signal.size(); ++i) {
			median.add(new Vector3());
		}

		//for (RawGesture rg : data) {
			for (int i = 0; i < data.peek().signal.size(); ++i) {
				median.elementAt(i).x = median.elementAt(i).x
						+ data.peek().signal.elementAt(i).x;
				median.elementAt(i).y = median.elementAt(i).y
						+ data.peek().signal.elementAt(i).y;
				median.elementAt(i).z = median.elementAt(i).z
						+ data.peek().signal.elementAt(i).z;
			}
		//}
/*
		for (int i = 0; i < median.size(); ++i) {
			median.elementAt(i).x = median.elementAt(i).x / median.size();
			median.elementAt(i).y = median.elementAt(i).y / median.size();
			median.elementAt(i).z = median.elementAt(i).z / median.size();
		}
*/
		maxDist = new Vector3();
		maxDist.x = Float.MIN_VALUE;
		maxDist.y = Float.MIN_VALUE;
		maxDist.z = Float.MIN_VALUE;

		for (RawGesture rg : data) {
				Vector3 val = GeometricEngine.DinamicTimeWrapping(
						rg.signal, median);
				if (val.x > maxDist.x) {
					maxDist.x = val.x;
				}
				if (val.y > maxDist.y) {
					maxDist.y = val.y;
				}
				if (val.z > maxDist.z) {
					maxDist.z = val.z;
				}
		}
		
		Log.v("yonutix created courve with distance: ", maxDist.x + " " + maxDist.y + " " + maxDist.z);

	}

}
