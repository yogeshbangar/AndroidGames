package com.htt.games.dragracing3d;

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
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.parser.Loader3DSMax;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

public class HTTRenderer extends RajawaliRenderer implements OnTouchListener {

	static Start mStart;
	static Context mContext;
	
	Loader3DSMax objParser;
	Object3D m3D_Bike[];
	Object3D m3D_Frant[];
	Object3D m3D_Rear[];
	Object3D m3D_Opp[];
	Object3D m3D_Road[];
	Object3D m3D_Fancing[];
	Object3D m3D_Light,m3D_Block;
	
	Plane oLogo, m2D_GearShift, m2D_Meter, m2D_Handle, m2D_Kata, m2D_FLine;
	Plane m2D_Back, m2D_Hint, /*m2D_Shop,*/ m2D_Win, m2D_Map, m2D_GoB, m2D_Buy;
	Plane m2D_CareerB, m2D_Conti, m2D_Garage, m2D_Next, m2D_Sing, m2D_wlBG;
	Plane m2D_LevelBack, m2D_LevelBoxS,  m2D_Retry, m2D_Mapbox, m2D_ComnBG;
	Plane m2D_MapDot,  m2D_Lost, m2D_QRaceB, m2D_RaceB, m2D_ShopB;
	Plane m2D_Bar,m2D_Gbar;
	
	Plane[][] m2D_Engin, m2D_Exhaus, m2D_Nitro, m2D_Gear, m2D_Lock;
	Plane[] m2D_ICN, m2D_PowerBar, m2D_PowerFill, m2D_UpBox, m2D_Arrow,m2D_MapDot2;
	Plane[] m2D_Pointer, m2D_City,m2D_Shift,m2D_ShiftTxt;
	
	boolean gameUnlock[][];
	boolean gamePlayed[][];
	boolean mBikeUnlock	[]= new boolean[17];
	boolean mAchiUnlock	[]= new boolean[6];
	boolean mIsQuckRace = false;
	boolean citiUnlock = false;
	boolean SingUpadate= false;
	boolean addFree = false;
	boolean fromLevel = false;
	int mIsGameWin = 0;
	int city = 0;
	int start = 0;
	int mLevel =0;
	int mBike = 0;
	int mDoller = 10000099;
	int upgrade = 0;
	int waitCounter = 0;
	int mScore = 0;
	int showCount = 0;
	int mShiftGear =0;
	long Gamestart = 0;
	
	float mBikex = 0;
	float mBikevx = 0;
	float mFLine = 1.2f;
	float mTowerX = -.41f;
	float mSpeed = 0;
	float gamedistace = 0;
	Bike mPlayer;
	Bike mOpponent;
//	Shift mShift;
	Spec mSpec[];
	Animation mAnim[];
	Smock mSmock[];
	
	GamePlay mGamePlay;
	
	Paint mPaint;
	Typeface tf;
	
	
	
	Plane oCoin;
	Bitmap mCoinBitmap;
	Canvas mCoinCanvas;
	AlphaMapTexture mCoinTexture;
	
	Plane oSPD;
	Bitmap mSPDBitmap;
	Canvas mSPDCanvas;
	AlphaMapTexture mSPDTexture;
	
