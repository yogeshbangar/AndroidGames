package com.hututu.game.TrainCrash;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.google.android.gms.ads.AdView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
@SuppressLint("HandlerLeak")
public class GameRenderer implements Renderer 
{
	final Group root;
	static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	InAppActivity mMainActivity;
	
	SimplePlane[]   mTex_Font,mTex_Font1,mTex_Tile,mTex_Train,mTex_Bogi,mTex_Arrow,mTex_Signal;
	SimplePlane 	mTex_Logo,mTex_Grass,mTex_StopBtn,mTex_SandBg; //mTex_Test
	SimplePlane     mTex_Splash,mTex_Btn,mTex_Tap2Start,mTex_Gplus,mTex_FB,mTex_Twitter,mTex_Icn,mTex_Achiev,mTex_Leder,mTex_CirIcn;
	SimplePlane     mTex_BackBtn,mTex_Board,mTex_Home,mTex_Retry,mTex_Play,mTex_GamePause,mTex_GameWin,mTex_GameOver,mTex_Next;
	SimplePlane     mTex_Sound[],mTex_Pause,mTex_Smoke,mTex_AchTxt,mTex_LeadeTxt,mTex_Menu,mTex_Partical[],mTex_Flag[][];
	SimplePlane     mTex_Stone[],mTex_BlastAni[],mTex_World[],mTex_Exit,mTex_LoadBar,mTex_Pointer,mTex_Box,mTex_Bomb,mTex_Level;
	SimplePlane     mTex_WorldShadow,mTex_BtnShadow,mTex_WorldLock,mTex_LevelLock,mTex_Red,mTex_Life,mTex_RemoveAds,mTex_Cong;
	SimplePlane		mTex_Test;
	
	int mWorldNo=0,mLevelNo=1,mUnlockLev=1;
	int mTrainNo,mArrowNo,mSignalNo,mBombNo;
	int mHilanaCnt,mLoosecnt,mWinCnt,mWaitCnt;
	int mAdCnt=0;
	float mHilaVal=0;
	boolean isHilana=false;
	boolean isAchieve[] = new boolean[6],is30Level=false;
	GameLevel mGameLevel;
	CTile	 mTile[][];
	Train	 mTrain[],mBogi[];
	Arrow    mArrow[];
	Signal   mSignal[];
	MidPoint mMidPoint[];
	Smoke    mSmoke[][],mParticle[],mFlag[];
	Bomb 	 mBomb[];
	
