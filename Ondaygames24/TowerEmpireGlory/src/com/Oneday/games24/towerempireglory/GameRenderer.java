package com.Oneday.games24.towerempireglory;

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

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int mSel = 0;
	boolean addFree = false;
	boolean SingUpadate = false;
	
	SimplePlane mTex_Logo,mTex_Play,mTex_pause,mTex_powerload,mTex_powerlock,mTex_$,mTex_Crystal,mTex_board;
	SimplePlane mTex_infoBox,mTex_upgrade,mTex_Key,mTex_Achiev,mTex_Leader,mTex_Menu,mTex_GameOver,mTex_Win;
	SimplePlane mTex_MenuBG,mTex_Continue,mTex_Setting,mTex_AboutT,mTex_Back,mTex_fb,mTex_GP,mTex_ResetT;
	SimplePlane mTex_share,mTex_twitter,mTex_Aboutscr,mTex_Cover,mTex_Rect,mTex_SMana,mTex_Lock;
	SimplePlane mTex_UpBox,mTex_Retry,mTex_Shop,mTex_Splash;
	
	SimplePlane[][] mTex_Opp[],mTex_Power,mTex_Gola,mTex_Upgrad,mTex_UpBow,mTex_Opp14;
	SimplePlane[] mTex_Music,mTex_sound,mTex_WalSpark,mTex_Fire,mTex_FireBall,mTex_FirePower,mTex_Kila;
	SimplePlane[] mTex_Arrow,mTex_BG,mTex_Bow,mTex_Fort,mTex_Born,mTex_button,mTex_lifebar,mTex_SparBreak;
	SimplePlane[] mTex_Freez,mTex_Spark,mTex_bar,mTex_AchI,mTex_Dot,mTex_Mana,mTex_BPower,mTex_OLife;
	
	boolean Achi[];
	boolean BowPower[];
	
	int OppCount[] = new int[14];
	int Mana[] = new int[10];
	int Tower[] = new int[7];
	int uPower[];
	int mStage = 1;
	int mLevel = 0;
	int totalOpp = 0;
	int RemainOpp = 0;
	int selUpgrd = 0;
	int selPower = 0;
	int Tdamy;
	int damy;
	int Wave = 0 ;
	float lifeBar;
	
	
	Arrow mArrow[];
	Opponent mOpp[];
	
	Bow mBow;
	Player mPlayer;
	Font  mFont;
	Fire mFire[];
	Power mPowerFire[];
	Freez mFreez; 
	Sparkal mSparkal;
	Cover mCover;
	PopUp mPopUp; 
	
