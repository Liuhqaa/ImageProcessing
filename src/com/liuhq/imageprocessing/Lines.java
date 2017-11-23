package com.liuhq.imageprocessing;

import java.util.ArrayList;
import android.graphics.Rect;
import android.util.Log;

public class Lines {

	private ArrayList<Point> lines = new ArrayList<>();
	private Point lastPoint = null;
	private Rect border = new Rect();

	public void addPoint(int x, int y) {
		Point point = new Point(x, y);
		if (null != lastPoint && !lastPoint.equals(x, y)) {
			lastPoint.setNextPoint(point);
			lines.add(lastPoint);
			expandBorderRect(point);
		}
		lastPoint = point;
	}

	
	public void clear() {
		lastPoint = null;
		lines.clear();
	}

	public int[] parse() {
		
		if (lines == null || lines.size() <= 0) {
			return new int[] {};
		}
		lines.remove(lines.size()-1);
		ArrayList<Point> outPoints = margerSameDir(lines);
		return getDirArray(outPoints);
	}


	private int[] getDirArray(ArrayList<Point> outPoints) {
		int[] dirArray = new int[outPoints.size()];
		int i = 0;
		for(Point point : outPoints)
		{
			dirArray[i++] = point.getDir();
		}
		return dirArray;
	}


	private int getArea() {
		return border.width() * border.height();
	}


	private ArrayList<Point> margerSameDir(ArrayList<Point> pointList) {
		ArrayList<Point> outPoints = new ArrayList<>();
		int dir ,lastDir = -1;
		Point lsPoint = null; 
		for (Point point : pointList) {
			dir = point.getDir();
			if(dir == lastDir)
			{
				lsPoint.expandBorderRect(point);
				lsPoint.expandBorderRect(point.getNextPoint());
			}else {
				outPoints.add(point);
				lsPoint = point;
				lastDir = dir;
			}
		}
		double area = getArea();
		
		lastDir = -1;
		ArrayList<Point>  result = new ArrayList<>();
		for (Point point : outPoints) {
			int time = (int) (area/point.getArea());
			Log.i("TAG", "degrees = " 
			+ point.getDegrees() + ", dir = " + point.getDir() 
			+ ", area = " + point.getArea() + " , time = " + time);
			if(time > 700)
			{
				continue;
			}
			if(lastDir == point.getDir())
			{
				lastPoint.expandBorderRects(point);
				continue;
			}
			result.add(point);
			lsPoint = point;
			lastDir = point.getDir();
		}
		
		return result;
	}
	
	private void expandBorderRect(Point point) {  
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
	
	  public void resetBorderRect(int x,int y) {  
		  border.left = x;  
		  border.right = x;  
		  border.top = y;  
		  border.bottom = y;  
	  } 
	  

}
