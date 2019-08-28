package com.tech.game.Audituch;
		
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;


//import android.content.Context;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

//import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
	

public class gameplay extends Thread {
	private int mCounter=0;
	public game mgame;
	private String TAG ="Audituch";
	public boolean is_run=false;
	private int brickSize = 0;
	private Random mRand = new Random();
	private int mTotalBricks=7;
	private int mCharX[]=new int[mTotalBricks];
	private int mCharY[]=new int[mTotalBricks];
	
	private int mPressFlag[]=new int[mTotalBricks];
	private int mMaxX=320,mMaxY=480;
	private int mSelectedChar=0;
	private int mIsRedSelect=0;
	private int wrongPush=7,rightPush=0,mTime=30;
	private Paint paint = null;
	private int mUp=0,mFlag=0;
	//Vibrator mVibrator = null;
	private int mNo2Popup=0;
	private int mTuchAni=0;
	private int mIsGameFinish=0,mIsSoundOff=0;
	
	public void doKeyUp(int keyCode, KeyEvent msg) {}  
	public void onDraw(Canvas canvas){}
	public void doKeyDown(int keyCode, KeyEvent msg) {}
	public void close(){try {this.join();} catch (InterruptedException e) {e.printStackTrace();}}
	public void DrawRactengle(Canvas c,Paint paint ,int x, int y, int dx, int dy){c.drawRect(x, y, x+dx, y+dy, paint);}
	public gameplay(game mgame,int maxX,int maxY,int sound)
	{
		this.mgame=mgame;mMaxX=maxX;mMaxY=maxY;doRandom();paint = new Paint();
		//mVibrator = (Vibrator) mgame.mBase.getSystemService(Context.VIBRATOR_SERVICE);
		mIsSoundOff=sound;
		if(sound==0)
			Music.play(((Activity)mgame.mContext).getApplicationContext(), R.drawable.bgmusic);
	}	
	