//	InApp mInApp;
	
	String mPName ="";
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
//	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {init();}};//AdHouse
	public GameRenderer(Context context) {
		mContext = context;
		mStart = (Start) mContext;
		root = new Group(this);
//		try {
//			handler2.sendEmptyMessage(AdView.VISIBLE);
//		} catch (Exception e) {
//		}
		init();
	}

	void init() {
		try {
			mArrow = new Arrow[50];
			for (int i = 0; i < mArrow.length; i++)
				mArrow[i] = new Arrow();
			mOpp = new Opponent[100];
			for (int i = 0; i < mOpp.length; i++)
				mOpp[i] = new Opponent();
			
			
			mBow = new Bow();
			mPlayer = new Player(this);
			mFire = new Fire[20];
			for(int i=0;i<mFire.length;i++){
				mFire[i] = new Fire();
			}
			mPowerFire = new Power[2];
			for(int i=0;i<mPowerFire.length;i++)
				mPowerFire[i] = new Power();
			
			mFreez = new Freez();
			mSparkal = new Sparkal();
			mCover = new Cover();
			mPopUp= new PopUp();
			Achi = new boolean[26];
			
			
			uPower = new int[6];
			BowPower = new boolean[27];
//			mInApp = new InApp();
//			mInApp.onCreate();
			
			
			
			mTex_Retry	= add("retry_text.png");
			mTex_Shop = add("shop_text.png");
			mTex_Splash = add("splash.png");
			
			mTex_BPower = new SimplePlane[6];
			Bitmap b= LoadImgfromAsset("upgrde/bp0.png");
			for(int i=0;i<4;i++){
				mTex_BPower[i] = addBitmap(Bitmap.createBitmap(b, (i)*b.getWidth()/4, 0,b.getWidth()/4, b.getHeight(), null, true));
			}
			b= LoadImgfromAsset("upgrde/bp4.png");
			mTex_BPower[4] = addBitmap(Bitmap.createBitmap(b, 0*b.getWidth()/2, 0,b.getWidth()/2, b.getHeight(), null, true));
			mTex_BPower[5] = addBitmap(Bitmap.createBitmap(b, 1*b.getWidth()/2, 0,b.getWidth()/2, b.getHeight(), null, true));
			b.recycle();
			mTex_Mana = new SimplePlane[9];
			b= LoadImgfromAsset("upgrde/m0.png");
			for(int i=0;i<8;i++){
				mTex_Mana[i] = addBitmap(Bitmap.createBitmap(b, (i)*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
			}
			b= LoadImgfromAsset("upgrde/m8.png");
			mTex_Mana[8] = addBitmap(Bitmap.createBitmap(b, 0*b.getWidth()/4, 0,b.getWidth()/4, b.getHeight(), null, true));
			mTex_SMana = addBitmap(Bitmap.createBitmap(b, 1*b.getWidth()/4, 0,b.getWidth()/4, b.getHeight(), null, true));
			mTex_Rect = addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/4, 0,b.getWidth()/4, b.getHeight(), null, true));
			b.recycle();
			mTex_Kila = new SimplePlane[7];
			b= LoadImgfromAsset("upgrde/k0.png");
			
			for(int i=0;i<mTex_Kila.length;i++){
				mTex_Kila[i] = addBitmap(Bitmap.createBitmap(b, (i)*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
			}
			b.recycle();
			mTex_UpBow = new SimplePlane[2][10];
			b= LoadImgfromAsset("upgrde/b0.png");
			for(int i=0;i<mTex_UpBow[0].length;i++){
				mTex_UpBow[0][i] = addBitmap(Bitmap.createBitmap(b, (i)*b.getWidth()/16, 0,b.getWidth()/16, b.getHeight(), null, true));
				mTex_UpBow[1][i] = add("upgrde/bo"+(i)+".png");
			}
			for(int i=0;i<6;i++){
				mTex_UpBow[1][i] = addBitmap(Bitmap.createBitmap(b, (i+10)*b.getWidth()/16, 0,b.getWidth()/16, b.getHeight(), null, true));
			}
			b= LoadImgfromAsset("upgrde/bo6.png");
			for(int i=0;i<4;i++){
				mTex_UpBow[1][i+6] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/4, 0,b.getWidth()/4, b.getHeight(), null, true));
			}
			
			b.recycle();
			
			mTex_Upgrad = new SimplePlane[5][2];
			b= LoadImgfromAsset("upgrde/bow_icon.png");
			
			mTex_Upgrad[0][0] = addBitmap(Bitmap.createBitmap(b, 5*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
			mTex_Upgrad[0][1] = addBitmap(Bitmap.createBitmap(b, 4*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
			
			mTex_Upgrad[1][0] = addBitmap(Bitmap.createBitmap(b, 1*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
			mTex_Upgrad[1][1] = addBitmap(Bitmap.createBitmap(b, 0*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
			
			mTex_Upgrad[2][0] = addBitmap(Bitmap.createBitmap(b, 7*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
			mTex_Upgrad[2][1] = addBitmap(Bitmap.createBitmap(b, 6*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
			
			mTex_Upgrad[3][0] = addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
			mTex_Upgrad[3][1] = addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
			
			mTex_Upgrad[4][0] = add("upgrde/button_power_unselect.png");
//			mTex_Upgrad[4][1] = add("button3.png");
			mTex_Cover = add("cover_Area.png");
			
			mTex_Dot = new SimplePlane[2];
			mTex_Dot[0] = add("dot0.png");
			mTex_Dot[1] = add("dot1.png");
			
			
			mTex_OLife = new SimplePlane[2];
			mTex_OLife[0] = add("life.png");
			mTex_OLife[1] = add("red_dot.png");
			
			b.recycle();
			mTex_AchI = new SimplePlane[26];
			b= LoadImgfromAsset("achiv.png");
			for(int i= 0;i<mTex_AchI.length;i++)
				mTex_AchI[i] = addBitmap(Bitmap.createBitmap(b, (i%16)*b.getWidth()/16, (i/16)*b.getHeight()/2
						,b.getWidth()/16, b.getHeight()/2, null, true));
			
			b.recycle();
			b= LoadImgfromAsset("Sparkle/0.png");
			mTex_Spark = new SimplePlane[13];
			for(int i= 0;i<mTex_Spark.length;i++)
				mTex_Spark[i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/4
						,b.getWidth()/4, b.getHeight()/4, null, true)); 
			
			
			b.recycle();
			b= LoadImgfromAsset("Sparkle/run_0.png");
			mTex_SparBreak = new SimplePlane[3];
			for(int i= 0;i<mTex_SparBreak.length;i++)
				mTex_SparBreak[i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, 0
						,b.getWidth()/4, b.getHeight(), null, true)); 
			
			
//			mTex_FBreak = new SimplePlane[4];
//			for(int i= 0;i<mTex_FBreak.length;i++)
//				mTex_FBreak[i] = add("Freeze/fb"+i+".png");
			b.recycle();
			mTex_Freez = new SimplePlane[8];
			b= LoadImgfromAsset("Freeze/0.png");
			for(int i= 0;i<mTex_Freez.length;i++)
				mTex_Freez[i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2,b.getWidth()/4, b.getHeight()/2, null, true));
			
//			mTex_FSton = add("Freeze/stone.png");
			b.recycle();
			mTex_Born = new SimplePlane[3];
			b= LoadImgfromAsset("burn.png");
			for(int i=0;i<mTex_Born.length;i++)
				mTex_Born[i] = addBitmap(Bitmap.createBitmap(b, (i)*b.getWidth()/4, 0,b.getWidth()/4, b.getHeight(), null, true));
			mTex_Aboutscr = add("about.png");
			mTex_Music = new SimplePlane[2];
			mTex_Music[0] = add("music_off.png");
			mTex_Music[1] = add("music_on.png");
			mTex_sound = new SimplePlane[2];
			mTex_sound[0] = add("sound_off_text.png");
			mTex_sound[1] = add("sound_on_text.png");
			mTex_AboutT = add("about_text.png");
			
			
			
			
			
//			mTex_Clude = new SimplePlane[2];
//			mTex_Clude[0] = add("menu_bg_clouddown.png");
//			mTex_Clude[1] = add("menu_bg_cloudup.png");
			mTex_MenuBG = add("menu_bg0.png");
			
			
			
			mTex_board = add("board_small.png");
			mTex_infoBox = add("info_textfill.png");
			mTex_upgrade = add("upgrade_text.png");
			
			
			mTex_powerlock = add("power_lock.png");
			
			Bitmap b1= LoadImgfromAsset("buttons.png");
			mTex_button = new SimplePlane[10];
			mTex_button[0] = addBitmap(Bitmap.createBitmap(b1, 0*b1.getWidth()/4, 0*b1.getHeight()/2,b1.getWidth()/4, b1.getHeight()/2, null, true));
			mTex_button[1] = addBitmap(Bitmap.createBitmap(b1, 1*b1.getWidth()/4, 0*b1.getHeight()/2,b1.getWidth()/4, b1.getHeight()/2, null, true));
			mTex_button[2] = add("button2.png");
			
			mTex_button[4] = add("button4.png");
			mTex_button[5] = addBitmap(Bitmap.createBitmap(b1, 2*b1.getWidth()/4, 0*b1.getHeight()/2,b1.getWidth()/4, b1.getHeight()/2, null, true));
			
			mTex_button[7]= add("button7.png");
			mTex_button[8] = add("button8.png");
			
			mTex_Win = addBitmap(Bitmap.createBitmap(b1, 0*b1.getWidth()/4, 1*b1.getHeight()/2,b1.getWidth()/4, b1.getHeight()/2, null, true));
			mTex_Continue = addBitmap(Bitmap.createBitmap(b1, 1*b1.getWidth()/4, 1*b1.getHeight()/2,b1.getWidth()/4, b1.getHeight()/2, null, true));
			mTex_GameOver = addBitmap(Bitmap.createBitmap(b1, 2*b1.getWidth()/4, 1*b1.getHeight()/2,b1.getWidth()/4, b1.getHeight()/2, null, true));
			mTex_lifebar = new SimplePlane[3];
			mTex_lifebar[0] = addBitmap(Bitmap.createBitmap(b1, 3*b1.getWidth()/4, 0*b1.getHeight()/2,b1.getWidth()/4, b1.getHeight()/2, null, true));
			mTex_lifebar[1] = add("level_lifebar1.png");
			mTex_lifebar[2] = add("level_lifebar2.png");
			
			b.recycle();
			mTex_bar = new SimplePlane[2];
			mTex_bar[0] = add("bar_gameplay0.png");
			b= LoadImgfromAsset("icon.png");
			mTex_Achiev	= addBitmap(Bitmap.createBitmap(b, 0*b.getWidth()/4, 0*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			mTex_Back 	= addBitmap(Bitmap.createBitmap(b, 1*b.getWidth()/4, 0*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			mTex_Leader = addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/4, 0*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			mTex_Menu 	= addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/4, 0*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			mTex_Setting= addBitmap(Bitmap.createBitmap(b, 0*b.getWidth()/4, 1*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			mTex_share	= addBitmap(Bitmap.createBitmap(b, 1*b.getWidth()/4, 1*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			mTex_$		= addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/4, 1*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			mTex_Crystal= addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/4, 1*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			mTex_Key	= addBitmap(Bitmap.createBitmap(b, 0*b.getWidth()/4, 2*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			mTex_Lock	= addBitmap(Bitmap.createBitmap(b, 1*b.getWidth()/4, 2*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			mTex_bar[1] = addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/4, 2*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			mTex_pause	= addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/4, 2*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
			
			
			b.recycle();
			b= LoadImgfromAsset("ui.png");
			mTex_UpBox		= addBitmap(Bitmap.createBitmap(b, 0*b.getWidth()/4, 0*b.getHeight()/2,b.getWidth()/4, b.getHeight()/2, null, true));
			mTex_button[3] 	= addBitmap(Bitmap.createBitmap(b, 1*b.getWidth()/4, 0*b.getHeight()/2,b.getWidth()/4, b.getHeight()/2, null, true));
			mTex_button[6] 	= addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/4, 0*b.getHeight()/2,b.getWidth()/4, b.getHeight()/2, null, true));
			mTex_button[9] 	= addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/4, 0*b.getHeight()/2,b.getWidth()/4, b.getHeight()/2, null, true));
			mTex_powerload	= addBitmap(Bitmap.createBitmap(b, 0*b.getWidth()/4, 1*b.getHeight()/2,b.getWidth()/4, b.getHeight()/2, null, true));
			mTex_twitter 	= addBitmap(Bitmap.createBitmap(b, 1*b.getWidth()/4, 1*b.getHeight()/2,b.getWidth()/4, b.getHeight()/2, null, true));
			mTex_GP			= addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/4, 1*b.getHeight()/2,b.getWidth()/4, b.getHeight()/2, null, true));
			mTex_fb			= addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/4, 1*b.getHeight()/2,b.getWidth()/4, b.getHeight()/2, null, true));
			
			
			
			
			
			mTex_ResetT = add("reset_text.png");
			mTex_Logo = add("logo.png");
			
			b.recycle();
			mTex_Arrow = new SimplePlane[10];
			b= LoadImgfromAsset("arrow/0.png");
			for (int i = 0; i < 8; i++)
				mTex_Arrow[i] = addBitmapRo(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2
						,b.getWidth()/4, b.getHeight()/2, null, true));
			
			
			b.recycle();
			b= LoadImgfromAsset("arrow/1.png");
			mTex_Arrow[8] = addBitmapRo(Bitmap.createBitmap(b, 0*b.getWidth()/2, 0,b.getWidth()/2, b.getHeight(), null, true));
			mTex_Arrow[9] = addBitmapRo(Bitmap.createBitmap(b, 1*b.getWidth()/2, 0,b.getWidth()/2, b.getHeight(), null, true));
			
			mTex_BG = new SimplePlane[1];
			for (int i = 0; i < mTex_BG.length; i++)
				mTex_BG[i] = add("bg/" + i + ".png");
//			b.recycle();
//			b= LoadImgfromAsset("bg/f0.png");
			mTex_Fort = new SimplePlane[1];
			mTex_Fort[0] = add("bg/f0.png");
//			for (int i = 0; i < mTex_Fort.length; i++)
//				mTex_Fort[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/4, 0,b.getWidth()/4, b.getHeight(), null, true));
			
			b.recycle();
			mTex_Bow = new SimplePlane[10];
			b= LoadImgfromAsset("bow/0.png");
			for (int i = 0; i < 8; i++)
				mTex_Bow[i] = addBitmapRo(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2
						,b.getWidth()/4, b.getHeight()/2, null, true));
			
			
			b.recycle();
			b= LoadImgfromAsset("bow/1.png");
			mTex_Bow[8] = addBitmapRo(Bitmap.createBitmap(b, 0*b.getWidth()/2, 0,b.getWidth()/2, b.getHeight(), null, true));
			mTex_Bow[9] = addBitmapRo(Bitmap.createBitmap(b, 1*b.getWidth()/2, 0,b.getWidth()/2, b.getHeight(), null, true));
			
//			mTex_Bow = new SimplePlane[10];
//			for (int i = 0; i < mTex_Bow.length; i++)
//				mTex_Bow[i] = addRotate("bow/" + i + ".png");1
			b.recycle();
			b = LoadImgfromAsset("power/0.png");
			mTex_Power= new SimplePlane[3][];
			for(int i=0,k=0;i<mTex_Power.length && k <8;i++){
				mTex_Power[i]= new SimplePlane[3];
				for(int j=0;j<mTex_Power[i].length&& k <8;j++){
					mTex_Power[i][j] = addBitmap(Bitmap.createBitmap(b, k*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
					k++;
				}
			}
			mTex_Power[2][2] = add("power/1.png");
			
			mTex_FireBall = new SimplePlane[1];
			for(int i=0;i<mTex_FireBall.length;i++)
				mTex_FireBall[i] = add("FireBlast/z"+i+".png");
			
			b.recycle();
			mTex_FirePower = new SimplePlane[16];
			b= LoadImgfromAsset("FireBlast/0.png");
			for(int i=0;i<mTex_FirePower.length;i++)
				mTex_FirePower[i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/4
						,b.getWidth()/4, b.getHeight()/4, null, true));
			
			
			
			mTex_Play = add("play.png");
			
			mFont = new Font(this);
			
			opppLoad();
			
//			gameReset();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	void gameReset() {
		for(int i=0;i<mArrow.length;i++)
			mArrow[i].set(-10, 0, 0);
		
		for (int i = 0; i < mOpp.length; i++)
			mOpp[i].set(0, 0, 0, -1,0);
		
		mBow.radian =0;
		Wave = 0;
		for(int i=0;i<14;i++){
			OppCount[i] = 0;
		}
		{
			int no = 0;
			for (int i = 0; i < 15; i++) {
				no += i;
				if (no >= mStage) {
					no =i;
					break;
				}
			}
			totalOpp = RemainOpp = 0;
			for(int i=0;i<no;i++){
				OppCount[i] = (int)(mStage*5f/no);
				totalOpp+=OppCount[i];
				RemainOpp+=OppCount[i];
			}
		}
		for(int i=0;i<mPowerFire.length;i++)
			mPowerFire[i].set(-10, -10, 1000);
		
		for(int i=0;i<mFire.length;i++)
			mFire[i].set(-10, -10, 0, 0,0);
		
		mFreez.set(-10, -10, 1000);
		mSparkal.set(-10, -10, 1000);
		mCover.set(0, 0, 0);
		mPlayer.set();
		int total =0;
		for(int i=0;total<mPlayer.TKill;i++){
			total +=(i)*100;
			mLevel = i; 
			if(total>mPlayer.TKill){
				break;
			}
		}
		lifeBar = .85f;
		root.istap = false;
		mStart.load();
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
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
			if(M.GameScreen != M.GAMEPLAY)
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
//		/*AdHouse*/
//		if (mStart.adHouse != null) {
//			if (M.GameScreen == M.GAMEADD) {
//				int inv = mStart.adHouse.getVisibility();
//				if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
//			} else {
//				int inv = mStart.adHouse.getVisibility();
//				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
//				}
//			}
//		}
//		/*AdHouse*/
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

		// Log.d("----------------=>  "+x,y+"   -----------    "+z);
	}

	public void onShake(float force) {
	}

	SimplePlane add(String ID) {
		SimplePlane SP = null;
//		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new SimplePlane();
			SP.loadBitmapString(ID);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
	}

	SimplePlane addBitmap(Bitmap b) {
		SimplePlane SP = null;
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxX),
					(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
	}

	SimplePlane addBitmapRo(Bitmap b) {
		SimplePlane SP = null;
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxY),
					(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
	}
	
	SimplePlane addRotate(String ID) {
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxY),
					(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
	}

	static Bitmap LoadImgfromAsset(String ID) {
		try {
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
		} catch (Exception e) {
			System.out.println(ID + " ~~~~~~~~~~~~~~  " + e.getMessage());
			return null;
		}
	}

	Bitmap resizeImg(Bitmap bitmapOrg, int newWidth, int newHeight) {
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
				height, matrix, true);
		// Log.d("resizeImg========","newWidth ["+newWidth+"] newHeight ["+newHeight+"]");
		return resizedBitmap;
	}

	Bitmap FlipHorizontal(Bitmap bitmapOrg) {
		Matrix matrix = new Matrix();
		matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
				bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}
	
	void opppLoad() {
		System.gc();
		mTex_Opp = new SimplePlane[15][3][];

		for (int op = 0; op < mTex_Opp.length; op++) {
			switch (op) {
			case 0: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][0] = new SimplePlane[6];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, ((i+6)%4)*b.getWidth()/4, ((i+6)/4)*b.getHeight()/4
							,b.getWidth()/4, b.getHeight()/4, null, true));

				mTex_Opp[op][1] = new SimplePlane[6];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/4
							,b.getWidth()/4, b.getHeight()/4, null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b, 0, 3*b.getHeight()/4
						,b.getWidth()/4, b.getHeight()/4, null, true));
			}
				break;

			case 1: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/1.png");
				mTex_Opp[op][0] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, (i)*b.getWidth()/4, 0
							,b.getWidth()/4, b.getHeight(), null, true));

				Bitmap b1= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][1] = new SimplePlane[8];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b1, (i%4)*b1.getWidth()/4, (i/4)*b1.getHeight()/2
							,b1.getWidth()/4, b1.getHeight()/2, null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b, (3)*b.getWidth()/4, 0
						,b.getWidth()/4, b.getHeight(), null, true));
			}
				break;
			case 2: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][0] = new SimplePlane[4];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, (i)*b.getWidth()/4, 0
							,b.getWidth()/4, b.getHeight(), null, true));
				
				Bitmap b2= LoadImgfromAsset("opp/"+op+"/2.png");
				mTex_Opp[op][1] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b2, i*b2.getWidth()/4, 0
							,b2.getWidth()/4, b2.getHeight(), null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b2, 3*b2.getWidth()/4, 0
						,b2.getWidth()/4, b2.getHeight(), null, true));
				
				
				Bitmap b1= LoadImgfromAsset("opp/"+op+"/1.png");
				mTex_WalSpark = new SimplePlane[8];
				for(int i=0;i<mTex_WalSpark.length;i++){
					mTex_WalSpark[i] = addBitmap(Bitmap.createBitmap(b1, (i%8)*b1.getWidth()/8, 0
							,b1.getWidth()/8, b1.getHeight(), null, true));
				}
			}

				break;
			case 3: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][0] = new SimplePlane[5];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, ((i+3)%4)*b.getWidth()/4, ((i+3)/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][1] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = add("opp/"+op+"/freeze.png");
			}
				break;
			case 4: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][0] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, ((i+4)%4)*b.getWidth()/4, ((i+4)/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][1] = new SimplePlane[4];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/4, b.getHeight()/2
						,b.getWidth()/4, b.getHeight()/2, null, true));
			}

				break;
			case 5: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][0] = new SimplePlane[4];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, ((i+3)%4)*b.getWidth()/4, ((i+3)/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][1] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/4, b.getHeight()/2
						,b.getWidth()/4, b.getHeight()/2, null, true));
			}

				break;
			case 6: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][0] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, ((i+3)%4)*b.getWidth()/4, ((i+3)/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));
				mTex_Opp[op][1] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/4, b.getHeight()/2
						,b.getWidth()/4, b.getHeight()/2, null, true));
			}
				break;
			case 7: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				Bitmap b1= LoadImgfromAsset("opp/"+op+"/1.png");
				mTex_Opp[op][0] = new SimplePlane[2];
				mTex_Opp[op][0][0] = addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/4, 0,b.getWidth()/4, b.getHeight(), null, true));
				mTex_Opp[op][0][1] = addBitmapRo(Bitmap.createBitmap(b1, b1.getWidth()/2, 0,b1.getWidth()/2, b1.getHeight(), null, true));

				mTex_Opp[op][1] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/4, 0,b.getWidth()/4, b.getHeight(), null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b1, 0, 0,b1.getWidth()/2, b1.getHeight(), null, true));
			}

				break;
			case 8: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][0] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, ((i+3)%4)*b.getWidth()/4, ((i+3)/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][1] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/4, b.getHeight()/2
						,b.getWidth()/4, b.getHeight()/2, null, true));
			}

				break;
			case 9: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][0] = new SimplePlane[1];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/4, 0
							,b.getWidth()/4, b.getHeight(), null, true));

				mTex_Opp[op][1] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/4, 0
							,b.getWidth()/4, b.getHeight(), null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = add("opp/"+op+"/freeze.png");
			}

				break;
			case 10: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/1.png");
				mTex_Opp[op][0] = new SimplePlane[2];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/2, 0
							,b.getWidth()/2, b.getHeight(), null, true));

				Bitmap b1= LoadImgfromAsset("opp/"+op+"/0.png");
				
				mTex_Opp[op][1] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b1, i*b1.getWidth()/4, 0
							,b1.getWidth()/4, b1.getHeight(), null, true));

				mTex_Opp[op][2] = new SimplePlane[2];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b1, 3*b1.getWidth()/4, 0
						,b1.getWidth()/4, b1.getHeight(), null, true));
				mTex_Opp[op][2][1] = add("opp/"+op+"/f0.png");
			}

				break;
			case 11: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/1.png");
				mTex_Opp[op][0] = new SimplePlane[2];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmapRo(Bitmap.createBitmap(b, i*b.getWidth()/2, 0
							,b.getWidth()/2, b.getHeight(), null, true));

				Bitmap b1= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][1] = new SimplePlane[2];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmapRo(Bitmap.createBitmap(b1, (i+2)*b1.getWidth()/4, 0
							,b1.getWidth()/4, b1.getHeight(), null, true));

				mTex_Opp[op][2] = new SimplePlane[2];
				for (int i = 0; i < mTex_Opp[op][2].length; i++)
					mTex_Opp[op][2][i] = addBitmap(Bitmap.createBitmap(b1, (i)*b1.getWidth()/4, 0
							,b1.getWidth()/4, b1.getHeight(), null, true));
				
				
