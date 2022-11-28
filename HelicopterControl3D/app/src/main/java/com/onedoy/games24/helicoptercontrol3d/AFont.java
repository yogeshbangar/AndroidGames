package com.onedoy.games24.helicoptercontrol3d;

import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.primitives.Plane;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;

public class AFont {
	Plane mPlan[];
	Texture mTexture[];
	Bitmap mBitmap[];
	Canvas mCanvas[];
	Bitmap mBitDol;
	Paint mPaint;
	Typeface tf;
	HTTRenderer mGR;

	public AFont(HTTRenderer _GR) {
		mGR = _GR;
		mBitDol = HTTRenderer.LoadImgfromAsset("ui/coin.png");
		load();
	}

	void load() {
		try {
			tf = Typeface.createFromAsset(mGR.getContext().getAssets(),"andyb.ttf");
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setTypeface(tf);
			mPaint.setColor(-1);
			mPaint.setTextAlign(Align.CENTER);
			mPaint.setTextSize(32);
			mPlan = new Plane[3];
			mTexture = new Texture[3];
			mBitmap = new Bitmap[3];
			mCanvas = new Canvas[3];

			for (int i = 0; i < mPlan.length; i++) {
				mBitmap[i] = Bitmap.createBitmap(256, 256, Config.ARGB_8888);
				mCanvas[i] = new Canvas(mBitmap[i]);
				Material cMet = new Material();
				mTexture[i] = new Texture("tex" + i, mBitmap[i]);
				cMet.addTexture(mTexture[i]);
				cMet.setColorInfluence(1);
				mPlan[i] = new Plane(2, 2, 1, 1);
				mPlan[i].setMaterial(cMet);
				mPlan[i].setTransparent(true);
				mPlan[i].setDoubleSided(true);
				mPlan[i].setColor(0);
				mPlan[i].setScale(5);
				mPlan[i].setRotation(-35, 180, -55);
				mPlan[i].setPosition(38, -30, 45);
				mGR.getCurrentScene().addChild(mPlan[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void update1Bitmap(final int i, int x, int y) {
		mCanvas[i].drawColor(0, Mode.CLEAR);
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setTextSize(35);
		switch (i) {
		case 0:
			mPaint.setColor(Color.WHITE);
			if(M.GameScreen == M.GAMEPLAY){
				mCanvas[i].drawText("Score: "+mGR.mScore, 128, 80, mPaint);
			}else if(M.GameScreen == M.GAMESHOP || M.GameScreen == M.GAMEMENU){
				mCanvas[i].drawText("Best: "+mGR.mHScore, 128, 40, mPaint);
			}else if(M.GameScreen == M.GAMESETTING){
				mCanvas[i].drawText(""+mGR.mHScore, 128, 40, mPaint);
			}else{
				mPaint.setColor(Color.BLACK);
				mCanvas[i].drawText("Score: "+mGR.mScore, 128, 50, mPaint);
				mPaint.setColor(Color.MAGENTA);
				mPaint.setTextSize(30);
				mCanvas[i].drawText("Best: "+mGR.mHScore, 128, 110, mPaint);
			}
			break;
		case 1:
			if (M.GameScreen == M.GAMEPLAY || M.GameScreen == M.GAMEPUASE) {
				mPaint.setColor(Color.GREEN);
				mCanvas[i].drawText("Tap to Play", 128, 40, mPaint);
			} else {
				mPaint.setColor(Color.GREEN);
				mCanvas[i].drawText(name[mGR.plNo], 128, 40, mPaint);
				mPaint.setTextSize(20);
				mPaint.setColor(Color.WHITE);
				mCanvas[i].drawText("Unlock By", 128, 80, mPaint);
				mCanvas[i].drawText(M.POINT[mGR.plNo] + " pts", 128, 110,mPaint);
			}
			break;
		case 2:
			if (mGR.mHScore >= M.POINT[mGR.plNo])
				mPaint.setColor(Color.RED);
			else
				mPaint.setColor(Color.GRAY);
			mCanvas[i].drawText("< Start >", 128, 40, mPaint);
			break;
		}
		mTexture[i].setBitmap(mBitmap[i]);
		mGR.getTextureManager().replaceTexture(mTexture[i]);
	}
	public final String name[] = { "HELIOPOLIS", "TITAN", "SAPPHIRE", "MICROKERNEL"};
}
