package com.hututu.game.cricket.premier.league;

import java.text.DecimalFormat;

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

	SimplePlane mTex_Logo, mTex_Ground, mTex_Popup, mTex_Yelo64, mTex_Blu128, mTex_Whit64x64;
	SimplePlane mTex_BPopup, mTex_Spark, mTex_Yelo128, mTex_Whit128,  mTex_Trophy, mTex_Rank;
	SimplePlane mTex_Bat32, mTex_Bal32, mTex_Yel32, mTex_Whit32, /*mTex_Yelo160,*/mTex_Yelo64x22;
	SimplePlane mTex_Tran128, mTex_Tran64, mTex_Bail, mTex_Whit64, mTex_Shadow/*, mTex_Whit160*/;
	SimplePlane mTex_Yelo160x22, mTex_Whit160x22, mTex_Yelo40x22, mTex_Whit40x22, mTex_Red64;
	SimplePlane mTex_White200x50, mTex_Red256, mTex_Whit64x22, mTex_Whit256, mTex_Pup512x256;
	SimplePlane mTex_RedPop256, mTex_BSedo, mTex_Cup,mTex_Yelo256, mTex_Purchase, mTex_Bat_S;
	SimplePlane mTex_Splash,mTex_Sfont,mTex_Duck;
	SimplePlane[] mTex_Face, mTex_4Like, mTex_Dot, mTex_Icon, mTex_Stump, mTex_Won, mTex_Run;
	SimplePlane[] mTex_Ampaire, mTex_Ball, mTex_ShotBtn, mTex_Loose, mTex_BatClip,mTex_AllBg;
	SimplePlane[] mTex_Clip, mTex_BolClip, mTex_Runner,mTex_Sing;
	
	SimplePlane[][]  mTex_Batman[],mTex_Baler,mTex_ComnBat,mTex_Filder,mTex_FAnim,mTex_BAnim;
	
	Font mFont;
	DecimalFormat df = new DecimalFormat();
	String ver = "1.0";
	
	
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	boolean TeamSelect[] = new boolean[2];
	boolean isFIxed = false;
	byte popUp = 0;
	byte Pos[][] = new byte[2][6];
	byte Over[] = new byte[6];

	int round[] =new int[2];
	int Hcol[] ={0,1};
	int Scol[] ={0,1};
	int mGold = 54328;
	int tS6[] = new int[2],tS4[] = new int[2];
	int Year[] = new int[2];
	int Serise = 1;
	int Formove =0;
	int type = 0;//T20 , T10 or Series
	int Duck = 10000;
	int Hint =0;
	Team mBatTeam[],mBalTeam[]; 
	TeamInfo mTeamInfo[][]; 
	Ball mBall[];
	Gulianim mGuli[];
	Batsman mBatsman = new Batsman();
	InApp mInApp;
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
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
			mInApp = new InApp();
			mInApp.onCreate();
			
			mTex_Spark = add("camera-flash.png");
			mTex_AllBg = new SimplePlane[2];
			mTex_AllBg[0] = add("UI/ui_bg.jpg");
			mTex_AllBg[1] = add("UI/ui_bg2.jpg");
			mTex_Splash = add("splash.jpg");
			mTex_Sfont = add("splash_font.png");
			mTex_Duck = add("duck.png");
			
			mTex_Logo = add("hututugames.png");
			mTex_Ground = add("ground/ground.jpg");
			mTex_Bail = add("ground/bails.png");
			mTex_Bat_S = add("bat_s.png");
			
			mTex_Tran64 = add("UI/po_pup_4.png");
//			mTex_Yelo160 = add("UI/Box_160_0.png");
//			mTex_Whit160 = add("UI/Box_160_1.png");
			
			
			mTex_RedPop256 = add("UI/po_pup_0.png");
			mTex_Red256 = add("UI/red_box0.png");
			
			mTex_Pup512x256 = add("UI/po_pup_6.png");
			
			
			mTex_Yelo128 = add("UI/Box_128_0.png");
			mTex_Whit128 = add("UI/Box_128_1.png");
			
			
