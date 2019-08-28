package com.hututu.game.scarygames;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.ads.AdView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GameRenderer implements Renderer , AccelerometerListener 
{
	final Group root;
	static Context mContext;
	public static Start mStart;
	Font	mFont;
	long startTime = System.currentTimeMillis();
	long startGame = System.currentTimeMillis();
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handler1 = new Handler() {public void handleMessage(Message msg) {mFont = new Font();}};
//	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
	
	boolean addFree = false;
	
	int arr[]=new int[9];
	int resumeCounter =0;
	int totalBricks=3,mSel=0;
	int mScore;
	int checkNumber = totalBricks*totalBricks;
	
	int mSelImg = 0;
	int mGameNo = 0;
	int mGameTime = 0;
	int mGameSound = 0;
	
	int Hscore,score = 0,newScore =500;
	int speenCounter = 50,counter1;
	
	float acx;
    float startY;
    float PlayerSpeed = .02f,WaterAniY;
    float scal = 0;
	float BG[];
    
	
	Vector mMistake[]; 
	
	SimplePlane[] mTex_Pic,mTex_Bar,mTex_Exit,mTex_Info,mTex_Back,mTex_Select,mTex_Disct,mTex_About,mImg_msg,mTex_GameIcon;
	SimplePlane[] mTex_Help,mTex_Ok,mTex_Play,mTex_Playgame,mTex_Share,mTex_Skal,mImg_BallMove,mImg_Speed,mImg_BallMoveFlip;
	SimplePlane mTex_Logo,mTex_BG,mTex_Splashfont,mTex_Right,mImg_strip,mTex_GameBG,mTex_Illution,mTex_5mistake;
	SimplePlane mTex_5starde,mTex_aboutusfont,mTex_Ring;
	SimplePlane mTex_developedby,mTex_discfont;
	SimplePlane mTex_helpfont,mTex_infoback,mTex_playfont;
	SimplePlane mTex_playshootfont,mTex_settingicon,mTex_shootthebottle;
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip; //AdHouse
	Ninja Player;
	Ninja msg;
	Sweet mSweet[];
	
	
	
	public GameRenderer(Context context) 
	{
		if(AccelerometerManager.isSupported())
			AccelerometerManager.startListening(this);
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}
	void init()
	{
		try
		{
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Skip = add("exit_icon.png");//AdHouse
			
			mTex_GameIcon		=  new SimplePlane[4];
			mTex_GameIcon[0]  	= add("gameicon/ballanceballicon.png");
			mTex_GameIcon[1]  	= add("gameicon/zigsawicon.png");
			mTex_GameIcon[2]  	= add("gameicon/illustionicon.png");
			mTex_GameIcon[3]  	= add("gameicon/mistakeicon.png");
			
			mImg_Speed			=  new SimplePlane[2];
			mImg_Speed[0]  		= add("greenclock.png");
			mImg_Speed[1]  		= add("redclock.png"); 
			mTex_GameBG			= add("ballbg.jpg");
			mTex_Illution		= add("illusions.jpg");
			mTex_5mistake		= add("5mistake.jpg");
			mImg_BallMove		= new SimplePlane[2];
			
			for(int i=0;i<mImg_BallMove.length;i++)
				mImg_BallMove[i]= add("a"+i+".png");
			
			Bitmap bit[]  		= new Bitmap[2];
			for(int i=0;i<bit.length;i++)
				 bit[i] 		= LoadImgfromAsset("a"+i+".png");
			
			mImg_BallMoveFlip 	= new SimplePlane[2];
		    for(int i=0;i<mImg_BallMoveFlip.length;i++)
		    	mImg_BallMoveFlip[i] = addBitmap(FlipHorizontal(bit[i]));
		    
		    mImg_strip	   	 	= add("s0.png");
		    mImg_msg			= new SimplePlane[2];
			mImg_msg[0]  		= add("speed-up.png");
			mImg_msg[1]  		= add("speed_down.png");
			mTex_Ring			= add("ring.png");
			mTex_Pic			= new SimplePlane[9];
			mTex_Logo			= add("hututugames.png");
			mTex_BG				= add("bg.jpg");
			mTex_Splashfont		= add("splashfont.png");
			mTex_Right			= add("right.png");
			
			mTex_Bar			= new SimplePlane[5];
			mTex_Bar[0]			= add("bar0.png");
			mTex_Bar[1]			= add("bar1.png");
			mTex_Bar[2]			= add("bar2.png");
			mTex_Bar[3]			= add("bar3.png");
			mTex_Bar[4]			= add("bar4.png");
			
			
			mTex_Exit			= new SimplePlane[2];
			mTex_Exit[0]		= add("exitde.png");
			mTex_Exit[1]		= add("exitse.png");
			mTex_Info			= new SimplePlane[2];
			mTex_Info[0]		= add("infode.png");
			mTex_Info[1]		= add("infose.png");
			mTex_5starde		= add("5starde.png");
			mTex_About			= new SimplePlane[2];
			mTex_About[0]		= add("aboutde.png");
			mTex_About[1]		= add("aboutse.png");
			mTex_aboutusfont	= add("aboutusfont.png");
			mTex_Back			= new SimplePlane[2];
			mTex_Back[0]		= add("backde.png");
			mTex_Back[1]		= add("backse.png");
			mTex_Select			= new SimplePlane[2];
			mTex_Select[0]		= add("deselect.png");
			mTex_Select[1]		= add("select.png");
			mTex_developedby	= add("developedby.png");
			mTex_Disct			= new SimplePlane[2];
			mTex_Disct[0]		= add("discde.png");
			mTex_Disct[1]		= add("disctse.png");
			mTex_discfont		= add("discfont.png");	
			mTex_Help			= new SimplePlane[2];
			mTex_Help[0]		= add("helpde.png");
			mTex_Help[1]		= add("helpse.png");
			mTex_helpfont		= add("helpfont.png");
			mTex_infoback		= add("infoback.png");
			mTex_Ok				= new SimplePlane[2];
			mTex_Ok[0]			= add("okde.png");
			mTex_Ok[1]			= add("okse.png");
			mTex_Play			= new SimplePlane[2];
			mTex_Play[0]		= add("playde.png");
			mTex_Play[1]		= add("playse.png");
			mTex_playfont		= add("playfont.png");
			mTex_Playgame		= new SimplePlane[2];
			mTex_Playgame[0]	= add("playgamede.png");
			mTex_Playgame[1]	= add("playgamese.png");
			mTex_playshootfont	= add("playshootfont.png");
			mTex_settingicon	= add("settingicon.png");
			mTex_Share			= new SimplePlane[2];
			mTex_Share[0]		= add("sharede.png");
			mTex_Share[1]		= add("sharese.png");
			mTex_shootthebottle	= add("shootthebottle.png");
			
			
			mFont				= new Font();
			mTex_Skal			= new SimplePlane[12];
			for(int i =0;i<mTex_Skal.length;i++)
			{
				mTex_Skal[i] 	= add("img/"+i+".jpg");
			}
			Player 				= new Ninja();
			msg 				= new Ninja();
			mSweet 				= new Sweet[8];
			for(int i=0;i<mSweet.length;i++)
			{
			  mSweet[i] 		= new Sweet();
			}
			BG					= new float[13];
			for(int i=0;i<BG.length;i++)
			{
				BG[i]			= 1 - (mTex_GameBG.Height()*i);
			}
			mMistake	= new Vector[5];
			{
				mMistake[0]		= new Vector(0.07f,0.87f, false);
				mMistake[1]		= new Vector(0.57f,0.67f, false);
				mMistake[2]		= new Vector(-.65f,0.41f, false);
				mMistake[3]		= new Vector(-.05f,0.18f, false);
				mMistake[4]		= new Vector(-.78f,0.64f, false);
			}
			imgPart("5.jpg");
			reset();
			try{handler1.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}
		}catch(Exception e){}
		
	}
	void reset()
	{
		startGame = System.currentTimeMillis();
		checkNumber = totalBricks*totalBricks-1;
		int i,j;
		mScore =0;
		for(i=0;i<arr.length;i++)
		 {
			arr[i] =i;
		 }
		for(int k=0;k<500;k++)
		{
			for(i=0;i<totalBricks*totalBricks;i++)
			 {
				 if(arr[i]==checkNumber)
					 break;
			 }
			j = M.mRand.nextInt(4);
			switch(j)
			{
				case 1:
					 if((i+1)%totalBricks!=0)
					 {
						 arr[i]=arr[i+1];
						 arr[i+1]=checkNumber;
					 }
				 break;
				 case 2:
					 if(i%totalBricks!=0)
					 {
						 arr[i]=arr[i-1];
						 arr[i-1]=checkNumber;
					 }
					 break;
				 case 3:
					 if(i<(totalBricks*totalBricks-totalBricks))
					 {
						 arr[i]=arr[i+totalBricks];
						 arr[i+totalBricks]=checkNumber;
					 }
					 break;
				 case 0:
					 if(i>=totalBricks)
					 {
						 arr[i]=arr[i-totalBricks];
						 arr[i-totalBricks]=checkNumber;
					 }
					 break;
			}
			
		}
		for(i =0;i<mMistake.length;i++)
		{
			mMistake[i].isBoolean =false;
		}
		BallgameReset();
		mStart.load();
	}
	void BallgameReset()
	{
		int i;
		float sy	= -1;
		speenCounter = 50;
		Player.setNinja(0,.5f,0, 0,false);
		msg.setNinja(0,10,0, 0,false);
//		BgNo      =  mRand.nextInt(mImg_bg.length);
		newScore = 5000000;
		sy	= -.9f;
		for(i=0;i<mSweet.length;i++)
		{
			float x;
			if(i==0)
			  x = .5f;
			else
			  x = M.mRand.nextFloat()-M.mRand.nextFloat();
			x = x%.8f;
			int g = M.mRand.nextInt()%15;
			if(g==0)
			{
				g=Math.abs(M.mRand.nextInt()%2);
				g = (g == 0 ? 1: 0);
			}
			else
				g=20;
			mSweet[i].setSweet(x,sy,g,false,0);
			sy -= .5f;
		}
		sy = -3f;
		
		startY	= 0.0f;
		mSel=score=0;
		scal = 0;
	    M.GameScreen = M.GAMEPLAY;
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	public void onDrawFrame(GL10 gl) 
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		root.draw(gl);
		if(mStart.adView!=null)
		{
			resumeCounter++;
			if(resumeCounter>50 && M.GameScreen != M.GAMEADD)
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.GONE){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.VISIBLE){try{handler.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
			}
		}
		/*AdHouse*/
		/*if (mStart.adHouse != null) {
			if (M.GameScreen == M.GAMEADD) {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			} else {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
				}
			}
		}*/
		/*AdHouse*/
		long dt = System.currentTimeMillis() - startTime;
		if (dt < 33)
			try {
				Thread.sleep(33 - dt);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		startTime = System.currentTimeMillis();
	}
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
public void onAccelerationChanged(float x, float y, float z) {
		
		acx = x;
		
		//Log.d("----------------=>  "+x,y+"   -----------    "+z);
	}
	public void onShake(float force) {
	}
	SimplePlane add (String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
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
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));}
		catch(Exception e)
		{
			//Log.d(""+ID,"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
			return null;
		}
	}
	Bitmap resizeImg(Bitmap bitmapOrg,int newWidth,int newHeight)
	{
		 int width = bitmapOrg.getWidth();
		 int height = bitmapOrg.getHeight();
		 float scaleWidth 	= ((float) newWidth) / width;
		 float scaleHeight = ((float) newHeight) / height;
		
		 Matrix matrix = new Matrix();
		 matrix.postScale(scaleWidth, scaleHeight);
		 matrix.postRotate(0);
		 Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,width, height, matrix, true);
//		 Log.d("resizeImg========","newWidth ["+newWidth+"] newHeight ["+newHeight+"]");
		 return resizedBitmap;
	}
	Bitmap FlipHorizontal(Bitmap bitmapOrg)
	{
		Matrix matrix = new Matrix();
		matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}
	void imgPart(String name)
	{
//		Log.d(name, name+"__________________________________________________________________________");
		Bitmap b = LoadImgfromAsset(name);
		
		int smallWidth=b.getWidth()/totalBricks;
	    int smallHight=b.getHeight()/totalBricks;
	    int k=0;
	    for(int i=0;i<totalBricks;i++)
	    {
	    	for(int j=0;j<totalBricks;j++)
	    	{
	    		Log.d((smallWidth*j)+"       "+(smallHight*i), (smallWidth-2)+"       "+(smallHight-2)+ "    "+i+"     "+j);
	    		mTex_Pic[k] = addBitmap(Bitmap.createBitmap(b, smallWidth*j,smallHight*i, smallWidth-2, smallHight-2, null, true));
	    		k++;
	    	}
	    }
	    //mTex_BigSanny = addBitmap(b);
	}
	
}
