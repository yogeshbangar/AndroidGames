package com.htt.games.shootinggame;

import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import rajawali.materials.Material;
import rajawali.materials.textures.Texture;
import rajawali.parser.Loader3DSMax;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

public class HTTRenderer extends RajawaliRenderer implements OnTouchListener {
	static Start mStart;
	static Context mContext;
	Plane oLogo;
	Loader3DSMax objParser;
	Opponent mOpp[];
	Bullate mBullate[];
	Group root;
	public HTTRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mContext = context;
		mStart = (Start) context;
		root = new Group(this);
	}
	protected void initScene() {
		try {
			mSurfaceView.setOnTouchListener(this);
			oLogo = addPlan("final_bg.png");
			oLogo.setRotation(180, 0, 180);
			oLogo.setPosition(0, 0, 0);
			oLogo.setScale(30f);
			oLogo.setTransparent(false);
			getCurrentScene().addChild(oLogo);
			init();
			getCurrentCamera().setZ(20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void init() {
		try {
			mOpp = new Opponent[20];
			for (int i = 0; i < mOpp.length; i++) {
				try {
					objParser = new Loader3DSMax(this, R.raw.box);
					objParser.parse();
					Material cloudmat2 = new Material();
					cloudmat2.addTexture(new Texture("Box" + i,LoadImgfromAsset("box.png")));
					mOpp[i] = new Opponent(objParser.getParsedObject());
					mOpp[i].Obj3D.setMaterial(cloudmat2);
					getCurrentScene().addChild(mOpp[i].Obj3D);
					objParser.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mBullate = new Bullate[10];
			for (int i = 0; i < mBullate.length; i++) {
				try {
					objParser = new Loader3DSMax(this, R.raw.box);
					objParser.parse();
					Material cloudmat2 = new Material();
					cloudmat2.addTexture(new Texture("Box" + i,LoadImgfromAsset("box.png")));
					mBullate[i] = new Bullate(objParser.getParsedObject());
					mBullate[i].Obj3D.setMaterial(cloudmat2);
					getCurrentScene().addChild(mBullate[i].Obj3D);
					objParser.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			gameReset();
			mStart.resume();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void gameReset() {
		for (int i = 0; i < mOpp.length; i++) {
			mOpp[i].reset();
		}
		for (int i = 0; i < mBullate.length; i++) {
			mBullate[i].reset();
		}
	}


	Plane addPlan(String str) {
		try {
			Plane plan;
			Bitmap bitID = LoadImgfromAsset(str);
			Material material = new Material();
			material.addTexture(new Texture(str.replace('.', '_'), bitID));
			material.setColorInfluence(0);
			plan = new Plane(bitID.getWidth() / 128f, bitID.getHeight() / 128f,
					1, 1);
			plan.setMaterial(material);
//			plan.setTransparent(true);
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
			plan = new Plane(bitID.getWidth() / 128f, bitID.getHeight() / 128f,1, 1);
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
			System.out.println("[" + ID + "]~~[" + e.getMessage() + "]");
			return null;
		}
	}

	public void onDrawFrame(GL10 gl) {
		root.Draw(gl);

		super.onDrawFrame(gl);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		root.onTouch(v, event);
		return true;
	}

	void setVisible(boolean visible) {
	}

}
