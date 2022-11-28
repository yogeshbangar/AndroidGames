package com.oneday.games24.fearlessmotoracing3d;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import rajawali.animation.mesh.VertexAnimationObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.textures.AlphaMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.parser.LoaderMD2;
import rajawali.parser.ParsingException;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

public class HTTRenderer extends RajawaliRenderer  implements OnTouchListener, SensorEventListener {
	
	static Start mStart;
	static Context mContext;
	DirectionalLight mLight[];
	
	VertexAnimationObject3D[] o3d_Tree[], o3d_Road,o3d_Coin;
	VertexAnimationObject3D o3d_Top,o3d_Boost;
	
	Player mPlayer;
	Opponent mOpp[];
	Plane oPaused, ofb, oGoogle, oHTT, oAchieve, oHelp, oGlow, oStart, oScore;
	Plane oBoard, oGameOver, oPlayTime, oTotalCoin, oPlay, oRetry, oMenu_bg;
	Plane oShop_BG, oMenu, oPower, /*oHelp_Txt,*/ oPlayTxt, oPurchase, oAdsFree;
	Plane oBack,OBuy, oCanT, oLogo, oSArrow, oMeter,oCircle,oSplash;
	Plane[] /*oMusic, oSencer,*/ oSound, oIcnBtn, oPowerBar,oArrow,oSide[];
	
	SensorManager mSensorManager;
	
	Plane oChallenge;
	Bitmap mChallengeBitmap;
	Canvas mChallengeCanvas;
	Paint mChallengePaint;
	AlphaMapTexture mChallengeTexture;
	
	Plane oTime;
	Bitmap mTimeBitmap;
	Canvas mTimeCanvas;
	Paint mTextPaint;
	AlphaMapTexture mTimeTexture;
	
	Plane oCoin;
	Bitmap mCoinBitmap;
	Canvas mCoinCanvas;
	AlphaMapTexture mCoinTexture;
	
	
	boolean bike[] = {false,true,true};
	
	byte TreVal[];
	
	int Challenge =0;
	static int mCoin = 0;
	static boolean addFree = false;
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	int mScore = 0;
	int mHScore = 0;
	long mTime = 0;
	
	final float width = 5f;
	
	Group root;
	boolean chalage =false;
	int ChallengeCom = 0;
	
	public HTTRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mContext =context;
		mStart = (Start)context;
		mSensorManager = (SensorManager) Start.mContext.getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
		root = new Group(this);

