package com.oneday.games24.fightersofocean;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Font {
	private int FontArr[][] = {{0,6,17,31,45,66,85,91,99,107,118,133,139,148,154,161,176,190,204,218,233,247,262,275,289,304,311,318,332,346,360,373,397,415,432,451,468,484,498,518,536,542,557,575,589,610,628,648,663,683,699,714,731,748,764,787,802,816,833,840,847,857,869,882,890,905,919,934,949,962,972,987,1002,1007,1016,1029,1036,1057,1071,1086,1100,1116,1126,1139,1149,1163,1177,1198,1210,1223,1236,1246,1252,1261,1275}};
	private SimplePlane[][] mFont;
	public Font()
	{
		mFont	= new SimplePlane[1][];
		for(int j =0;j<mFont.length;j++)
		{
//			System.out.println(j+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			mFont[j]= new SimplePlane[FontArr[j].length];
			Bitmap b = LoadImgfromAsset("fontstrip/"+j+".png");
			for(int i = 0 ;i<mFont[j].length;i++)
			{
				if(FontArr[j].length -2 >= i)
				{
//					System.out.println(i+" a  "+(FontArr[j][i+1]-FontArr[j][i]));
					mFont[j][i]	= addBitmap(Bitmap.createBitmap(b, FontArr[j][i],0, FontArr[j][i+1]-FontArr[j][i], b.getHeight(), null, true));
					
				}
				else
				{
//					System.out.println(i+"  a "+(b.getWidth()-FontArr[j][i]));
					mFont[j][i]	= addBitmap(Bitmap.createBitmap(b, FontArr[j][i],0, b.getWidth()-FontArr[j][i], b.getHeight(), null, true));
				}
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
	void draw(GL10 gl,String strs,float x, float y,int font,int aling)
	{
		int color = font;
//		font	= Math.abs(font)%mFont.length;
		font	= 0;
		if(aling == 1)//Center
		{
			x -= length(strs, font)/2;
		}
		if(aling == 2)//Right
		{
			x -= length(strs, font);
		}
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k == 32)
			{
				x+=mFont[font][0].width()*.8f;
				continue;
			}
			k -=33;
			if(k>=0 && k<mFont[font].length)
			{
				{
//					mFont[font][k].drawPos(gl,x+mFont[font][k].width()/2,y);
					switch (color) {
					case 5://Blue
						mFont[font][k].drawColor(gl,x+mFont[font][k].width()/2,y,.000f,.708f,1.00f);
						break;
					case 4://Gray
						mFont[font][k].drawColor(gl,x+mFont[font][k].width()/2,y,.325f,.325f,.355f);
						break;
					case 3://Yellow
						mFont[font][k].drawColor(gl,x+mFont[font][k].width()/2,y,1.00f,1.00f,0.00f);
						break;
					case 2://Orange
						mFont[font][k].drawColor(gl,x+mFont[font][k].width()/2,y,1.00f,.588f,0.00f);
						break;
					case 1://Black
						mFont[font][k].drawColor(gl,x+mFont[font][k].width()/2,y,0.00f,0.00f,0.00f);
						break;
					case 0://White
					default:
						mFont[font][k].drawColor(gl,x+mFont[font][k].width()/2,y,1.00f,1.00f,1.00f);
						break;
					}
					
					x+=mFont[font][k].width();
				}
			}
		}
	}
	float length(String strs,int font)
	{
		float x = 0;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k == 32)
			{
				x+=mFont[font][0].width()*.8f;
				continue;
			}
			k -=33;
			if(k>=0 && k<mFont[font].length)
			{
				x+=mFont[font][k].width();
			}
		}
		return x;
	}
}

