	Plane oDoler;
	Bitmap mDolerBitmap;
	Canvas mDolerCanvas;
	AlphaMapTexture mDolerTexture;
	
	
	Plane oUpdt;
	Bitmap mUpdtBitmap;
	Canvas mUpdtCanvas;
	AlphaMapTexture mUpdtTexture;
	
	
	Group root;
	public HTTRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mContext = context;
		mStart = (Start) context;
		root = new Group(this);
		mGamePlay = new GamePlay(this);
	}

	protected void initScene() {
		try {
			mSurfaceView.setOnTouchListener(this);
			oLogo = addPlan("logo.png");
			oLogo.setRotation(180, 0, 180);
			oLogo.setPosition(0, 0, 0);
			oLogo.setScale(5);
			oLogo.setTransparent(true);
			getCurrentScene().addChild(oLogo);
			oLogo.setVisible(true);
			tf = Typeface.createFromAsset(mContext.getAssets(),"andyb.ttf");
			
			mAnim = new Animation[8];
			for (int i = 0; i < 8; i++) {
				mAnim[i] = new Animation(i);
			}
			mSmock	= new Smock[10];
			for(int i=0;i<mSmock.length;i++)
				mSmock[i]	= new Smock();
			init();
			getCurrentScene().setBackgroundColor(Color.rgb(50, 52, 100));
			getCurrentCamera().setZ(20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void init() {
		try {
			m3D_Road = new Object3D[4];
			for(int i =0;i<m3D_Road.length;i++)
			{
				if(i==0){
					Material cloudmat2 = new Material();
					cloudmat2.addTexture(new Texture("road"+i,LoadImgfromAsset("bg.png")));
					objParser = new Loader3DSMax(this, R.raw.bg);
					objParser.parse();
					m3D_Road[i] = objParser.getParsedObject();
					m3D_Road[i].setMaterial(cloudmat2);
					objParser.clear();
				}else{
					m3D_Road[i] = m3D_Road[0].clone(true);
				}
				m3D_Road[i].setColor(0);
				m3D_Road[i].setScale(.01*M.ROAD);
				m3D_Road[i].setPosition(-50+i*M.ROAD*1.34, 0, 0);
				getCurrentScene().addChild(m3D_Road[i]);
			}
			m3D_Fancing = new Object3D[4];
			for(int i =0;i<m3D_Fancing.length;i++)
			{
				if (i < 2) {
					Material cloudmat2 = new Material();
					cloudmat2.addTexture(new Texture("fancing" + i,LoadImgfromAsset("fancing"+i+".png")));
					objParser = new Loader3DSMax(this, R.raw.fancing2);
					objParser.parse();
					m3D_Fancing[i] = objParser.getParsedObject();
					m3D_Fancing[i].setMaterial(cloudmat2);
					objParser.clear();
				} else{
					m3D_Fancing[i] = m3D_Fancing[i%2].clone(true);
				}
				m3D_Fancing[i].setColor(0);
				m3D_Fancing[i].setScale(.01*M.ROAD);
				m3D_Fancing[i].setPosition(-50+i*M.ROAD*1.34, 0, 0);
				getCurrentScene().addChild(m3D_Fancing[i]);
			}
			{
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("light",LoadImgfromAsset("light.png")));
				objParser = new Loader3DSMax(this, R.raw.light);
				objParser.parse();
				m3D_Light = objParser.getParsedObject();
				m3D_Light.setMaterial(cloudmat2);
				objParser.clear();
				
				m3D_Light.setColor(0);
				m3D_Light.setScale(.01*M.RMUL);
				m3D_Light.setPosition(0, 0, 0);
				getCurrentScene().addChild(m3D_Light);
			}
			{
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("block",LoadImgfromAsset("block.png")));
				objParser = new Loader3DSMax(this, R.raw.block);
				objParser.parse();
				m3D_Block = objParser.getParsedObject();
				m3D_Block.setMaterial(cloudmat2);
				objParser.clear();
				
				m3D_Block.setColor(0);
				m3D_Block.setScale(.01*M.ROAD);
				m3D_Block.setPosition(0, 0, 0);
				getCurrentScene().addChild(m3D_Block);
			}
			
			
			
			m3D_Bike = new Object3D[17];
			for(int i =0;i<m3D_Bike.length;i++)
			{
				Material cloudmat2 = new Material();
//				if(i%2==0)
//					cloudmat2.addTexture(new Texture("Bike"+i,LoadImgfromAsset("player1.png")));
//				else
//					cloudmat2.addTexture(new Texture("Bike"+i,LoadImgfromAsset("player2.png")));
				
				cloudmat2.addTexture(new Texture("Bike"+i,LoadImgfromAsset("bike.png")));
				objParser = new Loader3DSMax(this, R.raw.bike1);
				objParser.parse();
				m3D_Bike[i] = objParser.getParsedObject();
				m3D_Bike[i].setScale(.05f);
				m3D_Bike[i].setPosition(0, 1-2*i, 0);
				m3D_Bike[i].setRotZ(90);
				m3D_Bike[i].setColor(0);
				m3D_Bike[i].setMaterial(cloudmat2);
				getCurrentScene().addChild(m3D_Bike[i]);
				objParser.clear();
			}
			
			
			
			
			m3D_Frant = new Object3D[17];
			for(int i =0;i<m3D_Frant.length;i++)
			{
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("Bike"+i,LoadImgfromAsset("frontwh.png")));
				objParser = new Loader3DSMax(this, R.raw.frontwh);
				objParser.parse();
				m3D_Frant[i] = objParser.getParsedObject();
				m3D_Frant[i].setScale(.05f);
				m3D_Frant[i].setPosition(0, 1-2*i, 0);
				m3D_Frant[i].setRotZ(90);
				m3D_Frant[i].setColor(0);
				m3D_Frant[i].setMaterial(cloudmat2);
				getCurrentScene().addChild(m3D_Frant[i]);
				objParser.clear();
			}
			
			
			m3D_Rear = new Object3D[17];
			for(int i =0;i<m3D_Rear.length;i++)
			{
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("Bike"+i,LoadImgfromAsset("backwh.png")));
				objParser = new Loader3DSMax(this, R.raw.backwh);
				objParser.parse();
				m3D_Rear[i] = objParser.getParsedObject();
				m3D_Rear[i].setScale(.05f);
				m3D_Rear[i].setPosition(0, 1-2*i, 0);
				m3D_Rear[i].setRotZ(90);
				m3D_Rear[i].setColor(0);
				m3D_Rear[i].setMaterial(cloudmat2);
				getCurrentScene().addChild(m3D_Rear[i]);
				objParser.clear();
			}
			
			
			
			m3D_Opp = new Object3D[m3D_Bike.length];
			for(int i =0;i<m3D_Opp.length;i++)
			{
				m3D_Opp[i] = m3D_Bike[i].clone(true);
				m3D_Opp[i].setScale(.05f);
				m3D_Opp[i].setPosition(0, 1-2*i, 0);
				m3D_Opp[i].setRotZ(90);
				getCurrentScene().addChild(m3D_Opp[i]);
				objParser.clear();
			}
			Load2D();
			setVisible(false);
			mGamePlay.gameReset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void Load2D() {

		try {
			m2D_LevelBack = addPlan("levelbg.jpg");
			m2D_LevelBack.setScale(4.145f);
			getCurrentScene().addChild(m2D_LevelBack);
			
			
			m2D_Map = addPlan("Maplevel/map.jpg");
			m2D_Map.setScale(4.45f);
			getCurrentScene().addChild(m2D_Map);
			
			m2D_wlBG = addPlan("comp/youwin_lostbg.jpg");
			m2D_wlBG.setScale(4.45f);
			getCurrentScene().addChild(m2D_wlBG);
			
			m2D_FLine = addPlan("BG/finish_line.png");
			m2D_FLine.setRotation(180, 0, 180);
			m2D_FLine.setScale(6.3f);
			getCurrentScene().addChild(m2D_FLine);
			
			m2D_Meter = addPlan("BG/meter.png");
			m2D_Meter.setRotation(130, 0, 180);
			m2D_Meter.setPosition(0, -9.63, 6);
			m2D_Meter.setScale(2.09);
			getCurrentScene().addChild(m2D_Meter);
			
			m2D_Shift = new Plane[3];
			for (int i = 0; i < m2D_Shift.length; i++) {
				m2D_Shift[i] = addPlan("gearshift" + i + ".png");
				m2D_Shift[i].setPosition(0, -4, 6);
				m2D_Shift[i].setRotation(130, 0, 180);
				m2D_Shift[i].setScale(3);
				getCurrentScene().addChild(m2D_Shift[i]);
			}
			m2D_ShiftTxt = new Plane[3];
			for (int i = 0; i < m2D_ShiftTxt.length; i++) {
				m2D_ShiftTxt[i] = addPlan("geartxt" + i + ".png");
				m2D_ShiftTxt[i].setPosition(0, -8, 6);
				m2D_ShiftTxt[i].setRotation(130, 0, 180);
				m2D_ShiftTxt[i].setScale(3);
				getCurrentScene().addChild(m2D_ShiftTxt[i]);
			}
			m2D_Handle = addPlan("BG/handle.png");
			m2D_Handle.setRotation(130, 0, 180);
			m2D_Handle.setPosition(4.7, -8.7f, 6);
			m2D_Handle.setScale(1.9f);
			getCurrentScene().addChild(m2D_Handle);

			m2D_GearShift = addPlan("BG/gear_shift_1.png");
			m2D_GearShift.setRotation(130, 0, 180);
			m2D_GearShift.setPosition(0, 0, 0);
			m2D_GearShift.setScale(1.5f);
			
			
			m2D_Kata = addPlan("BG/kata.png");
			m2D_Kata.setRotation(130, 0, 180);
			m2D_Kata.setPosition(-.11f,-11.65f, 6);
			m2D_Kata.setScale(1.6f);
			getCurrentScene().addChild(m2D_Kata);
			
			
			
			m2D_Hint = addPlan("comp/hint_btn.png");
			m2D_Hint.setScale(4.45f);
			getCurrentScene().addChild(m2D_Hint);
			
			m2D_Next = addPlan("comp/next.png");
			m2D_Next.setScale(4.45f);
			getCurrentScene().addChild(m2D_Next);
			
			m2D_Retry = addPlan("comp/retry_btn.png");
			m2D_Retry.setScale(4.45f);
			getCurrentScene().addChild(m2D_Retry);
			
			
			m2D_Lost = addPlan("comp/lost_text.png");
			m2D_Lost.setScale(4.45f);
			m2D_Lost.setPosition(0, 5.56, 0);
			getCurrentScene().addChild(m2D_Lost);
			
			
			m2D_Win = addPlan("comp/youwin_text.png");
			m2D_Win.setScale(4.45f);
			m2D_Win.setPosition(0, 5.56, 0);
			getCurrentScene().addChild(m2D_Win);
			
			m2D_ComnBG = addPlan("commab_bg.jpg");
			m2D_ComnBG.setScale(4.2f);
			getCurrentScene().addChild(m2D_ComnBG);

			m2D_Arrow = new Plane[2];
			m2D_Arrow[0] = addPlan("Lmove.png");
			m2D_Arrow[0].setScale(4.45f);
			m2D_Arrow[0].setPosition(-5.3, 1.6, 10);
			getCurrentScene().addChild(m2D_Arrow[0]);
			
			m2D_Arrow[1] = addPlan("Rmove.png");
			m2D_Arrow[1].setScale(4.45f);
			m2D_Arrow[1].setPosition(5.3, 1.6, 10);
			getCurrentScene().addChild(m2D_Arrow[1]);
			
			m2D_Buy = addPlan("buy2.png");
			m2D_Buy.setScale(4.45f);
			m2D_Buy.setPosition(10.75, -1.77-2.48*0, 0);
			getCurrentScene().addChild(m2D_Buy);
			
			m2D_CareerB = addPlan("career.png");
			m2D_CareerB.setScale(4.45f);
			m2D_CareerB.setPosition(10.75, -1.77-2.48*1, 0);
			getCurrentScene().addChild(m2D_CareerB);
			
			m2D_Conti = addPlan("contnue.png");
			m2D_Conti.setScale(4.45f);
			m2D_Conti.setPosition(10.75, -1.77-2.48*0, 0);
			getCurrentScene().addChild(m2D_Conti);
			
			m2D_Garage = addPlan("comp/garage.png");
			m2D_Garage.setScale(4.45f);
			getCurrentScene().addChild(m2D_Garage);
			
//			m2D_GarageB = addPlan("garage_btn.png");
//			m2D_GarageB.setScale(4.45f);
//			m2D_GarageB.setPosition(10.75, -1.77-2.48*2, 0);
//			getCurrentScene().addChild(m2D_GarageB);
//			
			m2D_QRaceB = addPlan("quick_race.png");
			m2D_QRaceB.setScale(4.45f);
			m2D_QRaceB.setPosition(10.75, -1.77+-2.48*0, 0);
			getCurrentScene().addChild(m2D_QRaceB);
			
			
			m2D_ShopB = addPlan("shop.png");
			m2D_ShopB.setScale(4.45f);
			m2D_ShopB.setPosition(10.75, -1.77+-2.48*1, 0);
			getCurrentScene().addChild(m2D_ShopB);
			
		
			m2D_Back = addPlan("comp/back.png");
			m2D_Back.setScale(4.45f);
			getCurrentScene().addChild(m2D_Back);
			
			
			m2D_ICN = new Plane[6];
			for(int i =0;i<m2D_ICN.length;i++){
				m2D_ICN[i] = addPlan("icn"+i+".png");
				m2D_ICN[i].setScale(4.45f);
				m2D_ICN[i].setPosition(-6.44 + 3.18 * (i == 5 ? 4 : i), -6.9, 0);
				getCurrentScene().addChild(m2D_ICN[i]);
			}
			
			
			m2D_PowerBar = new Plane[3];
			for(int i =0;i<m2D_PowerBar.length;i++){
				m2D_PowerBar[i] = addPlan("power_bar.png");
				m2D_PowerBar[i].setScale(4.45f);
				m2D_PowerBar[i].setPosition(-6.44 + 3.18 * (i == 5 ? 4 : i), -6.9, 0);
				getCurrentScene().addChild(m2D_PowerBar[i]);
			}
			
			m2D_PowerFill = new Plane[3];
			for(int i =0;i<m2D_PowerFill.length;i++){
				m2D_PowerFill[i] = addPlan("barfill.png");
				m2D_PowerFill[i].setScale(4.45f);
				m2D_PowerFill[i].setPosition(-6.44 + 3.18 * (i == 5 ? 4 : i), -6.9, 0);
				getCurrentScene().addChild(m2D_PowerFill[i]);
			}
			
			m2D_MapDot = addPlan("Maplevel/map_dot.png");
			m2D_MapDot.setScale(4.45f);
			getCurrentScene().addChild(m2D_MapDot);
			m2D_MapDot2 = new Plane[7];
			for(int i =0;i<m2D_MapDot2.length;i++){
				m2D_MapDot2[i] = addPlan("Maplevel/map_dot2.png");
				m2D_MapDot2[i].setScale(4.45f);
				getCurrentScene().addChild(m2D_MapDot2[i]);
			}
			m2D_Mapbox = addPlan("Maplevel/map_box.png");
			m2D_Mapbox.setScale(4.45f);
			getCurrentScene().addChild(m2D_Mapbox);
			
			m2D_GoB = addPlan("Maplevel/go_btn.png");
			m2D_GoB.setScale(4.45f);
			getCurrentScene().addChild(m2D_GoB);
			
			m2D_City = new Plane[7];
			for(int i =0;i<m2D_City.length;i++){
				m2D_City[i] = addPlan("Maplevel/"+i+"0.png");
				m2D_City[i].setScale(4.45f);
				getCurrentScene().addChild(m2D_City[i]);
			}
			m2D_RaceB = addPlan("race.png");
			m2D_RaceB.setScale(4.45f);
			m2D_RaceB.setPosition(10.75, -1.77+-2.48*0, 0);
			getCurrentScene().addChild(m2D_RaceB);
			 
			
			m2D_LevelBoxS = addPlan("Maplevel/level_box.png");
			m2D_LevelBoxS.setScale(4.45f);
			getCurrentScene().addChild(m2D_LevelBoxS);
			
			
			
			
			
			m2D_Bar = addPlan("BG/bar.png");
			m2D_Bar.setPosition(0, 4.3, 6);
			m2D_Bar.setRotation(130, 0, 180);
			m2D_Bar.setScale(6,5,5);
			getCurrentScene().addChild(m2D_Bar);
			m2D_Pointer = new Plane[2];
			for (int i = 0; i < m2D_Pointer.length; i++) {
				m2D_Pointer[i] = addPlan("BG/pointer" + i + ".png");
				m2D_Pointer[i].setScale(8.00f);
				m2D_Pointer[i].setPosition(0, 5.07-1.54*i, 6);
				m2D_Pointer[i].setRotation(130, 0, 180);
				getCurrentScene().addChild(m2D_Pointer[i]);
			}
			
			
			
			m2D_Gbar = addPlan("garagebar.png");
			m2D_Gbar.setRotation(180, 0, 180);
			m2D_Gbar.setPosition(0,5.87, 0);
			m2D_Gbar.setScale(3.40,5.11,3.40);
			getCurrentScene().addChild(m2D_Gbar);
			
			m2D_Sing = addPlan("gbar.png");
			m2D_Sing.setScale(4.45f);
			getCurrentScene().addChild(m2D_Sing);
			
			
			m2D_UpBox = new Plane[4];
			m2D_Engin = new Plane[2][4];
			m2D_Exhaus = new Plane[2][4];
			m2D_Nitro = new Plane[2][4];
			m2D_Gear = new Plane[2][4];
			for (int i = 0; i < 4; i++) {
				m2D_UpBox[i] = addPlan("Garage/comman_box.png");
				m2D_UpBox[i].setPosition(-7.0+4.5*i, 2.84, 0);
				getCurrentScene().addChild(m2D_UpBox[i]);
				
				m2D_Engin[0][i] = addPlan("Garage/engine_install.png");
				m2D_Engin[0][i].setPosition(-7.0+4.5*i, 2.84, 0);
				getCurrentScene().addChild(m2D_Engin[0][i]);
				m2D_Engin[1][i] = addPlan("Garage/engine_lock.png");
				m2D_Engin[1][i].setPosition(-7.0+4.5*i, 2.84, 0);
				getCurrentScene().addChild(m2D_Engin[1][i]);
				
				m2D_Exhaus[0][i] = addPlan("Garage/exhaust_install.png");
				m2D_Exhaus[0][i].setPosition(-7.0+4.5*i, 2.84, 0);
				getCurrentScene().addChild(m2D_Exhaus[0][i]);
				m2D_Exhaus[1][i] = addPlan("Garage/exhaus_lockt.png");
				m2D_Exhaus[1][i].setPosition(-7.0+4.5*i, 2.84, 0);
				getCurrentScene().addChild(m2D_Exhaus[1][i]);
				
				m2D_Nitro[0][i] = addPlan("Garage/nitro_install.png");
				m2D_Nitro[0][i].setPosition(-7.0+4.5*i, 2.84, 0);
				getCurrentScene().addChild(m2D_Nitro[0][i]);
				m2D_Nitro[1][i] = addPlan("Garage/nitro_lock.png");
				m2D_Nitro[1][i].setPosition(-7.0+4.5*i, 2.84, 0);
				getCurrentScene().addChild(m2D_Nitro[1][i]);
				
				m2D_Gear[0][i] = addPlan("Garage/gear_install.png");
				m2D_Gear[0][i].setPosition(-7.0+4.5*i, 2.84, 0);
				getCurrentScene().addChild(m2D_Gear[0][i]);
				m2D_Gear[1][i] = addPlan("Garage/gear_lock.png");
				m2D_Gear[1][i].setPosition(-7.0+4.5*i, 2.84, 0);
				getCurrentScene().addChild(m2D_Gear[1][i]);
				
			}
					
			m2D_Lock = new Plane[2][10];
			for(int i = 0;i<m2D_Lock[0].length;i++){
				m2D_Lock[0][i] = addPlan("Maplevel/lock.png");
				m2D_Lock[0][i].setScale(4.45f);
				getCurrentScene().addChild(m2D_Lock[0][i]);
				m2D_Lock[1][i] = addPlan("Maplevel/Un_lock.png");
				m2D_Lock[1][i].setScale(4.45f);
				getCurrentScene().addChild(m2D_Lock[1][i]);
			}
//			m2D_Shop = addPlan("comp/shop_btn.png");
//			m2D_Shop.setScale(4.45f);
//			getCurrentScene().addChild(m2D_Shop);
			
			getCurrentScene().addChild(m2D_GearShift);
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
				oCoin.setColor(Color.WHITE);
				oCoin.setPosition(0, -1.1f, 1.5f);
				oCoin.setScale(1);
				oCoin.setRotation(180, 0, 180);
				oCoin.setTransparent(true);
				getCurrentScene().addChild(oCoin);
			}
			{
				Material SPDSphereMaterial = new Material();
				SPDSphereMaterial.enableLighting(true);
				SPDSphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				mSPDBitmap = Bitmap.createBitmap(256, 256,Config.ARGB_8888);
				mSPDTexture = new AlphaMapTexture("SPDTexture",mSPDBitmap);
				SPDSphereMaterial.addTexture(mSPDTexture);
				SPDSphereMaterial.setColorInfluence(1);

				oSPD = new Plane(2, 2, 1, 1);
				oSPD.setMaterial(SPDSphereMaterial);
				oSPD.setColor(Color.WHITE);
				oSPD.setPosition(0, -1.1f, 1.5f);
				oSPD.setScale(1);
				oSPD.setRotation(180, 0, 180);
				oSPD.setTransparent(true);
				getCurrentScene().addChild(oSPD);
			}
			{
				Material UpdtSphereMaterial = new Material();
				UpdtSphereMaterial.enableLighting(true);
				UpdtSphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				mUpdtBitmap = Bitmap.createBitmap(512, 256,Config.ARGB_8888);
				mUpdtTexture = new AlphaMapTexture("UpdtTexture",mUpdtBitmap);
				UpdtSphereMaterial.addTexture(mUpdtTexture);
				UpdtSphereMaterial.setColorInfluence(1);

				oUpdt = new Plane(4, 2, 1, 1);
				oUpdt.setMaterial(UpdtSphereMaterial);
				oUpdt.setColor(Color.WHITE);
				oUpdt.setPosition(0, -1.1f, 1.5f);
				oUpdt.setScale(1);
				oUpdt.setRotation(180, 0, 180);
				oUpdt.setTransparent(true);
				getCurrentScene().addChild(oUpdt);
			}
			{
				Material DolerSphereMaterial = new Material();
				DolerSphereMaterial.enableLighting(true);
				DolerSphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				mDolerBitmap = Bitmap.createBitmap(256, 256,Config.ARGB_8888);
				mDolerTexture = new AlphaMapTexture("DolerTexture",mDolerBitmap);
				DolerSphereMaterial.addTexture(mDolerTexture);
				DolerSphereMaterial.setColorInfluence(1);

				oDoler = new Plane(2, 2, 1, 1);
				oDoler.setMaterial(DolerSphereMaterial);
				oDoler.setColor(Color.WHITE);
				oDoler.setPosition(0, -1.1f, 1.5f);
				oDoler.setScale(1);
				oDoler.setRotation(180, 0, 180);
				oDoler.setTransparent(true);
				getCurrentScene().addChild(oDoler);
			}
		} catch (TextureException e) {
			e.printStackTrace();
		}
	}

	Plane addPlan(String str) {
		try {

			Plane plan;
			Bitmap bitID = LoadImgfromAsset(str);
			Material material = new Material();
			str = str.replace(".", "");
			str = str.replace("/", "");
			str = str.replace("png", "");
			str = str.replace("jpg", "");
			material.addTexture(new Texture(str, bitID));
			material.setColorInfluence(0);
			plan = new Plane(bitID.getWidth() / 128f, bitID.getHeight() / 128f,1, 1);
			plan.setRotation(180,0,180);
			plan.setMaterial(material);
			plan.setScale(4.5);
			plan.setTransparent(true);
			return plan;
		} catch (Exception e) {
			return null;
		}
	}

	Plane addPlan(String str, Bitmap bitID) {
		try {

			Plane plan;
			Material material = new Material();
			material.addTexture(new Texture(str.replace('.', '_'), bitID));
			material.setColorInfluence(0);
			plan = new Plane(bitID.getWidth() / 128f, bitID.getHeight() / 128f,
					1, 1);
			plan.setMaterial(material);
			plan.setTransparent(true);
			return plan;
		} catch (Exception e) {
			return null;
		}
	}

	Bitmap LoadImgfromAsset(String ID) {
		try {
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
		} catch (Exception e) {
			System.out.println("[" + ID + "]  ~~~~~  " + e.getMessage());
			return null;
		}
	}
	
	public void onDrawFrame(GL10 glUnused) {
//		M.GameScreen = M.GAMEUPGRD;
		mGamePlay.Counter++;
		root.Counter++;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			
			m3D_Bike[0].setScale(root.sz);
			m3D_Frant[0].setScale(root.sz);
			m3D_Rear[0].setScale(root.sz);
			
			/*m3D_Bike[0].setRotation(root.sx*100, root.sy*100,root.sz*100);
			m3D_Frant[0].setRotation(root.sx*100, root.sy*100,root.sz*100);
			m3D_Rear[0].setRotation(root.sx*100, root.sy*100, root.sz*100);
			*/
			m3D_Bike[0].setRotation(0, 0, 0);
			m3D_Frant[0].setRotation((root.Counter%360)*1, (root.Counter%360)*1, (root.Counter%360)*1);
			m3D_Rear[0].setRotation((root.Counter%360)*0, (root.Counter%360)*1, 0);
			
			m3D_Bike[0].setVisible(false);
			m3D_Frant[0].setVisible(true);
			m3D_Rear[0].setVisible(false);
			
			
			m3D_Bike[0].setPosition(0, 0, 0);
			m3D_Frant[0].setPosition(0, 0, 0);
			m3D_Rear[0].setPosition(0, 0, 0);
			
			
			/*m3D_Light.setVisible(true);
			m3D_Light.setScale(M.RMUL*root.sz);
			m3D_Light.setRotation(root.sx*100, 0, root.sy*100);
			oLogo.setVisible(true);
			if(mGamePlay.Counter>60)
				M.GameScreen = M.GAMEMENU;*/
			if(getCurrentCamera().getRotX()!=-10)
				getCurrentCamera().setRotX(-10);
			break;
		case M.GAMEMENU:
		case M.GAMEQRACE:
		case M.GAMESHOP:
		case M.GAMEPUASE:
			root.Draw_Menu(glUnused);
			break;
		case M.GAMECARER:
			root.Draw_Career(glUnused);
			break;
		case M.GAMELEVEL:
			root.Draw_Level(glUnused);
			break;
		case M.GAMEPLAY:
			mGamePlay.Draw();
			break;
		case M.GAMEUPGRD:
			root.Draw_UPgrade(glUnused);
			break;
		case M.GAMESPLASH:
			//Draw_Splash(gl);
			break;
		case M.GAMEWIN:
		case M.GAMEOVER:
		case M.GAMECONG:
			mGamePlay.Draw_GameOver(glUnused);
			break;
		}
		root.setting();
		super.onDrawFrame(glUnused);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		root.Counter++;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEMENU:
		case M.GAMEQRACE:
		case M.GAMESHOP:
		case M.GAMEPUASE:
			root.Handle_Menu(event);
			break;
		case M.GAMECARER:
			root.Handle_Career(event);
			break;
		case M.GAMELEVEL:
			root.Handle_Level(event);
			break;
		case M.GAMEPLAY:
			mGamePlay.Handle_Play(event);
			break;
		case M.GAMEUPGRD:
			root.Handle_Upgarde(event);
			break;
		case M.GAMESPLASH:
			//Draw_Splash(gl);
			break;
		case M.GAMEWIN:
		case M.GAMEOVER:
		case M.GAMECONG:
			mGamePlay.Handle_WinOver(event);
			break;
		}
		
		root.Handle(event);
