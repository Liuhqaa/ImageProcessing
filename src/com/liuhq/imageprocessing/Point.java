package com.liuhq.imageprocessing;

import java.util.ArrayList;

import android.graphics.Rect;

public class Point {

	private int x;
	private int y;
	private int dir;
	private int degrees;
	private Rect border = new Rect();
	private Point nextPoint;
	private ArrayList<Point> expandPoints ;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDir() {
		return dir;
	}

	public Point getNextPoint() {
		return nextPoint;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getDegrees() {
		return degrees;
	}

	public void setNextPoint(Point point) {
		this.nextPoint = point;
		this.degrees = getDegrees(this, nextPoint);
		this.dir = degressToDirection(degrees);
		resetBorderRect(this, nextPoint);
	}

	public void expandBorderRect(Point point) {
		if(expandPoints == null)
		{
			expandPoints = new ArrayList<>();
		}
		expandPoints.add(point);
		
		int x = point.getX();
		int y = point.getY();
		if (x < border.left) {
			border.left = x;
		} else if (x > border.right) {
			border.right = x;
		}

		if (y < border.top) {
			border.top = y;
		} else if (y > border.bottom) {
			border.bottom = y;
		}
	}

	private void resetBorderRect(Point lastPoint, Point point) {
		border.left = Math.min(lastPoint.x, point.x);
		border.right = Math.max(lastPoint.x, point.x);
		border.top = Math.min(lastPoint.y, point.y);
		border.bottom = Math.max(lastPoint.y, point.y);
	}

	private int getDegrees(Point lastPoint, Point point) {
		float dx = point.x - lastPoint.x;
		float dy = point.y - lastPoint.y;
		double degrees = 0;
		if (dy > 0) {
			if (dx > 0) {
				degrees = (Math.atan(dy / dx) * 180 / Math.PI);
			} else if (dx == 0) {
				degrees = 90;
			} else {
				degrees = 180 + (Math.atan(dy / dx) * 180 / Math.PI);
			}
		} else if (dy < 0) {
			if (dx > 0) {
				degrees = 360 + (Math.atan(dy / dx) * 180 / Math.PI);
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
		return (int) degrees;
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

	public int getArea() {
		int area = border.height() * border.width();
		if (area == 0) {
			if (border.width() == 0 && border.height() != 0) {
					area = border.height() * border.height();
			} else {
				area = border.width() * border.width();
			}
		}
		return area;
	}

	public void expandBorderRects(Point point) {
		expandBorderRect(point);
		expandBorderRect(point.getNextPoint());
		ArrayList<Point> expandPoints = point.getExpandPoints();
		if(expandPoints != null)
		{
			for(Point p : expandPoints)
			{
				expandBorderRect(p);
			}
		}
	}

	public ArrayList<Point> getExpandPoints() {
		return expandPoints;
	}

}
