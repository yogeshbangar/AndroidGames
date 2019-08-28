package com.fog.fog;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

public class Group {
	HTTRenderer mGR;
	public Group(HTTRenderer GR) {mGR =GR;}
	int _keyCode =0;
	float sx, sy, sz = 0.0f;
	float su, sv, sw = 0.0f;
	int counter = 0;
	int sel =0;

	public void setting() {
		float ud = .01f;
		switch (_keyCode) {
		case 1:
			sy -= ud;
			break;
		case 2:
			sy += ud;
			break;
		case 3:
			sx -= ud;
			break;
		case 4:
			sx += ud;
			break;
		case 5:
			sz -= ud;
			break;
		case 6:
			sz += ud;
			break;
			
		case 7:
			su -= ud;
			break;
		case 8:
			su += ud;
			break;
		case 9:
			sv -= ud;
			break;
		case 10:
			sv += ud;
			break;
		case 11:
			sw -= ud;
			break;
		case 12:
			sw += ud;
			break;
		}
		System.out.println("[su= " + su + "][sv= " + sv + "][sw= " + sw + "][sx= " + sx + "][sy= " + sy + "][sz= " + sz + "]");
	}

	public boolean Handle(MotionEvent event) {
		int val = 170;
		if (event.getX() < val && event.getY() < val) {
			_keyCode = 1;
		}
		if (event.getX() > M.ScreenWidth - val && event.getY() < val) {
			_keyCode = 2;
		}
		if (event.getX() < val && event.getY() > M.ScreenHieght - val) {
			_keyCode = 3;
		}
		if (event.getX() > M.ScreenWidth - val
				&& event.getY() > M.ScreenHieght - val) {
			_keyCode = 4;
		}
		if (event.getX() < val && event.getY() > (M.ScreenHieght - val * 2) / 2
				&& event.getY() < (M.ScreenHieght + val) / 2) {
			_keyCode = 5;
		}
		if (event.getX() > M.ScreenWidth - val
				&& event.getY() > (M.ScreenHieght - val * 2) / 2
				&& event.getY() < (M.ScreenHieght + val) / 2) {
			_keyCode = 6;
		}
		
		if (CircRectsOverlap(-.2, .9, .1, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .02)) {
			_keyCode = 7;
		}
		if (CircRectsOverlap(0.2, .9, .1, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .02)) {
			_keyCode = 8;
		}
		if (CircRectsOverlap(-.2, .0, .1, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .02)) {
			_keyCode = 9;
		}
		if (CircRectsOverlap(0.2, .0, .1, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .02)) {
			_keyCode = 10;
		}
		if (CircRectsOverlap(-.2,-.9, .1, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .02)) {
			_keyCode = 11;
		}
		if (CircRectsOverlap(0.2,-.9, .1, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .02)) {
			_keyCode = 12;
		}
		if (event.getAction() == MotionEvent.ACTION_UP)
			_keyCode = 0;
		return true;
	}
	void Draw(){
		gameplay();
		setting();
		counter++;
	}
	public boolean onTouch(View v, MotionEvent event) {
		Handle(event);
		return true;
	}
	
	
	
	void gameplay(){
		for (int i = 0; i < mGR.m3D_Road.length; i++) {
			mGR.m3D_Road[i].setVisible(true);
			mGR.m3D_Road[i].setZ(mGR.m3D_Road[i].getZ()+M.SPD);
		}
		
		for (int i = 0; i < mGR.m3D_Road.length; i++) {
			if(mGR.m3D_Road[i].getZ()>-2)
				mGR.m3D_Road[i].setZ(mGR.m3D_Road[(i == 0 ? mGR.m3D_Road.length : i) - 1].getZ() - (M.ROADY * M.ROADS));
		}
		
		for (int i = 0; i < mGR.m3D_Building[1].length; i++){
			
//			mGR.m3D_Building[0][i].setPosition( sx*10, sy*10, mGR.m3D_Road[i].getZ());
//			mGR.m3D_Building[1][i].setPosition(-sx*10, sy*10, mGR.m3D_Road[i].getZ());
			
//			mGR.m3D_Building[0][i].setPosition( 5,-5, mGR.m3D_Road[i].getZ());
//			mGR.m3D_Building[1][i].setPosition(-5,-5, mGR.m3D_Road[i].getZ());
			
			
//			mGR.m3D_Building[0][i].setScale(.0475f);
//			mGR.m3D_Building[1][i].setScale(.0475f);
			
//			mGR.m3D_Building[0][i].setRotation(sx*100, sy*100, sz*100);
			
//			mGR.m3D_Building[1][i].setScale(0.1f);
			mGR.m3D_Building[0][i].setZ(mGR.m3D_Road[i].getZ()+sz);
			mGR.m3D_Building[1][i].setZ(mGR.m3D_Road[i].getZ());
		}
		
		
		
//		mGR.getCurrentCamera().setZ(-6);
//		mGR.getCurrentCamera().setRotX(-6);
		
//		mGR.getCurrentCamera().setRotation(sx*100, sy*100, sz*100);
//		
//		mGR.getCurrentCamera().setPosition(su*10, sv*10,sw*10);
		
	
		for (int i = 0; i < mGR.mOpp.length; i++){
			if(mGR.mOpp[i].m3ds.getZ() < -2){
				mGR.mOpp[i].m3ds.setZ(mGR.mOpp[i].m3ds.getZ()+M.SPD*.5);
				mGR.mOpp[i].m3ds.setVisible(true);
			}else
				mGR.mOpp[i].m3ds.setVisible(false);
		}
		if (counter % 100 == 0) {
			for (int i = 0; i < mGR.mOpp.length; i++) {
				if (mGR.mOpp[i].m3ds.getZ() >= -2) {
					mGR.mOpp[i].set();
					break;
				}
			}
		}
		
		/*for (int i = 0; i < mGR.mOpp.length; i++){
			mGR.mOpp[i].m3ds.setPosition(sx, 0, -20);
			mGR.mOpp[i].m3ds.setVisible(true);
		}*/
		mGR.getCurrentCamera().setRotX(25);
		mGR.getCurrentCamera().setY(10);
	}
	
	
	
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }

	float screen2worldX(float a) {
		float c = ((a - M.ScreenWidth / 2) / M.ScreenHieght) * 2;
		return c;
	}

	float screen2worldY(float a) {
		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
		return c;
	}
	boolean Rect2RectIntersection(double ax,double ay,double adx,double ady,double bx,double by,double bdx,double bdy){
		ax -= adx/2;
		ay += ady/2;
		bx -= bdx/2;
		by += bdy/2;
		if( ax+adx > bx  && ay-ady < by && bx+bdx > ax && by-bdy< ay)
		{
			return true;
		}
		return false;
	}
	boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
    {
		float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
		if (bVectMag<(r1+r2))
           return true;
        return false ;

    }
	double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + getClass().getPackage().getName()));
		Start.mContext.startActivity(mIntent);
	}

	void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		Start.mContext.startActivity(mIntent);
	}
	void Twitter() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		Start.mContext.startActivity(mIntent);
	}
	void google() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		Start.mContext.startActivity(mIntent);
	}
	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		Start.mContext.startActivity(mIntent);
	}
}