//			mTex_Yelo316 = add("UI/Box_316_0.png");
			mTex_BPopup = add("UI/po_pup_5.png");
			mTex_Shadow = add("UI/shadow_0.png");
			mTex_Popup = add("UI/po_pup_2.png");
			mTex_Blu128 = add("UI/continue_box.png");
			mTex_Whit64x64 = add("UI/score_box1.png");
			mTex_Yelo160x22 = add("UI/player_box0.png");
			mTex_Whit160x22 = add("UI/player_box1.png");
			
			
			mTex_White200x50 = add("UI/score_box2.png");
			
			mTex_Rank = add("UI/rank.png");
			
			Bitmap b		= GameRenderer.LoadImgfromAsset("won_lost_sprite.png");
			mTex_Loose = new SimplePlane[2];
			mTex_Won = new SimplePlane[2];
			mTex_Loose[0] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*0, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_Loose[1] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*1, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_Won[0] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*2, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_Won[1] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*3, 0, b.getWidth() / 4, b.getHeight(), null, true));
			
			b.recycle();
			mTex_4Like = new SimplePlane[5];
			b = GameRenderer.LoadImgfromAsset("UI/like_sprite.png");
			for(int i=0;i<4;i++)
				mTex_4Like[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, i
						* b.getWidth() / 4, 0, b.getWidth() / 4, b.getHeight(), null, true));
				
			
			b.recycle();
			mTex_Clip = new SimplePlane[7];
			b	= GameRenderer.LoadImgfromAsset("clipart_sprite.png");
			for(int i =0;i<4;i++)
				mTex_Clip[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*i, 0, b.getWidth() / 4, b.getHeight(), null, true));
			
			b.recycle();
			b	= GameRenderer.LoadImgfromAsset("UI/boss_clipart_sprite.png");
			mTex_Clip[4] 	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*0, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_4Like[4]	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*1, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_Purchase	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*2, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_Trophy		= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*3, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_Clip[5] = add("UI/po_pup_1.png");
			b.recycle();
			b	= GameRenderer.LoadImgfromAsset("UI/boss_prv_sprite.png");
			mTex_Clip[6] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*0, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_Face = new SimplePlane[2];
			mTex_Face[0] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*1, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_Face[1] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*2, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_Tran128 = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*3, 0, b.getWidth() / 4, b.getHeight(), null, true));
			
			
			b.recycle();
			b	= GameRenderer.LoadImgfromAsset("UI/Box_64_0_sprite.png");
			mTex_Yelo64 	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*0, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Whit64 	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*1, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Red64 		= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*2, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Yelo40x22 	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*3, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Whit40x22 	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*4, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Whit64x22 	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*5, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Yelo64x22 	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*6, 0, b.getWidth() / 8, b.getHeight(), null, true));
			
			
			b.recycle();
			b	= GameRenderer.LoadImgfromAsset("UI/Box_256_0_sprite.png");
			mTex_Yelo256 = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/2)*0, 0, b.getWidth() / 2, b.getHeight(), null, true));
			mTex_Whit256 = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/2)*1, 0, b.getWidth() / 2, b.getHeight(), null, true));
			
			b.recycle();
			b	= GameRenderer.LoadImgfromAsset("Bowler1.png");
			mTex_BolClip = new SimplePlane[2];
			mTex_BolClip[0] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/2)*0, 0, b.getWidth() / 2, b.getHeight(), null, true));
			mTex_BolClip[1] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/2)*1, 0, b.getWidth() / 2, b.getHeight(), null, true));
			
			b.recycle();
			b	= GameRenderer.LoadImgfromAsset("runner.png");
			mTex_Runner = new SimplePlane[2];
			mTex_Runner[0] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/2)*0, 0, b.getWidth() / 2, b.getHeight(), null, true));
			mTex_Runner[1] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/2)*1, 0, b.getWidth() / 2, b.getHeight(), null, true));
			b.recycle();
			b	= GameRenderer.LoadImgfromAsset("sign_in.png");
			mTex_Sing = new SimplePlane[3];
			mTex_Sing[0] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*0, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_Sing[1] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/4)*1, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_Sing[2] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/2)*1, 0, b.getWidth() / 2, b.getHeight(), null, true));
			
			
			b.recycle();
			b	= GameRenderer.LoadImgfromAsset("batesman.png");
			mTex_BatClip = new SimplePlane[2];
			mTex_BatClip[0] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/2)*0, 0, b.getWidth() / 2, b.getHeight(), null, true));
			mTex_BatClip[1] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/2)*1, 0, b.getWidth() / 2, b.getHeight(), null, true));
			
			
			b.recycle();
			b		= GameRenderer.LoadImgfromAsset("Bat/comman1_sprite.png");
