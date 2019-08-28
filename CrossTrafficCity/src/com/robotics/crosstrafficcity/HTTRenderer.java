package com.robotics.crosstrafficcity;
import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.ads.AdView;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import rajawali.Object3D;
import rajawali.OrthographicCamera;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.textures.AlphaMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.parser.Loader3DSMax;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

public class HTTRenderer extends RajawaliRenderer implements OnTouchListener {
	static Start mStart;
	static Context mContext;
	DirectionalLight mLight;
	
	
	
	Object3D m3d_BGObj[][];
	Object3D[] m3d_Car,m3d_Wood,m3d_Tree,m3d_Drop,m3d_Train,m3d_Signel,m3d_Player,m3d_GiftBox,m3d_Bat;
	Object3D m3d_Egle,m3d_Gift,m3d_Mashin,m3d_Trance,m3d_Cube;
	
	
	Plane[] m2d_Tlight, m2d_TRed, m2d_Cent, m2d_Sound, m2d_Sleeping;
	Plane[] m2d_Hand, m2d_Tears, m2d_Fire;
	Plane oLogo, m2d_LBoard, m2d_Achiv, m2d_char, m2d_Play, m2d_Back;
	Plane m2d_RPati, m2d_Bpati, m2d_SPati, m2d_Win, m2d_Gift, m2d_Val;
	Plane m2d_Tran, m2d_Google, m2d_facebook, m2d_share, m2d_100, m2d_Crosy;
	Plane oTime,m2d_EPati,m2d_Ebutn,m2d_DEPati;
	
	
	Bitmap mTimeBitmap;
	Canvas mTimeCanvas;
	AlphaMapTexture mTimeTexture;
	
	Plane oFree;
	Bitmap mFreeBitmap;
	Canvas mFreeCanvas;
	AlphaMapTexture mFreeTexture;
	
	Plane oCoin;
	Bitmap mCoinBitmap;
	Canvas mCoinCanvas;
	AlphaMapTexture mCoinTexture;
	
	Plane oScore;
	Bitmap mScoreBitmap;
	Canvas mScoreCanvas;
	AlphaMapTexture mScoreTexture;
	
	Plane oExtra;
	Bitmap mExtraBitmap;
	Canvas mExtraCanvas;
	AlphaMapTexture mExtraTexture;
	
	
	Loader3DSMax objParser;
	boolean addFree = false;
	static boolean SingUpadate = false;
	
	boolean isbuyPlayer[];
	boolean isPeggie=false;
	boolean isdpublecent=false;
	
	int giftMove =0;
	int type = 0;
	int noTime = 0;
	int mScore;
	int mHScore;
	int giftNo = 0;
	int cent =145;
	int FreeCoin = 0;
	float scal = .1f;
	final float Spd = -.04f;
	float move =0;
	long time = System.currentTimeMillis()-21600000;
	
	long forAdColony = System.currentTimeMillis()-100000;
	
	Paint mPaint = new Paint();
	Backgraund mBGVactor[];
	Car mCar[];
	Wood mWoods[];
	Tree mTrees[];
	Player mPlayer;
	Train mTrain[];
	Water mWater[];
	Cent mCent[];
	Tears mTears[];
	Fire mFire[];
	Egle mEgle;
	Group root;
	InApp mInApp;
	public HTTRenderer(Context context) {
		super(context);
		setFrameRate(40);
		mContext = context;
		mStart = (Start) context;
		root = new Group(this);
		try {
			mInApp = new InApp();
			mInApp.onCreate();
		} catch (Exception e) {
			System.out.println("" + e.toString());
			// TODO: handle exception
		}
		// TODO Auto-generated constructor stub
	}

