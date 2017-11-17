package com.liuhq.imageprocessing;

import android.graphics.Bitmap;

public class ImageProUtil {
    
    public static int[] getPixels(Bitmap bitmap) {  
        int picHeight = bitmap.getHeight();  
        int picWidth = bitmap.getWidth();  
        int[] pixels = new int[picWidth * picHeight];  
        bitmap.getPixels(pixels, 0, picWidth, 0, 0, picWidth, picHeight);  
        return pixels;  
    } 
    
    //去色
    public static int[] discolor(int[] pixels) {
        int color,r,g,b,grey;
        int length = pixels.length;  
        for (int i = 0; i < length; i++) {  
            color = pixels[i];  
            r = (color & 0x00ff0000) >> 16;  
            g = (color & 0x0000ff00) >> 8;  
            b = (color & 0x000000ff);  
            grey = (int) (r * 0.299 + g * 0.587 + b * 0.114);  
            pixels[i] = grey << 16 | grey << 8 | grey | (color & 0xff000000) ;  
        }  
        return pixels; 
    }  
    
    //反相
    public static int[] reverseColor(int[] pixels) { 
        int color,r,g,b;
        int length = pixels.length;  
        for (int i = 0; i < length; ++i) {  
            color = pixels[i];  
            r = 255 - (color & 0x00ff0000) >> 16;  
            g = 255 - (color & 0x0000ff00) >> 8;  
            b = 255 - (color & 0x000000ff);  
            pixels[i] = r << 16 | g << 8 | b | (color & 0xff000000);  
        }  
        return pixels;  
    }  
    
    //颜色变化
    public static int[] changeColor_5(int[] pixels,int widht,int height) {
        int grey,cGrey,topIndex,centerIndex,bottomInde;
       int maxy = height -1;
        int maxx = widht -1;
        int[] outPixels = new int[pixels.length];
        int TCColor,LColor,CColor,RColor,BCColor;
        for (int y = 1; y < maxy; y++) {  
            topIndex = widht * (y-1);
            centerIndex = widht * y;
            bottomInde =  widht * (y+1);
            for (int x = 1; x < maxx; x++) { 
                TCColor = pixels[topIndex++ + 1];  
                LColor = pixels[centerIndex];  
                CColor = pixels[centerIndex + 1];  
                RColor = pixels[centerIndex++ + 2]; 
                BCColor = pixels[bottomInde++ + 1];  
                cGrey = CColor & 0xff;
                grey = Math.abs(TCColor & 0xff - cGrey)
                        + Math.abs(LColor & 0xff - cGrey)
                        + Math.abs(RColor & 0xff - cGrey)
                        + Math.abs(BCColor & 0xff - cGrey);
                if(grey > 255)
                {
                    grey = 255;
                }
                grey = 255 - grey;
                outPixels[centerIndex] = grey << 16 | grey << 8 | grey | (CColor & 0xff000000) ;  
            }
        }  
        return outPixels; 
    }  
    
    //颜色变化
    public static int[] changeColor_9(int[] pixels,int widht,int height) {
        int grey,cGrey,topIndex,centerIndex,bottomInde;
       int maxy = height -1;
        int maxx = widht -1;
        int[] outPixels = new int[pixels.length];
        int TLColor,TCColor,TRColor,LColor,CColor,RColor,BLColor,BCColor,BRColor;
        for (int y = 1; y < maxy; y++) {  
            topIndex = widht * (y-1);
            centerIndex = widht * y;
            bottomInde =  widht * (y+1);
            for (int x = 1; x < maxx; x++) { 
                TLColor = pixels[topIndex];  
                TCColor = pixels[topIndex + 1];  
                TRColor = pixels[topIndex++ + 2];
                
                LColor = pixels[centerIndex];  
                CColor = pixels[centerIndex + 1];  
                RColor = pixels[centerIndex++ + 2]; 
                
                BLColor = pixels[bottomInde];  
                BCColor = pixels[bottomInde + 1];  
                BRColor = pixels[bottomInde++ + 2];
                
                cGrey = CColor & 0xff;
                
                grey = Math.abs(TCColor & 0xff - cGrey)
                        + Math.abs(LColor & 0xff - cGrey)
                        + Math.abs(RColor & 0xff - cGrey)
                        + Math.abs(BCColor & 0xff - cGrey)
                        + Math.abs(TLColor & 0xff - cGrey)
                        + Math.abs(TRColor & 0xff - cGrey)
                        + Math.abs(BLColor & 0xff - cGrey)
                        + Math.abs(BRColor & 0xff - cGrey)
                        ;
                if(grey > 255)
                {
                    grey = 255;
                }
                grey = 255 - grey;
                outPixels[centerIndex] = grey << 16 | grey << 8 | grey | (CColor & 0xff000000) ;  
            }
        }  
        return outPixels; 
    }  
    