//			Bitmap b1		= GameRenderer.LoadImgfromAsset("Bat/comman1.png");
			
			mTex_ComnBat = new SimplePlane[2][2];
			mTex_ComnBat[0][0] = GameRenderer.addBitmap(Bitmap.createBitmap(b,0*b.getWidth() / 4, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_ComnBat[0][1] = GameRenderer.addBitmap(Bitmap.createBitmap(b,1*b.getWidth() / 4, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_ComnBat[1][0] = GameRenderer.addBitmap(Bitmap.createBitmap(b,2*b.getWidth() / 4, 0, b.getWidth() / 4, b.getHeight(), null, true));
			mTex_ComnBat[1][1] = GameRenderer.addBitmap(Bitmap.createBitmap(b,3*b.getWidth() / 4, 0, b.getWidth() / 4, b.getHeight(), null, true));
			b.recycle();
//			b1.recycle();
			mTex_Batman = new SimplePlane[5][][];
			mTex_Batman[0] = new SimplePlane[2][];
			mTex_Batman[0][0] = new SimplePlane[7];
			mTex_Batman[0][1] = new SimplePlane[7];
			b		= GameRenderer.LoadImgfromAsset("Bat/0/body.png");
			Bitmap b1		= GameRenderer.LoadImgfromAsset("Bat/0/clothes.png");
			for(int i =0;i<mTex_Batman[0][0].length;i++){
				mTex_Batman[0][0][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, 
						(i%4) * b.getWidth() / 4, (i/4)*(b.getHeight()/2), b.getWidth() / 4, b.getHeight()/2, null, true));
				mTex_Batman[0][1][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b1, 
						(i%4) * b1.getWidth() / 4, (i/4)*(b1.getHeight()/2), b1.getWidth() / 4, b1.getHeight()/2, null, true));
			}
			
			mTex_Batman[1] = new SimplePlane[2][];
			mTex_Batman[1][0] = new SimplePlane[8];
			mTex_Batman[1][1] = new SimplePlane[8];
			b		= GameRenderer.LoadImgfromAsset("Bat/1/body.png");
			b1		= GameRenderer.LoadImgfromAsset("Bat/1/clothes.png");
			for(int i =0;i<mTex_Batman[1][0].length;i++){
				mTex_Batman[1][0][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, 
						(i%4) * b.getWidth() / 4, (i/4)*(b.getHeight()/2), b.getWidth() / 4, b.getHeight()/2, null, true));
				mTex_Batman[1][1][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b1, 
						(i%4) * b1.getWidth() / 4, (i/4)*(b1.getHeight()/2), b1.getWidth() / 4, b1.getHeight()/2, null, true));
			}
			mTex_Batman[2] = new SimplePlane[2][];
			mTex_Batman[2][0] = new SimplePlane[7];
			mTex_Batman[2][1] = new SimplePlane[7];
			b		= GameRenderer.LoadImgfromAsset("Bat/2/body.png");
			b1		= GameRenderer.LoadImgfromAsset("Bat/2/clothes.png");
			for(int i =0;i<mTex_Batman[2][0].length;i++){
				mTex_Batman[2][0][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, 
						(i%4) * b.getWidth() / 4, (i/4)*(b.getHeight()/2), b.getWidth() / 4, b.getHeight()/2, null, true));
				mTex_Batman[2][1][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b1, 
						(i%4) * b1.getWidth() / 4, (i/4)*(b1.getHeight()/2), b1.getWidth() / 4, b1.getHeight()/2, null, true));
			}
			
			mTex_Batman[3] = new SimplePlane[2][];
			mTex_Batman[3][0] = new SimplePlane[7];
			mTex_Batman[3][1] = new SimplePlane[7];
			b		= GameRenderer.LoadImgfromAsset("Bat/3/body.png");
			b1		= GameRenderer.LoadImgfromAsset("Bat/3/clothes.png");
			for(int i =0;i<mTex_Batman[3][0].length;i++){
				mTex_Batman[3][0][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, 
						(i%4) * b.getWidth() / 4, (i/4)*(b.getHeight()/2), b.getWidth() / 4, b.getHeight()/2, null, true));
				mTex_Batman[3][1][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b1, 
						(i%4) * b1.getWidth() / 4, (i/4)*(b1.getHeight()/2), b1.getWidth() / 4, b1.getHeight()/2, null, true));
			}
			mTex_Batman[4] = new SimplePlane[2][];
			mTex_Batman[4][0] = new SimplePlane[7];
			mTex_Batman[4][1] = new SimplePlane[7];
			b		= GameRenderer.LoadImgfromAsset("Bat/4/body.png");
			b1		= GameRenderer.LoadImgfromAsset("Bat/4/clothes.png");
			for(int i =0;i<mTex_Batman[4][0].length;i++){
				mTex_Batman[4][0][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, 
						(i%4) * b.getWidth() / 4, (i/4)*(b.getHeight()/2), b.getWidth() / 4, b.getHeight()/2, null, true));
				mTex_Batman[4][1][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b1, 
						(i%4) * b1.getWidth() / 4, (i/4)*(b1.getHeight()/2), b1.getWidth() / 4, b1.getHeight()/2, null, true));
			}
			
			b		= GameRenderer.LoadImgfromAsset("ground/stump_sprite.png");
			mTex_Stump = new SimplePlane[2];
			mTex_Stump[0] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/2)*0, 0, b.getWidth() / 2, b.getHeight(), null, true));
			mTex_Stump[1] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/2)*1, 0, b.getWidth() / 2, b.getHeight(), null, true));
			
			mTex_ShotBtn = new SimplePlane[2];
			mTex_ShotBtn[0] = add("ground/Gp_button0.png");
			mTex_ShotBtn[1] = addBitmap(FlipHorizontal(mTex_ShotBtn[0].getBitmap()));
			
			mTex_Ampaire = new SimplePlane[7];
			b	= GameRenderer.LoadImgfromAsset("ground/empier.png");
			for(int i =0;i<mTex_Ampaire.length;i++){
				mTex_Ampaire[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, 
						i * b.getWidth() / 8, 0, b.getWidth() / 8, b.getHeight(), null, true));
			}
			mTex_Ball = new SimplePlane[8];
			b	= GameRenderer.LoadImgfromAsset("ground/ball.png");
			for(int i =0;i<mTex_Ball.length;i++){
				mTex_Ball[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, 
						i * b.getWidth() / 8, 0, b.getWidth() / 8, b.getHeight(), null, true));
			}
			
			mTex_Run = new SimplePlane[6];
			b = GameRenderer.LoadImgfromAsset("ground/run_number.png");
			for (int i = 0; i < mTex_Run.length; i++) {
				mTex_Run[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, i
						* b.getWidth() / 8, 0, (i == 5 ? 2 : 1) * b.getWidth()
						/ 8, b.getHeight(), null, true));
			}
			mTex_Baler = new SimplePlane[2][];
			for(int i =0;i<mTex_Baler.length;i++){
				mTex_Baler[i] = new SimplePlane[32];
				b = GameRenderer.LoadImgfromAsset("Ball/"+i+".png");
				for(int j=0;j<mTex_Baler[i].length;j++){
					mTex_Baler[i][j] = GameRenderer.addBitmap(Bitmap.createBitmap(b, 
							(j%16) * b.getWidth() / 16, (j/16)*(b.getHeight()/2), b.getWidth() / 16, b.getHeight()/2, null, true));
					
				}
			}
			
			b = GameRenderer.LoadImgfromAsset("Ball/2sprite.png");
			mTex_Filder = new SimplePlane[2][];
			for(int i =0;i<mTex_Filder.length;i++){
				mTex_Filder[i] = new SimplePlane[5];
				for(int j=0;j<4;j++){
					mTex_Filder[i][j] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(i*4+j) * b.getWidth() / 8, 0, b.getWidth() / 8, b.getHeight(), null, true));
				}
				mTex_Filder[i][4] = addBitmap(FlipHorizontal(mTex_Filder[i][1].getBitmap())); 
			}
			
			mTex_FAnim = new SimplePlane[2][];
			b = GameRenderer.LoadImgfromAsset("1sprite.png");
			for(int i =0;i<mTex_FAnim.length;i++){
				mTex_FAnim[i] = new SimplePlane[3];
				for(int j=0;j<mTex_FAnim[i].length;j++){
					mTex_FAnim[i][j] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(i*3+j) * b.getWidth() / 8, 0, b.getWidth() / 8, b.getHeight(), null, true));
				}
			}
			b = GameRenderer.LoadImgfromAsset("0sprite.png");
			b1 = GameRenderer.LoadImgfromAsset("bowlerAni1_sprite.png");
			mTex_BAnim = new SimplePlane[2][];
			for(int i =0;i<mTex_BAnim.length;i++){
				mTex_BAnim[i] = new SimplePlane[6];
//				b = GameRenderer.LoadImgfromAsset("bowler_ani"+i+".png");
				for(int j=0;j<4;j++){
					mTex_BAnim[i][j] = GameRenderer.addBitmap(Bitmap.createBitmap(b,(i*4+j) * (b.getWidth() / 8), 0, b.getWidth() / 8, b.getHeight(), null, true));
				}
				mTex_BAnim[i][4] = GameRenderer.addBitmap(Bitmap.createBitmap(b1,i* 2 * b1.getWidth() / 4, 0, b1.getWidth() / 4, b1.getHeight(), null, true));
				mTex_BAnim[i][5] = GameRenderer.addBitmap(Bitmap.createBitmap(b1,(i* 2+1) * b1.getWidth() / 4, 0, b1.getWidth() / 4, b1.getHeight(), null, true));
			}
			b.recycle();
			mTex_Dot = new SimplePlane[2];
			b = GameRenderer.LoadImgfromAsset("UI/ball_sadow_sprite.png");
			mTex_BSedo 	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*0, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Yel32 	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*1, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Whit32	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*2, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Bal32 	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*3, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Bat32 	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*4, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Dot[0]	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*5, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Dot[1]	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*6, 0, b.getWidth() / 8, b.getHeight(), null, true));
			mTex_Cup	= GameRenderer.addBitmap(Bitmap.createBitmap(b,(b.getWidth()/8)*7, 0, b.getWidth() / 8, b.getHeight(), null, true));
			
			
