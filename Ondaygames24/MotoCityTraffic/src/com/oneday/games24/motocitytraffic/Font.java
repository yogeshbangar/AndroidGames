package com.oneday.games24.motocitytraffic;

import javax.microedition.khronos.opengles.GL10;


import android.graphics.Bitmap;

public class Font {
	
//	byte arr[] = {13,10,13,13,12,13,12,11,13,12,14,13,13,14,13,14,14,15,7,10,15,10,19,14,14,14,14,14,14,11,14,13,19,15,13,15,4,12,6,17};
	byte arr[] = {12,18,20,14,28,20,10,11,12,14,18,11,11,11,13,18,10,18,14,18,16,19,18,18,18,11,11,16,18,16,17,24,18,18,24,22,15,17,21,18,
		         10,12,20,12,24,18,24,19,25,19,14,19,18,21,23,20,20,19,12,12,12,18,18,10,19,19,19,19,19,12,19,16,10,15,18,10,24,17,19,19,19,
		         12,14,12,17,17,24,17,17,18,13,10,13,18};
	SimplePlane mTexFont[];

	public Font() {
		
		mTexFont	= new SimplePlane[arr.length];
		Bitmap b = GameRenderer.LoadImgfromAsset("ui/font.png");
		int i=0;
		for(int k=0;k<6;k++)
		{
		  for(int j=0;j<16;j++)
			{
			  if(i<mTexFont.length)
			   {
		          mTexFont[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b,j*b.getWidth()/16,k*b.getHeight()/8,b.getWidth()/16,b.getHeight()/8,null,true));
  		          i++;
			   }
			}
		}
		b.recycle();
	}

	void Draw_String(GL10 gl, String strs, float x, float y,int align)
	{
		
		if(align == 1)//Center
		{
			x -= getWidth(strs)/2f;
		}
		if(align == 2)//Right
		{
			x -= (getWidth(strs));
		}
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k == 32)
			{
				x+=(arr[0]/M.mMaxX)*2f;
				continue;
			}
			k -=33;
			if(k>=0 && k<mTexFont.length)
			{
//				mTexFont[k].drawPos(gl,x,y);
//				x+=(arr[k]/M.mMaxX)*2f;
				float m = -mTexFont[k].width()/2+arr[k]/M.mMaxX;
				mTexFont[k].drawPos(gl,x+m, y);
				x+=arr[k]*2f/M.mMaxX;
			}
		}
	}
	void Draw_StringS(GL10 gl, String strs, float x, float y,int align,float s)
	{
		
		if(align == 1)//Center
		{
			x -= getWidth(strs)/2f;
		}
		if(align == 2)//Right
		{
			x -= (getWidth(strs));
		}
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k == 32)
			{
				x+=(arr[0]/M.mMaxX)*2f;
				continue;
			}
			k -=33;
			if(k>=0 && k<mTexFont.length)
			{
//				mTexFont[k].drawPos(gl,x,y);
//				x+=(arr[k]/M.mMaxX)*2f;
				float m = -mTexFont[k].width()/2+arr[k]/M.mMaxX;
				mTexFont[k].drawScal(gl,x+m,y,s,s);
				x+=arr[k]*2f/M.mMaxX;
			}
		}
	}
	float getWidth(String strs)
	{
		float x=0;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k==32){//Space
				x+=(arr[0]/M.mMaxX)*2f;
				continue;
			}
			k -=33;
			if(k >= 0 && k < mTexFont.length)
			   x+=(arr[k]/M.mMaxX)*2f;
		}
		return x;
	}
}