    //颜色变化
    public static int[] getBound_5(int[] pixels,int widht,int height,int errorVale) {
        int grey,cGrey,topIndex,centerIndex,bottomInde;
       int maxy = height -1;
        int maxx = widht -1;
        int[] outPixels = new int[pixels.length];
        int TCColor,LColor,CColor,RColor,BCColor;
        for (int y = 1; y < maxy; y++) {  
            topIndex = widht * (y-1);
            centerIndex = widht * y;
            bottomInde =  widht * (y+1);
            for (int x = 1; x < maxx; x++) { 
                TCColor = pixels[topIndex + 1];  
                LColor = pixels[centerIndex];  
                CColor = pixels[centerIndex + 1];  
                RColor = pixels[centerIndex++ + 2]; 
                BCColor = pixels[bottomInde + 1];  
                cGrey = CColor & 0xff;
                
               if(Math.abs(TCColor & 0xff - cGrey) > errorVale
                       || Math.abs(LColor & 0xff - cGrey) > errorVale
                       || Math.abs(RColor & 0xff - cGrey) > errorVale
                       || Math.abs(BCColor & 0xff - cGrey) > errorVale)
               {
                   grey = 255;
               }else{
                   grey = 0;
               }
                grey = 255 - grey;
                outPixels[centerIndex] = grey << 16 | grey << 8 | grey | (CColor & 0xff000000) ;  
            }
        }  
        return outPixels; 
    }  
    
    //颜色变化
    public static int[] getBound__9(int[] pixels,int widht,int height,int errorValue) {
        int grey,cGrey,topIndex,centerIndex,bottomInde;
       int maxy = height -1;
        int maxx = widht -1;
        int[] outPixels = new int[pixels.length];
        int TLColor,TCColor,TRColor,LColor,CColor,RColor,BLColor,BCColor,BRColor;
        for (int y = 1; y < maxy; y++) {  
            topIndex = widht * (y-1);
            centerIndex = widht * y;
            bottomInde =  widht * (y+1);
            for (int x = 1; x < maxx; x++) { 
                TLColor = pixels[topIndex];  
                TCColor = pixels[topIndex + 1];  
                TRColor = pixels[topIndex++ + 2];
                
                LColor = pixels[centerIndex];  
                CColor = pixels[centerIndex + 1];  
                RColor = pixels[centerIndex++ + 2]; 
                
                BLColor = pixels[bottomInde];  
                BCColor = pixels[bottomInde + 1];  
                BRColor = pixels[bottomInde++ + 2];
                
                cGrey = CColor & 0xff;
                
                grey = Math.abs(TCColor & 0xff - cGrey)
                        + Math.abs(LColor & 0xff - cGrey)
                        + Math.abs(RColor & 0xff - cGrey)
                        + Math.abs(BCColor & 0xff - cGrey)
                        + Math.abs(TLColor & 0xff - cGrey)
                        + Math.abs(TRColor & 0xff - cGrey)
                        + Math.abs(BLColor & 0xff - cGrey)
                        + Math.abs(BRColor & 0xff - cGrey)
                        ;
                grey = 255 - grey;
                outPixels[centerIndex] = grey << 16 | grey << 8 | grey | (CColor & 0xff000000) ;  
            }
        }  
        return outPixels; 
    }  
}
