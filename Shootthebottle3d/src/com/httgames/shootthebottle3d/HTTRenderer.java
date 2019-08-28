package com.httgames.shootthebottle3d;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import rajawali.Object3D;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.textures.AlphaMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.parser.Loader3DSMax;
import rajawali.primitives.Plane;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;

public class HTTRenderer extends RajawaliRenderer  implements OnTouchListener {
	
	static Start mStart;
	static Context mContext;
	Plane oLogo;
	Plane o2D_BG;
	Plane o2D_50Bottle;
	Plane o2D_100Bottle;
	Plane o2D_20Bottle;
	Plane o2D_Arcade;
	Plane o2D_20Sec;
	Plane o2D_40Sec;
	Plane o2D_60Sec;
	Plane o2D_TimeB;
	Plane o2D_Achiev;
	Plane o2D_BScore;
	Plane o2D_Gameover;
	Plane o2D_Gamepaused;
	Plane o2D_home;
	Plane o2D_Leader;
	Plane o2D_More;
	Plane o2D_Play;
	Plane o2D_Score;
	Plane o2D_Splash;
	Plane o2D_Start;
	
	Object3D m3D_Bottle[];
	Object3D m3D_Bottom[];
	Object3D m3D_Top[];
	Object3D m3D_Chair[];
	Object3D m3D_BG;
	Object3D m3D_Floor;
	Object3D m3D_Table;
	Object3D m3D_Ball;
	
	
	Paint mPaint;
	
	Plane oTime;
	Bitmap mTimeBitmap;
	Canvas mTimeCanvas;
	AlphaMapTexture mTimeTexture;
	
	
	Plane oScore;
	Bitmap mScoreBitmap;
	Canvas mScoreCanvas;
	AlphaMapTexture mScoreTexture;
	
	Loader3DSMax objParser;
	Group root;
	
	Typeface tf;
	Particle mParticle[][];
	
	Bottle mBottle[];
	Ball mBall[];
	
	boolean mIsArcad;
	boolean mIsGameStart;
	boolean addFree;
	boolean SingUpadate;
	boolean mAchiUnlock[] = new boolean[5];
	long mTimeScore;
	int NoBottle;
	
	long mTScore[] = new long[3];
	int mBScore[]= new int[3];
	int mMode = 0;
	
