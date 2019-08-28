package com.hututu.game.speeddragracing;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Font {
	private int FontArr[][] = {{0,16,24,40,54,69,84,99,114,129,144,159,174,188,203,218,233,248,264,272,288,303,317,332,347,362,376,391,406,421,436,451,466,481,496,511,526,541,549,567}
								,{0,20,30,48,66,84,102,121,139,157,175,194,212,230,248,267,285,303,323,332,351,369,387,405,423,442,460,478,496,515,533,551,569,588,606,624,643,658,666,684}
								,{0,20,30,48,66,84,102,121,139,157,175,194,212,230,248,267,285,303,323,332,351,369,387,405,423,442,460,478,496,515,533,551,569,588,606,624,643,658,666,684}};
	private SimplePlane[][] mFont;
	public Font()
	{
		mFont	= new SimplePlane[3][];
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
					mFont[j][i]	= addBitmap(Bitmap.createBitmap(b, FontArr[j][i]+2,0, FontArr[j][i+1]-FontArr[j][i]-4, b.getHeight(), null, true));
					
				}
				else
				{
//					System.out.println(i+"  a "+(b.getWidth()-FontArr[j][i]));
					mFont[j][i]	= addBitmap(Bitmap.createBitmap(b, FontArr[j][i]+2,0, b.getWidth()-FontArr[j][i]-4, b.getHeight(), null, true));
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
		font	= Math.abs(font)%mFont.length;
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
			if(k>47 && k < 58)
				k-=48;
			if(k>64 && k < 95)
				k-=55;
			if(k>96 && k < 123)
				k-=87;
			if(k>=0 && k<mFont[font].length)
			{
				{
					mFont[font][k].drawPos(gl,x+mFont[font][k].width()/2,y);
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
			if(k>47 && k < 58)
				k-=48;
			if(k>64 && k < 95)
				k-=55;
			if(k>96 && k < 123)
				k-=87;
			if(k>=0 && k<mFont[font].length)
			{
				x+=mFont[font][k].width();
			}
		}
		return x;
	}
}

























