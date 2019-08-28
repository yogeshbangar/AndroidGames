package com.oneday.games24.extreme.drag.racing;

import javax.microedition.khronos.opengles.GL10;


import android.graphics.Bitmap;

public class Font {

	float getWidth(String strs)
	{
		float x=0;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k==32){//Space
				x+=arr[0]/M.mMaxX;
				continue;
			}
			if (k > 96 && k < 123)
				k -= 87;
			else if (k > 64 && k < 91)
				k -= 55;
			else if (k > 47 && k < 58)
				k -= 48;
			if (k >= 0 && k < mFont.length)
				x+=arr[k]*2f/M.mMaxX;
		}
		return x;
	}

	void Draw_String(GL10 gl, String strs, float x, float y,int color,int align) {
////		float dx = mFont[0].width();
//		dx *= .5f;//(type == 0 ?  : .6f);
		
		if(align == 1)//Centre
			x-=(getWidth(strs)*.5f-arr[0]/M.mMaxX);
		for (int i = 0; i < strs.length(); i++) {
//			x += dx;
			int k = ((int) strs.charAt(i));
//			if(k == 32){
//				
//				continue;
//			}
			if (k == 45 )
				k = 38;
			else  if (k == 36 )
				k = 37;
			else  if (k == 47 )
				k = 39;
			else  if (k == 46 )
				k = 36;
			else  if (k > 96 && k < 123)
				k -= 87;
			else if (k > 64 && k < 91)
				k -= 55;
			else if (k > 47 && k < 58)
				k -= 48;
			else{
				x+=arr[0]/M.mMaxX;
				continue;
			}
			if (k >= 0 && k < mFont.length){
				float m = -mFont[k].width()/2+arr[k]/M.mMaxX;
				if(color == 0)//Black
					mFont[k].drawColor(gl, x+m, y,.3f,.3f,.3f,1);
				if(color == 1)//Weight
					mFont[k].drawColor(gl, x+m, y,.9f,.9f,.9f,1);
				if(color == 2)//Golden
					mFont[k].drawColor(gl, x+m, y,0f,.0f,.99f,1);
				
				x+=arr[k]*2f/M.mMaxX;
			}
		}
	}
	
	byte arr[] = {13,10,13,13,12,13,12,11,13,12,14,13,13,14,13,14,14,15,7,10,15,10,19,14,14,14,14,14,14,11,14,13,19,15,13,15,4,12,6,17};
	SimplePlane mNumFont[][];
	
	SimplePlane mFont[];

	
	void Draw_Number(GL10 gl, int no, float x, float y,int type) {
		float dx = mNumFont[type][0].width()*(type == 0?.8f:.5f);
		String strs = "" + no;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mNumFont[0].length)
				mNumFont[type][k].drawPos(gl, x + i * dx, y);
		}
	}
	void Draw_Number(GL10 gl, int no, float x, float y,int number,int type) {
		float dx = mNumFont[type][0].width()*.8f;
		String strs = "" + no;
		if(strs.length() == 1)
			strs = "00" + no;
		if(strs.length() == 2)
			strs = "0" + no;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mNumFont[type].length)
				mNumFont[type][k].drawPos(gl, x + i * dx, y);
		}
	}

public Font() {
		
		mNumFont = new SimplePlane[2][10];
		for (int j = 0; j < mNumFont.length; j++) {
			Bitmap b = GameRenderer.LoadImgfromAsset("font/n"+j+".png");
			for (int i = 0; i < mNumFont[j].length; i++)
				mNumFont[j][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, i * (b.getWidth() / 16), 0, b.getWidth() / 16, b.getHeight(), null, true));
		}
		
		mFont	= new SimplePlane[40];
		Bitmap b = GameRenderer.LoadImgfromAsset("font/0.png");
		for(int j=0;j<32;j++)
			mFont[j]	= GameRenderer.addBitmap(Bitmap.createBitmap(b, (j%8)*(b.getWidth()/8),(j/8)*(b.getHeight()/4), b.getWidth()/8, b.getHeight()/4, null, true));
		b.recycle();
		b = GameRenderer.LoadImgfromAsset("font/00.png");
		for(int j=32;j<mFont.length;j++)
			mFont[j]	= GameRenderer.addBitmap(Bitmap.createBitmap(b, (j-32)*(b.getWidth()/8),0, b.getWidth()/8, b.getHeight(), null, true));
	
		
	}

}