	OrthographicCamera orthoCam = new OrthographicCamera();
	
	
	
	
	void loadPlan(){
		try {
		m2d_Tlight = new Plane[M.LIGHT];
		for (int i = 0; i < m2d_Tlight.length; i++) {
			m2d_Tlight[i] = addPlan("red.png");
			m2d_Tlight[i].setTransparent(true);
			m2d_Tlight[i].setScale(2);
			getCurrentScene().addChild(m2d_Tlight[i]);
		}
		
		
		m2d_Sleeping = new Plane[M.SLEEP];
		for (int i = 0; i < m2d_Sleeping.length; i++) {
			m2d_Sleeping[i] = addPlan("sleeping.png");
			getCurrentScene().addChild(m2d_Sleeping[i]);
		}
		
		m2d_Tears = new Plane[M.TEAR];
		for (int i = 0; i < m2d_Tears.length; i++) {
			m2d_Tears[i] = addPlan("tears.png");
			getCurrentScene().addChild(m2d_Tears[i]);
		}
		
		m2d_Fire = new Plane[M.FIRE];
		for (int i = 0; i < m2d_Fire.length; i++) {
			m2d_Fire[i] = addPlan("f"+(i%3)+".png");
			getCurrentScene().addChild(m2d_Fire[i]);
		}
		
		m2d_Cent = new Plane[M.CENT];
		for (int i = 0; i < m2d_Cent.length; i++) {
			m2d_Cent[i] = addPlan("coin.png");
//			m2d_Cent[i].setTransparent(false);
			getCurrentScene().addChild(m2d_Cent[i]);
		}
		
		m2d_TRed = new Plane[M.LIGHT];
		for (int i = 0; i < m2d_TRed.length; i++) {
			m2d_TRed[i] = addPlan("red.png");
			m2d_TRed[i].setTransparent(true);
			m2d_TRed[i].setScale(.25);
			getCurrentScene().addChild(m2d_TRed[i]);
		}
		m2d_Tran = addPlan("trans_bg.png");
		m2d_Tran.setPosition(.86, -2.60, 2.99);
		m2d_Tran.setScale(4,12,1);
		m2d_Tran.setRotation(213, 24.1, 32.5);
		getCurrentScene().addChild(m2d_Tran);
		
		
		Bitmap b = LoadImgfromAsset("ui/icon_strip.png");
		m2d_LBoard = addPlan("lBord",Bitmap.createBitmap(b, (b.getWidth()/8)*4, 0, (b.getWidth()/8), b.getHeight()));
		m2d_LBoard.setScale(.5);
		m2d_LBoard.setRotZ(200);
		getCurrentScene().addChild(m2d_LBoard);
		
		m2d_Google = addPlan("google",Bitmap.createBitmap(b, (b.getWidth()/8)*5, 0, (b.getWidth()/8), b.getHeight()));
		m2d_Google.setScale(.5);
		m2d_Google.setRotZ(200);
		getCurrentScene().addChild(m2d_Google);

		m2d_facebook = addPlan("face",Bitmap.createBitmap(b, (b.getWidth()/8)*1, 0, (b.getWidth()/8), b.getHeight()));
		m2d_facebook.setScale(.5);
		m2d_facebook.setRotZ(200);
		getCurrentScene().addChild(m2d_facebook);
		
		
		m2d_share = addPlan("share",Bitmap.createBitmap(b, (b.getWidth()/8)*7, 0, (b.getWidth()/8), b.getHeight()));
		m2d_share.setScale(.5);
		m2d_share.setRotZ(200);
		getCurrentScene().addChild(m2d_share);
		
		
		m2d_Achiv = addPlan("Achive",Bitmap.createBitmap(b, (b.getWidth()/8)*6, 0, (b.getWidth()/8), b.getHeight()));
		m2d_Achiv.setScale(.5);
		m2d_Achiv.setRotZ(200);
		getCurrentScene().addChild(m2d_Achiv);
		
		m2d_char = addPlan("lBord",Bitmap.createBitmap(b, (b.getWidth()/8)*0, 0, (b.getWidth()/8), b.getHeight()));
		m2d_char.setScale(.5);
		m2d_char.setRotZ(200);
		getCurrentScene().addChild(m2d_char);
		
		
		m2d_Sound = new Plane[2];
		m2d_Sound[0] = addPlan("Setting",Bitmap.createBitmap(b, (b.getWidth()/8)*2, 0, (b.getWidth()/8), b.getHeight()));
		m2d_Sound[0].setScale(.5);
		m2d_Sound[0].setRotZ(200);
		getCurrentScene().addChild(m2d_Sound[0]);
		
		m2d_Sound[1] = addPlan("Setting",Bitmap.createBitmap(b, (b.getWidth()/8)*3, 0, (b.getWidth()/8), b.getHeight()));
		m2d_Sound[1].setScale(.5);
		m2d_Sound[1].setRotZ(200);
		getCurrentScene().addChild(m2d_Sound[1]);
		
		
		b = LoadImgfromAsset("ui/play_back.png");
		
		m2d_Play = addPlan("play_back",Bitmap.createBitmap(b, (b.getWidth()/2)*0, 0, (b.getWidth()/2), b.getHeight()));
		m2d_Play.setScale(.5);
		m2d_Play.setRotZ(200);
		getCurrentScene().addChild(m2d_Play);
		
		m2d_Back = addPlan("back_play",Bitmap.createBitmap(b, (b.getWidth()/2)*1, 0, (b.getWidth()/2), b.getHeight()));
		m2d_Back.setScale(.5);
		m2d_Back.setRotZ(200);
		getCurrentScene().addChild(m2d_Back);
		
		
		m2d_RPati = addPlan("red_line.png");
		m2d_RPati.setScale(.5);
		m2d_RPati.setRotZ(200);
		getCurrentScene().addChild(m2d_RPati);
//		
		m2d_Bpati = addPlan("blue_line.png");
		m2d_Bpati.setScale(.5);
		m2d_Bpati.setRotZ(200);
		getCurrentScene().addChild(m2d_Bpati);
		
		m2d_SPati = addPlan("sky_line.png");
		m2d_SPati.setScale(.5);
		m2d_SPati.setRotZ(200);
		getCurrentScene().addChild(m2d_SPati);
		
		m2d_EPati = addPlan("orrange_line.png");
		m2d_EPati.setScale(1.2);
		m2d_EPati.setRotZ(200);
		getCurrentScene().addChild(m2d_EPati);
		
		
		m2d_DEPati = addPlan("orrange_line.png");
		m2d_DEPati.setScale(1.4);
		m2d_DEPati.setRotZ(200);
		getCurrentScene().addChild(m2d_DEPati);
		
		m2d_Ebutn = addPlan("clock.png");
		m2d_Ebutn.setScale(1.2);
		m2d_Ebutn.setRotZ(200);
		getCurrentScene().addChild(m2d_Ebutn);
		
		
		b = LoadImgfromAsset("ui/icon_strip_1.png");
		
		m2d_Win = addPlan("win",Bitmap.createBitmap(b, (b.getWidth()/4)*2, 0, (b.getWidth()/4), b.getHeight()));
		m2d_Win.setScale(.5);
		m2d_Win.setRotZ(200);
		getCurrentScene().addChild(m2d_Win);
		
		m2d_Gift = addPlan("gift",Bitmap.createBitmap(b, (b.getWidth()/4)*1, 0, (b.getWidth()/4), b.getHeight()));
		m2d_Gift.setScale(.5);
		m2d_Gift.setRotZ(200);
		getCurrentScene().addChild(m2d_Gift);
		
		m2d_Val = addPlan("val",Bitmap.createBitmap(b, (b.getWidth()/4)*0, 0, (b.getWidth()/4), b.getHeight()));
		m2d_Val.setScale(.5);
		m2d_Val.setRotZ(200);
		getCurrentScene().addChild(m2d_Val);
		
		
		m2d_100 = addPlan("hundred",Bitmap.createBitmap(b, (b.getWidth()/4)*3, 0, (b.getWidth()/4), b.getHeight()));
		m2d_100.setScale(.5);
		m2d_100.setRotZ(200);
		getCurrentScene().addChild(m2d_100);
		
		
		
		b.recycle();
		m2d_Hand = new Plane[2];
		b = LoadImgfromAsset("hand.png");
		for (int i = 0; i < m2d_Hand.length; i++) {
			m2d_Hand[i] = addPlan("hand"+i,Bitmap.createBitmap(b, (b.getWidth()/2)*i, 0, (b.getWidth()/2), b.getHeight()));
			m2d_Hand[i].setPosition(1.01f,  -3.00f,  3.0f+i*.02);
			getCurrentScene().addChild(m2d_Hand[i]);
		}
		
		
		m2d_Crosy = addPlan("cross.png");
		m2d_Crosy.setRotZ(200);
		getCurrentScene().addChild(m2d_Crosy);
		
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
			oTime.setColor(Color.WHITE);
			oTime.setScale(.8);
			oTime.setRotation(180, 0, 200);
			oTime.setTransparent(true);
			getCurrentScene().addChild(oTime);
		}
		
		{
			Material FreeSphereMaterial = new Material();
			FreeSphereMaterial.enableLighting(true);
			FreeSphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
			mFreeBitmap = Bitmap.createBitmap(256, 128,Config.ARGB_8888);
			mFreeTexture = new AlphaMapTexture("FreeTexture",mFreeBitmap);
			FreeSphereMaterial.addTexture(mFreeTexture);
			FreeSphereMaterial.setColorInfluence(1);

			oFree = new Plane(2, 1, 1, 1);
			oFree.setMaterial(FreeSphereMaterial);
			oFree.setColor(Color.WHITE);
			oFree.setScale(.8);
			oFree.setRotation(180, 0, 200);
			oFree.setTransparent(true);
			getCurrentScene().addChild(oFree);
		}
		
		{
			Material CoinSphereMaterial = new Material();
			CoinSphereMaterial.enableLighting(true);
			CoinSphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
			mCoinBitmap = Bitmap.createBitmap(256, 128,Config.ARGB_8888);
			mCoinTexture = new AlphaMapTexture("CoinTexture",mCoinBitmap);
			CoinSphereMaterial.addTexture(mCoinTexture);
			CoinSphereMaterial.setColorInfluence(1);

			oCoin = new Plane(2, 1, 1, 1);
			oCoin.setMaterial(CoinSphereMaterial);
			oCoin.setScale(.8);
			oCoin.setColor(Color.RED);
			oCoin.setRotation(180, 0, 200);
			oCoin.setTransparent(true);
			getCurrentScene().addChild(oCoin);
		}
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
			oScore.setScale(.8);
			oScore.setColor(Color.WHITE);
			oScore.setRotation(180, 0, 200);
			oScore.setTransparent(true);
			getCurrentScene().addChild(oScore);
		}
		{
			Material ExtraSphereMaterial = new Material();
			ExtraSphereMaterial.enableLighting(true);
			ExtraSphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
			mExtraBitmap = Bitmap.createBitmap(256, 128,Config.ARGB_8888);
			mExtraTexture = new AlphaMapTexture("ExtraTexture",mExtraBitmap);
			ExtraSphereMaterial.addTexture(mExtraTexture);
			ExtraSphereMaterial.setColorInfluence(1);

			oExtra = new Plane(2, 1, 1, 1);
			oExtra.setMaterial(ExtraSphereMaterial);
			oExtra.setScale(.8);
			oExtra.setColor(Color.WHITE);
			oExtra.setRotation(180, 0, 200);
			oExtra.setTransparent(true);
			getCurrentScene().addChild(oExtra);
		}
//		planAdd();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void initScene() {
		try {
			mSurfaceView.setOnTouchListener(this);
			mLight = new DirectionalLight(5.0f, 0.0f, 0.0f); // set the
			mLight.setColor(1.0f, 1.0f, 1.0f);
			mLight.setPower(1);
			getCurrentScene().addLight(mLight);
			byte ran[] = {1,2,3,4,5};
			M.mRand.nextBytes(ran);
			oLogo = addPlan("roboticsapp.png");
			oLogo.setRotation(180, 0, 180);
			oLogo.setPosition(0, 0, .1);
			oLogo.setScale(1.2);
			oLogo.setTransparent(true);
			oLogo.setVisible(true);
			getCurrentScene().addChild(oLogo);
//			init();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void init() {
		try {
			orthoCam.setPosition(0, 0, 6);
			orthoCam.setZoom(1.2);
			orthoCam.setRotation(-44, -13, -14);
			oLogo.setRotation(180, 0, 200);
			oLogo.setScale(1);
			getCurrentScene().switchCamera(orthoCam);
			
			
			
			m3d_GiftBox = new Object3D[4];
			for(int i = 0 ;i<m3d_GiftBox.length;i++)
			{
				Material cloudmat2 = new Material();
				objParser = new Loader3DSMax(this, R.raw.boxgift);
				cloudmat2.addTexture(new Texture("boxgift",LoadImgfromAsset("boxgift.png")));
				objParser.parse();
				m3d_GiftBox[i] = objParser.getParsedObject();
				m3d_GiftBox[i].setScale(.03f);
				m3d_GiftBox[i].setPosition(0, -40, 0);
				m3d_GiftBox[i].setMaterial(cloudmat2);
				m3d_GiftBox[i].setColor(0);
				getCurrentScene().addChild(m3d_GiftBox[i]);
				objParser.clear();
			}
			
			m3d_Bat = new Object3D[3];
			for(int i = 0 ;i<m3d_Bat.length;i++)
			{
				Material cloudmat2 = new Material();
				if(i %3==0)
					objParser = new Loader3DSMax(this, R.raw.bat1);
				if(i %3==1)
					objParser = new Loader3DSMax(this, R.raw.bat2);
				if(i %3==2)
					objParser = new Loader3DSMax(this, R.raw.bat3);
				cloudmat2.addTexture(new Texture("bat"+i,LoadImgfromAsset("bat.png")));
				
				objParser.parse();
				m3d_Bat[i] = objParser.getParsedObject();
				m3d_Bat[i].setScale(.5f);
				m3d_Bat[i].setPosition(0, -40, 0);
				m3d_Bat[i].setMaterial(cloudmat2);
				m3d_Bat[i].setColor(0);
				getCurrentScene().addChild(m3d_Bat[i]);
				objParser.clear();
			}
			{
				Material cloudmat2 = new Material();
				objParser = new Loader3DSMax(this, R.raw.gift);
				cloudmat2.addTexture(new Texture("gift",LoadImgfromAsset("gift.png")));
				objParser.parse();
				m3d_Gift = objParser.getParsedObject();
				m3d_Gift.setScale(.03f);
				m3d_Gift.setPosition(0, -40, 0);
				m3d_Gift.setMaterial(cloudmat2);
				m3d_Gift.setColor(0);
				getCurrentScene().addChild(m3d_Gift);
				objParser.clear();
			}
			
			
			{
				Material cloudmat2 = new Material();
				objParser = new Loader3DSMax(this, R.raw.eagel);
				cloudmat2.addTexture(new Texture("Egle",LoadImgfromAsset("eagel.png")));
				objParser.parse();
				m3d_Egle = objParser.getParsedObject();
				m3d_Egle.setScale(.2f);
				m3d_Egle.setRotZ(180);
				m3d_Egle.setPosition(.46, -1.05, 3);
				m3d_Egle.setMaterial(cloudmat2);
				m3d_Egle.setColor(0);
				getCurrentScene().addChild(m3d_Egle);
				objParser.clear();
			}
			Box();
			m3d_BGObj = new Object3D[4][];
			m3d_BGObj[0] = new Object3D[10];
			for (int i = 0; i < m3d_BGObj[0].length; i++) {
				objParser = new Loader3DSMax(this, R.raw.road);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("road" + i,
						LoadImgfromAsset("road.png")));
				m3d_BGObj[0][i] = objParser.getParsedObject();
				m3d_BGObj[0][i].setScale(.03f);
				m3d_BGObj[0][i].setPosition(0, -40, 0);
				m3d_BGObj[0][i].setMaterial(cloudmat2);
				m3d_BGObj[0][i].setColor(0);
				getCurrentScene().addChild(m3d_BGObj[0][i]);
				objParser.clear();
			}
			m3d_BGObj[1] = new Object3D[10];
			for (int i = 0; i < m3d_BGObj[1].length; i++) {
				objParser = new Loader3DSMax(this, R.raw.grass);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("grass" + i, LoadImgfromAsset(i%2==0?"grass2.png":"grass.png")));
				m3d_BGObj[1][i] = objParser.getParsedObject();
				m3d_BGObj[1][i].setScale(.03f);
				// m3d_BGObj[1][i].setPosition(0,-0.99+.22*i, 0);
				m3d_BGObj[1][i].setPosition(0, -40, 0);
				m3d_BGObj[1][i].setMaterial(cloudmat2);
				m3d_BGObj[1][i].setColor(0);
				getCurrentScene().addChild(m3d_BGObj[1][i]);
				objParser.clear();
			}

