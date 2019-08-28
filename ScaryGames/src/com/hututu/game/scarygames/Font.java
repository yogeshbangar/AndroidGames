package com.hututu.game.scarygames;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Font {
	int FontArr0[] = {0,6,17,31,45,66,85,91,99,107,118,133,139,148,154,161,176,190,204,218,233,247,262,275,289,304,311,318,332,346,360,373,397,415,432,451,468,484,498,518,536,542,557,575,589,610,628,648,663,683,699,714,731,748,764,787,802,816,833,840,847,857,869,882,890,905,919,934,949,962,972,987,1002,1007,1016,1029,1036,1057,1071,1086,1100,1116,1126,1139,1149,1163,1177,1198,1210,1223,1236,1246,1252,1261,1275};
	int FontArr1[] = {0,7,16,28,40,58,73,79,86, 92,101,114,119,127,132,138,150,160,172,184,197,208,221,232,243,256,262,268,281,291,303,314,335,351,365,380,395,408,420,436,451,456,469,484,496,512,528,543,556,573,587,600,614,629,643,663,677,689,704,711,718,727,737,748,755,767,779,791,803,815,824,837, 849, 854, 862, 873, 879, 895, 907, 919, 932, 945, 954, 965, 974, 985, 997,1014,1025,1037,1048,1057,1061,1069,1082};
	private SimplePlane[] mFont0,mFont1;
	public Font()
	{
		mFont0	= new SimplePlane[FontArr0.length];
		{
			Bitmap b = LoadImgfromAsset("fontbig.png");
			for(int i = 0 ;i<mFont0.length;i++)
			{
				if(FontArr0.length -2 >= i)
				{
					mFont0[i]	= addBitmap(Bitmap.createBitmap(b, FontArr0[i],0, FontArr0[i+1]-FontArr0[i], b.getHeight(), null, true));
				}
				else
				{
					mFont0[i]	= addBitmap(Bitmap.createBitmap(b, FontArr0[i],0, b.getWidth()-FontArr0[i], b.getHeight(), null, true));
				}
			}
		}
		mFont1	= new SimplePlane[FontArr1.length];
		{
			Bitmap b = LoadImgfromAsset("fontsmall.png");
			for(int i = 0 ;i<mFont1.length;i++)
			{
				if(FontArr1.length -2 >= i)
					mFont1[i]	= addBitmap(Bitmap.createBitmap(b, FontArr1[i],0, FontArr1[i+1]-FontArr1[i], b.getHeight(), null, true));
				else
					mFont1[i]	= addBitmap(Bitmap.createBitmap(b, FontArr1[i],0, b.getWidth()-FontArr1[i], b.getHeight(), null, true));
			}
		}
	}
	SimplePlane addBitmap (Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	Bitmap LoadImgfromAsset(String ID)
	{
		try{
			return BitmapFactory.decodeStream(GameRenderer.mContext.getAssets().open(ID));}
		catch(Exception e)
		{
			//Log.d(""+ID,"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
			return null;
		}
	}
	void draw(GL10 gl,String strs,float x, float y,int font)
	{
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-33;
			if(k == -1)
			{
				x+=mFont0[0].width();
				continue;
			}
			if(k>=0 && k<mFont0.length)
			{
				if(font == 0)
				{
					mFont0[k].drawPos(gl,x+mFont0[k].width()/2,y);
					x+=mFont0[k].width();
				}
				else{
					mFont1[k].drawPos(gl,x+mFont1[k].width()/2,y);
					x+=mFont1[k].width();
				}
			}
		}
	}
}

