	private Handler handlerAdBanner = new Handler() {public void handleMessage(Message msg) {mStart.adBanner.setVisibility(msg.what);}};
//	private Handler handlerAdRect   = new Handler() {public void handleMessage(Message msg) {mStart.adRect.setVisibility(msg.what);}};
	
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}
	void init()
	{
		try
		{
			int i;
			mMainActivity = new InAppActivity();
			mMainActivity.onCreate();
			System.gc();
			
			mTex_Test = add("seting.png");
			
			mTex_Logo = add("hututugames.png");
			
			mTex_Exit    = add("exit.png");
			mTex_LoadBar = add("bar.png");
			mTex_Pointer = add("pointer.png");
			Bitmap b = LoadImgfromAsset("sprite.png");
			mTex_Tile = new SimplePlane[256];
			for(i = 0; i < mTex_Tile.length; i++)
			{
				mTex_Tile[i] = addBitmap(Bitmap.createBitmap(b, (((i%16)*b.getWidth())/16), (((i/16)*b.getHeight())/16),b.getWidth()/16, b.getHeight()/16, null, true));
			}
			mTex_Grass   = add("grass.png");
			mTex_SandBg  = add("send.png");
			mTex_Train   = new SimplePlane[5];
			mTex_Bogi    = new SimplePlane[4];
			for(i=0;i<mTex_Train.length;i++)
			 mTex_Train[i]   = addRotate("train/train"+i+".png");
			
			for(i=0;i<mTex_Bogi.length;i++)
				mTex_Bogi[i]    = addRotate("train/bogi"+i+".png");
			mTex_Arrow      = new SimplePlane[2];
			mTex_Arrow[0]   = addRotate("arow0.png");
			mTex_Arrow[1]   = addRotate("arow1.png");
			mTex_StopBtn	= add("stop.png");
			mTex_Signal     = new SimplePlane[2];    
			mTex_Signal[0]  = add("signal0.png"); 
			mTex_Signal[1]  = add("signal1.png");
			mTex_Smoke      = add("smoke.png");
			mTex_Partical   = new SimplePlane[11];
			for(i=0;i<mTex_Partical.length;i++)
			   mTex_Partical[i] =add("partical/"+i+".png");	
			
			mTex_Flag  = new SimplePlane[2][10];
			for(i=0;i<2;i++)
			{
				b = LoadImgfromAsset("flag/"+i+".png");
				for(int j=0;j<10;j++)
				{
					mTex_Flag[i][j]=addBitmap(Bitmap.createBitmap(b,((j%8)*b.getWidth())/8,(j/8)*(b.getHeight()/2),b.getWidth()/8,b.getHeight()/2,null, true));
				}
			}
			mTex_Stone = new SimplePlane[5];
			for(i=0;i<mTex_Stone.length;i++)
			mTex_Stone[i] = add("stone/"+i+".png"); 
			mTex_BlastAni = new SimplePlane[11];
			b = LoadImgfromAsset("bomb_blast.png");
			for( i=0;i<mTex_BlastAni.length;i++)
			{
				mTex_BlastAni[i] = addBitmap(Bitmap.createBitmap(b,((i%8)*b.getWidth())/8,(i/8)*(b.getHeight()/2),b.getWidth()/8,b.getHeight()/2,null, true));
			}
			mTex_Red  = add("red.png");
			mTex_Life = add("life.png");
			LoadUi();
			loadFont();
			InitGameObj();
//			GameReset();
		    M.loadSound(mContext);
		}catch(Exception e){}
	}
	void LoadUi()
	{
		mTex_Splash 	= add("ui/splash.png");
		mTex_Btn    	= add("ui/btn_3.png");
		mTex_Tap2Start  = add("ui/tap.png");
		mTex_Gplus      = add("ui/g+.png"); 
		mTex_FB         = add("ui/fb.png");
		mTex_Twitter    = add("ui/twitter.png");
		mTex_Icn        = add("ui/btn_2.png");
		mTex_Leder      = add("ui/lederboard.png");
		mTex_Achiev     = add("ui/achivment.png");
		mTex_CirIcn     = add("ui/btn.png");
		mTex_BackBtn    = add("ui/Back.png");
		mTex_Board      = add("ui/board.png");
		mTex_Home       = add("ui/home.png");
		mTex_Retry      = add("ui/retry.png");
		mTex_Play       = add("ui/play.png");
		mTex_GamePause  = add("ui/game_paused.png");
		mTex_GameOver   = add("ui/level_failed.png");
		mTex_GameWin    = add("ui/level_complet.png");
		mTex_Next       = add("ui/next.png");
		mTex_Sound      = new SimplePlane[2];
		mTex_Sound[0]   = add("ui/sound_on.png"); 
		mTex_Sound[1]   = add("ui/sound_off.png");
		mTex_Pause      = add("ui/paused.png");
		mTex_AchTxt     = add("ui/achivment_2.png");  
		mTex_LeadeTxt   = add("ui/lederboard_2.png");
		mTex_Menu       = add("ui/menu.png");
		mTex_World      = new SimplePlane[2];
		mTex_World[0]   = add("ui/worldIcn0.png");
		mTex_World[1]   = add("ui/worldIcn1.png");
		mTex_Box        = add("box.png");
		mTex_Bomb       = add("bomb.png");
		mTex_Level      = add("level.png");
		
		mTex_WorldShadow  = add("ui/worldback.png");
		mTex_BtnShadow    = add("ui/box_back.png");
		mTex_WorldLock    = add("ui/worldlock.png");
		mTex_LevelLock    = add("ui/locklevel.png");
		mTex_RemoveAds    = add("ui/remove_ads.png");
		mTex_Cong         = add("ui/congratulation.png"); 
		
 	}
	void loadFont(){
		mTex_Font   = new SimplePlane[10];
		Bitmap b    = LoadImgfromAsset("font.png");
		for(int i=0;i< mTex_Font.length; i++)
		{
		    mTex_Font[i]  = addBitmap(Bitmap.createBitmap(b,((i%8)*b.getWidth())/8,(i/8)*(b.getHeight()/2),b.getWidth()/8,b.getHeight()/2,null, true));
		}
		b    = LoadImgfromAsset("font1.png");
		mTex_Font1   = new SimplePlane[11];
		for(int i=0;i< mTex_Font1.length; i++)
		{
		    mTex_Font1[i]  = addBitmap(Bitmap.createBitmap(b,(i*b.getWidth())/16,0,b.getWidth()/16,b.getHeight(),null, true));
		}
	}
	void InitGameObj()
	{
		mGameLevel = new GameLevel(this);
		mTile = new CTile[M.ROW][M.COL];
		for(int i=0;i<M.ROW;i++)
		{
			for(int j=0;j<M.COL;j++)
			{
				mTile[i][j] = new CTile();
			}
		}
		
		mTrain    = new Train[10];
		mBogi     = new Train[mTrain.length];
		mMidPoint = new MidPoint[mTrain.length];
		mSignal   = new Signal[mTrain.length];
		mArrow    = new Arrow[20];
		
		for(int i=0;i<mTrain.length;i++)
		{
			mTrain[i]  = new Train();
			mBogi[i]   = new Train();
			mSignal[i] = new Signal(); 
			mMidPoint[i] =  new MidPoint();
		}
		for(int i=0;i<mArrow.length;i++)
			mArrow[i]  = new Arrow();
		
		mSmoke = new Smoke[mTrain.length][60];
		for(int i=0;i<mSmoke.length;i++)
		{
		   for(int j=0;j<mSmoke[i].length;j++)
			   mSmoke[i][j]  = new Smoke();
		}
		mParticle = new Smoke[200];
		for(int i=0;i<mParticle.length;i++)
		{
			mParticle[i] = new Smoke(); 
		}
		mFlag = new Smoke[5];
		for(int i=0;i<mFlag.length;i++)
		{
		  mFlag[i] = new Smoke();
		}
		mBomb = new Bomb[6];
		for(int i=0;i<mBomb.length;i++)
		{
			mBomb[i] = new Bomb();
		}
	}
	void ResetValue()
	{
		for(int i=0;i<mTrain.length;i++)
		{
			mTrain[i].ResetTrain();
			mBogi[i].ResetTrain();
			root.ResetSmoke(i);
		}
		for(int i=0;i<mParticle.length;i++)
		{
			if(mParticle[i].x!=mParticle[i].y)
				mParticle[i].x=mParticle[i].y=100;
		}
		for(int i=0;i<mFlag.length;i++)
		   mFlag[i].x=mFlag[i].y=100;
		for(int i=0;i<mArrow.length;i++)
		   mArrow[i].Reset();
		
		for(int i=0;i<mSignal.length;i++)
		   mSignal[i].ResetSignal();
		
		for(int i=0;i<mBomb.length;i++)
		{
			mBomb[i].reset();
			for(int j=0;j<mBomb[i].bx.length;j++)
			{
			  mBomb[i].bx[j] =mBomb[i].by[j]=100;
			}
		}
		
		mBombNo = mTrainNo = mArrowNo = 0;mSignalNo = 0;
		mHilaVal   = 0;mHilanaCnt = 100;
		mLoosecnt  = mWinCnt=mWaitCnt=0;
		isHilana   = false;
	}
	void GameReset()
	{ 
		root.Counter=0;
		M.BgStop();
		ResetValue();
		int no=0;
		for(int i=0;i<M.ROW;i++)
		{
			for(int j=0;j<M.COL;j++)
			{
				switch(mLevelNo)
				{
					case 1:
						no= mTile[i][j].mTNo = Level.l1[j][i];
						break;
					case 2:
						no= mTile[i][j].mTNo = Level.l2[j][i];
						break;
					case 3:
						no= mTile[i][j].mTNo = Level.l3[j][i];
						break;
					case 4:
						no= mTile[i][j].mTNo = Level.l4[j][i];
						break;
					case 5:
						no= mTile[i][j].mTNo = Level.l5[j][i];
						break;
					case 6:
						no= mTile[i][j].mTNo = Level.l6[j][i];
						break;
					case 7:
						no= mTile[i][j].mTNo = Level.l7[j][i];
						break;
					case 8:
						no= mTile[i][j].mTNo = Level.l8[j][i];
						break;
					case 9:
						no= mTile[i][j].mTNo = Level.l9[j][i];
						break;
					case 10:
						no= mTile[i][j].mTNo = Level.l10[j][i];
						break;
					case 11:
						no= mTile[i][j].mTNo = Level.l11[j][i];
						break;
					case 12:
						no= mTile[i][j].mTNo = Level.l12[j][i];
						break;
					case 13:
						no= mTile[i][j].mTNo = Level.l13[j][i];
						break;
					case 14:
						no= mTile[i][j].mTNo = Level.l14[j][i];
						break;
					case 15:
						no= mTile[i][j].mTNo = Level.l15[j][i];
						break;
					case 16:
						no= mTile[i][j].mTNo = Level.l16[j][i];
						break;
					case 17:
						no= mTile[i][j].mTNo = Level.l17[j][i];
						break;
					case 18:
						no= mTile[i][j].mTNo = Level.l18[j][i];
						break;
					case 19:
						no= mTile[i][j].mTNo = Level.l19[j][i];
						break;
					case 20:
						no= mTile[i][j].mTNo = Level.l20[j][i];
						break;
					case 21:
						no= mTile[i][j].mTNo = Level.l21[j][i];
						break;
					case 22:
						no= mTile[i][j].mTNo = Level.l22[j][i];
						break;
					case 23:
						no= mTile[i][j].mTNo = Level.l23[j][i];
						break;
					case 24:
						no= mTile[i][j].mTNo = Level.l24[j][i];
						break;
					case 25:
						no= mTile[i][j].mTNo = Level.l25[j][i];
						break;
					case 26:
						no= mTile[i][j].mTNo = Level.l26[j][i];
						break;
					case 27:
						no= mTile[i][j].mTNo = Level.l27[j][i];
						break;
					case 28:
						no= mTile[i][j].mTNo = Level.l28[j][i];
						break;
					case 29:
						no= mTile[i][j].mTNo = Level.l29[j][i];
						break;
					case 30:
						no= mTile[i][j].mTNo = Level.l30[j][i];
						break;
					case 31:
						no= mTile[i][j].mTNo = Level.l31[j][i];
						break;
					case 32:
						no= mTile[i][j].mTNo = Level.l32[j][i];
						break;
					case 33:
						no= mTile[i][j].mTNo = Level.l33[j][i];
						break;
					case 34:
						no= mTile[i][j].mTNo = Level.l34[j][i];
						break;
					case 35:
						no= mTile[i][j].mTNo = Level.l35[j][i];
						break;
					case 36:
						no= mTile[i][j].mTNo = Level.l36[j][i];
						break;
					case 37:
						no= mTile[i][j].mTNo = Level.l37[j][i];
						break;
					case 38:
						no= mTile[i][j].mTNo = Level.l38[j][i];
						break;
					case 39:
						no= mTile[i][j].mTNo = Level.l39[j][i];
						break;
					case 40:
						no= mTile[i][j].mTNo = Level.l40[j][i];
						break;
					case 41:
						no= mTile[i][j].mTNo = Level.l41[j][i];
						break;
					case 42:
						no= mTile[i][j].mTNo = Level.l42[j][i];
						break;
					case 43:
						no= mTile[i][j].mTNo = Level.l43[j][i];
						break;
					case 44:
						no= mTile[i][j].mTNo = Level.l44[j][i];
						break;
					case 45:
						no= mTile[i][j].mTNo = Level.l45[j][i];
						break;
					case 46:
						no= mTile[i][j].mTNo = Level.l46[j][i];
						break;
					case 47:
						no= mTile[i][j].mTNo = Level.l47[j][i];
						break;
					case 48:
						no= mTile[i][j].mTNo = Level.l48[j][i];
						break;
						
				}
				mTile[i][j].set(-.935f+i*mTex_Tile[0].width(),.925f-j*mTex_Tile[0].Height(),no);
				switch(mLevelNo)
				{
				  case 22:
					  if(i==0)
					   setTrain22Level();
					 break;
				  case 31:
					  if(i==0)
					   setTrain31Level();
					  break; 
				  case 35:
					  if(i==0)
					   setTrain35Level();
					  break;
				  case 47:
					  if(i==0)
					   setTrain47Level();
					  break;
				  case 48:
					  if(i==0)
					   setTrain48Level(); 
					  break;
			  	  default:
			  		  setTrain(i,j);
					 break;
				}
				SetArrow(i,j);
				setSignal(i,j);
				if(mLevelNo>25)
				{
				  setBomb(i,j);
				}
			}
		}
		mWinCnt=NostopTrain();
 	    M.GameScreen = M.GAMESTART;
//		System.out.println("   t     "+mTrainNo+"    a     "+mArrowNo+"     s    "+mSignalNo+"      b     "+mBombNo+"         w            "+mWinCnt);
		GameRenderer.mStart.LoadHandler();
	}
	
	float trainAng[]={90,180,270,0};
    void setTrain(int i,int j)
    {
       int jj=0;
       float x=0,y=0;
       
       switch(mTile[i][j].mTNo)
       {
			case 194:case 195:case 196:case 197: //Brown Train
				jj=mTile[i][j].mTNo-194;
				x = -.935f+i*mTex_Tile[0].width();
				y = .925f-j*mTex_Tile[0].Height();
				setTrainPos(x, y,trainAng[jj],M.BROWNTRAIN);
				break;
			case 198:case 199:case 200:case 201: // Yello Train
				jj=mTile[i][j].mTNo-198;
				x = -.935f+i*mTex_Tile[0].width();
				y = .925f-j*mTex_Tile[0].Height();
				setTrainPos(x, y,trainAng[jj],M.YELLOWTRAIN);
				break;
			case 202:case 203:case 204:case 205: // Green Train
				jj=mTile[i][j].mTNo-202;
				x = -.935f+i*mTex_Tile[0].width();
				y = .925f-j*mTex_Tile[0].Height();
				setTrainPos(x, y,trainAng[jj],M.GREENTRAIN);
				break;
			case 206:case 207:case 208:case 209: // Blue Train
				jj=mTile[i][j].mTNo-206;
				x = -.935f+i*mTex_Tile[0].width();
				y = .925f-j*mTex_Tile[0].Height();
				setTrainPos(x, y,trainAng[jj], M.BLUETRAIN);
				break;
			case 132:case 133:case 134:case 135:
				jj=mTile[i][j].mTNo-132;
				x = -.935f+i*mTex_Tile[0].width();
				y = .925f-j*mTex_Tile[0].Height();
				setTrainPos(x,y,trainAng[jj],4);
				break;
		}
    }
    void setTrainPos(float x,float y,float ang,int no)
    {
    	if(mLevelNo !=9  && mLevelNo !=19 && mLevelNo !=21 && mLevelNo !=22 && mLevelNo !=23 && mLevelNo!=24)
    	{
    	  if(mLevelNo ==20 && no == M.BLUETRAIN || mLevelNo ==36 && no == M.BROWNTRAIN || (mLevelNo ==38 || mLevelNo ==39) && no == M.BROWNTRAIN 
		    ||(mLevelNo ==39 || mLevelNo ==40) && no == M.GREENTRAIN || no ==4)
    	  {
    		  
    	  }
    	  else
    	  {
 	        mBogi[mTrainNo].set(x,y,ang,no);
    	  }
 	      mBogi[mTrainNo].x+=5*mBogi[mTrainNo].vx;
 	 	  mBogi[mTrainNo].y+=5*mBogi[mTrainNo].vy;
    	}
  	    mTrain[mTrainNo].set(x,y,ang,no);
        if(mBogi[mTrainNo].x == 100 && mBogi[mTrainNo].y==100)
 	    {
   	   	   mTrain[mTrainNo].x+=5*mTrain[mTrainNo].vx;
     	   mTrain[mTrainNo].y+=5*mTrain[mTrainNo].vy;
 	    }
        else
        {
 	      mTrain[mTrainNo].x+=25*mTrain[mTrainNo].vx;
 	      mTrain[mTrainNo].y+=25*mTrain[mTrainNo].vy;
        }
 	    mTrainNo++;
    }
    void setTrain22Level()
    {
      mTrainNo=4;
      for(int i=0;i<mTrainNo;i++)
      {
        mTrain[i].set(-.8f-i*.3f,0,0,i);
        mTrain[i].x+=5*mTrain[i].vx;
  	    mTrain[i].y+=5*mTrain[i].vy;
  	    mBogi[i].x=100f;
        mBogi[i].y=100f;
      }
    }
    void setTrain31Level()
    {
      mTrainNo=4;
      float x[] ={.2f,.15f,-.5f,-.93f};
      float y[] ={.529f,.798f,.529f,.4f};
      for(int i=0;i<mTrainNo;i++)
      {
    	 mTrain[i].set(x[i],y[i],i<3?180f:90f,3-i);
    	 mTrain[i].x+=5*mTrain[i].vx;
   	     mTrain[i].y+=5*mTrain[i].vy;
      }
    }
    void setTrain35Level()
    {
      mTrainNo=4;
      float x[] ={.419f,-.139f,-.559f,.45f};
      float y[] ={-.25f,.655f ,.07f  ,.929f};
      for(int i=0;i<mTrainNo;i++)
      {
    	 if(i!=0 && i!=3)
    	  mBogi[i].set(x[i],y[i],270-i*90f,i<2?i:i+1);
    	 if(i<3)
    	   mTrain[i].set(x[i],y[i],270-i*90f,i<2?i:i+1);
    	 else
    	    mTrain[i].set(x[i],y[i],180,i<2?i:i+1);
    	 
    	 mBogi[i].x +=5*mBogi[i].vx;
    	 mBogi[i].y +=5*mBogi[i].vy;
    	 mTrain[i].x+=25*mTrain[i].vx;
   	     mTrain[i].y+=25*mTrain[i].vy;
      }
    }
    void setTrain47Level()
    {
      mTrainNo=3;
      float x[] ={.669f,-.669f,-.729f};
      float y[] ={-.545f,.929f, .13f};
      for(int i=0;i<mTrainNo;i++)
      {
    	 mBogi[i].set(x[i],y[i] ,i<2?0:180f,i<2?i:i+1); 
    	 mTrain[i].set(x[i],y[i],i<2?0:180f,i<2?i:i+1);
    	 
    	 mBogi[i].x +=5*mBogi[i].vx;
    	 mBogi[i].y +=5*mBogi[i].vy;
    	 mTrain[i].x+=25*mTrain[i].vx;
   	     mTrain[i].y+=25*mTrain[i].vy;
      }
    }
    void setTrain48Level()
    {
      mTrainNo=4;
      float x[] ={ 0   ,.23f  ,.4f,-.25f};
      float y[] ={.915f,-.939f,.13f,-.27f};
      for(int i=0;i<mTrainNo;i++)
      {
    	 if(i<2)
    	 {
    	   mBogi[i].set(x[i],y[i] ,i%2==0?0:180f,i);
    	   mTrain[i].set(x[i],y[i],i%2==0?0:180f,i);
    	 }
    	 else
    	 {
    		 mTrain[i].set(x[i],y[i],i==2?180f:0,4);
    	 }
    	 
    	 mBogi[i].x +=5*mBogi[i].vx;
    	 mBogi[i].y +=5*mBogi[i].vy;
    	 mTrain[i].x+=25*mTrain[i].vx;
   	     mTrain[i].y+=25*mTrain[i].vy;
      }
    }
    void SetArrow(int i,int j)
    {
    	switch(mTile[i][j].mTNo)
    	{
		  case 160:case 161:case 162:
		  case 230:case 228:case 232: 
			   int jj=0;
			   float a160[] = {270,90,180};
			   if(mTile[i][j].mTNo<=162)
		  	     jj=mTile[i][j].mTNo-160;
			   else
			   {
				   jj=mTile[i][j].mTNo-230;
			     if(jj<0)
				    jj=1;
			   }
		  	   mArrow[mArrowNo].set(-.935f+i*mTex_Tile[0].width(),.925f-j*mTex_Tile[0].Height(),a160[jj],false);
 		       mArrowNo++;
 		       
 		     break;
		  case 168:
		  case 235:
	 	     mArrow[mArrowNo].set(-.935f+i*mTex_Tile[0].width(),.925f-j*mTex_Tile[0].Height(),0,false);
	 	     mArrowNo++;
	         break;
		  case 181:case 182:case 183:case 184:
			  float a180[] = {0,270,90,180};
			  jj=mTile[i][j].mTNo-181;
			  mArrow[mArrowNo].set(-.935f+i*mTex_Tile[0].width(),.925f-j*mTex_Tile[0].Height(),a180[jj],true);
			  mArrowNo++;
			 break;
		  case 233:
			   mArrow[mArrowNo].set(-.935f+i*mTex_Tile[0].width(),.925f-j*mTex_Tile[0].Height(),0,true);
			   mArrowNo++;
			 break;
		  case 229:
			   mArrow[mArrowNo].set(-.935f+i*mTex_Tile[0].width(),.925f-j*mTex_Tile[0].Height(),270,true);
			   mArrowNo++;
			 break;
		  case 231:
			   mArrow[mArrowNo].set(-.935f+i*mTex_Tile[0].width(),.925f-j*mTex_Tile[0].Height(),90,true);
			   mArrowNo++;
			 break;
		  case 234:
			   mArrow[mArrowNo].set(-.935f+i*mTex_Tile[0].width(),.925f-j*mTex_Tile[0].Height(),180,true);
			   mArrowNo++;
			 break;
		}
    }
    void setSignal(int i,int j)
    {
    	if(mTile[i][j].mTNo ==226 || mTile[i][j].mTNo ==227)
		{
	        mSignal[mSignalNo].set(-.935f+i*mTex_Tile[0].width(),.925f-j*mTex_Tile[0].Height(),mTile[i][j].mTNo==227?0:1);
 		    mSignalNo++;
		}
    }
    void setBomb(int i,int j)
    {
    	int c=0;
    	if( mTile[i][j].mTNo ==159 || mTile[i][j].mTNo ==236 || mTile[i][j].mTNo ==237)
		{
    		for(int k=0;k<mBomb.length && c<1;k++)
    		{
    		  if(mBomb[k].x==100 && mBomb[k].y==100)
    		  {
    		    mBomb[k].Set(-.935f+i*mTex_Tile[0].width(),.925f-j*mTex_Tile[0].Height());
    		    c++;
    		  }
    		}
    		if(mLevelNo==39)
    		  mBombNo=3;
    		else if(mLevelNo==42 || mLevelNo==44 || mLevelNo==45)
   		      mBombNo=2;
    		else
			  mBombNo=1;
		}
    }
    int NostopTrain()
    {
    	int cnt=0;
    	for(int i=0;i<mTrainNo;i++)
    	{
   		   if(mTrain[i].no==4)
    		 cnt++;
    	}
    	return cnt;
    }
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
  	    gl.glClearColor(0,0,0,1);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_REPEAT);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	public void onDrawFrame(GL10 gl) 
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
 	    gl.glClearColor(0,0,0,1);
		gl.glLoadIdentity();
		root.draw(gl);
		resumeCounter++;
		if(resumeCounter>50)
		{
			 if(mStart.adBanner!=null)
	 		 {
				if(!mStart.addFree && (M.GameScreen != M.GAMEMENU && M.GameScreen != M.GAMEPLAY &&  M.GameScreen != M.GAMESTART))
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
			// Adload
//			if (mStart.adRect != null)  
//			{
//				if(!mStart.addFree && M.GameScreen == M.GAMELOADING)
//				{
//					int inv = mStart.adRect.getVisibility();
//					if (inv == AdView.GONE) {try {handlerAdRect.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
//				} 
//				else
//				{
//					int inv = mStart.adRect.getVisibility();
//					if (inv == AdView.VISIBLE) {try {handlerAdRect.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
//					}
//				}
//			}
			if(mStart.addFree)
			{
				int inv = mStart.adBanner.getVisibility();
				if(inv==AdView.VISIBLE){try{handlerAdBanner.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
				
//				inv = mStart.adRect.getVisibility();
//				if (inv == AdView.VISIBLE) {try {handlerAdRect.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}}
			}
		}
		if(resumeCounter>500000)
			resumeCounter=0;
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
	SimplePlane addBig (String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane(((b.getWidth()+20f)/(M.mMaxX)),(b.getHeight()/(M.mMaxY)));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	SimplePlane addRotate(String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	SimplePlane addDouble(String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane((b.getWidth()/(.5f*M.mMaxX)),(b.getHeight()/(.65f*M.mMaxY)));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	public static SimplePlane addBitmap (Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	SimplePlane addBitmapR(Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	public static Bitmap LoadImgfromAsset(String ID)
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
		 Log.d("resizeImg========","newWidth ["+newWidth+"] newHeight ["+newHeight+"]");
		 return resizedBitmap;
	}
	Bitmap FlipHorizontal(Bitmap bitmapOrg,int type)
	{
		Matrix matrix = new Matrix();
		if(type ==0) // Verticle
		 matrix.postScale(1f,-1f);
		if(type ==1) //Horizontal
	  	 matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}

}