			m3d_BGObj[2] = new Object3D[10];
			for (int i = 0; i < m3d_BGObj[2].length; i++) {
				objParser = new Loader3DSMax(this, R.raw.water);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("water" + i,
						LoadImgfromAsset("water.png")));
				m3d_BGObj[2][i] = objParser.getParsedObject();
				m3d_BGObj[2][i].setScale(.03f);
				// m3d_BGObj[2][i].setPosition(0,-0.99+.22*i, 0);
				m3d_BGObj[2][i].setPosition(0, -40, 0);
				m3d_BGObj[2][i].setMaterial(cloudmat2);
				m3d_BGObj[2][i].setColor(0);
				getCurrentScene().addChild(m3d_BGObj[2][i]);
				objParser.clear();
			}
			m3d_BGObj[3] = new Object3D[10];
			for (int i = 0; i < m3d_BGObj[3].length; i++) {
				objParser = new Loader3DSMax(this, R.raw.treck);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("treck" + i,
						LoadImgfromAsset("treck.png")));
				m3d_BGObj[3][i] = objParser.getParsedObject();
				m3d_BGObj[3][i].setScale(.03f);
				m3d_BGObj[3][i].setPosition(0, -40, 0);
				m3d_BGObj[3][i].setMaterial(cloudmat2);
				m3d_BGObj[3][i].setColor(0);
				getCurrentScene().addChild(m3d_BGObj[3][i]);
				objParser.clear();
			}

			m3d_Car = new Object3D[M.CAR];
			for (int i = 0; i < m3d_Car.length; i++) {
				Material cloudmat2 = new Material();
				switch (i%15) {
				case 0:
					objParser = new Loader3DSMax(this, R.raw.auto);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("auto.png")));	
					break;
				case 1:
					objParser = new Loader3DSMax(this, R.raw.auto);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("auto2.png")));	
					break;
				case 2:
					objParser = new Loader3DSMax(this, R.raw.car1);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("car1.png")));	
					break;
				case 3:
					objParser = new Loader3DSMax(this, R.raw.car2);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("car2.png")));	
					break;
				case 4:
					objParser = new Loader3DSMax(this, R.raw.car2);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("car3.png")));	
					break;
				case 5:
					objParser = new Loader3DSMax(this, R.raw.car4);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("car4.png")));	
					break;
				case 6:
					objParser = new Loader3DSMax(this, R.raw.car5);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("car5.png")));	
					break;
				case 7:
					objParser = new Loader3DSMax(this, R.raw.car6);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("car6.png")));	
					break;
				case 8:
					objParser = new Loader3DSMax(this, R.raw.car6);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("car7.png")));	
					break;
				case 9:
					objParser = new Loader3DSMax(this, R.raw.embu);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("embu.png")));	
					break;
				case 10:
					objParser = new Loader3DSMax(this, R.raw.polic);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("polic.png")));	
					break;
				case 11:
					objParser = new Loader3DSMax(this, R.raw.truck);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("truck.png")));	
					break;
				case 12:
					objParser = new Loader3DSMax(this, R.raw.truck);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("truck2.png")));	
					break;
				case 13:
					objParser = new Loader3DSMax(this, R.raw.truck3);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("truck3.png")));	
					break;
				default:
					objParser = new Loader3DSMax(this, R.raw.truck3);
					cloudmat2.addTexture(new Texture("car" + i, LoadImgfromAsset("truck4.png")));
					break;
				}
				objParser.parse();
				m3d_Car[i] = objParser.getParsedObject();
				m3d_Car[i].setScale(.023f);
				m3d_Car[i].setPosition(0, -40, 0);
				m3d_Car[i].setMaterial(cloudmat2);
				m3d_Car[i].setColor(0);
				getCurrentScene().addChild(m3d_Car[i]);
				objParser.clear();
			}

			m3d_Train = new Object3D[3];
			for (int i = 0; i < m3d_Train.length; i++) {
				objParser = new Loader3DSMax(this, R.raw.train);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("train" + i, LoadImgfromAsset("train.png")));
				m3d_Train[i] = objParser.getParsedObject();
				m3d_Train[i].setScale(.023f);
				m3d_Train[i].setPosition(0, -40, 0);
				m3d_Train[i].setMaterial(cloudmat2);
				m3d_Train[i].setColor(0);
				getCurrentScene().addChild(m3d_Train[i]);
				objParser.clear();
			}
			m3d_Signel = new Object3D[10];
			for (int i = 0; i < m3d_Signel.length; i++) {
				objParser = new Loader3DSMax(this, R.raw.signal);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("signal" + i, LoadImgfromAsset("signal.png")));
				m3d_Signel[i] = objParser.getParsedObject();
				m3d_Signel[i].setScale(.023f);
				m3d_Signel[i].setPosition(0, -40, 0);
				m3d_Signel[i].setMaterial(cloudmat2);
				m3d_Signel[i].setColor(0);
				getCurrentScene().addChild(m3d_Signel[i]);
				objParser.clear();
			}
			
			
			
			m3d_Drop = new Object3D[M.DROP];
			for (int i = 0; i < m3d_Drop.length; i++) {
				objParser = new Loader3DSMax(this, R.raw.box2);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("train" + i, LoadImgfromAsset("box2.png")));
				m3d_Drop[i] = objParser.getParsedObject();
				m3d_Drop[i].setScale(M.mRand.nextInt(50)*.0001+.013f);
				m3d_Drop[i].setPosition(0, -40, 0);
				m3d_Drop[i].setMaterial(cloudmat2);
				m3d_Drop[i].setColor(0);
				getCurrentScene().addChild(m3d_Drop[i]);
				objParser.clear();
			}
			
			m3d_Wood = new Object3D[M.WOOD];
			for (int i = 0; i < m3d_Wood.length; i++) {
				Material cloudmat2 = new Material();
				if (i % 3 == 0) {
					objParser = new Loader3DSMax(this, R.raw.wood);
					cloudmat2.addTexture(new Texture("wood" + i, LoadImgfromAsset("wood.png")));
				}
				else if (i % 3 == 1) {
					objParser = new Loader3DSMax(this, R.raw.wood2);
					cloudmat2.addTexture(new Texture("wood" + i, LoadImgfromAsset("wood2.png")));
				}
				else if (i % 3 == 2) {
					objParser = new Loader3DSMax(this, R.raw.wood3);
					cloudmat2.addTexture(new Texture("wood" + i, LoadImgfromAsset("wood3.png")));
				}
				objParser.parse();
				
				
				m3d_Wood[i] = objParser.getParsedObject();
				m3d_Wood[i].setScale(.02f);
				m3d_Wood[i].setMaterial(cloudmat2);
				m3d_Wood[i].setColor(0);
				getCurrentScene().addChild(m3d_Wood[i]);
				objParser.clear();
			}
			m3d_Player = new Object3D[M.PLAYER];
			for(int i =0;i<m3d_Player.length;i++)
			{
				Material cloudmat2 = new Material();
				switch (i) {
				case 0:
					objParser = new Loader3DSMax(this, R.raw.baller);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("baller.png")));	
					break;
				case 1:
					objParser = new Loader3DSMax(this, R.raw.bhalu);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("bhalu.png")));	
					break;
				case 2:
					objParser = new Loader3DSMax(this, R.raw.boltman);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("boltman.png")));	
					break;
				case 3:
					objParser = new Loader3DSMax(this, R.raw.boy);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("boy.png")));	
					break;
				case 4:
					objParser = new Loader3DSMax(this, R.raw.chicken);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("chicken.png")));	
					break;
				case 5:
					objParser = new Loader3DSMax(this, R.raw.cow);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("cow.png")));	
					break;
				case 6:
					objParser = new Loader3DSMax(this, R.raw.croco);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("croco.png")));//
					break;
				case 7:
					objParser = new Loader3DSMax(this, R.raw.crying);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("crying.png")));	
					break;
				case 8:
					objParser = new Loader3DSMax(this, R.raw.dog);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("dog.png")));	
					break;
				case 9:
					objParser = new Loader3DSMax(this, R.raw.dregon);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("dregon.png")));	
					break;
				case 10:
					objParser = new Loader3DSMax(this, R.raw.elephant);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("elephant.png")));	
					break;
				case 11:
					objParser = new Loader3DSMax(this, R.raw.frank);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("frank.png")));	
					break;
				case 12:
					objParser = new Loader3DSMax(this, R.raw.horsh);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("horsh.png")));	
					break;
				case 13:
					objParser = new Loader3DSMax(this, R.raw.jairaff);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("jairaff.png")));	
					break;
					
				case 14:
					objParser = new Loader3DSMax(this, R.raw.lion);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("lion.png")));	
					break;
				case 15:
					objParser = new Loader3DSMax(this, R.raw.mallaro);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("mallaro.png")));	
					break;
				case 16:
					objParser = new Loader3DSMax(this, R.raw.man);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("man.png")));	
					break;
				case 17:
					objParser = new Loader3DSMax(this, R.raw.meerkat);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("meerkat.png")));	
					break;
				case 18:
					objParser = new Loader3DSMax(this, R.raw.mrd);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("MRD.png")));//wizard
					break;
				case 19:
					objParser = new Loader3DSMax(this, R.raw.mrdbull);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("MRD_bull.png")));	
					break;
				case 20:
					objParser = new Loader3DSMax(this, R.raw.panda);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("panda.png")));	
					break;
				case 21:
					objParser = new Loader3DSMax(this, R.raw.pig);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("pig.png")));	
					break;
				case 22:
					objParser = new Loader3DSMax(this, R.raw.pigeon);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("pigeon.png")));	
					break;
				case 23:
					objParser = new Loader3DSMax(this, R.raw.platypus);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("platypus.png")));	
					break;
				case 24:
					objParser = new Loader3DSMax(this, R.raw.poppy);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("poppy.png")));	
					break;
				case 25:
					objParser = new Loader3DSMax(this, R.raw.pug);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("pug.png")));//Dog	
					break;
				case 26:
					objParser = new Loader3DSMax(this, R.raw.scruffy);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("scruffy.png")));//Dog	
					break;
				case 27:
					objParser = new Loader3DSMax(this, R.raw.sleep);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("sleep.png")));	
					break;
				case 28:
					objParser = new Loader3DSMax(this, R.raw.speciman);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("speciman.png")));	
					break;
				case 29:
					objParser = new Loader3DSMax(this, R.raw.the);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("THE.png")));//ghost
					break;
				case 30:
					objParser = new Loader3DSMax(this, R.raw.turtle);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("turtle.png")));//Not Require	
					break;
				case 31:
					objParser = new Loader3DSMax(this, R.raw.vampire);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("vampire.png")));	
					break;
				default:
					objParser = new Loader3DSMax(this, R.raw.zombie);
					cloudmat2.addTexture(new Texture("plyer" + i, LoadImgfromAsset("zombie.png")));
					break;
				}
				objParser.parse();
				m3d_Player[i] = objParser.getParsedObject();
				m3d_Player[i].setScale(.1f);
				m3d_Player[i].setPosition(0, 0, 0);
				m3d_Player[i].setMaterial(cloudmat2);
				m3d_Player[i].setColor(0);
