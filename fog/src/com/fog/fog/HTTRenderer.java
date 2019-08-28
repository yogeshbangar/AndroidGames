package com.fog.fog;

import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import rajawali.Object3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.plugins.FogMaterialPlugin.FogParams;
import rajawali.materials.plugins.FogMaterialPlugin.FogType;
import rajawali.materials.textures.Texture;
import rajawali.parser.Loader3DSMax;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

public class HTTRenderer extends RajawaliRenderer implements OnTouchListener{

	static Start mStart;
	static Context mContext;
	Plane oLogo;
	
	private DirectionalLight mLight;
//	private Object3D mRoad;

	Loader3DSMax objParser;
	Object3D m3D_Road[];
	Object3D m3D_Building[][];
//	Object3D m3D_Build;
	
	Opponent mOpp[];
	Group root;

	public HTTRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mContext = context;
		mStart = (Start) context;
		root = new Group(this);
	}
	protected void initScene() {
		mSurfaceView.setOnTouchListener(this);
		mLight = new DirectionalLight(0, -1, -1);
		mLight.setPower(.5f);
		getCurrentScene().addLight(mLight);
		int fogColor = 0x999999;
		fogColor = Color.WHITE;
		getCurrentScene().setBackgroundColor(fogColor);
		getCurrentScene().setFog(new FogParams(FogType.LINEAR, fogColor, 20, 50));
		try {
			oLogo = addPlan("facebook.png");
			oLogo.setRotation(180, 0, 180);
			oLogo.setPosition(0, 0, 0);
			oLogo.setScale(5f);
			oLogo.setTransparent(true);
			oLogo.setDoubleSided(true);
			oLogo.setVisible(false);
			getCurrentScene().addChild(oLogo);
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void init() {
		try {
			
			/*{
				Material cloudmat2 = new Material();
				objParser = new Loader3DSMax(this, R.raw.b1);
				cloudmat2.addTexture(new Texture("b",
						LoadImgfromAsset("b1.png")));

				objParser.parse();
				m3D_Build = objParser.getParsedObject();
				m3D_Build.setMaterial(cloudmat2);
				m3D_Build.setColor(0);
				objParser.clear();
				m3D_Build.setZ(-12);
				m3D_Build.setRotation(90, 0,180);
				m3D_Build.setScale(.01);
				m3D_Build.setVisible(true);
				getCurrentScene().addChild(m3D_Build);
			}*/

			mOpp = new Opponent[20];
			for (int i = 0; i < mOpp.length; i++) {
				Material cloudmat2 = new Material();
				switch (i) {
				case 1:
					objParser = new Loader3DSMax(this, R.raw.auto);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("auto.png")));
					break;
				case 2:
					objParser = new Loader3DSMax(this, R.raw.car1);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("car1.png")));
					break;
				case 3:
					objParser = new Loader3DSMax(this, R.raw.car2);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("car2.png")));
					break;
				case 4:
					objParser = new Loader3DSMax(this, R.raw.car3);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("car3.png")));
					break;
				case 5:
					objParser = new Loader3DSMax(this, R.raw.car4);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("car4.png")));
					break;
				case 6:
					objParser = new Loader3DSMax(this, R.raw.car5);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("car5.png")));
					break;
				case 7:
					objParser = new Loader3DSMax(this, R.raw.car6);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("car6.png")));
					break;
				case 8:
					objParser = new Loader3DSMax(this, R.raw.car7);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("car7.png")));
					break;
				case 9:
					objParser = new Loader3DSMax(this, R.raw.car8);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("car8.png")));
					break;
				case 10:
					objParser = new Loader3DSMax(this, R.raw.car9);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("car9.png")));
					break;
				case 11:
					objParser = new Loader3DSMax(this, R.raw.car10);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("car10.png")));
					break;
				case 12:
					objParser = new Loader3DSMax(this, R.raw.car11);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("car11.png")));
					break;
				case 13:
					objParser = new Loader3DSMax(this, R.raw.roller);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("roller.png")));
					break;
				case 14:
					objParser = new Loader3DSMax(this, R.raw.traktor);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("traktor.png")));
					break;
				case 15:
					objParser = new Loader3DSMax(this, R.raw.truck1);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("truck1.png")));
					break;
				case 16:
					objParser = new Loader3DSMax(this, R.raw.truck2);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("truck2.png")));
					break;
				case 17:
					objParser = new Loader3DSMax(this, R.raw.truck3);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("truck3.png")));
					break;
				case 18:
					objParser = new Loader3DSMax(this, R.raw.truck4);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("truck4.png")));
					break;
				case 19:
					objParser = new Loader3DSMax(this, R.raw.truck5);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("truck5.png")));
					break;
				default:
					objParser = new Loader3DSMax(this, R.raw.truck6);
					cloudmat2.addTexture(new Texture("opp" + i,LoadImgfromAsset("truck6.png")));
					break;
				}
				objParser.parse();
				mOpp[i] =  new Opponent(objParser.getParsedObject(),cloudmat2);
				getCurrentScene().addChild(mOpp[i].m3ds);
				objParser.clear();
			}
			
			m3D_Building = new Object3D[2][6];
			for (int i = 0; i < m3D_Building[0].length; i++) {
				Material cloudmat2 = new Material();
				switch (i) {
				case 1:
					objParser = new Loader3DSMax(this, R.raw.b1);
					cloudmat2.addTexture(new Texture("b" + i,LoadImgfromAsset("b1.png")));
					break;
				case 2:
					objParser = new Loader3DSMax(this, R.raw.b2);
					cloudmat2.addTexture(new Texture("b" + i,LoadImgfromAsset("b2.png")));
					break;

				case 3:
					objParser = new Loader3DSMax(this, R.raw.b3);
					cloudmat2.addTexture(new Texture("b" + i,LoadImgfromAsset("b3.png")));
					break;

				case 4:
					objParser = new Loader3DSMax(this, R.raw.b4);
					cloudmat2.addTexture(new Texture("b" + i,LoadImgfromAsset("b4.png")));
					break;

				case 5:
					objParser = new Loader3DSMax(this, R.raw.b5);
					cloudmat2.addTexture(new Texture("b" + i,LoadImgfromAsset("b5.png")));
					break;
				default:
					objParser = new Loader3DSMax(this, R.raw.b6);
					cloudmat2.addTexture(new Texture("b" + i,LoadImgfromAsset("b6.png")));
					break;
				}
				
				objParser.parse();
				m3D_Building[0][i] = objParser.getParsedObject();
				m3D_Building[0][i].setMaterial(cloudmat2);
				m3D_Building[0][i].setColor(0);
				
				m3D_Building[0][i].setRotation(90, 0,0);
				m3D_Building[0][i].setPosition(6.3, -8.3f, - i * (M.ROADY) * (M.ROADS));
				m3D_Building[0][i].setScale(.1);
				m3D_Building[0][i].setVisible(true);
				getCurrentScene().addChild(m3D_Building[0][i]);
				
				m3D_Building[1][i] = m3D_Building[0][i].clone(true);
				m3D_Building[1][i].setPosition(-6.3, -8.3f, - i * (M.ROADY) * (M.ROADS));
				m3D_Building[1][i].setScale(.1);
				m3D_Building[1][i].setRotation(90, 0,180);
				m3D_Building[1][i].setVisible(true);
				
				if(i==1){
					m3D_Building[0][i].setY(-6.4f);
					m3D_Building[1][i].setY(-6.4f);
				}
				if(i==2){
					m3D_Building[0][i].setY(-7.1f);
					m3D_Building[1][i].setY(-7.1f);
				}
				if(i==5){
					m3D_Building[0][i].setPosition( 6.0,-10.9f,0);
					m3D_Building[1][i].setPosition(-6.0,-10.9f,0);
				}
				getCurrentScene().addChild(m3D_Building[1][i]);
				
				
				objParser.clear();
			}
			
			
			m3D_Road = new Object3D[6];
			for (int i = 0; i < m3D_Road.length; i++) {
				if (i == 0) {
					Material cloudmat2 = new Material();
					objParser = new Loader3DSMax(this, R.raw.road0);
					cloudmat2.addTexture(new Texture("road" + i,LoadImgfromAsset("road.png")));
					objParser.parse();
					m3D_Road[i] = objParser.getParsedObject();
					m3D_Road[i].setMaterial(cloudmat2);
					m3D_Road[i].setColor(0);
					objParser.clear();
				} else {
					m3D_Road[i] = m3D_Road[0].clone(true);
				}
				m3D_Road[i].setPosition(0, 0,-16 - i * (M.ROADY) * (M.ROADS));
				m3D_Road[i].setRotation(90, 0,180);
				m3D_Road[i].setScale(M.ROADS);
				m3D_Road[i].setVisible(true);
				getCurrentScene().addChild(m3D_Road[i]);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void gameReset() {
	}

	Plane addPlan(String str) {
		try {

			Plane plan;
			Bitmap bitID = LoadImgfromAsset(str);
			Material material = new Material();
			material.addTexture(new Texture(str.replace('.', '_'), bitID));
			material.setColorInfluence(0);
			plan = new Plane(bitID.getWidth() / 128f, bitID.getHeight() / 128f,1, 1);
			plan.setMaterial(material);
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
			plan = new Plane(bitID.getWidth() / 128f, bitID.getHeight() / 128f, 1, 1);
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
			System.out.println("[" + ID + "]  ~~~~~   " + e.getMessage());
			return null;
		}
	}

	public void onDrawFrame(GL10 glUnused) {
		root.Draw();
//		oLogo.setPosition(root.sx*10, root.sy*10, root.sz*10);
//		oLogo.setPosition(root.counter++, root.counter++, root.counter++);
//		oLogo.setZ(-20);
//		oLogo.setRotY(180);
//		oLogo.setRotation(root.counter, root.counter, root.counter++);
		
		
		super.onDrawFrame(glUnused);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		root.onTouch(v, event);
		return true;
	}

	
	
	
	void setVisible(boolean visible) {
	}
}
