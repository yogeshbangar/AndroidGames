package com.hututu.dont.tap.white.tile.piano.donttap;

import javax.microedition.khronos.opengles.GL10;
import android.graphics.Bitmap;

public class Font {
	byte arr[] = {14,8,13,13,14,12,14,12,14,14,19,14,14,16,14,13,16,16,6,12,16,13,19,17,18,13,18,15,13,15,16,19,24,17,17,15};
	GameRenderer mGR;
	SimplePlane mFont[];
	public Font(GameRenderer mGR){
		
		for(int i=0;i<arr.length;i++)
			arr[i] = (byte)(arr[i]+1); 
		
		mFont	= new SimplePlane[arr.length];
		Bitmap bit = mGR.LoadImgfromAsset("strip/0.png");
		for(int i =0;i<32;i++){
			mFont[i] = mGR.addBitmap(Bitmap.createBitmap(bit, (i%8)*bit.getWidth()/8, (i/8)*bit.getHeight()/4,
					bit.getWidth()/8, bit.getHeight()/4, null, true));
//				System.out.print(i+",");
		}
		bit = mGR.LoadImgfromAsset("strip/1.png");
		{
			for (int i = 32; i < 36; i++) {
				mFont[i] = mGR.addBitmap(Bitmap.createBitmap(bit,(i % 4) * bit.getWidth() / 4, 0,
						bit.getWidth() / 4, bit.getHeight(), null, true));
//					System.out.print(i+",");
			}
		}
	}

	void Draw_String(GL10 gl, String strs, float x, float y, int color, int align) {
		if (align == 1){// Centre
			x -= (getWidth(strs, color)  - 3*arr[0] / M.mMaxX);
		}
		for (int i = 0; i < strs.length(); i++) {
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
				x += (arr[0] / M.mMaxX)*.5f;
				continue;
			}
			if (k >= 0 && k < mFont.length) {
				float m = -mFont[k].width() / 2 + arr[k] / M.mMaxX;
				switch (color) {
				case 1:
					mFont[k].drawColor(gl, x + m, y,0,0,0,1,1);
					break;
				case 2:
					mFont[k].drawColor(gl, x + m, y,1,0,0,1,1);
					break;
				case 3:
					mFont[k].drawColor(gl, x + m, y,0,1,0,1,1);
					break;
				case 4:
					mFont[k].drawColor(gl, x + m, y,0,0,1,1,1);
					break;
				default:
					mFont[k].drawPos(gl, x + m, y);
					break;
				}
				x += arr[k] * 2f / M.mMaxX;
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
				x += (arr[0] / M.mMaxX)*.5f;
				continue;
			}
			if(k>=0 && k <arr.length)
				x += (arr[k] / M.mMaxX);
		}
		return x;
	}
}
