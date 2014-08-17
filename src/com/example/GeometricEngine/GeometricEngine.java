package com.example.GeometricEngine;

import java.util.Vector;

import android.util.Log;

public class GeometricEngine {
	private static GeometricEngine singleton = new GeometricEngine();

	private GeometricEngine() {
	}

	public static GeometricEngine getInstance() {
		return singleton;
	}

	public static double findSegment(Vector<Vector3> points, double dist) {
		double currentDist = 0;

		for (int i = 0; i < points.size() - 1; ++i) {
			double deltaD = points.elementAt(i)
					.getDist(points.elementAt(i + 1));
			if (deltaD == 0) {
				continue;
			}
			if (dist >= currentDist && dist <= currentDist + deltaD) {
				return i + (dist - currentDist) / deltaD;
			}
			currentDist += deltaD;
		}
		return -1;
	}

	public static Vector<Vector3> linearInterpolation(Vector<Vector3> points,
			int k) {
		if (k < 2) {
			return null;
		}

		Vector<Vector3> result = new Vector<Vector3>();

		double totalDist = 0;

		for (int i = 0; i < points.size() - 1; ++i) {
			totalDist += points.elementAt(i).getDist(points.elementAt(i + 1));
		}

		long deltaT = points.elementAt(points.size() - 1).timestamp
				- points.elementAt(0).timestamp;
		if (deltaT < 0)
			deltaT = -deltaT;
		deltaT /= (k - 1);
		for (int i = 0; i < k - 1; ++i) {
			double dist = (double) i / ((double) (k - 1)) * totalDist;

			double ind = findSegment(points, dist);
			Vector3 p0 = points.elementAt((int) Math.floor(ind));
			Vector3 p1 = points.elementAt((int) Math.floor(ind) + 1);

			Vector3 newPoint = p0.add((p1.diff(p0)).multiply(ind
					- Math.floor(ind)));
			newPoint.timestamp = points.elementAt(0).timestamp + deltaT*i;
			
			result.add(new Vector3(newPoint));

		}

		result.add(new Vector3(points.elementAt(points.size() - 1)));
		result.elementAt(result.size() - 1).timestamp = points.elementAt(points
				.size() - 1).timestamp;
		return result;
	}

	public static Vector<Vector3> remapDiscreteCourve(Vector<Vector3> points) {
		if (points.size() < 2) {
			return null;
		}

		return linearInterpolation(points, 32);
	}

	public static double maxThree(double a, double b, double c) {
		double max = (a > b) ? a : b;
		max = (max > c) ? max : c;
		return max;
	}

	public static double minThree(double a, double b, double c) {
		double min = (a < b) ? a : b;
		min = (min < c) ? min : c;
		return min;
	}

	public static Vector3 DinamicTimeWrapping(Vector<Vector3> c_1,
			Vector<Vector3> c_2) {
		int n = c_1.size();
		int m = c_2.size();

		double[][] xMat = new double[n][m];
		double[][] yMat = new double[n][m];
		double[][] zMat = new double[n][m];

		for (int i = 0; i < n; ++i) {
			xMat[i][0] = Double.MAX_VALUE;
			yMat[i][0] = Double.MAX_VALUE;
			zMat[i][0] = Double.MAX_VALUE;
		}

		for (int i = 0; i < m; ++i) {
			xMat[0][i] = Double.MAX_VALUE;
			yMat[0][i] = Double.MAX_VALUE;
			zMat[0][i] = Double.MAX_VALUE;
		}
		xMat[0][0] = 0;
		yMat[0][0] = 0;
		zMat[0][0] = 0;

		for (int i = 1; i < n; ++i) {
			for (int j = 1; j < m; ++j) {
				double costX = Math
						.abs(c_1.elementAt(i).x - c_2.elementAt(j).x);
				double costY = Math
						.abs(c_1.elementAt(i).y - c_2.elementAt(j).y);
				double costZ = Math
						.abs(c_1.elementAt(i).z - c_2.elementAt(j).z);

				xMat[i][j] = costX
						+ minThree(xMat[i - 1][j], xMat[i][j - 1],
								xMat[i - 1][j - 1]);

				yMat[i][j] = costY
						+ minThree(yMat[i - 1][j], yMat[i][j - 1],
								yMat[i - 1][j - 1]);

				zMat[i][j] = costZ
						+ minThree(zMat[i - 1][j], zMat[i][j - 1],
								zMat[i - 1][j - 1]);

			}
		}

		Vector3 res = new Vector3();
		res.x = xMat[n - 1][m - 1];
		res.y = yMat[n - 1][m - 1];
		res.z = zMat[n - 1][m - 1];

		return res;
	}

}