//		
		
		return true;
	}

	void setVisible(boolean visible) {
		for (int i = 0; i < m3D_Bike.length; i++)
			m3D_Bike[i].setVisible(visible);
		for (int i = 0; i < m3D_Opp.length; i++)
			m3D_Opp[i].setVisible(visible);
		for (int i = 0; i < m3D_Road.length; i++){
			m3D_Road[i].setVisible(visible);
			m3D_Fancing[i].setVisible(visible);
		}
		for (int i = 0; i < m2D_Arrow.length; i++)
			m2D_Arrow[i].setVisible(visible);
		for (int i = 0; i < m2D_ICN.length; i++)
			m2D_ICN[i].setVisible(visible);
		for (int i = 0; i < m2D_PowerBar.length; i++)
			m2D_PowerBar[i].setVisible(visible);
		for (int i = 0; i < m2D_PowerFill.length; i++)
			m2D_PowerFill[i].setVisible(visible);
		for (int i = 0; i < m2D_MapDot2.length; i++)
			m2D_MapDot2[i].setVisible(visible);
		for (int i = 0; i < m2D_Shift.length; i++)
			m2D_Shift[i].setVisible(visible);
		for (int i = 0; i < m2D_ShiftTxt.length; i++)
			m2D_ShiftTxt[i].setVisible(visible);
		for (int i = 0; i < m2D_Pointer.length; i++)
			m2D_Pointer[i].setVisible(visible);
		for (int i = 0; i < m2D_UpBox.length; i++)
			m2D_UpBox[i].setVisible(visible);
		for (int i = 0; i < m2D_City.length; i++) {
				m2D_City[i].setVisible(visible);
		}
		for (int i = 0; i < m2D_Lock.length; i++) {
			for (int j = 0; j < m2D_Lock[i].length; j++)
				m2D_Lock[i][j].setVisible(visible);
		}
		for (int i = 0; i < m2D_Engin.length; i++) {
			for (int j = 0; j < m2D_Engin[i].length; j++)
				m2D_Engin[i][j].setVisible(visible);
		}
		for (int i = 0; i < m2D_Exhaus.length; i++) {
			for (int j = 0; j < m2D_Exhaus[i].length; j++)
				m2D_Exhaus[i][j].setVisible(visible);
		}
		for (int i = 0; i < m2D_Nitro.length; i++) {
			for (int j = 0; j < m2D_Nitro[i].length; j++)
				m2D_Nitro[i][j].setVisible(visible);
		}
		for (int i = 0; i < m2D_Gear.length; i++) {
			for (int j = 0; j < m2D_Gear[i].length; j++)
				m2D_Gear[i][j].setVisible(visible);
		}
		m2D_Bar.setVisible(visible);
		m2D_Gbar.setVisible(visible);
		m2D_LevelBack.setVisible(visible);
		m2D_LevelBoxS.setVisible(visible);
		m2D_Map.setVisible(visible);
		m2D_MapDot.setVisible(visible);
		m2D_Mapbox.setVisible(visible);
		m2D_GoB.setVisible(visible);
		m2D_Sing.setVisible(visible);
		oLogo.setVisible(visible);
		m2D_GearShift.setVisible(visible);
		m2D_Meter.setVisible(visible);
		m2D_Handle.setVisible(visible);
		m2D_Kata.setVisible(visible);
		m2D_FLine.setVisible(visible);
		m2D_Back.setVisible(visible);
		m2D_Garage.setVisible(visible);
		m2D_Hint.setVisible(visible);
		m2D_Next.setVisible(visible);
		m2D_Retry.setVisible(visible);
//		m2D_Shop.setVisible(visible);
		m2D_Lost.setVisible(visible);
		m2D_wlBG.setVisible(visible);
		m2D_Win.setVisible(visible);
		m2D_ComnBG.setVisible(visible);
		m2D_Buy.setVisible(visible);
		m2D_CareerB.setVisible(visible);
		m2D_Conti.setVisible(visible);
//		m2D_GarageB.setVisible(visible);
		m2D_QRaceB.setVisible(visible);
		m2D_RaceB.setVisible(visible);
		m2D_ShopB.setVisible(visible);
		oCoin.setVisible(visible);
		oSPD.setVisible(visible);
		oDoler.setVisible(visible);
		oUpdt.setVisible(visible);
	}
	
	public void updateCoinBitmap() {
		if (mCoinCanvas == null) {
			mCoinCanvas = new Canvas(mCoinBitmap);
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setTypeface(tf);
			mPaint.setColor(Color.WHITE);
			mPaint.setTextAlign(Align.CENTER);
			mPaint.setTextSize(32);
		}
		mCoinCanvas.drawColor(0, Mode.CLEAR);
		if(M.GameScreen == M.GAMEPLAY)
			mCoinCanvas.drawText(""+mPlayer.Gear, 128, 64, mPaint);
		else if(M.GameScreen == M.GAMEOVER || M.GameScreen == M.GAMEWIN || M.GameScreen == M.GAMECONG){
			mCoinCanvas.drawText(mPlayer.time+"sec", 128, 20, mPaint);
			mCoinCanvas.drawText(mOpponent.time+"sec", 128,114, mPaint);
		}else if(M.GameScreen == M.GAMELEVEL){
			mCoinCanvas.drawText("$"+mDoller, 128, 16+45*0.1f, mPaint);
		}else if(M.GameScreen == M.GAMECARER){
			mCoinCanvas.drawText(root.cityName[city], 128, 64, mPaint);
		}else
			mCoinCanvas.drawText(""+mDoller, 128, 64, mPaint);
		mCoinTexture.setBitmap(mCoinBitmap);
		mTextureManager.replaceTexture(mCoinTexture);
	}

	public void updateSPDBitmap() {
		if (mSPDCanvas == null) {
			mSPDCanvas = new Canvas(mSPDBitmap);
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setTypeface(tf);
			mPaint.setColor(Color.WHITE);
			mPaint.setTextAlign(Align.CENTER);
			mPaint.setTextSize(32);
		}
		mSPDCanvas.drawColor(0, Mode.CLEAR);
		if (M.GameScreen == M.GAMEPLAY) {
			mSPDCanvas.drawText("" + (int) (mPlayer.Engine * 99), 128, 64,mPaint);
		}else if(M.GameScreen == M.GAMEOVER || M.GameScreen == M.GAMEWIN || M.GameScreen == M.GAMECONG){
			mSPDCanvas.drawText(mPlayer.MaxSpeed + " km/sec", 128, 30, mPaint);
			mSPDCanvas.drawText(mPlayer.m0to60 + " sec", 128, 128, mPaint);
			mSPDCanvas.drawText(mPlayer.m0to100 + " sec", 128, (256-30), mPaint);
		}else if(M.GameScreen == M.GAMELEVEL){
			mSPDCanvas.drawText("BONUS "+(int)(2 *(city+1)*15) , 128, 16+45*0.1f, mPaint);
			mSPDCanvas.drawText("PRICE "+(int)(2 *(city+1)*100), 128, 16+45*0.9f, mPaint);
		}else {
			mSPDCanvas.drawText("POWER", 128, 32, mPaint);
			mSPDCanvas.drawText("WEIGHT", 128, 64, mPaint);
			mSPDCanvas.drawText("NITRO", 128, 96, mPaint);
		}
		mSPDTexture.setBitmap(mSPDBitmap);
		mTextureManager.replaceTexture(mSPDTexture);
	}
	public void updateDolreBitmap() {
		if (mDolerCanvas == null) {
			mDolerCanvas = new Canvas(mDolerBitmap);
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setTypeface(tf);
			mPaint.setColor(Color.WHITE);
			mPaint.setTextAlign(Align.CENTER);
			mPaint.setTextSize(32);
		}
		mDolerCanvas.drawColor(0, Mode.CLEAR);
		if(M.GameScreen == M.GAMESHOP){
			mDolerCanvas.drawText(mSpec[mBike].Name, 128, 32, mPaint);
			if(!mBikeUnlock[mBike])
				mDolerCanvas.drawText("$"+root.getCost(mBike), 128, 64, mPaint);
		}else if(M.GameScreen == M.GAMEOVER || M.GameScreen == M.GAMEWIN || M.GameScreen == M.GAMECONG){
			int total = mPlayer.parfect+ mPlayer.Good+ mPlayer.over + mPlayer.bonus+mPlayer.price;
			mDolerCanvas.drawText(mPlayer.parfect + ""	, 128, 17+45*0, mPaint);
			mDolerCanvas.drawText(mPlayer.Good + ""		, 128, 16+45*1, mPaint);
			mDolerCanvas.drawText(mPlayer.over + ""		, 128, 16+45*2, mPaint);
			mDolerCanvas.drawText(mPlayer.bonus + ""	, 128, 16+45*3, mPaint);
			mDolerCanvas.drawText(mPlayer.price + ""	, 128, 16+45*4, mPaint);
			mDolerCanvas.drawText(total + ""			, 128, 16+45*5, mPaint);
		}else if(M.GameScreen == M.GAMELEVEL){
			mDolerCanvas.drawText("BONUS "+(int)(5 *(city+1)*15) , 128, 16+45*0.1f, mPaint);
			mDolerCanvas.drawText("PRICE "+(int)(5 *(city+1)*100), 128, 16+45*0.9f, mPaint);
			
			mDolerCanvas.drawText("BONUS "+(int)(7 *(city+1)*15) , 128, 16+45*4.5f, mPaint);
			mDolerCanvas.drawText("PRICE "+(int)(7 *(city+1)*100), 128, 16+45*5.2f, mPaint);
		} else if (M.GameScreen == M.GAMEUPGRD) {
			mPaint.setTextSize(17);
			switch (upgrade) {
			case 0:
				for (int i = 0; i < 4; i++) {
					if (mSpec[mBike].lvlWeight >= i) {
						mDolerCanvas.drawText("Install", (int)(20 + 72 * (i-i*.04)), 128,mPaint);
					} else if (mSpec[mBike].lvlWeight + 1 >= i) {
						mDolerCanvas.drawText(""
								+ (i * (root.wieght * (mBike + 1) * root.Mul)),(int)(20 + 72 * (i-i*.04)), 128, mPaint);
					}
				}
				break;
			case 1:
				for (int i = 0; i < 4; i++) {
					if (mSpec[mBike].lvlExhaust >= i) {
						mDolerCanvas.drawText("Install", (int)(20 + 72 * (i-i*.04)), 128,mPaint);
					} else if (mSpec[mBike].lvlExhaust + 1 >= i) {
						mDolerCanvas.drawText("" + (i * (root.Exhaust* (mBike + 1) * root.Mul)),(int)(20 + 72 * (i-i*.04)), 128, mPaint);
					}
				}
				break;
			case 2:
				for (int i = 0; i < 4; i++) {
					if (mSpec[mBike].lvlNitro >= i) {
						mDolerCanvas.drawText("Install", (int)(20 + 72 * (i-i*.04)), 128,mPaint);
					} else if (mSpec[mBike].lvlNitro + 1 >= i) {
						mDolerCanvas.drawText(""+ (i * (root.Nirto * (mBike + 1) * root.Mul)),(int)(20 + 72 * (i-i*.04)), 128, mPaint);
					}
				}
				break;
			case 3:
				for (int i = 0; i < 4; i++) {
					if (mSpec[mBike].lvlGearBox >= i) {
						mDolerCanvas.drawText("Install", (int)(20 + 72 * (i-i*.04)), 128,mPaint);
					} else if (mSpec[mBike].lvlGearBox + 1 >= i) {
						mDolerCanvas.drawText(""+ (i * (root.GBox * (mBike + 1) * root.Mul)),(int)(20 + 72 * (i-i*.04)), 128, mPaint);
					}
				}
				break;
			}
			mPaint.setTextSize(32);
		}else{
			mDolerCanvas.drawText("LEVEL "+(city+1), 128, 32, mPaint);
			if(mBikeUnlock[mBike])
				mDolerCanvas.drawText(mSpec[mBike].Name, 128, 64, mPaint);
		}
		mDolerTexture.setBitmap(mDolerBitmap);
		mTextureManager.replaceTexture(mDolerTexture);
	}
	
	public void updateUpdtBitmap() {
		if (mUpdtCanvas == null) {
			mUpdtCanvas = new Canvas(mUpdtBitmap);
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setTypeface(tf);
			mPaint.setColor(Color.WHITE);
			mPaint.setTextAlign(Align.CENTER);
			mPaint.setTextSize(32);
		}
		mUpdtCanvas.drawColor(0, Mode.CLEAR);
		if(M.GameScreen == M.GAMESHOP){
			String str = mSpec[mBike].Name;
			if(!mBikeUnlock[mBike])
				str = mSpec[mBike].Name + " : " + "$" + root.getCost(mBike);
			mUpdtCanvas.drawText(str, 256, 32, mPaint);
		}else if(M.GameScreen == M.GAMEUPGRD)
		{
			mUpdtCanvas.drawText("GARAGE", 450, 86, mPaint);
			mUpdtCanvas.drawText("Engine" ,  42+140*0f,128, mPaint);
			mUpdtCanvas.drawText("Exhaust",  40+140*1f,128, mPaint);
			mUpdtCanvas.drawText("Nirto"  ,  40+140*2f,128, mPaint);
			mUpdtCanvas.drawText("Gear"	  ,  55+140*3f,128, mPaint);
		}else{
			mUpdtCanvas.drawText("LEVEL "+(city+1), 256, 32, mPaint);
			if(mBikeUnlock[mBike])
				mUpdtCanvas.drawText(mSpec[mBike].Name, 256, 64, mPaint);
		}
		mUpdtTexture.setBitmap(mUpdtBitmap);
		mTextureManager.replaceTexture(mUpdtTexture);
	}
}
