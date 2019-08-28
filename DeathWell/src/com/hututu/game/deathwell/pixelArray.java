package com.hututu.game.deathwell;

import android.graphics.Bitmap;

public class pixelArray {
	private short X[], Y[];
	Bitmap mBitmap;
	public pixelArray(int max) {
		X = new short[max];
		Y = new short[max];
	}

	public void setPixelArray(Bitmap bitmap) {
		mBitmap = bitmap;
		int index = 0;
		int mcolor = -16;
		short y = 0, x = 0;
		int height = bitmap.getHeight();
		y = 0;
		x = 0;
		for (; y < height; y++) {
			if (bitmap.getPixel(x, y) < 0) {
				break;
			}
		}
		while (true) {
			if (index >= X.length)
				break;
			mcolor = bitmap.getPixel(x, y);
			X[index] = x;
			Y[index] = y;
//			System.out.println(x+"   "+y+"    "+index);
			index++;
			if ((mcolor == -16777216))// 0,0,0
			{
				x++;
			} else if ((mcolor == -16776961))// 0,0,255
			{
				y++;
			} else if ((mcolor == -16711936))// 0,255,0
			{
				x--;
			} else if ((mcolor == -16711681))// 0,255,255
			{
				y--;
			} else if ((mcolor == -65536))// 255,0,0
			{
				x++;
				y++;
			} else if ((mcolor == -256))// 255,255,0
			{
				x++;
				y--;
			} else if ((mcolor == -65281))// 255,0,255
			{
				x--;
				y++;
			} else if ((mcolor == -1))// 255,255,255
			{
				x--;
				y--;
			} else {
				break;
			}
		}
	}

	float getX(int index,float mul) {
		float x = world2screenX(((M.mMaxX - mBitmap.getWidth()) / 2)+ X[index])*mul;
		/*if (path == 0) {
			x *= 2;
		} else {
			if ((index > 102 && index < 282) || (index > 462 && index < 642))
				x *= 2.4;
			else
				x *= 2.3;
		}*/
		return x;
	}

	float getY(int index,float mul) {
		float y = world2screenY(((M.mMaxY - mBitmap.getHeight()) / 2)+ Y[index])*mul;
		/*if (path == 0)
			y *= 2;
		else {
			if ((index > 102 && index < 282) || (index > 462 && index < 642))
				y *= 2.4;
			else
				y *= 2.3;
		}*/
		return y;
	}

	float world2screenX(float a) {
		float c = ((a / M.mMaxX) - 0.5f) * 2;
		return c;
	}

	float world2screenY(float a) {
		float c = ((a / M.mMaxY) - 0.5f) * (-2);
		return c;
	}
}
