package com.example.gesturetrainer;

import java.util.HashMap;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.GeometricEngine.GeometricEngine;
import com.example.GeometricEngine.Vector3;
import com.example.GestureLayer.GestureListener;
import com.example.GestureLayer.RawGesture;
import com.example.SignalProcessing.SignalProcessingEngine;

public class ServiceGestureListener implements GestureListener {
	GestureService context;

	RawGesture currentGesture;

	public static boolean backgroundState = false;;

	public static HashMap<String, GestureCluster> allGestures = new HashMap<String, GestureCluster>();

	public ServiceGestureListener(GestureService context) {
		super();
		this.context = context;
		currentGesture = null;
	}

	@Override
	public void onGesture(RawGesture g) {
		Log.v("yonutix", "New Gesture");
		if (backgroundState) {
			Log.v("yonutix", "Background state true");
		} else {
			Log.v("yonutix", "Background state false");
		}
		if (ServiceGestureListener.backgroundState) {
			long t1 = System.currentTimeMillis();
			SignalProcessingEngine.mergeSimilarPoints(g.signal);
			if (g.signal.size() < 2) {
				RecordGesture.recordingState = false;
				return;
			}

			g.signal = GeometricEngine.remapDiscreteCourve(g.signal);

			g.normalize();

			for (String index : allGestures.keySet()) {
				GestureCluster gc = allGestures.get(index);
				Vector3 res = GeometricEngine.DinamicTimeWrapping(g.signal,
						gc.median);

				Log.v("yonutix " + index, "" + res.x + " " + res.y + " "
						+ res.z);
				if (res.x < gc.maxDist.x && res.y < gc.maxDist.y
						&& res.z < gc.maxDist.z) {
					String name = index.substring(0, index.indexOf("|"));
					String pack = index.substring(index.indexOf("|") + 1,
							index.length());

					Log.v("yonutix :::::::::::::::::::::::::::", name + " "
							+ pack);

					ComponentName aname = new ComponentName(pack, name);

					Intent i = new Intent(Intent.ACTION_MAIN);

					i.addCategory(Intent.CATEGORY_LAUNCHER);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					i.setComponent(aname);

					context.startActivity(i);

				}
			}
			
			long t2 = System.currentTimeMillis();
			
			Log.v("yonutix", "Timp cautare: " + ((double)(t2 - t1))/1000);
		}

		if (RecordGesture.recordingState) {
			Log.v("yonutix", "recordingState state true");
		} else {
			Log.v("yonutix", "recordingState state false");
		}

		if (RecordGesture.recordingState) {
			
			if(RecordGesture.ad != null)
				RecordGesture.ad.cancel();

			SignalProcessingEngine.mergeSimilarPoints(g.signal);
			if (g.signal.size() < 2) {
				RecordGesture.recordingState = false;
				return;
			}

			g.signal = GeometricEngine.remapDiscreteCourve(g.signal);

			g.normalize();

			currentGesture = g;

			RecordGesture.currentGestures.add(new RawGesture(g));

			for (RawGesture rg : RecordGesture.currentGestures) {
				Vector3 v = GeometricEngine.DinamicTimeWrapping(rg.signal,
						currentGesture.signal);
				Log.v("yonutix distances: ", "" + v.x + " " + v.y + " " + v.z);
			}

			RecordGesture.recordingState = false;
			// DumpLog._log(g);
		}
	}

	@Override
	public void onDelimiter() {

		if (RecordGesture.runningState) {
			RecordGesture.delimiterStatusText.setTextColor(Color.GREEN);
			RecordGesture.recordingBtn.setEnabled(true);
		}
	}

	@Override
	public void onRecording() {
		if (RecordGesture.runningState) {
			RecordGesture.delimiterStatusText.setTextColor(Color.RED);
			RecordGesture.recordingBtn.setEnabled(false);
		}
	}

}