//				mTex_Opp[op][2][0] = add("opp/"+op+"/Roler.png");
//				mTex_Opp[op][2][1] = add("opp/"+op+"/Roler_freeze.png");
				
				
				
				Bitmap b2= LoadImgfromAsset("CannonAni/gola/0.png");
				mTex_Gola = new SimplePlane[2][];
				mTex_Gola[0] = new SimplePlane[2];
				for(int i=0;i<mTex_Gola[0].length;i++)
					mTex_Gola[0][i] = addBitmap(Bitmap.createBitmap(b2, (i)*b2.getWidth()/2, 0
							,b2.getWidth()/2, b2.getHeight(), null, true));
				
				Bitmap b3= LoadImgfromAsset("CannonAni/gola/1.png");
				mTex_Gola[1] = new SimplePlane[4];
				for(int i=0;i<mTex_Gola[1].length;i++)
					mTex_Gola[1][i]= addBitmap(Bitmap.createBitmap(b3, (i)*b3.getWidth()/4, 0
							,b3.getWidth()/4, b3.getHeight(), null, true));
				
				Bitmap b4= LoadImgfromAsset("CannonAni/Fire/0.png");
				
				mTex_Fire = new SimplePlane[8];
				for(int i=0;i<mTex_Fire.length;i++)
					mTex_Fire[i] = addBitmap(Bitmap.createBitmap(b4, (i%4)*b4.getWidth()/4, (i/4)*b4.getHeight()/2
							,b4.getWidth()/4, b4.getHeight()/2, null, true));
			}

				break;
			case 12: {
				
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][0] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, ((i+4)%4)*b.getWidth()/4, ((i+4)/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));
				
				mTex_Opp[op][1] = new SimplePlane[4];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/4, b.getHeight()/2
						,b.getWidth()/4, b.getHeight()/2, null, true));
			}

				break;
			case 13: {
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][0] = new SimplePlane[2];
				mTex_Opp[op][0][0] = addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/4, b.getHeight()/2
						,b.getWidth()/4, b.getHeight()/2, null, true));
				mTex_Opp[op][0][1] = add("opp/"+op+"/1.png");

				mTex_Opp[op][1] = new SimplePlane[6];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b, 3*b.getWidth()/4, b.getHeight()/2
						,b.getWidth()/4, b.getHeight()/2, null, true));
			}

				break;
			case 14: {
				
				Bitmap b= LoadImgfromAsset("opp/"+op+"/0.png");
				mTex_Opp[op][0] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][0].length; i++)
					mTex_Opp[op][0][i] = addBitmap(Bitmap.createBitmap(b, ((i+3)%4)*b.getWidth()/4, ((i+3)/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][1] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp[op][1].length; i++)
					mTex_Opp[op][1][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp[op][2] = new SimplePlane[1];
				mTex_Opp[op][2][0] = addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/4, b.getHeight()/2
						,b.getWidth()/4, b.getHeight()/2, null, true));
				
				b.recycle();
				mTex_Opp14 = new SimplePlane[3][];
				b= LoadImgfromAsset("opp/"+op+"/1.png");
				mTex_Opp14[0] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp14[0].length; i++)
					mTex_Opp14[0][i] = addBitmap(Bitmap.createBitmap(b, ((i+3)%4)*b.getWidth()/4, ((i+3)/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp14[1] = new SimplePlane[3];
				for (int i = 0; i < mTex_Opp14[1].length; i++)
					mTex_Opp14[1][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4, (i/4)*b.getHeight()/2
							,b.getWidth()/4, b.getHeight()/2, null, true));

				mTex_Opp14[2] = new SimplePlane[1];
				mTex_Opp14[2][0] = addBitmap(Bitmap.createBitmap(b, 2*b.getWidth()/4, b.getHeight()/2
						,b.getWidth()/4, b.getHeight()/2, null, true));
				
			}
				break;
			}

		}
		System.gc();
	}
}
