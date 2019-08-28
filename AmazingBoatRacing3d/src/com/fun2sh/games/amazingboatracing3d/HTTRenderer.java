package com.fun2sh.games.amazingboatracing3d;

import javax.microedition.khronos.opengles.GL10;
import com.google.android.gms.ads.AdView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import rajawali.Object3D;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.methods.SpecularMethod;
import rajawali.materials.textures.AlphaMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.parser.Loader3DSMax;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

@SuppressLint("ClickableViewAccessibility")
public class HTTRenderer extends RajawaliRenderer  implements OnTouchListener,SensorEventListener {
	
	long startTime = System.currentTimeMillis();
	public static Context mContext;
	public static Start  mStart;
	Group root;
//	InAppActivity mInApp;
	SensorManager mSensorManager; //For Sensor
	
	Plane       	mPlaneFont[]    = new Plane[2];     
	AlphaMapTexture mAlphaTexFont[] = new AlphaMapTexture[mPlaneFont.length];
	Bitmap  	    mBitmapFont[]   = new Bitmap[mPlaneFont.length];  
	Canvas  		mCanvasFont[]   = new Canvas[mPlaneFont.length];  
	Paint   		mPaintFont[]    = new Paint[mPlaneFont.length];  
	
	
	Plane mTex_Logo,mTex_BackBg,mTex_Splash,mTex_UiBtn[],mTex_ScoreTxt,mTex_BestTxt; 
	Plane mTex_GameOver,mTex_Pause,mTex_SoundOff[],mTex_Button1,mTex_Button2;
	Plane mTex_Pattern[],mTex_TransBG,mTex_Play,mTex_ScoreBox[];
	
	
	Object3D mWater[],mWaterTrans[];
	Player mPlayer;
	Opponent mOpp[];
	Coin mCoin[];
	Object mStone,mCloud[];
	RoadSide mDesert[][];
	Animation mAni,mOppAni[];
	
