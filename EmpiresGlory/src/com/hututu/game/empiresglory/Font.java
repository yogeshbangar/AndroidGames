package com.hututu.game.empiresglory;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;



public class Font {
	final byte wd[] = {9,12,17,11,19,16,8,11,11,13,11,8,11,8,11,15,9,12,12,14,12,13,13,13,13,8,9,12,12,11,13,17,15,15,13,15,13,14,15,15,9,10,15,12,16,13,15,14,15,15,11,14,13,14,17,13,14,13,10,11,10,12,12,8,13,13,12,14,12,13,13,13,9,12,13,9,18,13,13,14,13,11,10,11,13,13,17,13,13,12,11,9,11,13};
	SimplePlane mTex_Font[][];
	
	public Font(GameRenderer mGR){

//		for(int j=0;j<wd.length;j++){
//			wd[j]-=1;
//		}
		if(mTex_Font==null){
			mTex_Font		= new SimplePlane[2][];
			for(int k=0;k<2;k++){
				Bitmap b		= GameRenderer.LoadImgfromAsset("Font/"+k+".png");
				mTex_Font[k]		= new SimplePlane[wd.length];
				int m =0;
				for(int i=0;i<6 && m < mTex_Font[k].length;i++)
				{
					for(int j=0;j<16 && m < mTex_Font[k].length;j++){
						mTex_Font[k][m] = mGR.addBitmap(Bitmap.createBitmap(b, j*b.getWidth()/16, i*b.getHeight()/8,b.getWidth()/16, b.getHeight()/8, null, true));
						m++;
					}
				}
			}
		}
	
	}
	float getWidth(String strs,int type)
	{
		float x=0;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k==32){//Space
				x+=wd[0]/M.mMaxX;
				continue;
			}
			k-=33;
			x+=wd[k]*2f/M.mMaxX;
		}
		return x;
	}
	void Draw(GL10 gl,String strs,float x,float y,int type,int Aling){
		if(Aling == 1)//Center
		{
			x-=getWidth(strs,type)/2;
		}
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k==32){//Space
				x+=wd[0]/M.mMaxX;
				continue;
			}
			k-=33;
			float m = -mTex_Font[type][k].width()/2+wd[k]/M.mMaxX;
			mTex_Font[type][k].drawPos(gl,x+m, y);
			x+=wd[k]*2f/M.mMaxX;
		}
	}
	
}
