package com.liuhq.imageprocessing;

import java.util.ArrayList;
import android.util.Log;

public class Lines {

	private ArrayList<Point> lines = new ArrayList<>();
	private Point lastPoint = null;
	private int lastDir;

	public void addPoint(int x, int y) {
		Point point = new Point(x, y);
		if (null != lastPoint && !lastPoint.equals(x, y)) {
			int dir = getDir(lastPoint, point);
			if (lastDir != dir) {
				point.setDir(dir);
				lines.add(point);
				this.lastDir = dir;
			}
		}
		lastPoint = point;

	}

	private int getDir(Point lastPoint, Point point) {
		float dx = point.x - lastPoint.x;
		float dy = point.y - lastPoint.y;
		double degrees = 0;
		if (dy > 0) {
			if (dx > 0) {
				degrees = (Math.atan(dy / dx) * 180 / Math.PI);
			} else if (dx == 0) {
				degrees = 90;
			} else {
				degrees = 180 - (Math.atan(dy / dx) * 180 / Math.PI);
			}
		} else if (dy < 0) {
			if (dx > 0) {
				degrees = 360 - (Math.atan(dy / dx) * 180 / Math.PI);
			} else if (dx == 0) {
				degrees = 270;
			} else {
				degrees = 180 + (Math.atan(dy / dx) * 180 / Math.PI);
			}
		} else {
			if (dx > 0) {
				degrees = 0;
			} else if (dx < 0) {
				degrees = 180;
			} else {
				
			}
		}

		int dir = degressToDirection(degrees);
		Log.i("TAG", "degrees = " + degrees + " , direction = " + dir);
		return dir;
	}

	private int degressToDirection(double degrees) {
		double error = 22.5d;
		int direction = -1;
		if (degrees < error || degrees > (360 - error)) {
			direction = 1;
		} else if (degrees > error && degrees < 90 - error) {
			direction = 2;
		} else if (degrees > 90 - error && degrees < 90 + error) {
			direction = 3;
		} else if (degrees > 90 + error && degrees < 180 - error) {
			direction = 4;
		} else if (degrees > 180 - error && degrees < 180 + error) {
			direction = 5;
		} else if (degrees > 180 + error && degrees < 270 - error) {
			direction = 6;
		} else if (degrees > 270 - error && degrees < 270 + error) {
			direction = 7;
		} else {
			direction = 8;
		}
		return direction;
	}

	public void clear() {
		lastPoint = null;
		lines.clear();
	}

	public int[] parse() {
		if (lines == null || lines.size() <= 0) {
			return new int[] {};
		}

		int[] codes = new int[lines.size()];
		int i = 0;
		for (Point point : lines) {
			codes[i] = point.dir;
			i++;
		}
		return codes;
	}
}
