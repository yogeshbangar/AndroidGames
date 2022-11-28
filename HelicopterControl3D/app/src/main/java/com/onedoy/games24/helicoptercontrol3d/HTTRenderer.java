package com.onedoy.games24.helicoptercontrol3d;


import org.rajawali3d.Object3D;
import org.rajawali3d.cameras.OrthographicCamera;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.Loader3DSMax;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.primitives.Plane;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

@SuppressLint("ClickableViewAccessibility")
public class HTTRenderer extends RajawaliRenderer implements OnTouchListener {
	static Start mStart = null;
	Group root;
	
	Loader3DSMax objParser;
	Texture mBGTexture;
	Bitmap mBitBG[];
	Object3D m3D_Player[];
	Object3D m3D_BG[];
	
	Plane m2D_Play, m2D_Player, m2D_Setting, m2D_Text, m2DLogo, m2D_GameOver;
	Plane m2D_Achiev, m2D_Back, m2D_Comman, m2D_Leader, m2D_Play2, m2D_Setex;
	Plane m2D_Share, m2D_Sound[], m2D_STxt, m2D_Btn, m2D_Arow[];
	Plane m2D_Exit[];
	
	
	AFont mFont;
	Opponent mOpp[];
	Player mPly;
	Collision mColi[];

	OrthographicCamera orthoCam = new OrthographicCamera();
	DirectionalLight mLight;
	
	boolean iSGamePause = true;
	
	int bgNo =0;
	int plNo=0;
	int mScore=0;
	int mHScore=0;
	int mTotal=0;
	
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	
	public HTTRenderer(Context context) {
		super(context);
		mStart = (Start)context;
		root = new Group(this);
	}
	
