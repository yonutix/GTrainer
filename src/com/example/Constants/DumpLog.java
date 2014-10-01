package com.example.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.os.Environment;
import android.util.Log;

import com.example.GeometricEngine.Vector3;
import com.example.GestureLayer.RawGesture;

public class DumpLog {
	private static DumpLog singleton = new DumpLog();

	static File raf;
	static FileOutputStream fOut;
	static OutputStreamWriter myOutWriter;
	
	static final String LOG_FILE_NAME = "logfile.log";

	private DumpLog() {

	}

	public static void startSesssion() {
		try {
			raf = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + LOG_FILE_NAME);

			raf.createNewFile();
			fOut = new FileOutputStream(Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ "/" + LOG_FILE_NAME);
			myOutWriter = new OutputStreamWriter(fOut);
		} catch (Exception e) {
			Log.v("yonutix", "Exception occured" + e.getMessage());
		}
	}

	public static void startSesssion(String filename) {
		try {
			raf = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + filename);

			raf.createNewFile();
			fOut = new FileOutputStream(Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ "/"
					+ filename);
			myOutWriter = new OutputStreamWriter(fOut);
		} catch (Exception e) {
			Log.v("yonutix", "Exception occured" + e.getMessage());
		}
	}

	public static void endSession() {
		try {
			myOutWriter.close();
			fOut.close();
		} catch (Exception e) {
			Log.v("yonutix", "Exception occured" + e.getMessage());
		}
	}

	public static DumpLog getInstance() {
		return singleton;
	}

	public static void _log(Vector3 v) {
		try {
			myOutWriter.write(v.timestamp + " " + v.x + " " + v.y + " " + v.z
					+ "\n");
			// Log.v("yonutix", "Something to write");
		} catch (Exception e) {
			Log.v("yonutix", "Exception occured" + e.getMessage());
		}
	}

	public static void logHr(Vector3 v) {
		try {
			myOutWriter.write(v.toString());
		} catch (Exception e) {
			Log.v("yonutix", "Exception occured" + e.getMessage());
		}
	}
	
	public static void wLog(RawGesture g) {
		startSesssion();

		for (int i = 0; i < g.signal.size(); ++i) {
			try {
				myOutWriter.write("" + g.normalizedTime.elementAt(i)
						+ " " + g.signal.elementAt(i).x + " "
						+ g.signal.elementAt(i).y + " "
						+ g.signal.elementAt(i).z + "\n");
			} catch (Exception e) {
				Log.v("yonutix", "Exception occured" + e.getMessage());
			}
		}

		endSession();
	}

}
