package com.htt.games.chase2camera;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Matrix;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.hardware.SensorManager;

import rajawali.Camera;
import rajawali.ChaseCamera;
import rajawali.Object3D;
import rajawali.SerializedObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.textures.Texture;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.math.Matrix4;
import rajawali.math.Quaternion;
import rajawali.math.vector.Vector3;
import rajawali.parser.Loader3DSMax;
import rajawali.primitives.Cube;
import rajawali.primitives.Plane;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;

public class HTTRenderer extends RajawaliRenderer  implements OnTouchListener{

	private Object3D mRaptor, mSphere;
	private Object3D[] mCubes;
	private Object3D mRootCube;
	Plane oRoad[];
	private float mTime;
	Group root;
	public HTTRenderer(Context context) {
		super(context);
		root = new Group(this);
	}
	Vector3 mCameraOffset = new Vector3();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			setCameraOffset(mCameraOffset);
		}
	};
	public void onProgressChanged() {
		
		handler.sendEmptyMessage(0);
	}
	protected void initScene() {
		mSurfaceView.setOnTouchListener(this);
		DirectionalLight light = new DirectionalLight(0, -.6f, .4f);
		light.setPower(1);
		getCurrentScene().addLight(light);

		// -- create sky sphere
		mSphere = new Sphere(400, 8, 8);
		Material sphereMaterial = new Material();
		try {
			sphereMaterial.addTexture(new Texture("skySphere", R.drawable.skysphere));
			sphereMaterial.setColorInfluence(0);
		} catch (TextureException e1) {
			e1.printStackTrace();
		}
		mSphere.setMaterial(sphereMaterial);
		mSphere.setDoubleSided(true);
		getCurrentScene().addChild(mSphere);

		try {
			// -- load gzipped serialized object
			ObjectInputStream ois;
			GZIPInputStream zis = new GZIPInputStream(mContext
					.getResources().openRawResource(R.raw.raptor));
			ois = new ObjectInputStream(zis);
			mRaptor = new Object3D(
					(SerializedObject3D) ois.readObject());
			Material raptorMaterial = new Material();
			raptorMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
			raptorMaterial.enableLighting(true);
			raptorMaterial.addTexture(new Texture("raptorTex", R.drawable.raptor_texture));
			raptorMaterial.setColorInfluence(0);
			mRaptor.setMaterial(raptorMaterial);
			mRaptor.setScale(.5f);
			getCurrentScene().addChild(mRaptor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// -- create a bunch of cubes that will serve as orientation helpers

		mCubes = new Object3D[30];

		mRootCube = new Cube(1);
		Material rootCubeMaterial = new Material();
		rootCubeMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
		rootCubeMaterial.enableLighting(true);
		try {
			rootCubeMaterial.addTexture(new Texture("camouflage", R.drawable.camouflage));
			rootCubeMaterial.setColorInfluence(0);
		} catch (TextureException e) {
			e.printStackTrace();
		}
		mRootCube.setMaterial(rootCubeMaterial);
		mRootCube.setY(-1f);
		// -- similar objects with the same material, optimize
		mRootCube.setRenderChildrenAsBatch(true);
		getCurrentScene().addChild(mRootCube);
		
		
		
		mCubes[0] = mRootCube;

		for (int i = 1; i < mCubes.length; ++i) {
			Object3D cube = mRootCube.clone(true);
			cube.setY(-1f);
			cube.setZ(i * 30);
			mRootCube.addChild(cube);
			mCubes[i] = cube;
			mCubes[i].setVisible(true);
		}

		
		oRoad = new Plane[20];
		for(int i =0;i<oRoad.length;i++){
			oRoad[i] = addPlan("raod_5.png");
			oRoad[i].setRotation(90, 0, 0);
			oRoad[i].setPosition(0,0,50*i);
			oRoad[i].setDoubleSided(true);
			oRoad[i].setScale(15f);
			oRoad[i].setVisible(true);
			getCurrentScene().addChild(oRoad[i]);
		}

		
		// -- create a chase camera
		// the first parameter is the camera offset
		// the second parameter is the interpolation factor
		ChaseCamera chaseCamera = new ChaseCamera(new Vector3(0, 3, 16),
				.1f);
		// -- tell the camera which object to chase
		chaseCamera.setObjectToChase(mRaptor);
		// -- set the far plane to 1000 so that we actually see the sky sphere
		chaseCamera.setFarPlane(1000);
		getCurrentScene().replaceAndSwitchCamera(chaseCamera, 0);
	}

	public void setCameraOffset(Vector3 offset) {
		
		System.out.println(mCameraOffset.x+"    "+mCameraOffset.y+"    "+mCameraOffset.z);
		
		// -- change the camera offset
		((ChaseCamera) getCurrentCamera()).setCameraOffset(offset);
	}
int counter =0;
	public void onDrawFrame(GL10 glUnused) {
		counter++;
		if(counter == 3){
			ForSwitchCamera();
		}
		
		super.onDrawFrame(glUnused);
		
		mRaptor.setVisible(true);
		// -- no proper physics here, just a bad approximation to keep
		// this example as short as possible ;-)
		mRaptor.setZ(mRaptor.getZ() + 2f);
		mRaptor.setX((float) Math.sin(mTime) * 20f);
		mRaptor.setRotZ((float) Math.sin(mTime + 8f) * -30f);
		mRaptor.setRotY(180 + (mRaptor.getRotZ() * .1f));
		mRaptor.setRotY(180);
		mRaptor.setY((float) Math.cos(mTime) * 10f);
		mRaptor.setRotX((float) Math.cos(mTime + 1f) * -180f);
		mSphere.setVisible(true);
		mSphere.setZ(mRaptor.getZ());
		mTime += .01f;

		mRootCube.setVisible(true);
		
		if (mRootCube.getZ() - mRaptor.getZ() <= (30 * -6)) {
			mRootCube.setZ(mRaptor.getZ());
		}
		
		
		for(int i =0;i<oRoad.length;i++){
			if(oRoad[i].getZ() <mRaptor.getY()-50){
				oRoad[i].setY(oRoad[(i==0?oRoad.length:i)-1].getY()+15);
			}
			oRoad[i].setVisible(true);
		}
		
		root.setting();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		if(MotionEvent.ACTION_UP == event.getAction()){
			mCameraOffset.setAll(root.sx*100, root.sy*100, root.sz*100);
			onProgressChanged();
			
		}
		root.Handle(event);
		return true;
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
	
	/*
	
	static Start mStart;
	static Context mContext;
	DirectionalLight mLight[];
	Plane oLogo;
	
	
	Plane oRoad[];
	Camerar mCam;
	
	
	Camera oCam;
	Loader3DSMax objParser;
//	Object3D m3d_Road[];
	
	float mSeekBarX = 50,mSeekBarY = 50,mSeekBarZ = 50;
//	Object3D Gofor ;
	
	Player mPlayer;
	SensorManager mSensorManager;
	Group root;
	int ChallengeCom = 0;
	public HTTRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mPlayer = new Player(this);
		mContext =context;
		mStart = (Start)context;
		root = new Group(this);
	}
	
	protected void initScene() {
		try {
			mSurfaceView.setOnTouchListener(this);
			oLogo = addPlan("facebook.png");
			oLogo.setRotation(180, 0, 180);
			oLogo.setPosition(0,0, -1);
			oLogo.setDoubleSided(true);
			oLogo.setScale(15f);
			getCurrentScene().addChild(oLogo);
			mCam = new Camerar(this);
			init();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	ChaseCamera chaseCamera ;
	void init() {
		try {
			{
				objParser = new Loader3DSMax(this, R.raw.truck);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("Gofor",LoadImgfromAsset("truck.png")));
				mPlayer.Gofor = objParser.getParsedObject();
				mPlayer.Gofor.setScale(.2f);
				mPlayer.Gofor.setPosition(0, 0, 10);
				mPlayer.Gofor.setRotation(0, 0, 0);
				mPlayer.Gofor.setMaterial(cloudmat2);
				mPlayer.Gofor.setColor(0);
				getCurrentScene().addChild(mPlayer.Gofor);
				objParser.clear();
			}
			oCam =getCurrentCamera();
//			chaseCamera = new ChaseCamera(new Vector3(0, -20, 50),1000f);
			chaseCamera = new ChaseCamera(new Vector3(0, 3, 16),.1f);
			chaseCamera.setObjectToChase(mPlayer.Gofor);
			chaseCamera.setFarPlane(1000);
			getCurrentScene().replaceAndSwitchCamera(chaseCamera, 0);
			
			
			oRoad = new Plane[20];
			for(int i =0;i<oRoad.length;i++){
				oRoad[i] = addPlan("raod_5.png");
				oRoad[i].setRotation(180, 0, 180);
				oRoad[i].setPosition(0,15*i, -1);
				oRoad[i].setDoubleSided(true);
				oRoad[i].setScale(15f);
				getCurrentScene().addChild(oRoad[i]);
			}
			
			
			
//			getCurrentCamera().setZ(60);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
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
//		mCam.update();
		super.onDrawFrame(glUnused);
	}
	Quaternion q = new Quaternion();
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		root.onTouch(v, event);
		if(MotionEvent.ACTION_UP == event.getAction()){
			mCameraOffset.setAll(root.sx*100, root.sy*100, root.sz*100);
			onProgressChanged();
			
			
			if(getCurrentCamera() == chaseCamera)
				getCurrentScene().replaceAndSwitchCamera(oCam, 0);
			else
				getCurrentScene().replaceAndSwitchCamera(chaseCamera, 0);
		}
		return true;
	}
	int cot =0;
	
	
	
	
	

*/}
	