	@Override
	protected void initScene() {
		mLight = new DirectionalLight(-4, 10,-4); // set the direction
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(1);
		
		getCurrentScene().addLight(mLight);
		
		((View) mStart.mSurfaceView).setOnTouchListener(this);
		orthoCam.setPosition(0, 0, 6);
		orthoCam.setZoom(10);
		orthoCam.setRotation(-35, 0, -55);
		getCurrentScene().switchCamera(orthoCam);
		getCurrentScene().setBackgroundColor(-13487004);
		m2DLogo = addPlan("logo.png");
		m2DLogo.setPosition(20, -30, 31);
		
//		init();
	}

	
	void init(){
		try {
			bgNo =0;
			mBitBG = new Bitmap[3];
			for(int i =0;i<mBitBG.length;i++)
				mBitBG[i] = LoadImgfromAsset("city"+i+".png");
			
			m3D_BG = new Object3D[M.ROAD];
			for(int i =0;i<m3D_BG.length;i++)
			{
				if (i == 0) {
					Material mater = new Material();
//					mater.setDiffuseMethod(new DiffuseMethod.Lambert());
//					mater.enableLighting(true);
					
					mBGTexture =  new Texture("abc",mBitBG[bgNo]);
					mater.addTexture(mBGTexture);
					objParser = new Loader3DSMax(this, R.raw.city);
					objParser.parse();
					m3D_BG[i] = objParser.getParsedObject();
					m3D_BG[i].setMaterial(mater);
					m3D_BG[i].setColor(0);
					m3D_BG[i].setScale(M.B);
					objParser.clear();
				}else{
					m3D_BG[i] = m3D_BG[0].clone(true);
				}
				m3D_BG[i].setPosition(-20+M.A*M.B*i, -2.3, 0);
				getCurrentScene().addChild(m3D_BG[i]);
			}
			
			
			
			m3D_Player = new Object3D[4];
			for(int i =0;i<m3D_Player.length;i++)
			{
				Material mater = new Material();
				mater.addTexture(new Texture("abc",LoadImgfromAsset("h"+i+".png")));
				switch (i) {
				case 0:
					objParser = new Loader3DSMax(this, R.raw.h0);
					break;
				case 1:
					objParser = new Loader3DSMax(this, R.raw.h1);
					break;
				case 2:
					objParser = new Loader3DSMax(this, R.raw.h2);
					break;
				default:
					objParser = new Loader3DSMax(this, R.raw.h3);
					break;
				}
				objParser.parse();
				m3D_Player[i] = objParser.getParsedObject();
				m3D_Player[i].setMaterial(mater);
				objParser.clear();
				m3D_Player[i].setColor(0);
				m3D_Player[i].setScale(.3);
				m3D_Player[i].setPosition(-5+i*10,-5.6,12.7);
				m3D_Player[i].setVisible(false);
				getCurrentScene().addChild(m3D_Player[i]);
			}
			
			Bitmap b = LoadImgfromAsset("shadow.png");
			mOpp = new Opponent[5];
			for (int i = 0; i < mOpp.length; i++) {
				mOpp[i] = new Opponent(this, b);
				Material sphereMaterial = new Material();
				sphereMaterial.addTexture(new Texture("opp" + i,LoadImgfromAsset("h"+(i%4)+".png")));
				sphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
				sphereMaterial.enableLighting(true);
				mOpp[i].m3D_Obj = new Sphere(1, 16, 16);
				mOpp[i].m3D_Obj.setMaterial(sphereMaterial);
				mOpp[i].m3D_Obj.setColor(Color.RED);
				mOpp[i].m3D_Obj.setScale(M.B);
				mOpp[i].m3D_Obj.setPosition(40 + M.A * M.B * i, -2.3, 0);
				getCurrentScene().addChild(mOpp[i].m3D_Obj);
			}
			mColi = new Collision[20];
			for(int i = 0;i<mColi.length;i++){
				mColi[i] = new Collision(this);
				
			}
			mPly = new Player(this,LoadImgfromAsset("hshadow.png"));
			{
				Material mater = new Material();
				mater.addTexture(new Texture("blade", LoadImgfromAsset("blade.png")));
				objParser = new Loader3DSMax(this, R.raw.blade);
				objParser.parse();
				mPly.m3D_Blade = objParser.getParsedObject();
				mPly.m3D_Blade.setMaterial(mater);
				mPly.m3D_Blade.setColor(0);
				mPly.m3D_Blade.setScale(M.B);
				objParser.clear();
				mPly.m3D_Blade.setPosition(-20, -2.3, 0);
				getCurrentScene().addChild(mPly.m3D_Blade);

			}
			Load2D();
			gamereset(false);
			setVisible(false);
		} catch (Exception e) {
		}
		
	}

	
	void Load2D() {
		try {
			for (int i = 0; i < mOpp.length; i++) {
				getCurrentScene().addChild(mOpp[i].m2D_Shado);
			}
			getCurrentScene().addChild(mPly.m2D_Shado);
			m2D_Comman = addPlan("ui/comman.png");
			m2D_Setting = addPlan("ui/setting.png");
			m2D_Text  = addPlan("ui/text.png");
			m2D_Leader = addPlan("ui/leader.png");
			m2D_Play2 = addPlan("ui/play2.png");
			m2D_Setex = addPlan("ui/setting_text.png");
			m2D_Share = addPlan("ui/share.png");
			
			m2D_Sound = new Plane[2];
			m2D_Sound[0] = addPlan("ui/sound.png");
			m2D_Sound[1] = addPlan("ui/soundf.png");
			
			m2D_Arow = new Plane[2];
			m2D_Arow[0] = addPlan("ui/Lmove.png");
			m2D_Arow[1] = addPlan("ui/Rmove.png");
			
			m2D_STxt = addPlan("ui/soundt.png");
			m2D_Back = addPlan("ui/back.png");
			m2D_Achiev = addPlan("ui/achievement.png");
			m2D_Btn = addPlan("ui/btn.png");
			m2D_GameOver = addPlan("ui/gameover.png");
			m2D_Player = addPlan("ui/player.png");
			m2D_Play  = addPlan("ui/play.png");
			
			m2D_Exit = new Plane[5];
			m2D_Exit[0]  = addPlan("exit/no.png");
			m2D_Exit[1]  = addPlan("exit/yes.png");
			m2D_Exit[2]  = addPlan("exit/rate.png");
			m2D_Exit[3]  = addPlan("exit/banner.jpg");
			m2D_Exit[4]  = addPlan("exit/freegames.png");
			mFont = new AFont(this);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	void gamereset(boolean isCreat) {
		for (int i = 0; i < mOpp.length; i++) {
			mOpp[i].reset(100 + i * 30);
		}
		for(int i = 0;i<mColi.length;i++){
			mColi[i].mCube.setVisible(false);
		}
		setPlayer(mPly.imgNo);
		mScore=0;
		iSGamePause = true;
		if(isCreat){
			if(ads % 3 ==0)
				mStart.load();
			ads++;
		}
	}
	int ads =0;
	void setPlayer(final int no){
		if(mPly.obj.size()>0){
			getCurrentScene().removeChild(mPly.obj.get(0));
			mPly.obj.clear();
		}
		mPly.obj.add(m3D_Player[no].clone(true));
		mPly.set();
		getCurrentScene().addChild(mPly.obj.get(0));
	}
	@Override
	public void onRender(final long elapsedTime, final double deltaTime) {
		super.onRender(elapsedTime, deltaTime);
		root.Draw();
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		root.onTouch(event);
		return true;
	}
	@Override
	public void onOffsetsChanged(float xOffset, float yOffset,
			float xOffsetStep, float yOffsetStep, int xPixelOffset,
			int yPixelOffset) {
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
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
			plan = new Plane(bitID.getWidth() / 24f, bitID.getHeight() / 24f,1, 1);
			plan.setMaterial(material);
			plan.setScale(1);
			plan.setRotation(-35, 180, -55);
			plan.setPosition(21, -30, 31);
			plan.setTransparent(true);
			plan.setDoubleSided(true);
			getCurrentScene().addChild(plan);
			return plan;
		} catch (Exception e) {
			return null;
		}
	}
	Plane addPlan2(Bitmap bitID) {
		try {

			Plane plan;
			Material material = new Material();
			material.addTexture(new Texture("shadow", bitID));
			material.setColorInfluence(0);
			plan = new Plane(bitID.getWidth() / 24f, bitID.getHeight() / 24f,1, 1);
			plan.setMaterial(material);
			plan.setScale(1);
			plan.setTransparent(true);
			return plan;
		} catch (Exception e) {
			return null;
		}
	}
	static Bitmap LoadImgfromAsset(String ID) {
		try {
			return BitmapFactory.decodeStream(Start.mContext.getAssets().open(ID));
		} catch (Exception e) {
			System.out.println("[" + ID + "]  ~~~~~  " + e.getMessage());
			return null;
		}
	}
	void setVisible(boolean visible){
		for(int i =0;i<m3D_Player.length;i++)
			m3D_Player[i].setVisible(visible);
		for(int i =0;i<m3D_BG.length;i++)
			m3D_BG[i].setVisible(visible);
//		Object3D m3D_BGObj[][];
		
		m2D_Play.setVisible(visible);
		m2D_Player.setVisible(visible);
		m2D_Setting.setVisible(visible);
		m2D_Text.setVisible(visible);
		m2D_Achiev.setVisible(visible);
		m2D_Back.setVisible(visible);
		m2D_Comman.setVisible(visible);
		m2D_Leader.setVisible(visible);
		m2D_Play2.setVisible(visible);
		m2D_Setex.setVisible(visible);
		m2D_Share.setVisible(visible);
		m2D_Sound[0].setVisible(visible);
		m2D_Sound[1].setVisible(visible);
		m2D_Arow[0].setVisible(visible);
		m2D_Arow[1].setVisible(visible);
		m2D_STxt.setVisible(visible);
		m2DLogo.setVisible(visible);
		m2D_GameOver.setVisible(visible);;
		
		for(int i =0;i<mFont.mPlan.length;i++)
			mFont.mPlan[i].setVisible(visible);
		for(int i =0;i<mOpp.length;i++){
			mOpp[i].m3D_Obj.setVisible(visible);
			mOpp[i].m2D_Shado.setVisible(visible);
		}
		if(mPly.obj.size()>0)
			mPly.obj.get(0).setVisible(visible);
		mPly.m2D_Shado.setVisible(visible);
		for(int i =0;i<mColi.length;i++){
			mColi[i].mCube.setVisible(visible);
		}
		m2D_Btn.setVisible(visible);
		mPly.m3D_Blade.setVisible(visible);
		for(int i =0;i<m2D_Exit.length;i++)
			m2D_Exit[i].setVisible(visible);
	}
	
}