//				m3d_Player.setDoubleSided(true);
				getCurrentScene().addChild(m3d_Player[i]);
				objParser.clear();
			}
			
			loadPlan();
			/*{
				LoaderMD2 top = new LoaderMD2(mContext.getResources(), mTextureManager, R.raw.jeckpot);
				top.parse();
				m3d_Mashin = (Object3D) top.getParsedObject();
				m3d_Mashin.setScale(.006f);
				m3d_Mashin.setBackSided(true);
				m3d_Mashin.setTransparent(true);
				getCurrentScene().addChild(m3d_Mashin);
			}*/
			
			{
				Material cloudmat2 = new Material();
				objParser = new Loader3DSMax(this, R.raw.jeckpot);
				cloudmat2.addTexture(new Texture("jeckpot",LoadImgfromAsset("jeckpot.png")));
				objParser.parse();
				m3d_Mashin = objParser.getParsedObject();
				m3d_Mashin.setScale(.03f);
				m3d_Mashin.setMaterial(cloudmat2);
				m3d_Mashin.setDoubleSided(true);
//				m3d_Mashin.setTransparent(true);
				m3d_Mashin.setColor(0);
				getCurrentScene().addChild(m3d_Mashin);
				objParser.clear();
			}
			{
				Material cloudmat2 = new Material();
				objParser = new Loader3DSMax(this, R.raw.trans);
				cloudmat2.addTexture(new Texture("trans",LoadImgfromAsset("trans.png")));
				objParser.parse();
				m3d_Trance = objParser.getParsedObject();
				m3d_Trance.setScale(.03f);
				m3d_Trance.setMaterial(cloudmat2);
				m3d_Trance.setDoubleSided(true);
				m3d_Trance.setTransparent(true);
				m3d_Trance.setColor(0);
				getCurrentScene().addChild(m3d_Trance);
				objParser.clear();
			}
			{
				Material cloudmat2 = new Material();
				objParser = new Loader3DSMax(this, R.raw.cube);
				cloudmat2.addTexture(new Texture("cube",LoadImgfromAsset("cube.png")));
				objParser.parse();
				m3d_Cube = objParser.getParsedObject();
				m3d_Cube.setScale(.03f);
				m3d_Cube.setMaterial(cloudmat2);
				m3d_Cube.setDoubleSided(true);
				m3d_Cube.setColor(0);
				getCurrentScene().addChild(m3d_Cube);
				objParser.clear();
			}
			/*updateTimeBitmap();
			TimeUpdate();
			updateFreeBitmap();
			FreeUpdate();
			updateCoinBitmap();
			CoinUpdate();
			updateScoreBitmap();
			ScoreUpdate();
			updateExtraBitmap();
			ExtraUpdate();
			*/
			mBGVactor = new Backgraund[15];
			for (int i = 0; i < mBGVactor.length; i++) {
				mBGVactor[i] = new Backgraund();
			}
			mCar = new Car[M.CAR];
			for (int i = 0; i < mCar.length; i++) {
				mCar[i] = new Car();
			}
			mWoods = new Wood[M.WOOD];
			for (int i = 0; i < mWoods.length; i++) {
				mWoods[i] = new Wood();
			}
			mTrees = new Tree[M.TREE];
			for (int i = 0; i < mTrees.length; i++) {
				mTrees[i] = new Tree();
			}
			
			
			mTears = new Tears[M.TEAR];
			for (int i = 0; i < mTears.length; i++) {
				mTears[i] = new Tears();
			}
			mFire = new Fire[M.FIRE];
			for (int i = 0; i < mFire.length; i++) {
				mFire[i] = new Fire();
			}
			mTrain = new Train[M.LIGHT];
			for (int i = 0; i < mTrain.length; i++) {
				mTrain[i] = new Train();
			}
			
			mWater = new Water[M.DROP];
			for (int i = 0; i < mWater.length; i++) {
				mWater[i] = new Water();
			}
			isbuyPlayer = new boolean[M.PLAYER];
			isbuyPlayer[0]= true;
