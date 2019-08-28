package com.hututu.game.papershot;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Font {
	private int FontArr[][] = {	 {0,13,33,61,87,127,161,173,187,202,224,251,262,279,290,307,333,353,380,406,435,462,488,513,539,566,579,591,617,644,670,695,739,773,804,836,868,897,922,957,987,1000,1025,1058,1083,1119,1150,1185,1213,1249,1280,1308,1339,1370,1402,1446,1474,1504,1536,1553,1570,1589,1613,1639,1652,1680,1709,1735,1762,1789,1810,1838,1863,1874,1891,1915,1926,1963,1989,2018,2045,2073,2093,2117,2138,2163,2189,2227,2253,2278,2305,2326,2338,2358,2386}
								,{0,7,21,41,61,90,115,122,132,142,158,177,184,196,203,215,233,247,266,284,305,324,343,362,381,400,407,414,432,451,469,488,522,546,568,591,614,635,654,679,701,708,726,748,767,792,814,839,860,887,909,930,952,974,997,1029,1049,1070,1093,1104,1114,1125,1142,1161,1169,1188,1208,1227,1247,1266,1281,1300,1318,1324,1335,1352,1358,1386,1404,1424,1444,1464,1478,1495,1508,1526,1545,1572,1590,1608,1627,1640,1646,1659,1678}
								,{0,5,16,30,44,65,83,89,97,105,116,130,136,146,152,159,173,183,197,210,225,239,253,267,281,295,301,307,321,335,349,363,386,404,420,437,453,468,481,499,515,521,534,551,565,583,599,618,633,652,668,683,699,715,732,755,770,785,802,810,817,826,839,852,857,871,885,899,913,927,937,951,964,969,978,991,996,1016,1029,1044,1058,1073,1083,1096,1107,1120,1134,1154,1166,1180,1183,1203,1209,1218,1232}};
	private SimplePlane[][] mFont;
	public Font()
	{
		mFont	= new SimplePlane[3][];
		mFont[0]= new SimplePlane[FontArr[0].length];
		{
			Bitmap b = LoadImgfromAsset("UI/menufont.png");
			for(int i = 0 ;i<mFont[0].length;i++)
			{
				if(FontArr[0].length -2 >= i)
				{
//					System.out.println(i+" a  "+(FontArr0[i+1]-FontArr[0][i]));
					mFont[0][i]	= addBitmap(Bitmap.createBitmap(b, FontArr[0][i],0, FontArr[0][i+1]-FontArr[0][i], b.getHeight(), null, true));
					
				}
				else
				{
//					System.out.println(i+"  a "+(b.getWidth()-FontArr[0][i]));
					mFont[0][i]	= addBitmap(Bitmap.createBitmap(b, FontArr[0][i],0, b.getWidth()-FontArr[0][i], b.getHeight(), null, true));
				}
			}
		}
		mFont[1]	= new SimplePlane[FontArr[1].length];
		{
			Bitmap b = LoadImgfromAsset("UI/headingfont.png");
			for(int i = 0 ;i<mFont[1].length;i++)
			{
				if(FontArr[1].length -2 >= i)
				{
//					System.out.println(i+" b  "+(FontArr[1][i+1]-FontArr[1][i]));
					mFont[1][i]	= addBitmap(Bitmap.createBitmap(b, FontArr[1][i],0, FontArr[1][i+1]-FontArr[1][i], b.getHeight(), null, true));
				}
				else
				{
//					System.out.println(i+" b  "+(b.getWidth()-FontArr[1][i]));
					mFont[1][i]	= addBitmap(Bitmap.createBitmap(b, FontArr[1][i],0, b.getWidth()-FontArr[1][i], b.getHeight(), null, true));
				}
					
			}
		}
		mFont[2]	= new SimplePlane[FontArr[2].length];
		{
			Bitmap b = LoadImgfromAsset("UI/scorefont.png");
			for(int i = 0 ;i<mFont[2].length;i++)
			{
				if(FontArr[2].length -2 >= i)
				{
//					System.out.println(i+"  c "+(FontArr[2][i+1]-FontArr[2][i]));
					mFont[2][i]	= addBitmap(Bitmap.createBitmap(b, FontArr[2][i],0, FontArr[2][i+1]-FontArr[2][i], b.getHeight(), null, true));
				}
				else{
//					System.out.println(i+" c  "+(b.getWidth()-FontArr[2][i]));
					mFont[2][i]	= addBitmap(Bitmap.createBitmap(b, FontArr[2][i],0, b.getWidth()-FontArr[2][i], b.getHeight(), null, true));
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
		if(aling == 1)
		{
			x -= length(strs, font)/2;
		}
		if(aling == 2)
		{
			x += length(strs, font)/2;
		}
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-33;
			if(k == -1)
			{
				x+=mFont[font][0].width();
				continue;
			}
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
			int k = ((int)strs.charAt(i))-33;
			if(k == -1)
			{
				x+=mFont[font][0].width();
				continue;
			}
			if(k>=0 && k<mFont[font].length)
			{
				x+=mFont[font][k].width();
			}
		}
		return x;
	}
}

























