package com.liuhq.imageprocessing;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_handwritten);
//		initData();
	}

	private void initData()
	    {
	        ImageView imSrc = (ImageView)findViewById(R.id.im_src);
	        ImageView imDest = (ImageView)findViewById(R.id.im_dest);
	        
	        Bitmap srcBitMap = BitmapFactory
	                .decodeResource(getResources(), R.drawable.ic_ps);
	        
	        int width = srcBitMap.getWidth();  
	        int height = srcBitMap.getHeight(); 
	        int[] pixels = ImageProUtil.getPixels(srcBitMap);
	        pixels = ImageProUtil.discolor(pixels);  
//	        int[] pixelsA = Sketch.changeColor_5(pixels,width,height);  
//	        imSrc.setImageBitmap(srcBitMap);
	        imSrc.setImageBitmap(Bitmap.createBitmap(pixels, width, height,  
	                Config.ARGB_8888));
	        
//	        Bitmap srcBitMapB = BitmapFactory
//	                .decodeResource(getResources(), R.drawable.ic_car);
	        pixels = ImageProUtil.getBound_9(pixels,width,height,6);  
	        imDest.setImageBitmap(Bitmap.createBitmap(pixels, width, height,  
	                Config.ARGB_8888));
	    }

}