//			for(int i =1;i<isbuyPlayer.length;i++){
//				isbuyPlayer[i] = M.mRand.nextBoolean();
//			}
			mCent = new Cent[M.CENT];
			for(int i =0;i< mCent.length;i++){
				mCent[i] = new Cent();
			}
			mEgle = new Egle();
			
			mPlayer = new Player();
			gameReset(true);
			mStart.resume();
			setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void gameReset(final boolean isStart) {
		giftNo = M.mRand.nextInt(6);
		type = 1;
		noTime = 4;
		int newobj = 0;
		for (int i = 0; i < mCar.length; i++) {
			m3d_Car[i].setPosition(0, -40, 0);
			m3d_Car[i].setScale(.023f);
			m3d_Car[i].setRotation(0, 0, 0);
			m3d_Car[i].setPosition(0, -40, 0);
			mCar[i].set(-1,0.1f);
		}
		for (int i = 0; i < mWoods.length; i++) {
			mWoods[i].set(-1,0.1f);
		}
		for (int i = 0; i < mTrees.length; i++) {
			mTrees[i].set(-1);
		}
		
		for (int i = 0; i < m3d_BGObj.length; i++) {
			for (int j = 0; j < m3d_BGObj[i].length; j++) {
				m3d_BGObj[i][j].setPosition(0, -40, 0);
			}
		}
		
		for (int i = 0; i < mBGVactor.length; i++) {
			for (int j = 0; j < m3d_BGObj.length; j++) {
				newobj = getObj(type);
				if (newobj >= 0) {
					// mBGVactor[i].set(type, newobj, -0.99f + .22f * i);
					mBGVactor[i].set(type, newobj, M.MIN2 + .268f * i);
					m3d_BGObj[type][newobj].setY(mBGVactor[i].y);
					if(i > 5){
						switch (type) {
						case 0://Road
							root.setCar(i);
							break;
						case 1://Gross
							root.setTree(i);
							break;
						case 2://Water
							root.setWood(i);
							break;
						case 3://Train
	//						root.setTree(i);
							break;
						}
					}
					break;
				} else {
					type++;
					type %= m3d_BGObj.length;
					noTime++;
				}
			}
			noTime--;
			if (noTime < 0) {
				type = type == 0?1:0;
				noTime = M.mRand.nextInt(m3d_BGObj.length) + 1;
			}
		}
		int ge[] = new int[isbuyPlayer.length];
		int ge1[] = new int[isbuyPlayer.length];
		int oldone =0;
		int newone =0;
		for(int i =1;i<isbuyPlayer.length;i++){
			if(!isbuyPlayer[i]){
				ge[newone] = i;
				newone++;
			}else{
				ge1[oldone] = i;
				oldone++;
			}
		}
		giftNo = M.mRand.nextInt(3);
		switch (giftNo) {
		case 0:
			m2d_RPati.setPosition(.73, 2, 3);
			m2d_Bpati.setPosition(.92, -1, 3);
			break;
		case 1:
			m2d_Bpati.setPosition(.92, 2, 3);
			m2d_SPati.setPosition(.92, -1, 3);
			break;
		case 2:
			m2d_RPati.setPosition(.73, 2, 3);
			m2d_SPati.setPosition(.92, -1, 3);
			break;
		}
		if (newone == 0) {
			switch (giftNo) {
			case 0:
				System.out.println(m2d_Bpati.getY()+"  ^^^^^^^^^^^^^^^^^  "+m2d_RPati.getY());
				break;
			case 1:
				if(isPeggie)
					giftNo =100;
				break;
			case 2:
				 giftNo =100;
				break;
			}
			
		}
		mPlayer.set(0, mBGVactor[3].y,3,oldone == 0?0:ge1[M.mRand.nextInt(oldone)],newone == 0?-1:ge[M.mRand.nextInt(newone)]);
		for (int i = 0; i < mTrain.length; i++) {
			mTrain[i].set(-10,0);
		}
		for (int i = 0; i < mWater.length; i++) {
			m3d_Drop[i].setPosition(-100, 0, 0);
		}
		for(int i =0;i< mCent.length;i++){
			mCent[i].reset();
			m2d_Cent[i].setPosition(0, -100, 0);
		}
		mScore =0;
		giftMove =0;
		M.animalStop();
		/*System.out.println(mStart.isVideoAvail+"  isStart  "+isStart);
		if(!isStart && forAdColony+120000 < System.currentTimeMillis() && !mStart.isVideoAvail){
			forAdColony = System.currentTimeMillis();
			mStart.isVideoAvail = false;
//			mStart.LoadAdColony();
		}*/
			
		if(!isStart && adsCount%3==0){
			mStart.load();
		}
		adsCount++;
	}
	int adsCount =0;
	int getObj(int type) {
		for (int i = 0; i < m3d_BGObj[type].length; i++) {
			if (m3d_BGObj[type][i].getY() < M.MIN2) {
				return i;
			}
		}
		return -1;
	}

	public void Box() {
		m3d_Tree = new Object3D[M.TREE];
		for (int i = 0; i < m3d_Tree.length; i++) {
			try {
				Material cloudmat2 = new Material();
				switch (i%8) {
				case 0:
					objParser = new Loader3DSMax(this, R.raw.ston);
					cloudmat2.addTexture(new Texture("tree" + i, LoadImgfromAsset("ston.png")));
					break;
				case 1:
					objParser = new Loader3DSMax(this, R.raw.ston);
					cloudmat2.addTexture(new Texture("tree" + i, LoadImgfromAsset("ston2.png")));
					break;
				case 2:
					objParser = new Loader3DSMax(this, R.raw.tree);
					cloudmat2.addTexture(new Texture("tree" + i, LoadImgfromAsset("tree.png")));
					break;
				case 3:
					objParser = new Loader3DSMax(this, R.raw.tree2);
					cloudmat2.addTexture(new Texture("tree" + i, LoadImgfromAsset("tree2.png")));
					break;
				case 4:
					objParser = new Loader3DSMax(this, R.raw.tree3);
					cloudmat2.addTexture(new Texture("tree" + i, LoadImgfromAsset("tree3.png")));
					break;
				case 5:
					objParser = new Loader3DSMax(this, R.raw.build);
					cloudmat2.addTexture(new Texture("tree" + i, LoadImgfromAsset("build.png")));
					break;
				case 6:
					objParser = new Loader3DSMax(this, R.raw.build2);
					cloudmat2.addTexture(new Texture("tree" + i, LoadImgfromAsset("build2.png")));
					break;
				default:
					objParser = new Loader3DSMax(this, R.raw.build3);
					cloudmat2.addTexture(new Texture("tree" + i, LoadImgfromAsset("build3.png")));
					break;
				
				}
				objParser.parse();
				m3d_Tree[i] = objParser.getParsedObject();
				m3d_Tree[i].setScale(.04f);
				m3d_Tree[i].setPosition(0, -3 + i * .5, i%8>2?-.14:-.07);
//				m3d_Tree[i].setPosition(0, -3 + i * .5, 0);
				if(i%8==2 || i%8==3)
					m3d_Tree[i].setPosition(0, -3 + i * .5, i%8==3 ? -.04f:-.01);
				m3d_Tree[i].setMaterial(cloudmat2);
				m3d_Tree[i].setColor(0);
				getCurrentScene().addChild(m3d_Tree[i]);
				objParser.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	Plane addPlan(String str) {
		try {

			Plane plan;
			Bitmap bitID = LoadImgfromAsset(str);
			Material material = new Material();
			material.addTexture(new Texture(str.replace('.', '_'), bitID));
			material.setColorInfluence(0);
			plan = new Plane(bitID.getWidth() / 256f, bitID.getHeight() / 256f,1, 1);
			plan.setMaterial(material);
			plan.setRotation(180, 0, 180);
			plan.setVisible(false);
//			plan.setScale(.1);
			plan.setTransparent(true);
			return plan;
		} catch (Exception e) {
			return null;
		}
	}

	Plane addPlan(String str, Bitmap bitID) {
		try {

			Plane plan;
			// Bitmap bitID = LoadImgfromAsset(str);
			Material material = new Material();
			material.addTexture(new Texture(str.replace('.', '_'), bitID));
			material.setColorInfluence(0);
			plan = new Plane(bitID.getWidth() / 128f, bitID.getHeight() / 128f,
					1, 1);
			plan.setMaterial(material);
			plan.setTransparent(true);
			plan.setRotation(180, 0, 180);
			return plan;
		} catch (Exception e) {
			return null;
		}
	}

	Bitmap LoadImgfromAsset(String ID) {
		try {
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
		} catch (Exception e) {
			System.out
					.println("["
							+ ID
							+ "]   !!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"
							+ e.getMessage());
			return null;
		}
	}
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
//	private Handler handlerF = new Handler() {public void handleMessage(Message msg) {mStart.adfull.setVisibility(msg.what);}};
	
	public void onDrawFrame(GL10 glUnused) {
		root.Draw();
		
		if (mStart.adView != null) {
			if (M.GameScreen == M.GAMEPLAY) {
				int inv = mStart.adView.getVisibility();
				if (inv == AdView.GONE) {
					try {
						handler.sendEmptyMessage(AdView.VISIBLE);
					} catch (Exception e) {
					}
				}
			} else {
				int inv = mStart.adView.getVisibility();
				if (inv == AdView.VISIBLE) {
					try {
						handler.sendEmptyMessage(AdView.GONE);
					} catch (Exception e) {
					}
				}
			}
		}
		/*if (mStart.adfull != null) {
			if (M.GameScreen == M.GAMEOVER || M.GameScreen == M.GAMEPAUSE) {
				int inv = mStart.adfull.getVisibility();
				if (inv == AdView.GONE) {
					try {
						handlerF.sendEmptyMessage(AdView.VISIBLE);
					} catch (Exception e) {
					}
				}
			} else {
				int inv = mStart.adfull.getVisibility();
				if (inv == AdView.VISIBLE) {
					try {
						handlerF.sendEmptyMessage(AdView.GONE);
					} catch (Exception e) {
					}
				}
			}
		}*/
		
		super.onDrawFrame(glUnused);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(mPlayer!=null)
			root.onTouch(v, event);
		return true;
	}

	void setVisible(boolean visible) {
		oLogo.setVisible(visible);
		for (int i = 0; i < m3d_BGObj.length; i++) {
			for (int j = 0; j < m3d_BGObj[i].length; j++) {
				if (visible != m3d_BGObj[i][j].isVisible())
					m3d_BGObj[i][j].setVisible(visible);
			}
		}
		for (int i = 0; i < m3d_Car.length; i++) {
			if (visible != m3d_Car[i].isVisible())
				m3d_Car[i].setVisible(visible);
		}
		for (int i = 0; i < m3d_Wood.length; i++) {
			if (visible != m3d_Wood[i].isVisible())
				m3d_Wood[i].setVisible(visible);
		}
		for (int i = 0; i < m3d_Tree.length; i++) {
			if (visible != m3d_Tree[i].isVisible())
				m3d_Tree[i].setVisible(visible);
		}
		for (int i = 0; i < m3d_Drop.length; i++) {
			if (visible != m3d_Drop[i].isVisible())
				m3d_Drop[i].setVisible(visible);
		}
		for (int i = 0; i < m3d_Train.length; i++) {
			if (visible != m3d_Train[i].isVisible())
				m3d_Train[i].setVisible(visible);
		}
		for (int i = 0; i < m3d_Signel.length; i++) {
			if (visible != m3d_Signel[i].isVisible())
				m3d_Signel[i].setVisible(visible);
		}
		for (int i = 0; i < m3d_Player.length; i++) {
			if (visible != m3d_Player[i].isVisible())
				m3d_Player[i].setVisible(visible);
		}
		for (int i = 0; i < m2d_Tlight.length; i++) {
			if (visible != m2d_Tlight[i].isVisible())
				m2d_Tlight[i].setVisible(visible);
		}
		for (int i = 0; i < m2d_TRed.length; i++) {
			if (visible != m2d_TRed[i].isVisible())
				m2d_TRed[i].setVisible(visible);
		}
		for (int i = 0; i < m3d_GiftBox.length; i++) {
			if (m3d_GiftBox[i].isVisible())
				m3d_GiftBox[i].setVisible(visible);
		}
		for (int i = 0; i < m2d_Sleeping.length; i++) {
			if (m2d_Sleeping[i].isVisible())
				m2d_Sleeping[i].setVisible(false);
		}
		for (int i = 0; i < m2d_Tears.length; i++) {
			if (m2d_Tears[i].isVisible())
				m2d_Tears[i].setVisible(false);
		}
		for (int i = 0; i < m2d_Fire.length; i++) {
			if (m2d_Fire[i].isVisible())
				m2d_Fire[i].setVisible(false);
		}
		for(int i = 0 ;i<m3d_Bat.length;i++){
			m3d_Bat[i].setVisible(visible);
		}
		for (int i = 0; i < m2d_Hand.length; i++){
			m2d_Hand[i].setVisible(visible);
		}
		m2d_LBoard.setVisible(visible);
		m2d_Achiv.setVisible(visible);
		m2d_char.setVisible(visible);
		m2d_Play.setVisible(visible);
		m2d_Back.setVisible(visible);
		m2d_Sound[0].setVisible(visible);
		m2d_Sound[1].setVisible(visible);

		m2d_RPati.setVisible(visible);
		m2d_Bpati.setVisible(visible);
		m2d_SPati.setVisible(visible);
		m2d_EPati.setVisible(visible);
		m2d_Win.setVisible(visible);
		m2d_Gift.setVisible(visible);
		m2d_Val.setVisible(visible);
		m2d_100.setVisible(visible);
		oTime.setVisible(visible);
		oCoin.setVisible(visible);
		oScore.setVisible(visible);
		oExtra.setVisible(visible);
		oFree.setVisible(visible);
		m2d_Tran.setVisible(visible);

		m2d_Google.setVisible(visible);
		m2d_facebook.setVisible(visible);
		m2d_share.setVisible(visible);
		for (int i = 0; i < m2d_Cent.length; i++) {
			m2d_Cent[i].setVisible(visible);
		}
		m3d_Egle.setVisible(visible);
		m3d_Mashin.setVisible(visible);
		m3d_Gift.setVisible(visible);
		m3d_Trance.setVisible(visible);
		m3d_Cube.setVisible(visible);
		m2d_Crosy.setVisible(visible);
		m2d_Ebutn.setVisible(visible);
		m2d_DEPati.setVisible(visible);
	}
	boolean mShouldUpdateTime = false;
	public void updateTimeBitmap() {
		/*new Thread(new Runnable() {
			public void run() {*/
				if (mTimeCanvas == null) {
					mTimeCanvas = new Canvas(mTimeBitmap);
					mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
					Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"andyb.ttf");
					mPaint.setTypeface(tf);
					mPaint.setColor(Color.WHITE);
					mPaint.setTextSize(22);
				}
				mTimeCanvas.drawColor(0, Mode.CLEAR);
				mPaint.setColor(Color.RED);
				mPaint.setTextSize(22);
				if (giftNo == 0) {
					if (cent > 99)
						mTimeCanvas.drawText("Win Prices", 0, 64, mPaint);
					else
						mTimeCanvas.drawText((100 - cent) + " to go", 0, 64, mPaint);
				} else {
					mPaint.setTextSize(20);
					mTimeCanvas.drawText(M.NAME[mPlayer.buyChar], 0, 64, mPaint);
					mTimeCanvas.drawText("      0.99", 0, 105, mPaint);
				}
				mShouldUpdateTime = true;
				TimeUpdate();
			/*}
		}).start();*/
	}
	void TimeUpdate(){
		if (mShouldUpdateTime) {
			mTimeTexture.setBitmap(mTimeBitmap);
			mTextureManager.replaceTexture(mTimeTexture);
			mShouldUpdateTime = false;
		}
	}
	
	boolean mShouldUpdateFree = false;
	public void updateFreeBitmap() {
		/*new Thread(new Runnable() {
			public void run() {*/
				if (mFreeCanvas == null) {
					mFreeCanvas = new Canvas(mFreeBitmap);
					mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
					Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"andyb.ttf");
					mPaint.setTypeface(tf);
					mPaint.setColor(Color.WHITE);
					mPaint.setTextSize(22);
				}
				mFreeCanvas.drawColor(0, Mode.CLEAR);
				mPaint.setColor(Color.RED);
				mPaint.setTextSize(22);
				if (M.GameScreen == M.GAMEGIFT){
					mFreeCanvas.drawText("Cent : "+FreeCoin, 0,64, mPaint);
				}
				else if(M.GameScreen == M.GAMESHOP){
					mFreeCanvas.drawText("0.99", 0,64, mPaint);
				}else{
					if(giftNo == 0){
						long mmtme =System.currentTimeMillis() - time;
						if(mmtme > 21600000){
							mFreeCanvas.drawText("Free Gift", 0,64, mPaint);
						}else{
							mmtme = 21600000-mmtme;
							mFreeCanvas.drawText("Free Gift in "+(mmtme/3600000)+":"+((mmtme/60000)%60), 0,64, mPaint);
						}
	//				
	//				mFreeCanvas.drawText("Free Gift", 0,64, mPaint);
					}else{
						mFreeCanvas.drawText("Peggie Bank     4.99", 0,64, mPaint);
					}
				}
				mShouldUpdateFree = true;
				FreeUpdate();
			/*}
		}).start();*/
	}
	void FreeUpdate(){
		if (mShouldUpdateFree) {
			mFreeTexture.setBitmap(mFreeBitmap);
			mTextureManager.replaceTexture(mFreeTexture);
			mShouldUpdateFree = false;
		}
	}
	boolean mShouldUpdateCoin = false;
	public void updateCoinBitmap() {
		/*new Thread(new Runnable() {
			public void run() {*/
				if (mCoinCanvas == null) {
					mCoinCanvas = new Canvas(mCoinBitmap);
					mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
					Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"andyb.ttf");
					mPaint.setTypeface(tf);
					mPaint.setColor(Color.WHITE);
				}
				mCoinCanvas.drawColor(0, Mode.CLEAR);
				mPaint.setColor(Color.RED);
//				mCoinCanvas.drawText("Peggie Bank", 0,15, mPaint);
				mPaint.setTextSize(20);
				if (M.GameScreen == M.GAMESHOP) {
					if (root.shopimg < 0) {
						mCoinCanvas.drawText("Random", 0, 40, mPaint);
					} else {
						mCoinCanvas.drawText(M.NAME[root.shopimg
								% M.NAME.length], 0, 40, mPaint);
					}
				} else {
					if (giftNo == 2) {
						mCoinCanvas.drawText("Earn coin faster &", 0, 40, mPaint);
						mCoinCanvas.drawText("Get Bonus 1000 coin", 0, 62, mPaint);
					} else {
						mPaint.setTextSize(20);
						mCoinCanvas.drawText("Buy now and get", 0, 40, mPaint);
						mCoinCanvas.drawText("Bonus 250 coin", 0, 62, mPaint);
					}
				}
				mShouldUpdateCoin = true;
				CoinUpdate();
			/*}
		}).start();*/
	}
	void CoinUpdate(){
		if (mShouldUpdateCoin) {
			mCoinTexture.setBitmap(mCoinBitmap);
			mTextureManager.replaceTexture(mCoinTexture);
			mShouldUpdateCoin = false;
		}
	}
	boolean mShouldUpdateScore = false;
	public void updateScoreBitmap() {
		/*new Thread(new Runnable() {
			public void run() {*/
				if (mScoreCanvas == null) {
					mScoreCanvas = new Canvas(mScoreBitmap);
					mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
					Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"andyb.ttf");
					mPaint.setTypeface(tf);
				}
				mPaint.setTextSize(20);
				mScoreCanvas.drawColor(0, Mode.CLEAR);
				mScoreCanvas.drawText("Score: "+mScore+"    Coin:"+cent, 0, 40, mPaint);
				if(M.GameScreen == M.GAMEOVER)
					mScoreCanvas.drawText("Best Score:"+mHScore , 0, 62, mPaint);
				mShouldUpdateScore = true;
				ScoreUpdate();
			/*}
		}).start();*/
	}
	void ScoreUpdate(){
		if (mShouldUpdateScore) {
			mScoreTexture.setBitmap(mScoreBitmap);
			mTextureManager.replaceTexture(mScoreTexture);
			mShouldUpdateScore = false;
		}
	}
	boolean mShouldUpdateExtra = false;
	public void updateExtraBitmap() {
		
		/*new Thread(new Runnable() {
			public void run() {*/
				if (mExtraCanvas == null) {
					mExtraCanvas = new Canvas(mExtraBitmap);
					mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
					Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"andyb.ttf");
					mPaint.setTypeface(tf);
				}
				mPaint.setColor(Color.RED);
				mPaint.setTextSize(25);
				mExtraCanvas.drawColor(0, Mode.CLEAR);
				mExtraCanvas.drawText("Get Extra Coin", 0, 40, mPaint);
				mShouldUpdateExtra = true;
				ExtraUpdate();
			/*}
		}).start();*/
	}
	void ExtraUpdate(){
		if (mShouldUpdateExtra) {
			mExtraTexture.setBitmap(mExtraBitmap);
			mTextureManager.replaceTexture(mExtraTexture);
			mShouldUpdateExtra = false;
		}
	}
}