//			mTex_BSedo = add("ground/ball_sadow.png");
//			mTex_Yel32 = add("UI/Box_32_0.png");
//			mTex_Whit32 = add("UI/Box_32_1.png");
//			mTex_Bal32 = add("UI/Box_32_ball.png");
//			mTex_Bat32 = add("UI/Box_32_bat.png");
//			mTex_Dot = new SimplePlane[2];
//			for(int i=0;i<mTex_Dot.length;i++)
//				mTex_Dot[i] = add("UI/dot"+i+".png");
//			mTex_Cup = add("UI/trophy_icon.png");
			b.recycle();
			mTex_Icon = new SimplePlane[8];
			b = GameRenderer.LoadImgfromAsset("UI/button_strip.png");
			for (int i = 0; i < 7; i++) {
				mTex_Icon[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, 
						i* b.getWidth() / 8, 0,b.getWidth()/ 8, b.getHeight(), null, true));
			}
			mTex_Icon[7] = addBitmap(FlipHorizontal(mTex_Icon[6].getBitmap()));
			
			mBatTeam = new Team[2];
			mBatTeam[0] = new Team();
			mBatTeam[1] = new Team();
			
			mBalTeam = new Team[2];
			mBalTeam[0] = new Team();
			mBalTeam[1] = new Team();
			
			mBall = new Ball[2];
			mBall[0] = new Ball();
			mBall[1] = new Ball();
			
			mTeamInfo  = new TeamInfo[2][6];
			for(int i =0;i<mTeamInfo.length;i++){
				for(int j =0;j<mTeamInfo[i].length;j++){
					mTeamInfo[i][j] = new TeamInfo();
				}
			}
			mFont = new Font();
			df.setMaximumFractionDigits(2);
			Year[1] = Year[0] = 0;
			mGuli = new Gulianim[2];
			mGuli[0] = new Gulianim();
			mGuli[1] = new Gulianim();
			ver = GameRenderer.mStart.getPackageManager().getPackageInfo(GameRenderer.mStart.getPackageName(), 0).versionName;
		}catch(Exception e){}
		
	}
	
	void gameReset(int over,int bat,int ball)
	{
		mGuli[0].set(-.01f, -.59f);
		mGuli[1].set(0.01f, -.59f);
		M.tBall = over*6;
//		M.tBall = 6;
		mBall[Serise].set(10.05f,  0.34f,0);
		mBatTeam[Serise].set(bat,0,1);
		mBalTeam[Serise].set(ball,6,7);
		mBatsman.set(0,100);
		for(int i=0;i<Over.length;i++){
			Over[i] = -1;
		}
		type =0;
		mBalTeam[Serise].Run = 80+M.mRand.nextInt(100);
		Duck = 10000;
		popUp =3;
		Hint++;
		Hint%=8;
		mStart.load();
	}
	void reset(){
		for(int i=0;i<mTeamInfo[Serise].length;i++){
			mTeamInfo[Serise][i].reset();
		}
		tS6[Serise] = 0;
		tS4[Serise] = 0;
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
			if(M.GameScreen == M.LOADING && !addFree)
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
	static SimplePlane add (String ID)
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
	static SimplePlane addBitmap (Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}

	static Bitmap LoadImgfromAsset(String ID) {
		try {
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
		} catch (Exception e) {
			System.out.println(ID + "  ~~~~~~~  " + e.getMessage());
			return null;
		}
	}
	static Bitmap resizeImg(Bitmap bitmapOrg,int newWidth,int newHeight)
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
	static Bitmap FlipHorizontal(Bitmap bitmapOrg)
	{
		Matrix matrix = new Matrix();
		matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}
}