	public HTTRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mContext =context;
		mStart = (Start)context;
		root = new Group(this);
		// TODO Auto-generated constructor stub
	}
	protected void initScene() {
		try {
			mSurfaceView.setOnTouchListener(this);
			
			tf = Typeface.createFromAsset(mContext.getAssets(),"JingJing.ttf");
			
			oLogo = addPlan("logo.png");
			oLogo.setRotation(180, 0, 180);
			oLogo.setPosition(0,0, 0);
			oLogo.setScale(1f);
			oLogo.setTransparent(true);
			oLogo.setVisible(true);
			getCurrentScene().addChild(oLogo);
			
			getCurrentScene().setBackgroundColor(0, 0, 0, 0);
			getCurrentCamera().setZ(6);
			
			
			setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	void load2d() {
		try {
			o2D_Splash = addPlan("UI/splash.jpg");
			o2D_Splash.setScale(.78);
			getCurrentScene().addChild(o2D_Splash);
			
			o2D_BG = addPlan("UI/ui_bg.jpg");
			o2D_BG.setScale(.78);
			o2D_BG.setVisible(true);
			getCurrentScene().addChild(o2D_BG);
			
			
			o2D_20Bottle = addPlan("UI/20bottle.png");
			o2D_20Bottle.setScale(.78);
			o2D_20Bottle.setPosition(0,.42f-0*.72f, 0);
			getCurrentScene().addChild(o2D_20Bottle);
			
			o2D_50Bottle = addPlan("UI/50bottle.png");
			o2D_50Bottle.setScale(.78);
			o2D_50Bottle.setPosition(0, .42f-1*.72f, 0);
			getCurrentScene().addChild(o2D_50Bottle);
			
			o2D_100Bottle = addPlan("UI/100bottle.png");
			o2D_100Bottle.setScale(.78);
			o2D_100Bottle.setPosition(0,.42f-2*.72f, 0);
			getCurrentScene().addChild(o2D_100Bottle);
			
			
			
			o2D_Arcade = addPlan("UI/arcade_btn.png");
			o2D_Arcade.setScale(.78);
			getCurrentScene().addChild(o2D_Arcade);
			
			o2D_20Sec = addPlan("UI/20sec.png");
			o2D_20Sec.setScale(.78);
			getCurrentScene().addChild(o2D_20Sec);
			
			o2D_40Sec = addPlan("UI/40sec.png");
			o2D_40Sec.setScale(.78);
			getCurrentScene().addChild(o2D_40Sec);
			
			o2D_60Sec = addPlan("UI/60sec.png");
			o2D_60Sec.setScale(.78);
			getCurrentScene().addChild(o2D_60Sec);
			
			o2D_TimeB = addPlan("UI/time_btn.png");
			o2D_TimeB.setScale(.78);
			getCurrentScene().addChild(o2D_TimeB);
			
			

			
			
			o2D_Gameover = addPlan("UI/gameover.png");
			o2D_Gameover.setScale(.78);
			o2D_Gameover.setPosition(0, -1.20f, 0);
			getCurrentScene().addChild(o2D_Gameover);
			
			o2D_Gamepaused = addPlan("UI/gamepaused.png");
			o2D_Gamepaused.setScale(.78);
			o2D_Gamepaused.setPosition(0, -1.20f, 0);
			getCurrentScene().addChild(o2D_Gamepaused);
			
			o2D_home = addPlan("UI/home.png");
			o2D_home.setScale(.78);
			o2D_home.setPosition(-.77f+.51f*0, 0.66f, 0);
			getCurrentScene().addChild(o2D_home);
			
			o2D_Achiev = addPlan("UI/achiev.png");
			o2D_Achiev.setScale(.78);
			o2D_Achiev.setPosition(-.77f+.51f*1, 0.66f, 0);
			getCurrentScene().addChild(o2D_Achiev);
			
			o2D_Leader = addPlan("UI/lether.png");
			o2D_Leader.setScale(.78);
			o2D_Leader.setPosition(-.77f+.51f*2, 0.66f, 0);
			getCurrentScene().addChild(o2D_Leader);
			
			o2D_More = addPlan("UI/more.png");
			o2D_More.setScale(.78);
			o2D_More.setPosition(-.77f+.51f*3, 0.66f, 0);
			getCurrentScene().addChild(o2D_More);
			
			o2D_Play = addPlan("UI/play.png");
			o2D_Play.setScale(.78);
			o2D_Play.setPosition(0, 1.27f, 0);
			getCurrentScene().addChild(o2D_Play);
			
			o2D_Score = addPlan("UI/score.png");
			o2D_Score.setScale(.78);
			o2D_Score.setPosition(0, 0.21f, 0);
			getCurrentScene().addChild(o2D_Score);
			
			
			o2D_BScore = addPlan("UI/bestscore.png");
			o2D_BScore.setScale(.78);
			o2D_BScore.setPosition(0,-.41f, 0);
			getCurrentScene().addChild(o2D_BScore);
			
			{
				Material ScoreSphereMaterial = new Material();
				ScoreSphereMaterial.enableLighting(true);
				ScoreSphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				mScoreBitmap = Bitmap.createBitmap(256, 128,Config.ARGB_8888);
				mScoreTexture = new AlphaMapTexture("ScoreTexture",mScoreBitmap);
				ScoreSphereMaterial.addTexture(mScoreTexture);
				ScoreSphereMaterial.setColorInfluence(1);

				oScore = new Plane(2, 1, 1, 1);
				oScore.setMaterial(ScoreSphereMaterial);
				oScore.setDoubleSided(true);
				oScore.setColor(Color.CYAN);
				oScore.setTransparent(true);
				
				getCurrentScene().addChild(oScore);
			}
			{
				Material timeSphereMaterial = new Material();
				timeSphereMaterial.enableLighting(true);
				timeSphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				mTimeBitmap = Bitmap.createBitmap(256, 128,Config.ARGB_8888);
				mTimeTexture = new AlphaMapTexture("timeTexture",mTimeBitmap);
				timeSphereMaterial.addTexture(mTimeTexture);
				timeSphereMaterial.setColorInfluence(1);

				oTime = new Plane(2, 1, 1, 1);
				oTime.setMaterial(timeSphereMaterial);
				oTime.setDoubleSided(true);
				oTime.setColor(Color.WHITE);
				oTime.setTransparent(true);
				
				getCurrentScene().addChild(oTime);
			}
			
			o2D_Start = addPlan("UI/start.png");
			o2D_Start.setScale(.2f);
			o2D_Start.setRotation(118, 0, 180);
			o2D_Start.setPosition(0, -4.74, .96f);
			getCurrentScene().addChild(o2D_Start);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	void init() {
		try {
			mBall = new Ball[M.BALL];
			for(int i =0;i<mBall.length;i++)
			{
				mBall[i] = new Ball();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("stone"+i,LoadImgfromAsset("stone.png")));
				mBall[i].obj3d = new Sphere(1, 4, 4);
				mBall[i].obj3d.setScale(.14);
				mBall[i].obj3d.setMaterial(cloudmat2);
				mBall[i].obj3d.setColor(0);
				getCurrentScene().addChild(mBall[i].obj3d);
			}
			
			{
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("ball",LoadImgfromAsset("stone.png")));
				m3D_Ball = new Sphere(1, 8, 8);
				m3D_Ball.setScale(.14);
				m3D_Ball.setMaterial(cloudmat2);
				m3D_Ball.setColor(0);
				getCurrentScene().addChild(m3D_Ball);
			}
			
			
			m3D_Bottom = new Object3D[M.Bottle];
			for(int i =0;i<m3D_Bottom.length;i++)
			{
				Material cloudmat2 = new Material();
				switch (i) {
				case 0:
					objParser = new Loader3DSMax(this, R.raw.bottom01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01.png")));
					break;
				case 1:
					objParser = new Loader3DSMax(this, R.raw.bottom01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01A.png")));
					break;

				case 2:
					objParser = new Loader3DSMax(this, R.raw.bottom01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01B.png")));
					break;

				case 3:
					objParser = new Loader3DSMax(this, R.raw.bottom01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01C.png")));
					break;

				case 4:
					objParser = new Loader3DSMax(this, R.raw.bottom02);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle02.png")));
					break;
				case 5:
					objParser = new Loader3DSMax(this, R.raw.bottom05);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle05.png")));
					break;

				case 6:
					objParser = new Loader3DSMax(this, R.raw.bottom06);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle06.png")));
					break;

				case 7:
					objParser = new Loader3DSMax(this, R.raw.bottom07);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle07.png")));
					break;

				case 8:
					objParser = new Loader3DSMax(this, R.raw.bottom08);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle08.png")));
					break;

				case  9:
					objParser = new Loader3DSMax(this, R.raw.bottom09);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle09.png")));
					break;

				case 10:
					objParser = new Loader3DSMax(this, R.raw.bottom10);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle10.png")));
					break;

				case 11:
					objParser = new Loader3DSMax(this, R.raw.bottom11);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle11.png")));
					break;

				case 12:
					objParser = new Loader3DSMax(this, R.raw.bottom12);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle12.png")));
					break;

				default:
					break;
				}
				
				objParser.parse();
				m3D_Bottom[i] = objParser.getParsedObject();
				m3D_Bottom[i].setScale(.014);
				m3D_Bottom[i].setMaterial(cloudmat2);
				m3D_Bottom[i].setColor(0);
				getCurrentScene().addChild(m3D_Bottom[i]);
				objParser.clear();
			}
			m3D_Chair = new Object3D[3];
			for(int i =0;i<m3D_Chair.length;i++)
			{
				if(i ==0){
					Material cloudmat2 = new Material();
					objParser = new Loader3DSMax(this, R.raw.chair);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("chair.png")));
					objParser.parse();
					m3D_Chair[i] = objParser.getParsedObject();
					m3D_Chair[i].setMaterial(cloudmat2);
					m3D_Chair[i].setColor(0);
				}else{
					m3D_Chair[i] = m3D_Chair[0].clone(true);
				}
				m3D_Chair[i].setScale(.035);
				m3D_Chair[i].setPosition(0.04f+.81f*i,.15	,0);
				
				getCurrentScene().addChild(m3D_Chair[i]);
				objParser.clear();
			}
			m3D_Top = new Object3D[M.Bottle];
			for(int i =0;i<m3D_Top.length;i++)
			{
				Material cloudmat2 = new Material();
				switch (i) {
				case 0:
					objParser = new Loader3DSMax(this, R.raw.top01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01.png")));
					break;
				case 1:
					objParser = new Loader3DSMax(this, R.raw.top01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01A.png")));
					break;

				case 2:
					objParser = new Loader3DSMax(this, R.raw.top01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01B.png")));
					break;

				case 3:
					objParser = new Loader3DSMax(this, R.raw.top01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01C.png")));
					break;

				case 4:
					objParser = new Loader3DSMax(this, R.raw.top02);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle02.png")));
					break;

				case 5:
					objParser = new Loader3DSMax(this, R.raw.top05);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle05.png")));
					break;

				case 6:
					objParser = new Loader3DSMax(this, R.raw.top06);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle06.png")));
					break;

				case 7:
					objParser = new Loader3DSMax(this, R.raw.top07);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle07.png")));
					break;

				case 8:
					objParser = new Loader3DSMax(this, R.raw.top08);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle08.png")));
					break;

				case  9:
					objParser = new Loader3DSMax(this, R.raw.top09);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle09.png")));
					break;

				case 10:
					objParser = new Loader3DSMax(this, R.raw.top10);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle10.png")));
					break;

				case 11:
					objParser = new Loader3DSMax(this, R.raw.top11);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle11.png")));
					break;

				case 12:
					objParser = new Loader3DSMax(this, R.raw.top12);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle12.png")));
					break;

				default:
					break;
				}
				
				objParser.parse();
				m3D_Top[i] = objParser.getParsedObject();
				m3D_Top[i].setScale(.014);
				m3D_Top[i].setMaterial(cloudmat2);
				m3D_Top[i].setColor(0);
				getCurrentScene().addChild(m3D_Top[i]);
				objParser.clear();
			}
			m3D_Bottle = new Object3D[M.Bottle];
			for(int i =0;i<m3D_Bottle.length;i++)
			{
				Material cloudmat2 = new Material();
				switch (i) {
				case 0:
					objParser = new Loader3DSMax(this, R.raw.b01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01.png")));
					break;
				case 1:
					objParser = new Loader3DSMax(this, R.raw.b01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01A.png")));
					break;

				case 2:
					objParser = new Loader3DSMax(this, R.raw.b01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01B.png")));
					break;

				case 3:
					objParser = new Loader3DSMax(this, R.raw.b01);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle01C.png")));
					break;

				case 4:
					objParser = new Loader3DSMax(this, R.raw.b02);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle02.png")));
					break;

				case 5:
					objParser = new Loader3DSMax(this, R.raw.b05);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle05.png")));
					break;

				case 6:
					objParser = new Loader3DSMax(this, R.raw.b06);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle06.png")));
					break;

				case 7:
					objParser = new Loader3DSMax(this, R.raw.b07);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle07.png")));
					break;

				case 8:
					objParser = new Loader3DSMax(this, R.raw.b08);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle08.png")));
					break;

				case  9:
					objParser = new Loader3DSMax(this, R.raw.b09);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle09.png")));
					break;

				case 10:
					objParser = new Loader3DSMax(this, R.raw.b10);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle10.png")));
					break;

				case 11:
					objParser = new Loader3DSMax(this, R.raw.b11);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle11.png")));
					break;

				case 12:
					objParser = new Loader3DSMax(this, R.raw.b12);
					cloudmat2.addTexture(new Texture("bottle"+i,LoadImgfromAsset("bottle12.png")));
					break;

				default:
					break;
				}
				
				objParser.parse();
				m3D_Bottle[i] = objParser.getParsedObject();
				m3D_Bottle[i].setScale(.014);
				m3D_Bottle[i].setMaterial(cloudmat2);
				m3D_Bottle[i].setColor(0);
				getCurrentScene().addChild(m3D_Bottle[i]);
				objParser.clear();
			}
			{

				objParser = new Loader3DSMax(this, R.raw.bg);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("bg",LoadImgfromAsset("bg.png")));
				m3D_BG = objParser.getParsedObject();
				m3D_BG.setScale(.01f);
				m3D_BG.setPosition(0,0, 0);
				m3D_BG.setMaterial(cloudmat2);
				m3D_BG.setColor(0);
				getCurrentScene().addChild(m3D_BG);
				objParser.clear();
				m3D_BG.setVisible(true);
			}
			{

				objParser = new Loader3DSMax(this, R.raw.floor);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("floor",LoadImgfromAsset("floor.png")));
				m3D_Floor = objParser.getParsedObject();
				m3D_Floor.setScale(.01f);
				m3D_Floor.setPosition(0,0, 0);
				m3D_Floor.setMaterial(cloudmat2);
				m3D_Floor.setColor(0);
				m3D_Floor.setVisible(false);
				getCurrentScene().addChild(m3D_Floor);
				objParser.clear();
			
			}
			
			{
				objParser = new Loader3DSMax(this, R.raw.table);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("table",LoadImgfromAsset("table.png")));
				m3D_Table = objParser.getParsedObject();
				m3D_Table.setScale(.01f);
				m3D_Table.setPosition(0,0, 0);
				m3D_Table.setMaterial(cloudmat2);
				m3D_Table.setColor(0);
				m3D_Table.setVisible(false);
				getCurrentScene().addChild(m3D_Table);
				objParser.clear();
			}
			mParticle = new Particle[M.Bottle][];
			for(int i =0;i<mParticle.length;i++){
				mParticle[i] = new Particle[10];
				for(int j =0;j<mParticle[i].length;j++){
					mParticle[i][j] = new Particle(addPlan("p/"+i+"/"+j+".png")); 
					getCurrentScene().addChild(mParticle[i][j].oPlan);
				}
			}
			mBottle = new Bottle[6];
			for(int i =0;i<mBottle.length;i++){
				mBottle[i] = new Bottle();
			}
			load2d();
//			gameReset(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	void gameReset(boolean isArcade, int mode){
		for(int i =0;i<mBall.length;i++)
		{
			mBall[i].obj3d.setPosition(0, 1, -1);
		}
		bottleset();
		mIsArcad = isArcade;
		mMode = mode;
		if(isArcade){
			mTimeScore = System.currentTimeMillis();
			switch (mMode) {
			case 0:
				NoBottle = 20;	
				break;
			case 1:
				NoBottle = 50;
				break;
			default:
				NoBottle = 100;
				mMode = 2;
				break;
			}
		}else{
			switch (mMode) {
			case 0:
				mTimeScore = System.currentTimeMillis() +20000;	
				break;
			case 1:
				mTimeScore = System.currentTimeMillis() +40000;
				break;
			default:
				mTimeScore = System.currentTimeMillis() +60000;
				mMode = 2;
				break;
			}
			NoBottle = 0;
		}
		mIsGameStart = false;
		if(ads%3==0)
			mStart.load();
		ads++;
	}
	int ads = 0;
	int crack = 0;
	void bottleset() {
		crack = 0;
		int bot[] = new int[M.Bottle];
		if (M.mRand.nextBoolean()) {
			for (int i = 0; i < bot.length; i++) {
				bot[i] = i;
			}
		} else {
			for (int i = 0; i < bot.length; i++) {
				bot[i] = M.Bottle - i -1;
			}
		}
		for (int i = 0; i < 100; i++) {
			int t1 = M.mRand.nextInt(bot.length);
			int t2 = M.mRand.nextInt(bot.length);
			int t3 = bot[t1];
			bot[t1] = bot[t2];
			bot[t2] = t3;
		}
		
		for (int i = 0; i < mBottle.length; i++) {
			mBottle[i].set(bot[i]);
			m3D_Top[bot[i]].setZ(.83f);
		}
		for(int i =0;i<mParticle.length;i++){
			for(int j =0;j<mParticle[i].length;j++){
				mParticle[i][j].oPlan.setVisible(false);
			}
		}
		M.sound5(R.drawable.apear);
	}
	
	Plane addPlan(String str) {
		try {
			
			Plane plan;
			Bitmap bitID = LoadImgfromAsset(str);
			Material material = new Material();
			str = str.replace(".png", "");
			str = str.replace(".jpg", "");
			str = str.replace('/', '_');
			material.addTexture(new Texture(str,bitID));
			material.setColorInfluence(0);
			plan = new Plane(bitID.getWidth()/128f,bitID.getHeight()/128f,1,1);
			plan.setMaterial(material);
			plan.setTransparent(true);
			plan.setRotation(180, 0, 180);
			return plan;
		} catch (Exception e) {
			return null;
		}
	}

	Plane addPlan(String str,Bitmap bitID) {
		try {
			Plane plan;
//			Bitmap bitID = LoadImgfromAsset(str);
			Material material = new Material();
			material.addTexture(new Texture(str.replace('.', '_') ,bitID));
			material.setColorInfluence(0);
			plan = new Plane(bitID.getWidth()/128f,bitID.getHeight()/128f,1,1);
			plan.setMaterial(material);
			plan.setTransparent(true);
			return plan;
		} catch (Exception e) {
			return null;
		}
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
	public void onDrawFrame(GL10 glUnused) {
		root.Draw();
		
		
		super.onDrawFrame(glUnused);
		
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		root.onTouch(v, event);
		return true;
	}
	
	void setVisible(boolean visible) {
		if(m3D_Table==null){
			return;
		}
		oLogo.setVisible(visible);
		o2D_BG.setVisible(visible);
		o2D_50Bottle.setVisible(visible);
		o2D_100Bottle.setVisible(visible);
		o2D_20Bottle.setVisible(visible);
		o2D_Arcade.setVisible(visible);
		o2D_20Sec.setVisible(visible);
		o2D_40Sec.setVisible(visible);
		o2D_60Sec.setVisible(visible);
		o2D_TimeB.setVisible(visible);
		o2D_Achiev.setVisible(visible);
		o2D_BScore.setVisible(visible);
		o2D_Gameover.setVisible(visible);
		o2D_Gamepaused.setVisible(visible);
		o2D_home.setVisible(visible);
		o2D_Leader.setVisible(visible);
		o2D_More.setVisible(visible);
		o2D_Play.setVisible(visible);
		o2D_Score.setVisible(visible);
		o2D_Splash.setVisible(visible);
		oScore.setVisible(visible);
		oTime.setVisible(visible);
		for(int i =0;i<m3D_Bottle.length;i++)
			m3D_Bottle[i].setVisible(visible);
		for(int i =0;i<m3D_Bottom.length;i++)
			m3D_Bottom[i].setVisible(visible);
		for(int i =0;i<m3D_Top.length;i++)
			m3D_Top[i].setVisible(visible);
		for(int i =0;i<m3D_Chair.length;i++)
			m3D_Chair[i].setVisible(visible);
		for(int i =0;i<mBall.length;i++)
			mBall[i].obj3d.setVisible(visible);
		m3D_BG.setVisible(visible);
		m3D_Floor.setVisible(visible);
		m3D_Table.setVisible(visible);
		m3D_Ball.setVisible(visible);
		o2D_Start.setVisible(visible);
	}
	
	public void updateScoreBitmap() {

		if (mScoreCanvas == null) {
			mScoreCanvas = new Canvas(mScoreBitmap);
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setTypeface(tf);
			mPaint.setColor(Color.WHITE);
		}
		mPaint.setTextSize(32);
		mScoreCanvas.drawColor(0, Mode.CLEAR);
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setColor(Color.WHITE);
		if (M.GameScreen == M.GAMEPLAY) {
			if (mIsArcad)
				mScoreCanvas.drawText(NoBottle+"", 128, 64, mPaint);
			else{
				if(mIsGameStart)
					mScoreCanvas.drawText(((mTimeScore - System.currentTimeMillis())/1000)+" Sec", 128, 64, mPaint);
				else{
					mScoreCanvas.drawText((20*(1+mMode))+" Sec", 128, 64, mPaint);
				}
			}
		} else {
			if (mIsArcad)
				mScoreCanvas.drawText((mTimeScore/1000)+" Sec", 128, 64, mPaint);
			else
				mScoreCanvas.drawText(NoBottle+"", 128, 64, mPaint);
		}
//		mScoreCanvas.drawText("4444444", 128, 64, mPaint);
		mScoreTexture.setBitmap(mScoreBitmap);
		mTextureManager.replaceTexture(mScoreTexture);
	}
	public void updateTimeBitmap() {
		if (mTimeCanvas == null) {
			mTimeCanvas = new Canvas(mTimeBitmap);
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setTypeface(tf);
		}
		mPaint.setTextSize(32);
		mTimeCanvas.drawColor(0, Mode.CLEAR);
		mPaint.setTextAlign(Align.CENTER);
		if (mIsArcad)
			mTimeCanvas.drawText((mTScore[mMode]/1000)+" Sec", 128, 64, mPaint);
		else
			mTimeCanvas.drawText(mBScore[mMode]+"", 128, 64, mPaint);
		
		mTimeTexture.setBitmap(mTimeBitmap);
		mTextureManager.replaceTexture(mTimeTexture);
	}
}
	