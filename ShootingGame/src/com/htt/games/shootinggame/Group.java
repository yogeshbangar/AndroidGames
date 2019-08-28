package com.htt.games.shootinggame;

import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

public class Group {
	HTTRenderer mGR;
	public Group(HTTRenderer GR) {mGR =GR;}
	int _keyCode =0;
	float sx, sy, sz = 0.9f;
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
		}
		System.out.println("~~~~~~~  " + sx + "f,  " + sy + "f,  " + sz + "f");
	}
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
	void Draw(GL10 gl){
		Draw_Gameplay(gl);
		setting();
		counter++;
	}
	public boolean onTouch(View v, MotionEvent event) {
		Handle_Gameplay(event);
		Handle(event);
		return true;
	}
	
	void setOpp(){
		for(int i =0;i<mGR.mOpp.length;i++){
			if(mGR.mOpp[i].Obj3D.getY() <= -14){
				mGR.mOpp[i].set();
				break;
			}
		}
	}
	
	void Draw_Gameplay(GL10 gl){
		mGR.oLogo.setVisible(true);
		if(counter%50==0)
			setOpp();
		for (int i = 0; i < mGR.mBullate.length; i++) {
			mGR.mBullate[i].update(); 
		}
		for (int i = 0; i < mGR.mOpp.length; i++) {
			mGR.mOpp[i].update();
			if (mGR.mOpp[i].Obj3D.isVisible()) {
				for (int j = 0; j < mGR.mBullate.length; j++) {
					if (mGR.mBullate[j].Obj3D.isVisible()) {
						if (CirCir(mGR.mOpp[i].Obj3D.getX(),
								mGR.mOpp[i].Obj3D.getY(), 2,
								mGR.mBullate[j].Obj3D.getX(),
								mGR.mBullate[j].Obj3D.getY(), 2)) {
							mGR.mOpp[i].reset();
							mGR.mBullate[j].reset();
						}
					}
				}
			}
		}
		
		
		
		
		if(mGR.getCurrentCamera().getRotX()!=-65)
			mGR.getCurrentCamera().setRotX(-65);
		
		
//		mGR.getCurrentCamera().setRotX(sx*100);
//		mGR.getCurrentCamera().setRotY(sy*100);
//		mGR.getCurrentCamera().setRotY(sy*100);
	}

	boolean Handle_Gameplay(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			for (int i = 0; i < mGR.mBullate.length; i++) {
				if (mGR.mBullate[i].Obj3D.getY() >= 60) {
					mGR.mBullate[i].set(screen2worldX(event.getX()), screen2worldY(event.getY()));
					break;
				}
			}
		}
		
		return true;
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
