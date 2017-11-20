package com.liuhq.imageprocessing;

public class Point {
	
	public int x;
	public int y;
	public int dir;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
