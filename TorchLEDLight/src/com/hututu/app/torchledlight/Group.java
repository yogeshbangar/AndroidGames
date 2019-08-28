package com.hututu.app.torchledlight;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	
	int Counter =0;
	float sx,sy;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}
	
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	
	@Override
	public void draw(GL10 gl) 
	{
		if(!mGR.mIsDisplayOn)
			DrawTexture(gl, mGR.mTex_bg, 0, 0);
			
		if(mGR.mIsLedOn)
			DrawTexture(gl, mGR.mTex_Button[1], -.4f, .4f);
		else
			DrawTexture(gl, mGR.mTex_Button[0], -.4f, .4f);
		DrawTexture(gl, mGR.mTex_ledlight, -.4f, 0.15f);
		
		if(mGR.mIsDisplayOn)
			DrawTexture(gl, mGR.mTex_Button[1], 0.4f, .4f);
		else
			DrawTexture(gl, mGR.mTex_Button[0], 0.4f, .4f);
		DrawTexture(gl, mGR.mTex_screenlight, 0.4f,0.15f);
		
		
		DrawTexture(gl, mGR.mTex_downloadgames	, 0, -.20f);
//		DrawTexture(gl, mGR.mTex_5star			, 0, -.50f);
		
		DrawTexture(gl, mGR.mTex_hututugames, -.65f,-.9f);
		
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		if(CirCir(-.4f, .4f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
		{
			mGR.mSel = 1;
		}
		if(CirCir(0.4f, .4f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
		{
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0,-.2f, .08f,0.6f, screen2worldX(event.getX()), screen2worldY(event.getY()), .08f))
		{
			mGR.mSel = 3;
		}
//		if(CircRectsOverlap(0,-.5f, .08f,mGR.mTex_5star.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .08f))
//		{
//			mGR.mSel = 4;
//		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				mGR.mIsLedOn = !mGR.mIsLedOn;
				if(mGR.mIsLedOn)
				{
					mGR.flashLightOn();
				}
				else
				{
					mGR.flashLightOff();
				}
				break;
			case 2:
				mGR.mIsDisplayOn = !mGR.mIsDisplayOn;
				if(mGR.mIsDisplayOn)
				{
					mGR.Britness(1);
				}
				else
				{
					mGR.Britness(.25f);
				}
				break;
			case 3:
				Intent intent1 = new Intent(Intent.ACTION_VIEW);
				intent1.setData(Uri.parse(M.MARKET));
				GameRenderer.mContext.startActivity(intent1);
				break;
			case 4:
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
				GameRenderer.mContext.startActivity(intent);
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, 0,0,angle, x, y);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawFlip(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawFilp(gl, x, y);
	}
	void DrawTransScal(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
	{
		Tex.drawTransprentScal(gl, x, y, z, t);
	}
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }
	float screen2worldX(float a)
	{
		float c = ((a / M.ScreenWidth)- 0.5f)*2;
		return c;
	}
	float screen2worldY(float a)
	{
		float c = ((a / M.ScreenHieght)- 0.5f)*(-2);
		return c;
	}
	boolean Rect2RectIntersection(float ax,float ay,float adx,float ady,float bx,float by,float bdx,float bdy)
	{
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
	void drawNumber(GL10 gl,int no,float x, float y)
	{
//		float dx = mGR.mTex_Font[0].width();
//		 String strs = ""+no;
//		for(int i =0;i<strs.length();i++)
//		{
//			int k = ((int)strs.charAt(i))-48;
//			if(k>=0 && k<mGR.mTex_Font.length)
//				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
//		}
	}
	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + getClass().getPackage().getName()));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void Twitter() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void google() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void share2friend() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(
				Intent.EXTRA_SUBJECT,
				"Roking new Game '"
						+ GameRenderer.mContext.getResources().getString(
								R.string.app_name) + "'");
		// i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,
				"Let the battle commence!!! Download it now and let’s ROCK!!!!  "
						+ M.SHARELINK+getClass().getPackage().getName());
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i,
					"Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
