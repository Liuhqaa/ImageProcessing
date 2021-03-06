package com.liuhq.imageprocessing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class HandWrittenView extends View {

	private static final float STROKE_WIDTH = 5f;
	private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;

	private float lastTouchX;
	private float lastTouchY;
	private Paint paint = new Paint();
	private Path path = new Path();
	private final RectF dirtyRect = new RectF();
	private Lines line = new Lines();
	private float measureError;//�����������
	
	public HandWrittenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint = new Paint();
	    paint.setAntiAlias(true);  
	    paint.setColor(Color.BLACK);  
	    paint.setStyle(Paint.Style.STROKE);  
	    paint.setStrokeJoin(Paint.Join.ROUND);  
	    paint.setStrokeCap(Paint.Cap.ROUND);
	    paint.setStrokeWidth(1); 
	    this.measureError = 0;
	    

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getX();
		float eventY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(eventX, eventY);
			line.clear();
			line.resetBorderRect((int)eventX,(int) eventY);
			line.addPoint((int)eventX,(int) eventY);
			return true; 
		case MotionEvent.ACTION_MOVE:
			resetDirtyRect(eventX, eventY); 
			int historySize = event.getHistorySize();
			for (int i = 0; i < historySize; i++) {
				float historicalX = event.getHistoricalX(i);
				float historicalY = event.getHistoricalY(i);
				expandDirtyRect(historicalX, historicalY);
				path.lineTo(historicalX, historicalY);
				line.addPoint((int)historicalX,(int) historicalY);
			}
			path.lineTo(eventX, eventY);
			line.addPoint((int)eventX,(int) eventY);
			
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			resetDirtyRect(eventX, eventY); 
			int size = event.getHistorySize();
			for (int i = 0; i < size; i++) {
				float historicalX = event.getHistoricalX(i);
				float historicalY = event.getHistoricalY(i);
				expandDirtyRect(historicalX, historicalY);
				path.lineTo(historicalX, historicalY);
				line.addPoint((int)historicalX,(int) historicalY);
			}
			path.lineTo(eventX, eventY);
			line.addPoint((int)eventX,(int) eventY);
			recognitionNumber();
			break;
		default:
			return false;
		}
		
	    lastTouchX = eventX;  
	    lastTouchY = eventY; 
	    
		invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH), (int) (dirtyRect.top - HALF_STROKE_WIDTH),
				(int) (dirtyRect.right + HALF_STROKE_WIDTH), (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));
		return true;
	}
	

	private void recognitionNumber() {
		int[] numbers = line.parse();
		String str = "";
		for(int num : numbers)
		{
			str = str  + num + ",";
		}
		Log.i("TAG", "number = " + str);
		Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
	}

	private void expandDirtyRect(float historicalX, float historicalY) {  
	    if (historicalX < dirtyRect.left) {  
	      dirtyRect.left = historicalX;  
	    } else if (historicalX > dirtyRect.right) {  
	      dirtyRect.right = historicalX;  
	    }  
	    if (historicalY < dirtyRect.top) {  
	      dirtyRect.top = historicalY;  
	    } else if (historicalY > dirtyRect.bottom) {  
	      dirtyRect.bottom = historicalY;  
	    }  
	  }  
	  
	  private void resetDirtyRect(float eventX, float eventY) {  
	    dirtyRect.left = Math.min(lastTouchX, eventX);  
	    dirtyRect.right = Math.max(lastTouchX, eventX);  
	    dirtyRect.top = Math.min(lastTouchY, eventY);  
	    dirtyRect.bottom = Math.max(lastTouchY, eventY);  
	  }  

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawPath(path, paint);
	}
}