		// TODO Auto-generated constructor stub
	}
	protected void initScene() {
		try {
			
			
			
			mSurfaceView.setOnTouchListener(this);
			mLight = new DirectionalLight[4];
			for(int i =0;i<mLight.length;i++){
				if(i==1){
					mLight[i] = new DirectionalLight(0.0f, 5.0f, 0.0f); // set the direction
					mLight[i].setColor(1.0f, 1.0f, 1.0f);
					mLight[i].setPower(.7f);
				}
				else if(i==0){
					mLight[i] = new DirectionalLight(0.0f, 0.0f, 5.0f); // set the direction
					mLight[i].setColor(1f, 1f, 1f);
					mLight[i].setPower(.5f);
				}else if(i==2){
					mLight[i] = new DirectionalLight(-.5f, 0.0f, 0.0f); // set the direction
					mLight[i].setColor(1.0f, 1.0f, 1.0f);
					mLight[i].setPower(1);
				}
				else {
					mLight[i] = new DirectionalLight(5.0f, 0.0f, 0.0f); // set the direction
					mLight[i].setColor(1.0f, 1.0f, 1.0f);
					mLight[i].setPower(1);
				}
				getCurrentScene().addLight(mLight[i]);
			}
			
			oLogo = addPlan("hututugames.png");
			oLogo.setRotation(180, 0, 180);
			oLogo.setPosition(0,0, -1.939);
			oLogo.setTransparent(true);
			getCurrentScene().addChild(oLogo);
			getCurrentCamera().setZ(6);
			
						
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	void init() {
		try {
			oSide = new Plane[4][];
			for (int i = 0; i < oSide.length; i++) {
				oSide[i] = new Plane[2];
				for (int j = 0; j < oSide[i].length; j++) {
					oSide[i][j] = addPlan("road"+i+".png");
					oSide[i][j].setRotation(180, 0, 180);
					oSide[i][j].setPosition(0, -11 + j * 22.2, 0);
					oSide[i][j].setScale(5.91, 11.11, 1);
					getCurrentScene().addChild(oSide[i][j]);
				}
			}
			{
				o3d_Road = new VertexAnimationObject3D[8];
				for (int i = 0; i < o3d_Road.length; i++) {
					LoaderMD2 rod = new LoaderMD2(mContext.getResources(),mTextureManager, R.raw.raod_1);
					rod.parse();
					o3d_Road[i] = (VertexAnimationObject3D) rod.getParsedAnimationObject();
					o3d_Road[i].setScale(.01f * width);
					o3d_Road[i].setPosition(0, i * (width - .02), 0);
					o3d_Road[i].setBackSided(true);
					getCurrentScene().addChild(o3d_Road[i]);
				}
			}
			
			{
				o3d_Coin = new VertexAnimationObject3D[10];
				for (int i = 0; i < o3d_Coin.length; i++) {
					LoaderMD2 rod = new LoaderMD2(mContext.getResources(),mTextureManager, R.raw.coin);
					rod.parse();
					o3d_Coin[i] = (VertexAnimationObject3D) rod.getParsedAnimationObject();
					o3d_Coin[i].setScale(.08);
					o3d_Coin[i].setPosition(0, 20 * (width - .02), 0);
					o3d_Coin[i].setBackSided(true);
					getCurrentScene().addChild(o3d_Coin[i]);
				}
			}
			
			LoaderMD2 parPlayer1 = new LoaderMD2(mContext.getResources(),mTextureManager, R.raw.bike1);
			LoaderMD2 parPlayer2 = new LoaderMD2(mContext.getResources(),mTextureManager, R.raw.bike2);
			LoaderMD2 parPlayer3 = new LoaderMD2(mContext.getResources(),mTextureManager, R.raw.bike3);
			LoaderMD2 o3dplyr = new LoaderMD2(mContext.getResources(), mTextureManager, R.raw.player);
			LoaderMD2 o3dExd = new LoaderMD2(mContext.getResources(), mTextureManager, R.raw.player1);
			parPlayer1.parse();
			parPlayer2.parse();
			parPlayer3.parse();
			o3dplyr.parse();
			o3dExd.parse();
			mPlayer = new Player(parPlayer1,parPlayer2,parPlayer3,o3dplyr,o3dExd,addPlan("shadow0.png"));
			
			getCurrentScene().addChild(mPlayer.obj[0]);
			getCurrentScene().addChild(mPlayer.obj[1]);
			getCurrentScene().addChild(mPlayer.obj[2]);
			getCurrentScene().addChild(mPlayer.o3d_Player);
			getCurrentScene().addChild(mPlayer.o3d_PExd);
			
			
			mOpp = new Opponent[10];
			for (int i = 0; i < mOpp.length; i++) {
				int id = R.raw.pickup_car;
				switch(i%5){
				case 1:
					id = R.raw.tata_pickup;
					break;
				case 2:
					id = R.raw.container;
					break;
				case 3:
					id = R.raw.nano;
					break;
				case 4:
					id = R.raw.sedan2;
					break;
				default:
					id = R.raw.pickup_car;
					break;
				}
				LoaderMD2 parCar = new LoaderMD2(mContext.getResources(),mTextureManager, id);
				parCar.parse();
				mOpp[i] = new Opponent(parCar,addPlan("shadow.png"),addPlan("indicator.png"));
				getCurrentScene().addChild(mOpp[i].obj);
				getCurrentScene().addChild(mOpp[i].oShadow);
			}
			
			TreVal = new byte[4];
			o3d_Tree = new VertexAnimationObject3D[4][4];
			int id = R.raw.ped;
			for (int i = 0; i < o3d_Tree.length; i++) {
				for (int j = 0; j < o3d_Tree[i].length; j++) {
					if (i == 0)
						id = R.raw.ped;
					if (i == 1)
						id = R.raw.tannel;// id = R.raw.pole;
					if (i == 2)
						id = R.raw.pole;// id = R.raw.city;
					if (i == 3)
						id = R.raw.city;// id = R.raw.tannel;

					LoaderMD2 partre = new LoaderMD2(mContext.getResources(),mTextureManager, id);
					partre.parse();
					o3d_Tree[i][j] = (VertexAnimationObject3D) partre.getParsedAnimationObject();
					if(i == 0){
						o3d_Tree[i][j].setPosition(2.5f * (j % 2 == 0 ? -1 : 1),(i * width*2) , 0);
						o3d_Tree[i][j].setScale(.03);
						o3d_Tree[i][j].setTransparent(true);
					}
					else{
						o3d_Tree[i][j].setScale(.01f * width);
						o3d_Tree[i][j].setPosition(0,(i * width*2) , 0);
					}
					if(i == 1 || i == 2){
						o3d_Tree[i][j].setScale(.01f * width,.02f * width,.01f * width);
					}
					o3d_Tree[i][j].setBackSided(true);
					
					getCurrentScene().addChild(o3d_Tree[i][j]);
				}
			}
			oSplash = addPlan("splash.png");
			oSplash.setRotation(180, 0, 180);
			oSplash.setPosition(0, 0, -1.94);
			oSplash.setTransparent(true);
			getCurrentScene().addChild(oSplash);
			
			oShop_BG = addPlan("selectionbg.png");
			oShop_BG.setRotation(180, 0, 180);
			oShop_BG.setPosition(0, 0, -1.94);
			oShop_BG.setTransparent(true);
			getCurrentScene().addChild(oShop_BG);

			{
				LoaderMD2 top = new LoaderMD2(mContext.getResources(),
						mTextureManager, R.raw.gear_box);
				top.parse();
				o3d_Top = (VertexAnimationObject3D) top
						.getParsedAnimationObject();
				o3d_Top.setScale(.006f);
				o3d_Top.setBackSided(true);
				o3d_Top.setTransparent(true);
				getCurrentScene().addChild(o3d_Top);
			}
			{
				LoaderMD2 boost = new LoaderMD2(mContext.getResources(),
						mTextureManager, R.raw.boost_effect);
				boost.parse();
				o3d_Boost = (VertexAnimationObject3D) boost
						.getParsedAnimationObject();
				o3d_Boost.setScale(.011f);
				o3d_Boost.setBackSided(true);
				o3d_Boost.setTransparent(true);
				getCurrentScene().addChild(o3d_Boost);
			}
					
			oMenu_bg = addPlan("menu_bg.png");
			oMenu_bg.setRotation(180, 0, 180);
			oMenu_bg.setPosition(0, 0, -1.94);
			oMenu_bg.setTransparent(true);
			getCurrentScene().addChild(oMenu_bg);

			oCircle = addPlan("blue_cir.png");
			oCircle.setPosition(0, -1.1f, 1.5f);
			oCircle.setScale(.56f);
			oCircle.setRotation(120, 0, 180);
			oCircle.setTransparent(true);
			getCurrentScene().addChild(oCircle);

			oBoard = addPlan("board.png");
			oBoard.setPosition(0, -1.1f, 1.5f);
			oBoard.setScale(.56f, .56f, .56f);
			oBoard.setRotation(120, 0, 180);
			oBoard.setTransparent(true);
			getCurrentScene().addChild(oBoard);

			oGameOver = addPlan("gameover.png");
			oGameOver.setPosition(0, -1.1f, 1.51f);
			oGameOver.setScale(.56f, .56f, .56f);
			oGameOver.setRotation(124, 0, 180);
			oGameOver.setTransparent(true);
			getCurrentScene().addChild(oGameOver);

			oPlayTime = addPlan("playedtime.png");
			oPlayTime.setPosition(0, -1.1f, 1.51f);
			oPlayTime.setScale(.56f, .56f, .56f);
			oPlayTime.setRotation(124, 0, 180);
			oPlayTime.setTransparent(true);
			getCurrentScene().addChild(oPlayTime);

			oTotalCoin = addPlan("totalcoins.png");
			oTotalCoin.setPosition(0, -1.1f, 1.51f);
			oTotalCoin.setScale(.5f, .5f, .5f);
			oTotalCoin.setRotation(124, 0, 180);
			oTotalCoin.setTransparent(true);
			getCurrentScene().addChild(oTotalCoin);

			oIcnBtn = new Plane[3];
			for (int i = 0; i < oIcnBtn.length; i++) {
				oIcnBtn[i] = addPlan("iconbtn.png");
				oIcnBtn[i].setRotation(124, 0, 180);
				oIcnBtn[i].setTransparent(true);
				getCurrentScene().addChild(oIcnBtn[i]);
			}

			oPlay = addPlan("play.png");
			oPlay.setRotation(120, 0, 180);
			oPlay.setTransparent(true);
			getCurrentScene().addChild(oPlay);

			oRetry = addPlan("retry.png");
			oRetry.setRotation(124, 0, 180);
			oRetry.setTransparent(true);
			getCurrentScene().addChild(oRetry);

			oMenu = addPlan("menu.png");
			oMenu.setRotation(124, 0, 180);
			oMenu.setTransparent(true);
			getCurrentScene().addChild(oMenu);

			oPaused = addPlan("gamepaused.png");
			oPaused.setRotation(120, 0, 180);
			oPaused.setScale(.56f, .56f, .56f);
			oPaused.setTransparent(true);
			getCurrentScene().addChild(oPaused);

			oBack = addPlan("back_btn.png");
			oBack.setRotation(114, 0, 180);
			oBack.setPosition(-13.5, 65.7, -1.939);
			oBack.setTransparent(true);
			getCurrentScene().addChild(oBack);

			OBuy = addPlan("buy.png");
			OBuy.setRotation(114, 0, 180);
			OBuy.setPosition(1.19, .54, -1.939);
			OBuy.setTransparent(true);
			getCurrentScene().addChild(OBuy);

			oPlayTxt = addPlan("play_sel.png");
			oPlayTxt.setRotation(114, 0, 180);
			oPlayTxt.setPosition(1.19, .54, -1.939);
			oPlayTxt.setTransparent(true);
			getCurrentScene().addChild(oPlayTxt);

			oPurchase = addPlan("purchas_price.png");
			oPurchase.setRotation(114, 0, 180);
			oPurchase.setPosition(-1, .52, -1.939);
			oPurchase.setScale(1);
			oPurchase.setTransparent(true);
			getCurrentScene().addChild(oPurchase);

			oCanT = addPlan("can_not.png");
			oCanT.setRotation(114, 0, 180);
			oCanT.setPosition(-.98, .39f, -1.939);
			oCanT.setScale(1);
			oCanT.setTransparent(true);
			getCurrentScene().addChild(oCanT);

//			oHelp_Txt = addPlan("help.png");
//			oHelp_Txt.setRotation(180, 0, 180);
//			oHelp_Txt.setPosition(0, 0, -1.939);
//			oHelp_Txt.setTransparent(true);
//			getCurrentScene().addChild(oHelp_Txt);

			oAdsFree = addPlan("addfree.png");
			oAdsFree.setRotation(114, 0, 180);
			oAdsFree.setScale(3);
			oAdsFree.setPosition(-.6, 19.3, -1.939);
			oAdsFree.setTransparent(true);
			getCurrentScene().addChild(oAdsFree);

			oPowerBar = new Plane[4];
			for (int i = 0; i < oPowerBar.length; i++) {
				oPowerBar[i] = addPlan("bar.png");
				oPowerBar[i].setRotation(114, 0, 180);
				oPowerBar[i].setPosition(0.0, 2.07, -1.939);
				oPowerBar[i].setTransparent(true);
				getCurrentScene().addChild(oPowerBar[i]);
			}
			oPower = addPlan("bar_trans.png");
			oPower.setRotation(114, 0, 180);
			oPower.setScale(0.74);
			oPower.setPosition(-0.18, -.59, -1.939);
			oPower.setTransparent(true);
			getCurrentScene().addChild(oPower);
			{
//				oMusic = new Plane[2];
//				oMusic[0] = addPlan("music.png");
//				oMusic[0].setRotation(180, 0, 180);
//				oMusic[0].setTransparent(true);
//				oMusic[0].setPosition(1.2, 2.07, -1.939);
//				getCurrentScene().addChild(oMusic[0]);
//
//				oMusic[1] = addPlan("music_off.png");
//				oMusic[1].setRotation(180, 0, 180);
//				oMusic[1].setTransparent(true);
//				oMusic[1].setPosition(1.2, 2.07, -1.939);
//				getCurrentScene().addChild(oMusic[1]);

				/*oSencer = new Plane[2];
				oSencer[0] = addPlan("sencer.png");
				oSencer[0].setRotation(180, 0, 180);
				oSencer[0].setTransparent(true);
				oSencer[0].setPosition(1.2, 2.07, -1.939);
				getCurrentScene().addChild(oSencer[0]);

				oSencer[1] = addPlan("sencer_off.png");
				oSencer[1].setRotation(180, 0, 180);
				oSencer[1].setPosition(1.2, 2.07, -1.939);
				oSencer[1].setTransparent(true);
				getCurrentScene().addChild(oSencer[1]);
*/
				oSound = new Plane[2];
				oSound[0] = addPlan("sound_on.png");
				oSound[0].setRotation(180, 0, 180);
				oSound[0].setPosition(-1.2, 2.07, -1.939);
				oSound[0].setTransparent(true);
				getCurrentScene().addChild(oSound[0]);

				oSound[1] = addPlan("sound_off.png");
				oSound[1].setRotation(180, 0, 180);
				oSound[1].setPosition(-1.2, 2.07, -1.939);
				oSound[1].setTransparent(true);
				getCurrentScene().addChild(oSound[1]);

				ofb = addPlan("facebook.png");
				ofb.setRotation(180, 0, 180);
				ofb.setPosition(-.75f, -2.8f, -1.939);
				ofb.setTransparent(true);
				getCurrentScene().addChild(ofb);

				oGoogle = addPlan("gp.png");
				oGoogle.setRotation(180, 0, 180);
				oGoogle.setTransparent(true);
				oGoogle.setPosition(0.0, -2.8f, -1.939);
				oGoogle.setPosition(.0f, -2.8f, -1.939);
				getCurrentScene().addChild(oGoogle);

				oHTT = addPlan("hututu.png");
				oHTT.setRotation(180, 0, 180);
				oHTT.setTransparent(true);
				oHTT.setPosition(0.75f, -2.8f, -1.939);
				getCurrentScene().addChild(oHTT);

				oStart = addPlan("start_spedo.png");
				oStart.setRotation(180, 0, 180);
				oStart.setTransparent(true);
				oStart.setPosition(0, .11f, -1.939);
				getCurrentScene().addChild(oStart);

				oAchieve = addPlan("achiv_speedo.png");
				oAchieve.setRotation(180, 0, 180);
				oAchieve.setTransparent(true);
				oAchieve.setPosition(1.03f, -0.53f, -1.939);
				getCurrentScene().addChild(oAchieve);

				oHelp = addPlan("help_spedo.png");
				oHelp.setRotation(180, 0, 180);
				oHelp.setTransparent(true);
				oHelp.setPosition(-1.03f, -0.53f, -1.939);
				getCurrentScene().addChild(oHelp);

				oGlow = addPlan("start_glow.png");
				oGlow.setRotation(180, 0, 180);
				oGlow.setTransparent(true);
				oGlow.setPosition(.06, -.07, -1.939);
				getCurrentScene().addChild(oGlow);

//				oIndicator = addPlan("indicator.png");
//				oIndicator.setPosition(0, -1.1f, 1.5f);
//				oIndicator.setScale(.56f);
//				oIndicator.setRotation(120, 0, 180);
//				oIndicator.setTransparent(true);
				

				
				
				oMeter = addPlan("spedo_gameplay.png");
				oMeter.setPosition(0, -1.1f, 1.5f);
				oMeter.setScale(.56f);
				oMeter.setRotation(120, 0, 180);
				oMeter.setTransparent(true);
				getCurrentScene().addChild(oMeter);

				oSArrow = addPlan("spedoline.png");
				oSArrow.setRotation(180, 0, 180);
				oSArrow.setPosition(0, 0, -1.939);
				oSArrow.setTransparent(true);
				getCurrentScene().addChild(oSArrow);

				oScore = addPlan("score_box.png");
				oScore.setRotation(180, 0, 180);
				oScore.setTransparent(true);
				oScore.setPosition(0, -1.85, -1.939);
				getCurrentScene().addChild(oScore);
				
				
				oArrow = new Plane[2];
				for (int i = 0; i < oArrow.length; i++) {
					oArrow[i] = addPlan("a1.png");
					oArrow[i].setRotation(114, 0, (i==0?0:180));
					oArrow[i].setScale(1.6);
					oArrow[i].setPosition(2.35*(i==0?-1:1), 6.1, -1.939);
					oArrow[i].setTransparent(true);
					getCurrentScene().addChild(oArrow[i]);
				}
				
				
				
				{
					Material ChallangeSphereMaterial = new Material();
					ChallangeSphereMaterial.enableLighting(true);
					ChallangeSphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
					mChallengeBitmap = Bitmap.createBitmap(256, 256,Config.ARGB_8888);
					mChallengeTexture = new AlphaMapTexture("ChallangeTexture", mChallengeBitmap);
					ChallangeSphereMaterial.addTexture(mChallengeTexture);
					ChallangeSphereMaterial.setColorInfluence(1);

					oChallenge = new Plane(2, 2, 1, 1);
					oChallenge.setMaterial(ChallangeSphereMaterial);
					oChallenge.setDoubleSided(true);
					oChallenge.setColor(Color.WHITE);
					oChallenge.setPosition(0, -1.1f, 1.5f);
					oChallenge.setScale(.56f, .56f, .56f);
					oChallenge.setRotation(180, 0, 180);
					oChallenge.setTransparent(true);
					getCurrentScene().addChild(oChallenge);
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
					oTime.setPosition(0, -1.1f, 1.5f);
					oTime.setScale(.56f, .56f, .56f);
					oTime.setRotation(120, 0, 180);
					oTime.setTransparent(true);
					getCurrentScene().addChild(oTime);
				}
				{
					Material CoinSphereMaterial = new Material();
					CoinSphereMaterial.enableLighting(true);
					CoinSphereMaterial
							.setDiffuseMethod(new DiffuseMethod.Lambert());
					mCoinBitmap = Bitmap.createBitmap(256, 128,
							Config.ARGB_8888);
					mCoinTexture = new AlphaMapTexture("CoinTexture",mCoinBitmap);
					CoinSphereMaterial.addTexture(mCoinTexture);
					CoinSphereMaterial.setColorInfluence(1);

					oCoin = new Plane(2, 1, 1, 1);
					oCoin.setMaterial(CoinSphereMaterial);
					oCoin.setDoubleSided(true);
					oCoin.setColor(Color.WHITE);
					oCoin.setPosition(0, -1.1f, 1.5f);
					oCoin.setScale(.56f, .56f, .56f);
					oCoin.setRotation(120, 0, 180);
					oCoin.setTransparent(true);
					getCurrentScene().addChild(oCoin);
				}
				for(int i =0;i<mOpp.length;i++)
					getCurrentScene().addChild(mOpp[i].oIndicator);
				// mCoin = mFrameCount;
				getCurrentScene().addChild(mPlayer.oShadow);
			}
			
			
			updateCoinBitmap();
			updateTimeBitmap();
			updateChallangeBitmap();
			getCurrentCamera().setZ(6);
			getCurrentCamera().setY(2);
			getCurrentCamera().setRotX(-60);
			setVisible(false);
			gameReset();
			mStart.resume();

		} catch (TextureException e) {
			e.printStackTrace();
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void gameReset(){
		M.stop();
		for(int i =0;i<mOpp.length;i++){
//			mOpp[i].set(.1f,(i+2)*10);
			mOpp[i].set(.1f,-10,0);
		}
		for(int i =0;i<TreVal.length;i++){
			TreVal[i] =0;
		}
		mPlayer.set();
		mTime = System.currentTimeMillis();
		chalage = true;
		ChallengeCom =0;
		mScore = 0;
		o3d_Boost.setScale(mPlayer.obj[0].getScale());
		for(int i =0;i<o3d_Coin.length;i++)
			o3d_Coin[i].setPosition(0,  i*(width - .02), 0);
		mStart.load();
	}
	Plane addPlan(String str) {
		try {
			
			Plane plan;
			Bitmap bitID = LoadImgfromAsset(str);
			Material material = new Material();
			material.addTexture(new Texture(str.replace('.', '_') ,bitID));
			material.setColorInfluence(0);
			plan = new Plane(bitID.getWidth()/128f,bitID.getHeight()/128f,1,1);
			plan.setMaterial(material);
			plan.setTransparent(true);
//			System.out.println("["+str+"]   Plan");
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
//			System.out.println("["+str+"]   Plan");
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
	boolean mShouldUpdateChallange = false;
	public void updateChallangeBitmap() {
		new Thread(new Runnable() {
			public void run() {
				if (mChallengeCanvas == null) {
					mChallengeCanvas = new Canvas(mChallengeBitmap);
					mChallengePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
					mChallengePaint.setColor(Color.WHITE);
					mChallengePaint.setTextSize(40);
				}
				int y =30;
				int diff =30;
				int x =128;
				mChallengePaint.setTextSize(diff);
				mChallengeCanvas.drawColor(0, Mode.CLEAR);
				mChallengePaint.setTextAlign(Align.CENTER);
				if(ChallengeCom > 0 && ChallengeCom <  200){
					mChallengeCanvas.drawText("~~Congrates~~", x, y, mChallengePaint);
					mChallengePaint.setTextSize(25);
					mChallengeCanvas.drawText("Challenge is Completed", x, y+diff, mChallengePaint);
				}else if(chalage){					
					mChallengePaint.setColor(Color.WHITE);
					mChallengeCanvas.drawText("Challenge", x, y, mChallengePaint);
					mChallengePaint.setTextSize(20);
					if(M.Challenges[Challenge%M.Challenges.length][0]!=0)
						mChallengeCanvas.drawText("Drive "+M.Challenges[Challenge%M.Challenges.length][0]+" meter  Safely", x, y+=diff, mChallengePaint);
					if(M.Challenges[Challenge%M.Challenges.length][1]!=0)
						mChallengeCanvas.drawText( (y != diff?"and ":"")+"Overtake "+M.Challenges[Challenge%M.Challenges.length][1]+" Cars",x,y+=diff, mChallengePaint);
					if(M.Challenges[Challenge%M.Challenges.length][2]!=0)
						mChallengeCanvas.drawText((y != diff?"and ":"")+"Collect "+M.Challenges[Challenge%M.Challenges.length][2]+" Coins",x,y+=diff, mChallengePaint);
					if(M.Challenges[Challenge%M.Challenges.length][3]!=0)
						mChallengeCanvas.drawText((y != diff?"In ":"Drive ")+M.Challenges[Challenge%M.Challenges.length][3]+" Min",x,y+=diff, mChallengePaint);
					mChallengeCanvas.drawText( "Get Free "+M.Challenges[Challenge%M.Challenges.length][4]+" Coins",x,y+=diff, mChallengePaint);
					mChallengePaint.setTextSize(diff);
					mChallengeCanvas.drawText("Tap To Continue", x, y+=diff, mChallengePaint);
				}else{
					mChallengePaint.setTextSize(10);
					mChallengeCanvas.drawText(mPlayer.tapBot+"", x, 64, mChallengePaint);
				}
				mShouldUpdateChallange = true;
			}
		}).start();
	}
	void ChallangeUpdate(){
		if (mShouldUpdateChallange) {
			mChallengeTexture.setBitmap(mChallengeBitmap);
			mTextureManager.replaceTexture(mChallengeTexture);
			mShouldUpdateChallange = false;
		}
	}
	
	
	
	
	
	
	
	
	boolean mShouldUpdateTime = false;
	public void updateTimeBitmap() {
		new Thread(new Runnable() {
			public void run() {
				if (mTimeCanvas == null) {
					mTimeCanvas = new Canvas(mTimeBitmap);
					mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
					mTextPaint.setColor(Color.WHITE);
					mTextPaint.setTextSize(22);
				}
				mTextPaint.setTextSize(22);
				mTimeCanvas.drawColor(0, Mode.CLEAR);
				if(M.GameScreen == M.GAMEPLAY || M.GameScreen == M.GAMEOVER){
					mTextPaint.setColor(Color.WHITE);
					mTextPaint.setTextSize(10);
					String str = "";
					long tim =mTime;
					if(M.GameScreen == M.GAMEPLAY)
						tim= System.currentTimeMillis() - mTime;

					int mili = (int)(tim/60000);
					if(mili<=0){
						str = "00";
					}else if(mili<=10){
						str = "0"+mili;
					}else {
						str = ""+mili;
					}
					mili = (int)(tim/1000)%60;
					if(mili<=0){
						str += ":00";
					}else if(mili<=10){
						str += ":0"+mili;
					}else {
						str += ":"+mili;
					}
					
					
					mTimeCanvas.drawText(str, 0,64, mTextPaint);
				}else if(M.GameScreen == M.GAMESHOP){
					if(bike[mPlayer.bike])
						mTimeCanvas.drawText(""+M.BIKEPRICE[mPlayer.bike], 0,64, mTextPaint);
					else
						mTimeCanvas.drawText("Coin Not Require", 0,64, mTextPaint);
				}else if(M.GameScreen == M.GAMEMENU){
					mTimeCanvas.drawText(getStr(7 ,mScore), 0,64, mTextPaint);
				}else{
					mTimeCanvas.drawText(""+mScore, 0,64, mTextPaint);
				}
				
				mShouldUpdateTime = true;
			}
		}).start();
	}
	void TimeUpdate(){
		if (mShouldUpdateTime) {
			mTimeTexture.setBitmap(mTimeBitmap);
			mTextureManager.replaceTexture(mTimeTexture);
			mShouldUpdateTime = false;
		}
	}
	boolean mShouldUpdateCoin = false;
	public void updateCoinBitmap() {
		new Thread(new Runnable() {
			public void run() {
				if (mCoinCanvas == null) {
					mCoinCanvas = new Canvas(mCoinBitmap);
					mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
					mTextPaint.setColor(Color.WHITE);
					mTextPaint.setTextSize(22);
				}
				mCoinCanvas.drawColor(0, Mode.CLEAR);
				mTextPaint.setTextSize(22);
				if(M.GameScreen == M.GAMEPLAY){
					String str = ((int)mPlayer.Distance)+"";
//					String str = (mPlayer.level+1)+"";
//					String str = mCoin+"";
					for(int i=0;i<5-(((int)mPlayer.Distance)+"").length();i++)
						str="0"+str;
					mTextPaint.setColor(Color.WHITE);
					mTextPaint.setTextSize(10);
					mCoinCanvas.drawText(str, 0,64, mTextPaint);
				}else if(M.GameScreen == M.GAMESHOP){
					String str = getStr(7 ,mCoin);
					mCoinCanvas.drawText(str, 0,64, mTextPaint);
				}else if(M.GameScreen == M.GAMEMENU){
					String str = getStr(6 ,mCoin);
					mCoinCanvas.drawText(str, 0,64, mTextPaint);
				}else{
					mCoinCanvas.drawText(""+mCoin, 0,64, mTextPaint);
				}
				
				
				
				if(M.GameScreen == M.GAMEOVER){
					
				}else{
					
				}
				mShouldUpdateCoin = true;
			}
		}).start();
	}
	void CoinUpdate(){
		if (mShouldUpdateCoin) {
			mCoinTexture.setBitmap(mCoinBitmap);
			mTextureManager.replaceTexture(mCoinTexture);
			mShouldUpdateCoin = false;
		}
	}

	void setVisible(boolean visible) {
		for (int i = 0; i < oSide.length; i++) {
			for (int j = 0; j < oSide[i].length; j++){
				oSide[i][j].setVisible(false);
			}
		}

		for (int i = 0; i < mOpp.length; i++) {
			mOpp[i].obj.setVisible(visible);
			mOpp[i].oShadow.setVisible(visible);
			mOpp[i].oIndicator.setVisible(visible);
		}
		for (int i = 0; i < o3d_Tree.length; i++) {
			for (int j = 0; j < o3d_Tree[i].length; j++)
				o3d_Tree[i][j].setVisible(visible);
		}
		for (int i = 0; i < o3d_Road.length; i++) {
			o3d_Road[i].setVisible(visible);
		}
		for (int i = 0; i < mPlayer.obj.length; i++)
			mPlayer.obj[i].setVisible(visible);
		for (int i = 0; i < oIcnBtn.length; i++) {
			oIcnBtn[i].setVisible(visible);
		}
		for (int i = 0; i < o3d_Coin.length; i++) {
			o3d_Coin[i].setVisible(visible);
		}
		oBoard.setVisible(visible);
		oGameOver.setVisible(visible);
		oPlayTime.setVisible(visible);
		oTotalCoin.setVisible(visible);
		oPlay.setVisible(visible);
		oRetry.setVisible(visible);
		oMenu.setVisible(visible);
		oTime.setVisible(visible);
		oCoin.setVisible(visible);
		oPaused.setVisible(visible);
		oBack.setVisible(visible);
		OBuy.setVisible(visible);
		oPlayTxt.setVisible(visible);
		oPurchase.setVisible(visible);
		oCanT.setVisible(visible);
		oLogo.setVisible(visible);
		oSArrow.setVisible(visible);
		oCircle.setVisible(visible);
		oMeter.setVisible(visible);
//		oHelp_Txt.setVisible(visible);
		oAdsFree.setVisible(visible);
		oPower.setVisible(visible);
		for (int i = 0; i < oPowerBar.length; i++)
			oPowerBar[i].setVisible(visible);
		oSplash.setVisible(visible);
		oShop_BG.setVisible(visible);
		oMenu_bg.setVisible(visible);
//		oMusic[0].setVisible(visible);
//		oMusic[1].setVisible(visible);
//		oSencer[0].setVisible(visible);
//		oSencer[1].setVisible(visible);
		oSound[0].setVisible(visible);
		oSound[1].setVisible(visible);
		ofb.setVisible(visible);
		oGoogle.setVisible(visible);
		oHTT.setVisible(visible);
		o3d_Boost.setVisible(visible);
		oAchieve.setVisible(visible);
		oHelp.setVisible(visible);
		oGlow.setVisible(visible);
		oStart.setVisible(visible);
		oScore.setVisible(visible);
		oChallenge.setVisible(visible);
		
		o3d_Top.setVisible(visible);
		mPlayer.o3d_Player.setVisible(visible);
		mPlayer.o3d_PExd.setVisible(visible);
		mPlayer.oShadow.setVisible(visible);
		
		for (int i = 0; i < oArrow.length; i++) {
			oArrow[i].setVisible(visible);
		}
		updateTimeBitmap();
		updateCoinBitmap();
	}

	String getStr(int no, int val) {
		int k = no - ("" + val).length();
		String str = "";
		if (k >= 0) {
			int i = 0;
			while (k > i) {
				str += "0 ";
				i++;
			}
			k = val;
			String m = "";
			while (k > 0) {
				m += k % 10;
				k /= 10;
			}
			i = m.length() - 1;
			while (i >= 0) {
				str += m.charAt(i) + " ";
				i--;
			}
		} else {
			str = "" + val;
		}
//		System.out.println(str + "~~~~~~~~~~~~~~~" + val);
		return str;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(mPlayer!=null)
			mPlayer.vx = -event.values[0]*mPlayer.Spd*.05f;
	}
}
	