	public boolean doTouchEvent (MotionEvent event)
	{
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			 break;		
		 case MotionEvent.ACTION_MOVE:
			 break;
		  case MotionEvent.ACTION_UP:
			//for(int j=0;j<mTotalBricks;j++)
			  	if(mIsGameFinish==0)
				{
			  		
					if(mPressFlag[mSelectedChar]!=1)
					{
						if(checkColloision(mCharX[mSelectedChar],mCharY[mSelectedChar],mgame.mImg_smallA[0].getWidth(),mgame.mImg_smallA[0].getHeight(),event.getX(),event.getY()))
						{
							if(mIsRedSelect==1)
								wrongPush--;
							//mVibrator.vibrate(50);
							mPressFlag[mSelectedChar]=1;
							mTuchAni=1;
							rightPush++;
							mFlag=0;
							mUp=0;
							if(mIsSoundOff==0)
								Music.playEffect(((Activity)mgame.mContext).getApplicationContext(), R.drawable.collisionsound);
							break;
						}
					}
				}
			  	else
			  	{
			  		Music.stop(mgame.mContext);
			  		((Activity)mgame.mContext).finish();
			  	}
			break;
		}
		return false;
	}
	int sx,sy;
	
	int val = 10;
	int vsx= val,vsy = val;
	public void run()
	{
		while(is_run)
		{
			Canvas c=null;
			c=mgame.getHolder().lockCanvas();   	
			mgame.onDraw(c);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(10);
			paint.setColor(Color.BLACK);
			c.drawPaint(paint);
			paint.setAntiAlias(false);
			paint.setColor(Color.GREEN);
			c.drawBitmap(mgame.mImg_BG, 0, 0, paint);
			c.drawPath(mgame.path, paint);
			
			if (mgame.GmaskImg2 != null) {
				c.drawBitmap(mgame.GmaskImg2, sx, sy, null);

				sx += vsx;
				sy += vsy;
				if (sx > mgame.GmaskImg2.getWidth()/2) {
					vsx = -val;
				}
				if (sy > mgame.GmaskImg2.getHeight()/2) {
					vsy = -val;
				}
				if (sx < -mgame.GmaskImg2.getWidth()/2) {
					vsx = val;
				}
				if (sy < -mgame.GmaskImg2.getHeight()/2) {
					vsy = val;
				}
			}
//			System.out.println(mgame.GmaskImg.getWidth()+"  "+mgame.GmaskImg.getHeight()+"  "+sx+"  "+sy);
			if(c!=null)
			{
				mgame.getHolder().unlockCanvasAndPost(c);
			}
			try
			{
				sleep(mTime);
			}
			catch (Exception e) {
				System.out.println("exception "+e);
			}
		}
			       	 
	}
	
	
	
	
	
	private boolean checkColloision(int x,int y,int dx,int dy,float f,float g)
	{
		if(x<f && y<g && dx+x>f && dy+y>g)
			return true;
		return false;
	}
			
	void doRandom()
	{
		int t1 = (mMaxY*4)/10;
		int t2 = (mMaxY*7)/10;
		mCharX[0]= mMaxX/4-mgame.mImg_smallA[0].getWidth()/2;
		mCharY[0]= t1+ t2/4-mgame.mImg_BigA[0].getHeight();
		
		mCharX[1]= (mMaxX*3)/4-mgame.mImg_smallA[0].getWidth()/2;
		mCharY[1]= t1+ t2/4-mgame.mImg_BigA[0].getHeight();
		
		
		mCharX[2]= (mMaxX)/5-mgame.mImg_smallA[0].getWidth()/2;
		mCharY[2]= t1+ t2/2-mgame.mImg_BigA[0].getHeight();
		
		mCharX[3]=(mMaxX)/2-mgame.mImg_smallA[0].getWidth()/2;
		mCharY[3]= t1+ t2/2-mgame.mImg_BigA[0].getHeight();
		
		mCharX[4]=(mMaxX*4)/5-mgame.mImg_smallA[0].getWidth()/2;
		mCharY[4]= t1+ t2/2-mgame.mImg_BigA[0].getHeight();
		
		
		mCharX[5]=mMaxX/4-mgame.mImg_BigA[0].getWidth()/2;
		mCharY[5]= t1+ (t2*3)/4-mgame.mImg_BigA[0].getHeight();
		
		mCharX[6]= (mMaxX*3)/4-mgame.mImg_BigA[0].getWidth()/2;
		mCharY[6]= t1+ (t2*3)/4-mgame.mImg_BigA[0].getHeight();
		
	
	}
	public String ReadSettings(){
        FileInputStream fIn = null;
        byte[] inputBuffer = new byte[255];
        String data = null;
        try{
       	 fIn = ((Activity)mgame.mContext).openFileInput("level.dat");
	          if(fIn!=null)
	          {
	        	  int i = fIn.available();
	        	  fIn.read(inputBuffer);
	        	  data = new String(inputBuffer,0,i);
	          }
         }
         catch (Exception e) {      
       	  e.printStackTrace();
       	 
         }
         finally {
       	  try {
       		  if(fIn!=null)
       			  fIn.close();
       	  } catch (IOException e) {
       		  e.printStackTrace();
       	  }
       	  
         }	
         if(data==null)
       	  return "1";
         return data;
    }
	 public void WriteSettings(String data)
	 {
		 
	     FileOutputStream fOut = null;
       OutputStreamWriter osw = null;
       try{
      	 fOut = ((Activity)mgame.mContext).openFileOutput("level.dat",0);      
      	 osw = new OutputStreamWriter(fOut);
      	 osw.write(data);
      	 osw.flush();
      	 
       }
       catch (Exception e) {      
      	 e.printStackTrace();
      	 
       }
       finally {
      	 try {
          	 osw.close();
          	 fOut.close();
           } catch (IOException e) {
          	 e.printStackTrace();
           }
       }
   }
}