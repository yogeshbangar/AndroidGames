package com.htt.games.chase2camera;


import rajawali.math.MathUtil;
import rajawali.math.Quaternion;
import rajawali.math.vector.Vector3.Axis;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

public class Group {
	HTTRenderer mGR;
	public Group(HTTRenderer GR) {mGR =GR;}
	int _keyCode =0;
	float sx =0, sy =0, sz = 0.0f;
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
//		System.out.println( "~11~~~~~~  " + sx
//				+ "f,  " + sy + "f,  " + sz + "f");
	}
	public boolean Handle(MotionEvent event){
		int val = 170;
		if(event.getX() <val && event.getY() <val){_keyCode = 1;}
		if(event.getX() >M.ScreenWidth/2-val && event.getX() <M.ScreenWidth/2+val){sx=0;sy=0;sz=0;}
		if(event.getX() >M.ScreenWidth - val && event.getY() <val){_keyCode = 2;}
		if(event.getX() < val && event.getY() >M.ScreenHieght-val){_keyCode = 3;}
		if(event.getX() >M.ScreenWidth - val  && event.getY() >M.ScreenHieght-val){_keyCode = 4;}
		if(event.getX() < val && event.getY() >(M.ScreenHieght-val*2)/2 && event.getY() <(M.ScreenHieght+val)/2){_keyCode = 5;}
		if(event.getX() >M.ScreenWidth - val  && event.getY() >(M.ScreenHieght-val*2)/2 && event.getY() <(M.ScreenHieght+val)/2){_keyCode = 6;}
		if(event.getAction()== MotionEvent.ACTION_UP)_keyCode = 0;
		return true;
	}
	double mLastY =0;
	Quaternion quat = new Quaternion().fromAngleAxis(Axis.Z, 0.0);
//	void Draw(){
//		counter++;
//		if(counter == 3){
//			mGR.ForSwitchCamera();
//		}
//		mGR.oLogo.setVisible(true);
//		mGR.mPlayer.updateNew();
//		for(int i =0;i<mGR.oRoad.length;i++){
//			mGR.oRoad[i].setVisible(true);
//		}
//		
//		for(int i =0;i<mGR.oRoad.length;i++){
//			if(mGR.oRoad[i].getY() <mGR.mPlayer.Gofor.getY()-50){
//				mGR.oRoad[i].setY(mGR.oRoad[(i==0?mGR.oRoad.length:i)-1].getY()+15);
//			}
//			mGR.oRoad[i].setVisible(true);
//		}
//		
//		mGR.chaseCamera.setRotation(sx*100,sy*100,sz*100);
		
//		{
////			mGR.mPlayer.Gofor.setRotation(sx*100,sy*100,sz*100);
//		}
//		
//		mGR.getCurrentCamera().setRotation(0,0, mGR.mPlayer.Gofor.getRotZ());
//		mGR.getCurrentCamera().setPosition(mGR.mPlayer.Gofor.getX(), mGR.mPlayer.Gofor.getY() , mGR.mPlayer.Gofor.getZ()+100);
//		
//		
//		mGR.getCurrentCamera().setRotation(0,0, mGR.mPlayer.Gofor.getRotZ());
//		quat.fromAngleAxis(Axis.Y, mGR.mPlayer.Gofor.getRotZ());
//		
//		mGR.getCurrentCamera().setPosition(mGR.mPlayer.Gofor.getPosition().transform(quat));
//		
		/*float radius = 200;
		float degreeStep = 15;
		float distanceFromGround = 20;

//		for (int i = 0; i < 360; i += degreeStep) 
//		{
			double radians = MathUtil.degreesToRadians(mGR.mPlayer.Gofor.getRotZ());
			double x = Math.cos(radians) * Math.sin(radians)* radius;
			double z = Math.sin(radians) * radius;
			
			  
			
			double y = mGR.mPlayer.Gofor.getPosition().transform(quat)getAltitude(x, z) + distanceFromGround;

			if (i > 0)
				y = (y + mLastY) * .5f;
			mLastY = y;
		
//		}
		mGR.getCurrentCamera().setPosition(mGR.mPlayer.Gofor.getPosition().multiply(Math.cos(mGR.mPlayer.Gofor.getRotZ())));
		
		
		mGR.getCurrentCamera().setPosition(x, y,50);
		*/
		
//		if(mGR.mPlayer.Gofor.getRotZ() == 90)
//			mGR.getCurrentCamera().setPosition(-mGR.mPlayer.com, mGR.mPlayer.Gofor.getX()+mGR.mPlayer.Gofor.getY(),mGR.mPlayer.Gofor.getZ()+50);
//		else if(mGR.mPlayer.Gofor.getRotZ() == 180)
//			mGR.getCurrentCamera().setPosition(mGR.mPlayer.Gofor.getX()-mGR.mPlayer.com*2, -mGR.mPlayer.Gofor.getY(),mGR.mPlayer.Gofor.getZ()+50);
//		else if(mGR.mPlayer.Gofor.getRotZ() == 270)
//			mGR.getCurrentCamera().setPosition(-mGR.mPlayer.com, -mGR.mPlayer.Gofor.getX()+mGR.mPlayer.Gofor.getY(),mGR.mPlayer.Gofor.getZ()+50);
//		else 
//			mGR.getCurrentCamera().setPosition(mGR.mPlayer.Gofor.getX()+sx*100, sy*100+mGR.mPlayer.Gofor.getY(),mGR.mPlayer.Gofor.getZ()+50);
//		setting();
//	}
	public boolean onTouch(View v, MotionEvent event) {
		Handle(event);
		return true;
	}
	
	boolean Handle_Gameplay(MotionEvent event){
		if(MotionEvent.ACTION_UP == event.getAction()){
//			mGR.mCameraOffset.setAll(0,0,10);
//			mGR.setCameraOffset(mGR.mCameraOffset);
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
