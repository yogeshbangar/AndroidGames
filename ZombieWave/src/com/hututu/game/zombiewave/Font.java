package com.hututu.game.zombiewave;

import javax.microedition.khronos.opengles.GL10;
import android.graphics.Bitmap;

public class Font {
	byte arr[][] = {
			{14,8,13,13,14,12,14,12,14,14,19,14,14,16,14,13,16,16,6,12,16,13,19,17,18,13,18,15,13,15,16,19,24,17,17,15},
			{27,13,27,27,27,27,27,27,27,27,31,27,27,27,27,27,27,27,10,28,28,27,45,27,27,27,27,28,27,27,27,32,45,29,29,26},
			{11,7,10,10,11,10,11,10,11,11,11,12,8,12,10,7,11,11,4,7,11,4,16,12,12,12,11,7,8,8,10,11,16,12,12,8,5,9}
	};
	GameRenderer mGR;
	SimplePlane mFont[][];
//	SimplePlane mFont1[];
//	SimplePlane mFont2[];
	public Font(GameRenderer mGR){
		for(int j=0;j<arr.length;j++){
			for(int i=0;i<arr[j].length;i++)
				arr[j][i] = (byte)(arr[j][i]+1); 
		}
		mFont	= new SimplePlane[3][];
		for(int j =0;j<mFont.length;j++)
		{
			if(j==2)
				mFont[j] = new SimplePlane[38];
			else
				mFont[j] = new SimplePlane[36];
			Bitmap bit = mGR.LoadImgfromAsset("font/"+j+"0.png");
			
			for(int i =0;i<32;i++){
				mFont[j][i] = mGR.addBitmap(Bitmap.createBitmap(bit, (i%8)*bit.getWidth()/8, (i/8)*bit.getHeight()/4,
						bit.getWidth()/8, bit.getHeight()/4, null, true));
//				System.out.print(i+",");
			}
			bit = mGR.LoadImgfromAsset("font/"+j+"1.png");
			if (j == 2) {
				for(int i =32;i<38;i++){
					mFont[j][i] = mGR.addBitmap(Bitmap.createBitmap(bit, (i%8)*bit.getWidth()/8, 0,
							bit.getWidth()/8, bit.getHeight(), null, true));
//					System.out.print(i+",");
				}
			}else{
				for (int i = 32; i < 36; i++) {
					mFont[j][i] = mGR.addBitmap(Bitmap.createBitmap(bit,
							(i % 4) * bit.getWidth() / 4, 0,
							bit.getWidth() / 4, bit.getHeight(), null, true));
//					System.out.print(i+",");
				}
			}
		}
	}

	void Draw_String(GL10 gl, String strs, float x, float y, int color, int align) {
//		float dx = mFont[color][0].width();
//		dx *= .4f;
//		if(color == 2)
//			dx *= .7f;
		if (align == 1){// Centre
			x -= (getWidth(strs, color)  - 3*arr[color][0] / M.mMaxX);
//			x -=strs.length()*dx * .5f;
		}
		for (int i = 0; i < strs.length(); i++) {
//			x += dx;
			int k = ((int) strs.charAt(i));
			if (k == 45)
				k = 38;
			else if (k == 36)
				k = 37;
			else if (k == 47)
				k = 39;
			else if (k == 46)
				k = 36;
			else if (k == 58)
				k = 36;
			else if (k > 96 && k < 123)
				k -= 87;
			else if (k > 64 && k < 91)
				k -= 55;
			else if (k > 47 && k < 58)
				k -= 48;
			else {
//				x -= dx*.5f;
				x += (arr[color][0] / M.mMaxX)*.5f;
				continue;
			}
			if (k >= 0 && k < mFont[color].length) {
				float m = -mFont[color][k].width() / 2 + arr[color][k] / M.mMaxX;
				if(color==1)
					mFont[color][k].drawPos(gl, x + m, y);
				else
					mFont[color][k].drawRGB(gl, x + m, y,1,0,0,0,1);
				x += arr[color][k] * 2f / M.mMaxX;
			}
		}
	}
	void Draw_StringRGB(GL10 gl, String strs, float x, float y, int color, int align,int rgb) {
//		float dx = mFont[color][0].width();
//		dx *= .4f;
//		if(color == 2)
//			dx *= .7f;
		if (align == 1){// Centre
			x -= (getWidth(strs, color)  - 3*arr[color][0] / M.mMaxX);
//			x -=strs.length()*dx * .5f;
		}
		for (int i = 0; i < strs.length(); i++) {
//			x += dx;
			int k = ((int) strs.charAt(i));
			if (k == 45)
				k = 38;
			else if (k == 36)
				k = 37;
			else if (k == 47)
				k = 39;
			else if (k == 46)
				k = 36;
			else if (k == 58)
				k = 36;
			else if (k > 96 && k < 123)
				k -= 87;
			else if (k > 64 && k < 91)
				k -= 55;
			else if (k > 47 && k < 58)
				k -= 48;
			else {
//				x -= dx*.5f;
				x += (arr[color][0] / M.mMaxX)*.5f;
				continue;
			}
			if (k >= 0 && k < mFont[color].length) {
				float m = -mFont[color][k].width() / 2 + arr[color][k] / M.mMaxX;
				if(rgb == 1)
					mFont[color][k].drawRGB(gl, x + m, y,1,1,.62f,0,1);
				else
					mFont[color][k].drawRGB(gl, x + m, y,1,0,0,0,1);
				x += arr[color][k] * 2f / M.mMaxX;
			}
		}
	}
	float getWidth(String strs,int color)
	{
		float x=0;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if (k == 45)
				k = 38;
			else if (k == 36)
				k = 37;
			else if (k == 47)
				k = 39;
			else if (k == 46)
				k = 36;
			else if (k == 58)
				k = 36;
			else if (k > 96 && k < 123)
				k -= 87;
			else if (k > 64 && k < 91)
				k -= 55;
			else if (k > 47 && k < 58)
				k -= 48;
			else {
				x += (arr[color][0] / M.mMaxX)*.5f;
				continue;
			}
			if(k>=0 && k <arr[color].length)
				x += (arr[color][k] / M.mMaxX);
		}
		return x;
	}
}
