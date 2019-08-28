package com.fun2sh.craftcar;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

public class Group {
	HTTRenderer mGR;
	public Group(HTTRenderer GR) {mGR =GR;}
	int _keyCode =0;
	float sx, sy, sz = 0.0f;
	int counter = 0;
	int sel =0;
	public void setting() {float ud = .01f;switch (_keyCode) {case 1:sy -= ud;break;case 2:sy += ud;break;case 3:sx -= ud;break;case 4:sx += ud;break;case 5:sz -= ud;break;case 6:sz += ud;break;}System.out.println("~~~~~~~  " + sx + "f,  " + sy + "f,  "+ sz + "f");}
	public boolean Handle(MotionEvent event){
		int val = 170;
		if(event.getX() <val && event.getY() <val){_keyCode = 1;}
		if(event.getX() >M.ScreenWidth - val && event.getY() <val){_keyCode = 2;}
		if(event.getX() < val && event.getY() >M.ScreenHieght-val){_keyCode = 3;}
		if(event.getX() >M.ScreenWidth - val  && event.getY() >M.ScreenHieght-val){_keyCode = 4;}
		if(event.getX() < val && event.getY() >(M.ScreenHieght-val*2)/2 && event.getY() <(M.ScreenHieght+val)/2){_keyCode = 5;}
		if(event.getX() >M.ScreenWidth - val  && event.getY() >(M.ScreenHieght-val*2)/2 && event.getY() <(M.ScreenHieght+val)/2){_keyCode = 6;}
		if(event.getAction()== MotionEvent.ACTION_UP)_keyCode = 0;
		return true;
	}
	void Draw(){
		gameplay();
		setting();
	}
	public boolean onTouch(View v, MotionEvent event) {
		Handle(event);
		return true;
	}
	
	
	
	void gameplay(){
		counter++;
		for (int i = 0; i < mGR.m3D_Road.length; i++) {
			mGR.m3D_Road[i].setVisible(true);
			mGR.m3D_Road[i].setY(mGR.m3D_Road[i].getY()+M.SPD);
			mGR.m3D_Road[i].setVisible(false);
		}
		
		for (int i = 0; i < mGR.m3D_Road.length; i++) {
			if(mGR.m3D_Road[i].getY()<-(M.ROADY * M.ROADS*1.2))
				mGR.m3D_Road[i].setY(mGR.m3D_Road[(i == 0 ? mGR.m3D_Road.length : i) - 1].getY() + (M.ROADY * M.ROADS));
		}
		for (int i = 0; i < mGR.m3D_Building[1].length; i++){
			
//			mGR.m3D_Building[0][i].setPosition( sx*10, mGR.m3D_Road[i].getY(), sz*10);
//			mGR.m3D_Building[1][i].setPosition(-sx*10, mGR.m3D_Road[i].getY(), sz*10);
			
			mGR.m3D_Building[0][i].setPosition( 5, mGR.m3D_Road[i].getY(), -5);
			mGR.m3D_Building[1][i].setPosition(-5, mGR.m3D_Road[i].getY(), sy*10);
			
			mGR.m3D_Building[1][i].setScale(.1f);
			mGR.m3D_Building[0][i].setScale(.1f);
			
			
		}
		mGR.m3D_Char[0].setPosition(0, -7, 0);
		mGR.m3D_Char[0].setScale(2);
		mGR.m3D_Char[0].setVisible(true);
		
		
		for (int i = 0; i < mGR.mOpp.length; i++){
			if(mGR.mOpp[i].m3ds.getY() > -10){
				mGR.mOpp[i].m3ds.setY(mGR.mOpp[i].m3ds.getY()+M.SPD*.5);
				mGR.mOpp[i].m3ds.setVisible(true);
			}else
				mGR.mOpp[i].m3ds.setVisible(false);
		}
		if (counter % 100 == 0) {
			for (int i = 0; i < mGR.mOpp.length; i++) {
				if (mGR.mOpp[i].m3ds.getY() <= -10) {
					mGR.mOpp[i].set();
					break;
				}
			}
		}
		mGR.getCurrentCamera().setZ(sz*100);
		mGR.getCurrentCamera().setRotX(sx*100);
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
