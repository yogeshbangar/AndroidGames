package com.hututu.game.cricket.premier.league;

import javax.microedition.khronos.opengles.GL10;
import android.graphics.Bitmap;

public class Font {
	final byte wd[][] = {{4,8,12,11,17,14,4,6,6,9,10,4,6,4,6,10,8,10,10,12,11,11,11,11,11,4,4,10,10,10,10,20,14,14,14,13,12,11,14,13,4,11,13,11,14,13,15,12,16,13,12,12,13,13,19,12,12,13,16,6,7,10,10,5,11,11,11,11,11,9,11,10,4,7,10,4,16,10,12,11,11,8,10,8,10,10,16,10,11,10,7,3,8,12},
			{5,10,14,14,21,17,7,7,8,11,14,6,8,5,7,14,11,14,14,15,14,14,13,14,14,5,6,13,14,13,13,24,19,16,18,16,14,13,19,16,5,13,16,14,18,16,18,15,19,16,15,16,16,16,22,15,16,16,8,9,8,12,14,6,14,14,14,14,14,11,13,13,5,9,13,5,20,13,14,14,14,10,12,9,13,14,20,13,13,13,10,4,10,14},
			};
	SimplePlane mTex_Font[][];
	public Font()
	{
		if(mTex_Font==null){
			mTex_Font		= new SimplePlane[2][];
			for(int k=0;k<mTex_Font.length;k++){
				Bitmap b		= GameRenderer.LoadImgfromAsset("font/"+k+".png");
				mTex_Font[k]		= new SimplePlane[94];
				for (int i = 0; i < mTex_Font[k].length; i++) {
					mTex_Font[k][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, 
							(i%16) * b.getWidth() / 16, (i/16) * b.getHeight() / 8, 
							b.getWidth() / 16, b.getHeight() / 8, null, true));
					
					wd[k][i]+=1+k;
				}
			}
		}
	}
	void Draw(GL10 gl,String strs,float x,float y,int type,int Aling,int Color){
		if(type > 1){
			DrawS(gl, strs, x, y, 1.5f,0);
			return;
		}
		if(Aling == 1)//Center
		{
			x-=getWidth(strs,type)/2-mTex_Font[type][0].width()*.4f;
		}
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k==32){//Space
				x+=wd[type][4]/M.mMaxX;
				continue;
			}
			k-=33;
			float m = -mTex_Font[type][k].width()/2+wd[type][k]/M.mMaxX;
			switch (Color) {
			case 1:
				mTex_Font[type][k].drawSRGBT(gl,x+m, y,1,0,0,0,1);//Black
				break;
			case 2:
				mTex_Font[type][k].drawSRGBT(gl,x+m, y,1,1,.9f,0,1);//Yellow
				break;
			case 3:
				mTex_Font[type][k].drawSRGBT(gl,x+m, y,1,1,0,0,1);//Red
				break;
			default:
				mTex_Font[type][k].drawPos(gl,x+m, y);//White
				break;
			}
				
			x+=wd[type][k]*2f/M.mMaxX;
		}
	}
	float getWidth(String strs,int type)
	{
		float x=0;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k==32){//Space
				x+=wd[type][0]/M.mMaxX;
				continue;
			}
			k-=33;
			x+=wd[type][k]*2f/M.mMaxX;
		}
		return x;
	}
	void DrawS(GL10 gl,String strs,float x,float y,float s,int Aling){
		if(Aling == 1)//Center
		{
			x-=(getWidth(strs,1)/2-mTex_Font[1][0].width()*.4f)*s;
		}
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k==32){//Space
				x+=wd[1][4]/M.mMaxX;
				continue;
			}
			k-=33;
			float m = (-mTex_Font[1][k].width()/2+wd[1][k]/M.mMaxX)*s;
			mTex_Font[1][k].drawScal(gl,x+m, y,s,s);
			x+=wd[1][k]*2f/M.mMaxX*s;
		}
	}
}