	int mSel=0;
	int mOppGenCnt=0,mGenTime=150,mGenSpeed=3,mGenDelay=60;
	int mCrashCnt=0,mCoinCollect,mScore,mBestScore;
	int mSideNo,mStartCnt,mCrossBoat;
	byte mSide[];
	boolean isAchieve[] = new boolean[5];
	boolean isResume=false;
	float mPlayTime;
	long mGameTime=0;
	public HTTRenderer(Context context){
		
		super(context);
		mContext = context;
		mStart   = (Start)context;
		mSensorManager = (SensorManager) Start.mContext.getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);
		root     = new Group(this);
		try{
//			mInApp = new InAppActivity();
//			mInApp.onCreate();
		}catch (Exception e) {
			System.out.println("" + e.toString());
			// TODO: handle exception
		}
	} 
	protected void initScene() {
		try {
			     mSurfaceView.setOnTouchListener(this);
			     getCurrentScene().setBackgroundColor(1,1,1,.5f);
			     if(mTex_Logo ==null)
			     {
			       mTex_Logo       = addPlan("logo","logo.png");
			       getCurrentScene().addChild(mTex_Logo);
			       Load2dTexture();
			       isResume =false;
			     }
				 M.GameScreen = M.GAMELOGO;
				 root.Counter=0;
				 setFrameRate(60);
				 M.loadSound(mContext);
//				 System.out.println("innnnnnnnnnnnnnnnnnitttttttttt");
			
		 }catch (Exception e){
			e.printStackTrace();
	   } 
	}
	void SetLight()
	{
//		mLight = new DirectionalLight(0,0,1);
//		mLight.setPower(1.5f);
//		getCurrentScene().addLight(mLight);
	}
	void LoadGameObj() 
	{
	  try{
//			  	LoaderMD2 Parsemd2;
			  	Loader3DSMax mload3ds;
			  	mCloud = new Object[2];
			  	for(int i=0;i<mCloud.length;i++)
			  	{
			  		mCloud[i]        = new Object();
			  		mCloud[i].mImg   = addPlan("sky"+i,"sky.png");
				 	getCurrentScene().addChild(mCloud[i].mImg);
			  	}
			  	mTex_BackBg = addPlan("back_bg","back_bg.png");
			 	getCurrentScene().addChild(mTex_BackBg);
			  	mDesert = new RoadSide[3][6];
			  	mSide   = new byte[mDesert[0].length];
				for(int i=0;i<mDesert.length;i++)
				{
				   for(int j=0;j<mDesert[0].length;j++)
				   {
					  mDesert[i][j] = new RoadSide();
				  	  if(j==0)
					  {
					  		  String mat[] = {"grass.png","ice.png","desert.png"};
							  mload3ds  = new Loader3DSMax(this,R.raw.desert);
							  mload3ds.parse();
							  mDesert[i][j].mObj = mload3ds.getParsedObject();
							  Material  mMaterial = new Material();
							  mMaterial.addTexture(new Texture("desert"+i,LoadImgfromAsset(mat[i])));
							  mMaterial.enableLighting(true);
					  	      mMaterial.setColorInfluence(0);
							  mDesert[i][j].mObj.setMaterial(mMaterial);
							  mDesert[i][j].mObj.setColor(0);
							  mload3ds.clear();
						  }
						  else
							  mDesert[i][j].mObj = mDesert[i][0].mObj.clone(true);
					  	      mDesert[i][j].mObj.setColor(0);
					  	      
						   getCurrentScene().addChild(mDesert[i][j].mObj);
					  }
				  }
				 mWater       = new Object3D[10];//9
				 mWaterTrans  = new Object3D[10];//9
				for(int i=0;i<mWater.length;i++)
				{
				     if(i==0)
						{
						  mload3ds  = new Loader3DSMax(this,R.raw.water);
						  mload3ds.parse();
						  mWater[0] = mload3ds.getParsedObject();
						  Material mMaterial = new Material();
						  mMaterial.addTexture(new Texture("water"+i,LoadImgfromAsset("water.png")));
						  mMaterial.enableLighting(true);
				  	      mMaterial.setColorInfluence(0);
						  mWater[0].setMaterial(mMaterial);
						  mWater[0].setColor(0);
						  mload3ds.clear();

						 }
					   else
						  mWater[i] =mWater[0].clone(true);
					     getCurrentScene().addChild(mWater[i]);
//  					    System.out.println("In Waterrrrrrrrr       "+i);
				 }
				 
			    mStone    = new Object();
				 {
				 	mload3ds  = new Loader3DSMax(this,R.raw.stone_01);
					mload3ds.parse();
					mStone.mObj3d   = mload3ds.getParsedObject();
				 	Material mMaterial = new Material();
				 	mMaterial.addTexture(new Texture("stone_01",LoadImgfromAsset("stone_01.png")));
				 	mMaterial.enableLighting(true);
			  	    mMaterial.setColorInfluence(0);
				 	mStone.mObj3d.setMaterial(mMaterial);
				 	mStone.mObj3d.setColor(0);
				 	mload3ds.clear();
//				 	mStone.mD2.setTransparent(true);
				 	getCurrentScene().addChild(mStone.mObj3d);
			  	    
			     }
				for(int i=0;i<mWaterTrans.length;i++)
				{
				   if(i==0)
					  {
						  mload3ds  = new Loader3DSMax(this,R.raw.watert);
						  mload3ds.parse();
						  mWaterTrans[0] = mload3ds.getParsedObject();
						  Material mMaterial = new Material();
						  mMaterial.addTexture(new Texture("watert"+i,LoadImgfromAsset("watert.png")));
						  mMaterial.enableLighting(true);
					  	  mMaterial.setColorInfluence(0);
						  mWaterTrans[0].setMaterial(mMaterial);
						  mWaterTrans[0].setColor(0);
						  mload3ds.clear();
//						  System.out.println("In Roadddddddd");
					  }
					  else
						  mWaterTrans[i] =mWaterTrans[0].clone(true);
				  	      mWaterTrans[i].setTransparent(true);
				  
					  getCurrentScene().addChild(mWaterTrans[i]);
//						  System.out.println("In WaterTrans   "+i);
				  	
				 }
				 mAni      = new Animation();
			 	 for(int i=0;i<mAni.mobj.length;i++)
			 	 {
				 	   mload3ds  = new Loader3DSMax(this,R.raw.animation);
					   mload3ds.parse();
					 	   mAni.mobj[i] = mload3ds.getParsedObject();
				 	   Material mMaterial = new Material();
				 	   mMaterial.addTexture(new Texture("mAni"+i,LoadImgfromAsset("animation/water"+i+".png")));
				 	   mMaterial.enableLighting(true);
				 	   mMaterial.setColorInfluence(0);
				 	   mAni.mobj[i].setMaterial(mMaterial);
				 	   mAni.mobj[i].setTransparent(true);
				 	   mAni.mobj[i].setColor(0);
				 	  
					   mload3ds.clear();
				       getCurrentScene().addChild(mAni.mobj[i]);
			 	 }
				mPlayer = new Player(this);
				{
				      mload3ds  = new Loader3DSMax(this,R.raw.player01);
					  mload3ds.parse();
					  mPlayer.mO3d = mload3ds.getParsedObject();
					  Material mMaterial = new Material();
					  mMaterial.addTexture(new Texture("player",LoadImgfromAsset("player01.png")));
					  mMaterial.enableLighting(true);
				 	  mMaterial.setColorInfluence(0);
					  mPlayer.mO3d.setMaterial(mMaterial);
					  mPlayer.mO3d.setColor(0);
					  mload3ds.clear();
					  getCurrentScene().addChild(mPlayer.mO3d);
				}
				mOppAni    = new Animation[15];
				for(int i=0;i<mOppAni.length;i++)
				{
					mOppAni[i]      = new Animation();
					for(int j=0;j<mOppAni[0].mobj.length;j++)
					{
						mOppAni[i].mobj[j] = mAni.mobj[j].clone(true);
						mOppAni[i].mobj[j].setTransparent(true);
						mOppAni[i].mobj[j].setColor(0);
					    getCurrentScene().addChild(mOppAni[i].mobj[j]);
					}
						
				}
				mOpp       = new Opponent[15];
			    for(int i=0;i<mOpp.length;i++)
			    {
			    	mOpp[i]   = new Opponent(this);
			    	if(i<4)
			    	{
						 mload3ds  = new Loader3DSMax(this,R.raw.player01);
						 mload3ds.parse();
					 	 mOpp[i].mO3d = mload3ds.getParsedObject();
						 Material mMaterial = new Material();
						 mMaterial.addTexture(new Texture("opp"+i,LoadImgfromAsset("opponent/"+i+".png")));
						 mMaterial.enableLighting(true);
					 	 mMaterial.setColorInfluence(0);
					 	 mOpp[i].mO3d.setMaterial(mMaterial);
					  	 mOpp[i].mO3d.setColor(0);
						 mload3ds.clear();
			    	}
			    	else
				    	mOpp[i].mO3d = mOpp[i%4].mO3d.clone(true);
				    	
			    	getCurrentScene().addChild(mOpp[i].mO3d);

			    }
			     mCoin = new Coin[6];
				 for(int i=0;i<mCoin.length;i++)
				 {
					 mCoin[i]  = new Coin();
					 if(i==0)
					 {
						 mload3ds  = new Loader3DSMax(this,R.raw.dollar);
					 	 mload3ds.parse();
					 	 mCoin[0].mD2 = mload3ds.getParsedObject();
					 	 Material mMaterial = new Material();
					 	 mMaterial.addTexture(new Texture("Coin"+0,LoadImgfromAsset("dollar.png")));
					 	 mMaterial.enableLighting(true);
					 	 mMaterial.setColorInfluence(0);
						 mCoin[0].mD2.setMaterial(mMaterial);
					 	 mCoin[0].mD2.setColor(0);
						 mload3ds.clear();
//						 System.out.println("In Coinnnnnnn");
					 }
					 else
						 mCoin[i].mD2  = mCoin[0].mD2.clone(true);
	
					 getCurrentScene().addChild(mCoin[i].mD2);
				 }
			    Runtime.getRuntime().gc();
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
	void Load2dTexture()
	{
		mTex_Splash     = addPlan("splash","ui/splash.jpg");
		mTex_Pattern    = new Plane[2];
		mTex_Pattern[0]	= addPlan("pattern","ui/pattern.png");
		mTex_Pattern[1]	= addPlan("pattern","ui/pattern.png");
		
		mTex_TransBG    = addPlan("pattern","ui/trans.png");
		mTex_Play       = addPlan("play","ui/play.png");
		mTex_UiBtn      = new Plane[8];
		Bitmap b        = LoadImgfromAsset("ui/icon_strip.png");
		for(int i=0;i<mTex_UiBtn.length;i++)
		{
	      mTex_UiBtn[i] = addBitmap("Ui_Icn"+i,Bitmap.createBitmap(b,(i*b.getWidth())/8,0,(b.getWidth())/8,b.getHeight(),null,true));
		}
		mTex_Button1   = addPlan("button1","ui/button1.png");
		mTex_Button2   = addPlan("button2","ui/button2.png");
		mTex_ScoreTxt  = addPlan("score","ui/score.png");
		mTex_BestTxt   = addPlan("best_score","ui/best_score.png");
		mTex_GameOver  = addPlan("game_over","ui/game_over.png");
		mTex_Pause     = addPlan("game_paused","ui/game_paused.png");
		mTex_SoundOff  = new Plane[2];
		mTex_SoundOff[0] = addPlan("sound_off"  ,"ui/sound_off.png");
		mTex_SoundOff[1] = addPlan("sound_off"  ,"ui/sound_off.png");
		
		mTex_ScoreBox    = new Plane[2];
		mTex_ScoreBox[0] = addPlan("score_button"  ,"ui/score_button.png");
		mTex_ScoreBox[1] = addPlan("score_button"  ,"ui/score_button.png");
        LoadBitmap();
		Runtime.getRuntime().gc();
	}
	void AddUiChild()
	{
		System.out.println("In Adddddd"); 
		getCurrentScene().addChild(mTex_Splash);
		getCurrentScene().addChild(mTex_TransBG);
		for(int i=0;i<2;i++)
		 getCurrentScene().addChild(mTex_Pattern[i]);
		
		getCurrentScene().addChild(mTex_Button1);
		getCurrentScene().addChild(mTex_Button2);
		getCurrentScene().addChild(mTex_Play);
	    getCurrentScene().addChild(mTex_ScoreTxt);
	    getCurrentScene().addChild(mTex_BestTxt);
	    getCurrentScene().addChild(mTex_GameOver);
	    for(int i=0;i<mTex_UiBtn.length;i++)
	      getCurrentScene().addChild(mTex_UiBtn[i]);
	    getCurrentScene().addChild(mTex_Pause);
	    for(int i=0;i<2;i++)
	    {
	      getCurrentScene().addChild(mTex_SoundOff[i]);
	      getCurrentScene().addChild(mTex_ScoreBox[i]);
	    }
	    for(int i=0;i<mPlaneFont.length;i++)
	    getCurrentScene().addChild(mPlaneFont[i]);
	    System.out.println("In Outttt");
	}
	Typeface mTTffont;
	void LoadBitmap()
	{
		mTTffont = Typeface.createFromAsset(getContext().getAssets(),"ui/font.ttf");
		for(int i=0;i<mPlaneFont.length;i++)
		{
	    	Material mMaterial = new Material();
			mMaterial.setDiffuseMethod(new DiffuseMethod.Lambert(1));
			mBitmapFont[i]   = Bitmap.createBitmap(128,128,Config.ARGB_8888);
			mAlphaTexFont[i] = new AlphaMapTexture("FontTexture"+i, mBitmapFont[i]);
			try{
			    mMaterial.addTexture(mAlphaTexFont[i]);
			}catch (TextureException e){e.printStackTrace();}
			mMaterial.enableLighting(true);
			mMaterial.setColorInfluence(1);
			mPlaneFont[i] = new Plane(1,1,1,1);
			mPlaneFont[i].setMaterial(mMaterial);
			mPlaneFont[i].setRotation(0,180,0);
			mPlaneFont[i].setColor(1,1,1,1f);
		}
	}
	void GameReset()
	{
		System.out.println("In Resettttttttttttttt");
		root.SetCamera();
		mPlayTime   = 0;
		mCoinCollect= 0;
		mOppGenCnt  = 0;
		mCrashCnt=0;
		root.Counter=0;
		mStartCnt=0;
		mSideNo=0;
		mGenTime=150;
		mGenSpeed=3;
		mGenDelay=60;
		mStoneCnt=0;
		mCrossBoat=0;
		mScore=0;
		for(int i=0;i<mWater.length;i++)
		{
			mWater[i].setScale(.28f,.369f,0);
	  	    mWater[i].setPosition(0,13.19f+i*18.461f,-4.8f);//-5.29f 13.19f
	  	    
	  	    mWaterTrans[i].setScale(.28f,.369f,0);
	  	    mWaterTrans[i].setPosition(0,13.09f+i*18.431f,-4.569f);//-5.29f 13.19f
		}
		for(int i=0;i<mDesert.length;i++)
		{
		  for(int j=0;j<mDesert[0].length;j++)
		  {	
	  	    mDesert[i][j].mObj.setRotZ(90);
		    mDesert[i][j].Set(0,26.901f+j*45,-5.1f,0);
		    mDesert[i][j].mObj.setScale(.07f,.045f,.078);
		  }
		}
		mPlayer.Set(0,6.6f,-4.56f,.388f);
		mAni.set(0,6.6f,-4.56f,.1f,.05f,.518f,0);
	    for(int i=0;i<mOpp.length;i++)
	    {
	       float x[] = {-3.5f,0,3.5f,-2.5f,0,2.5f,-1.5f,0,1.5f,-.5f,0,.5f};
	        mOpp[i].Set(x[i%x.length],30+i*9,-4.56f,.388f);
	        mOppAni[i].set(mOpp[i].x, mOpp[i].y,-4.56f,.1f,.05f,.518f,0);
	    }
		for(int i=0;i<mSide.length;i++)
	  	   mSide[i]=(byte)0;
	    for(int i=0;i<mCoin.length;i++)
		 {
	    	mCoin[i].Set((M.mRand.nextBoolean()?M.mRand.nextInt()%20:-M.mRand.nextInt()%20)*.1f,60+i*12f,-4.85f,.23f);
		 }
	    mStone.Set(100,100,-5.29f,.065f);
	    for(int i=0;i<mCloud.length;i++)
	    {
	    	mCloud[i].SetCloud(0,1.899f,-1.56f+i*-1.08f,1.129f,1f,.541f);//-.491
	    }
	    getCurrentScene().setBackgroundColor(50f/255f,52f/255f,100f/255f,.5f);
		Runtime.getRuntime().gc();
		M.GameScreen = M.GAMESTART;
		if(root.AdCount%3==0)
		  HTTRenderer.mStart.LoadHandler();
		System.out.println("In Outttt");
	}
	int mStoneCnt=0;
	void CreateBoat()
	{
		 int ii = M.randomRangeInt(4,7);
	     for(int n = 0; n<ii; n++)
	         CreateNew();
	}
	void CreateNew()
	{
		for(int i=0;i<mOpp.length;i++)
		{  
			if(mOpp[i].y<0)
			{
				float x[] = {-3.5f,0,3.5f,-2.5f,0,2.5f,-1.5f,0,1.5f,-.5f,0,.5f};
				mOpp[i].Set(x[i%x.length],FindBig()+M.randomRangeInt(7,12),-4.56f,.388f);
				mCrossBoat++;
				mStoneCnt++;
				if(mStoneCnt>10)
				{
				   if(mStone.x==100 && mStone.y==100)
				   {
					  mStone.Set((M.mRand.nextBoolean()?M.mRand.nextInt()%15:-M.mRand.nextInt()%15)*.1f,mOpp[i].y+5,-5.29f,.065f);
					  mStoneCnt=0;
				   }
				}
				break;
			}
		}
	}
	float FindBig()
	{
		float big=70;
		for(int i=0;i<mOpp.length;i++)
		{
  		   if(mOpp[i].y>big)
		  	  big=mOpp[i].y; 
		}
//		System.out.println("                    "+big);
		return big;
		
		
	}
	void setVisible(boolean isVisible)
	{
		mTex_Logo.setVisible(isVisible);
	}
	
	public void DrawFont(final String no,float x,float y,float z,float r,float s,Align mAl,boolean isVisible)
	{
		int id=0;
			if(mCanvasFont[id] == null) {
	
				mCanvasFont[id] = new Canvas(mBitmapFont[id]);
				mPaintFont[id]  = new Paint(Paint.ANTI_ALIAS_FLAG);
				mPaintFont[id].setTypeface(mTTffont);
				mPaintFont[id].setColor(Color.WHITE);
				mPaintFont[id].setTextSize(24);
				mPaintFont[id].setAlpha(255); 
//				mPaintFont[id].setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
			}
		    mPaintFont[id].setTextAlign(mAl);
			mCanvasFont[id].drawColor(0, Mode.CLEAR);
			float dx=0; 
			switch(mPaintFont[id].getTextAlign())
			{
				case RIGHT:
					dx = mCanvasFont[id].getWidth();
					break;
				case LEFT:
					dx = 0;
					break;
				case CENTER:
					dx = mCanvasFont[id].getWidth()/2;
					break;
			}
			mCanvasFont[id].drawText(no+"",dx,mCanvasFont[id].getHeight()/2,mPaintFont[id]);
			mAlphaTexFont[id].setBitmap(mBitmapFont[id]);
			mTextureManager.replaceTexture(mAlphaTexFont[id]);
	   mPlaneFont[id].setColor(1,1,1,1);
	   root.DrawTexture(mPlaneFont[id],x,y,z,isVisible);
	   mPlaneFont[id].setScale(s);
	   mPlaneFont[id].setRotation(r,180,0);
	}
	public void DrawFont2(final String no,float x,float y,float z,float r,float s,Align mAl,boolean isVisible)
	{
		int id=1;
			if(mCanvasFont[id] == null) {
	
				mCanvasFont[id] = new Canvas(mBitmapFont[id]);
				mPaintFont[id]  = new Paint(Paint.ANTI_ALIAS_FLAG);
				mPaintFont[id].setTypeface(mTTffont);
				mPaintFont[id].setColor(Color.WHITE);
				mPaintFont[id].setTextSize(24);
//				mPaintFont[id].setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
			}
		    mPaintFont[id].setTextAlign(mAl);
			mCanvasFont[id].drawColor(0, Mode.CLEAR);
			float dx=0; 
			switch(mPaintFont[id].getTextAlign())
			{
				case RIGHT:
					dx = mCanvasFont[id].getWidth();
					break;
				case LEFT:
					dx = 0;
					break;
				case CENTER:
					dx = mCanvasFont[id].getWidth()/2;
					break;
			}
			mCanvasFont[id].drawText(no+"",dx,mCanvasFont[id].getHeight()/2,mPaintFont[id]);
			mAlphaTexFont[id].setBitmap(mBitmapFont[id]);
			mTextureManager.replaceTexture(mAlphaTexFont[id]);
	   mPlaneFont[id].setColor(1,1,1,1);
	   root.DrawTexture(mPlaneFont[id],x,y,z,isVisible);
	   mPlaneFont[id].setScale(s);
	   mPlaneFont[id].setRotation(r,180,0);
	}
	
	Plane addPlan(String Title,String str) {
		try{
		
			Plane plan;
			Bitmap bitmap=null;
			bitmap = LoadImgfromAsset(str);
			if(!IsPowerOfTwo(bitmap.getWidth()) || !IsPowerOfTwo(bitmap.getHeight()))
				bitmap = PTImg(bitmap);
			Material material = new Material();
			
			material.setDiffuseMethod(new DiffuseMethod.Lambert());
			material.setSpecularMethod(new SpecularMethod.Phong());
			
			material.addTexture(new Texture(Title,bitmap));
			plan = new Plane(bitmap.getWidth()/128f,bitmap.getHeight()/128f,1,1);
			material.enableLighting(true);
			material.setColorInfluence(0);
			plan.setMaterial(material);
			plan.setTransparent(true);
			plan.setVisible(false);
			plan.loadBitmap(bitmap);
			plan.setRotation(0,180,0);
//			plan.setBlendFunc(GLES20.GL_ONE,GLES20.GL_ONE_MINUS_SRC_ALPHA);
			return plan;
		} catch (Exception e) {
			return null;
		}
	}
	Plane addPlanWH(String Title,String str,float w,float h) {
		try{
		
			Plane plan;
			Bitmap bitmap=null;
			bitmap = LoadImgfromAsset(str);
			if(!IsPowerOfTwo(bitmap.getWidth()) || !IsPowerOfTwo(bitmap.getHeight()))
				bitmap = PTImg(bitmap);
			Material material = new Material();
			material.setDiffuseMethod(new DiffuseMethod.Lambert());
			material.setSpecularMethod(new SpecularMethod.Phong());
			material.addTexture(new Texture(Title,bitmap));
			plan = new Plane(bitmap.getWidth()/w,bitmap.getHeight()/h,1,1);
			material.enableLighting(true);
			material.setColorInfluence(0);
			plan.setMaterial(material);
			plan.setTransparent(true);
			plan.setVisible(false);
			plan.loadBitmap(bitmap);
			plan.setRotation(0,180,0);
//			plan.setBlendFunc(GLES20.GL_ONE,GLES20.GL_ONE_MINUS_SRC_ALPHA);
			return plan;
		} catch (Exception e) {
			return null;
		}
	}
//	void LoadFont(){
//		mTex_Font   = new Plane[10];
//		Bitmap b    = LoadImgfromAsset("fontstrip.png");
//		for(int i=0;i<mTex_Font.length;i++)
//		{
//		   mTex_Font[i] = addBitmap("font"+i,Bitmap.createBitmap(b,(i*b.getWidth())/16,0,b.getWidth()/16,b.getHeight(),null, true));
//		   getCurrentScene().addChild(mTex_Font[i]);
//		}
//	}
	public static Plane addBitmap(String Title,Bitmap bitmap)
	{
		Plane plan = null;
		try
		{
			Material material = new Material();
			material.setDiffuseMethod(new DiffuseMethod.Lambert());
			material.setSpecularMethod(new SpecularMethod.Phong());
			material.addTexture(new Texture(Title,bitmap));
			plan = new Plane(bitmap.getWidth()/128f,bitmap.getHeight()/128f,1,1);
			material.enableLighting(true);
			material.setColorInfluence(0);
			plan.setMaterial(material);
			plan.setTransparent(true);
			plan.setVisible(false);
			plan.loadBitmap(bitmap);
			plan.setRotation(0,180,0);
		}catch(Exception e){}
		return plan;
	}
	Bitmap LoadImgfromAsset(String ID)
	{
		try{
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));}
		catch(Exception e)
		{
			System.out.println("["+ID+"]   !!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
			return null;
		}
	}
	Bitmap PTImg(Bitmap bitmapOrg) {
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		int newWidth = PT(width);
		int newHeight = PT(height);
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
				height, matrix, true);
		Log.d("resizeImg========", "newWidth [" + newWidth + "] newHeight ["+ newHeight + "]");
		return resizedBitmap;
	}
	int PT(int x) {
		--x;
		for (int i = 1; i < 32; i <<= 1) {
			x = x | x >> i;
		}
		return x + 1;
	}
	boolean IsPowerOfTwo(int x)
	{
	    return (x & (x - 1)) == 0;
	}
	private Handler handlerAdBanner = new Handler() {public void handleMessage(Message msg) {mStart.adBanner.setVisibility(msg.what);}};
	public void onDrawFrame(GL10 glUnused) {
//		System.out.println("In Sttttttttttttttttttttt "+getFrameRate());
		super.onDrawFrame(glUnused);
		 if(mStart.adBanner!=null && !mStart.addFree)
 		 {
			if(M.GameScreen == M.GAMEPLAY)
			{
				int inv=mStart.adBanner.getVisibility();
				if(inv==AdView.GONE){try{handlerAdBanner.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv=mStart.adBanner.getVisibility();
				if(inv==AdView.VISIBLE){try{handlerAdBanner.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
			}
		 }
		root.Draw();
	    
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		root.onTouch(v, event);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(mPlayer!=null && M.GameScreen == M.GAMEPLAY)
		{
//			if(mBoat.x>-4.5 && mBoat.x<4.5)
			  mPlayer.vx = event.values[1]*mPlayer.mSpd*.04f;
//			else
//			 mBoat.vx=0;
		}
	}
}
	