package com.hututu.game.bikerace;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import rajawali.Object3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.animation.Animation.RepeatMode;
import rajawali.animation.mesh.VertexAnimationObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.math.vector.Vector3.Axis;
import rajawali.parser.Loader3DSMax;
import rajawali.postprocessing.PostProcessingManager;
import rajawali.postprocessing.effects.ShadowEffect;
import rajawali.primitives.Cube;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

public class HTTRenderer extends RajawaliRenderer  implements OnTouchListener{
	CallActivity mStart;
	DirectionalLight mLight;
	VertexAnimationObject3D o3d_MD2;
	Loader3DSMax objParser;
	Object3D cloud3d;
	private Object3D mEmpty;
	
	static boolean addFree = false;
	public HTTRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mStart = (CallActivity)context;
	}
	public void initScene() {
		mSurfaceView.setOnTouchListener(this);
		mLight = new DirectionalLight();
		mLight.setDirection(1, -1, -1);
		mLight.setPower(1.5f);
		getCurrentScene().addLight(mLight);
		
		getCurrentCamera().setFarPlane(50);		
		getCurrentCamera().setPosition(5, 15, 30);
		getCurrentCamera().setLookAt(0, 0, 0);

		Material planeMaterial = new Material();
		planeMaterial.enableLighting(true);
		planeMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
		
		Plane plane = new Plane(Axis.Y);
		plane.setScale(10);
		plane.setMaterial(planeMaterial);
		plane.setColor(Color.GREEN);
		getCurrentScene().addChild(plane);		
		
		Material sphereMaterial = new Material();
		sphereMaterial.enableLighting(true);
		sphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
		
		for(int z=0; z<10; z++) {
			for(int x=0; x<10; x++) {
				Cube cube = new Cube(.7f);
				cube.setMaterial(sphereMaterial);
				cube.setColor(Color.rgb(100 + 10 * x, 0, 0));
				cube.setPosition(-4.5f + x, 5, -4.5f + z);
				
				getCurrentScene().addChild(cube);
			}
		}
		
		Material cubeMaterial = new Material();
		cubeMaterial.enableLighting(true);
		cubeMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
		
		Cube cube = new Cube(2);
		cube.setMaterial(cubeMaterial);
		cube.setColor(Color.GRAY);
		cube.setY(1.5f);
		getCurrentScene().addChild(cube);
		
		
		
		mEmpty = new Object3D();
		TranslateAnimation3D anim = new TranslateAnimation3D(new Vector3(5, -5, -4), new Vector3(-5, -5, 4));
		anim.setDurationMilliseconds(20000);
		anim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
		anim.setTransformable3D(mEmpty);
		getCurrentScene().registerAnimation(anim);
		anim.play();
		
		mPostProcessingManager = new PostProcessingManager(this);
		ShadowEffect shadowEffect = new ShadowEffect(getCurrentScene(), getCurrentCamera(), mLight, 2048);
		shadowEffect.setShadowInfluence(.5f);
		mPostProcessingManager.addEffect(shadowEffect);
		shadowEffect.setRenderToScreen(true);
	}
	protected void initScene1() {
		try {
			mSurfaceView.setOnTouchListener(this);
			mLight = new DirectionalLight(0.0f, 0.0f, 5.0f); // set the direction
			mLight.setColor(0.0f, 1.0f, 1.0f);
			mLight.setPower(1);
			getCurrentScene().addLight(mLight);
			load3ds2();
			/*{
				LoaderMD2 top = new LoaderMD2(mContext.getResources(),mTextureManager, R.raw.bikem);
				top.parse();
				o3d_MD2 = (VertexAnimationObject3D) top.getParsedAnimationObject();
				o3d_MD2.setScale(.06f);
				o3d_MD2.setBackSided(true);
				getCurrentScene().addChild(o3d_MD2);
			}*/
			
			
			plane1 = addPlan("bike1.png");
			plane1.setScale(2);
			plane1.setPosition(0, 0, 2);
			plane1.setDoubleSided(true);
			getCurrentScene().addChild(plane1);
			
			mPostProcessingManager = new PostProcessingManager(this);
			ShadowEffect shadowEffect = new ShadowEffect(getCurrentScene(), getCurrentCamera(), mLight, 2048);
			shadowEffect.setShadowInfluence(.5f);
			mPostProcessingManager.addEffect(shadowEffect);
			shadowEffect.setRenderToScreen(true);
			
			getCurrentCamera().setZ(20);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	Plane plane1;
	public void load3ds2()
	{
		
		
		Plane plane = addPlan("animal.png");
		plane.setScale(10);
		getCurrentScene().addChild(plane);
		
		
		
		objParser = new Loader3DSMax(this, R.raw.animal);
		try {
			objParser.parse();
			Material cloudmat2 = new Material();
			cloudmat2.addTexture(new Texture("bike1",LoadImgfromAsset("animal.png")));
//			cloudmat2.setColor(1);
			cloud3d = objParser.getParsedObject();
			cloud3d.setScale(1.6f);
			cloud3d.setPosition(0, 0, 1);
			cloud3d.setMaterial(cloudmat2);
			cloud3d.setColor(0);
			getCurrentScene().addChild(cloud3d);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	private PostProcessingManager mPostProcessingManager;
	int counter;
	public void onDrawFrame(GL10 glUnused) {
		counter++;
		if (touchTurn != 0 && plane1!=null) {
			plane1.setRotY(plane1.getRotY()+touchTurn);
		}

		if (touchTurnUp != 0&& plane1!=null) {
			plane1.setRotX(plane1.getRotX()+touchTurnUp);
		}
		
		if (touchTurn != 0 && o3d_MD2!=null) {
			o3d_MD2.setRotY(o3d_MD2.getRotY()+touchTurn);
		}

		if (touchTurnUp != 0&& o3d_MD2!=null) {
			o3d_MD2.setRotX(o3d_MD2.getRotX()+touchTurnUp);
		}
		
		if (touchTurn != 0 && cloud3d !=null) {
			cloud3d.setRotY(cloud3d.getRotY()+touchTurn);
			touchTurn = 0;
		}

		if (touchTurnUp != 0&& cloud3d !=null) {
			cloud3d.setRotX(cloud3d.getRotX()+touchTurnUp);
			touchTurnUp = 0;
		}
		
		
		super.onDrawFrame(glUnused);
		
	}
	@Override
	public void onRender(final double deltaTime) {
		mLight.setLookAt(mEmpty.getPosition());
		mPostProcessingManager.render(deltaTime);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		newTuch(event);
		if (event.getAction() == MotionEvent.ACTION_UP){
			if(event.getY()<100){
				mStart.load();
			}
			if(event.getY()>M.ScreenHieght-100){
				mStart.ShowInterstitial();
			}
		}
		return true;
	}
	
	private float xpos = -1;
	private float ypos = -1;

	private float touchTurn = 0;
	private float touchTurnUp = 0;

	public boolean newTuch(MotionEvent me) {
		
		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			xpos = me.getX();
			ypos = me.getY();
			return true;
		}

		if (me.getAction() == MotionEvent.ACTION_UP) {
			xpos = -1;
			ypos = -1;
			touchTurn = 0;
			touchTurnUp = 0;
			return true;
		}

		if (me.getAction() == MotionEvent.ACTION_MOVE) {
			float xd = me.getX() - xpos;
			float yd = me.getY() - ypos;

			xpos = me.getX();
			ypos = me.getY();

			touchTurn = xd / -10f;
			touchTurnUp = yd / -10f;
			return true;
		}

		try {
			Thread.sleep(15);
		} catch (Exception e) {
			// No need for this...
		}

		return true;
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
//			plan.setScale(.1);
			plan.setTransparent(true);
			
			
			plan.setTransparent(true);
			return plan;
		} catch (Exception e) {
			return null;
		}
	}
}
	