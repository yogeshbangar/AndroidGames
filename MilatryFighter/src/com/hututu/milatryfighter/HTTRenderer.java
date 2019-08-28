package com.hututu.milatryfighter;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import rajawali.ChaseCamera;
import rajawali.Object3D;
import rajawali.OrthographicCamera;
import rajawali.animation.mesh.VertexAnimationObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.parser.Loader3DSMax;
import rajawali.parser.LoaderMD2;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

public class HTTRenderer extends RajawaliRenderer  implements OnTouchListener, SensorEventListener {
	
	static Start mStart;
	static Context mContext;
	DirectionalLight mLight[];
//	VertexAnimationObject3D /*o3d_One,*/o3d_Two;
	Plane oLogo;

	Loader3DSMax objParser;
	Object3D Road[];
	
	
	Object3D Box[];
	
	Object3D Gofor ;
	
	
	SensorManager mSensorManager;
	Group root;
	int ChallengeCom = 0;
	
	public HTTRenderer(Context context) {
		super(context);
		setFrameRate(60);
		mContext =context;
		mStart = (Start)context;
		mSensorManager = (SensorManager) Start.mContext.getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);
		root = new Group(this);
		// TODO Auto-generated constructor stub
	}
	protected void initScene() {
		try {
			mSurfaceView.setOnTouchListener(this);
			/*mLight = new DirectionalLight[4];
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
			}*/
//			init();
			
			
			oLogo = addPlan("facebook.png");
			oLogo.setRotation(180, 0, 180);
			oLogo.setPosition(0,0, 0);
			oLogo.setScale(.1f);
			oLogo.setTransparent(true);
			getCurrentScene().addChild(oLogo);
			
//			temp();
			getCurrentCamera().setZ(6);

			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
//	OrthographicCamera camera = new OrthographicCamera();
	void init() {
		try {
			
//			LoaderMD2 one = new LoaderMD2(mContext.getResources(),mTextureManager, R.raw.road);
//			one.parse();
//			o3d_One = (VertexAnimationObject3D) one.getParsedAnimationObject();
//			o3d_One.setScale(.02f);
//			o3d_One.setPosition(0,0,0);
//			o3d_One.setBackSided(true);
//			o3d_One.setDoubleSided(true);
//			getCurrentScene().addChild(o3d_One);
			
//			LoaderMD2 two = new LoaderMD2(mContext.getResources(),mTextureManager, R.raw.bike1);
//			two.parse();
//			o3d_Two = (VertexAnimationObject3D) two.getParsedAnimationObject();
//			o3d_Two.setScale(.05f);
//			o3d_Two.setPosition(0, 0, 0);
//			o3d_Two.setRotY(90);
//			o3d_Two.setBackSided(true);
//			getCurrentScene().addChild(o3d_Two);
//			o3d_Two.setVisible(false);
//			
			load3ds2();
			{
				objParser = new Loader3DSMax(this, R.raw.box);
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("Gofor",
						LoadImgfromAsset("box.png")));
				Gofor = objParser.getParsedObject();
				Gofor.setScale(.01f);
				Gofor.setPosition(0, 0, 0);
				Gofor.setMaterial(cloudmat2);
				// Gofor.setColor(0);
				getCurrentScene().addChild(Gofor);
				objParser.clear();
			}
//			Box();
			
			
			ChaseCamera chaseCamera = new ChaseCamera(new Vector3(0, 3, 16),
					.1f);
			// -- tell the camera which object to chase
			chaseCamera.setObjectToChase(Gofor);
			// -- set the far plane to 1000 so that we actually see the sky sphere
			chaseCamera.setFarPlane(1000);
			getCurrentScene().replaceAndSwitchCamera(chaseCamera, 0);
			
			setVisible(false);
			gameReset();
			mStart.resume();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	void gameReset(){}
	
	float scal = 1;
	public void load3ds2() {
		Road = new Object3D[20];
		for (int i = 0; i < Road.length; i++) {
			if(i<12){
				objParser = new Loader3DSMax(this, R.raw.raod_1);
			}else /*if(i==12 || i==13||i==14)*/{
				objParser = new Loader3DSMax(this, R.raw.curve);
			}
			/*else if(i==15 || i==16||i==17){
				objParser = new Loader3DSMax(this, R.raw.road2);
			}
			else if(i==18){
				objParser = new Loader3DSMax(this, R.raw.road1);
			}
			else if(i==19){
				objParser = new Loader3DSMax(this, R.raw.road3);
			}*/
			try {
				objParser.parse();
				Material cloudmat2 = new Material();
				if(i<12){
					cloudmat2.addTexture(new Texture("road"+i,LoadImgfromAsset("raod_1.jpg")));
				}else /*if(i==12 || i==13||i==14)*/{
					cloudmat2.addTexture(new Texture("road"+i,LoadImgfromAsset("curve.png")));
				}
				Road[i] = objParser.getParsedObject();
				Road[i].setScale(.001f);
				Road[i].setPosition(0,2-i*.1, 0);
				Road[i].setMaterial(cloudmat2);
				Road[i].setColor(0);
				getCurrentScene().addChild(Road[i]);
				objParser.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
//	Loader3DSMax objParser;
	Object3D m3d_Egle,m3d_B2;
	void temp(){
		try {
		{
			Material cloudmat2 = new Material();
			objParser = new Loader3DSMax(this, R.raw.raod_1);
			cloudmat2.addTexture(new Texture("b1", LoadImgfromAsset("raod_1.jpg")));
			objParser.parse();
			m3d_Egle = objParser.getParsedObject();
			m3d_Egle.setScale(.2f);
			m3d_Egle.setRotZ(180);
			m3d_Egle.setPosition(0,0,0);
			m3d_Egle.setMaterial(cloudmat2);
			m3d_Egle.setColor(0);
			getCurrentScene().addChild(m3d_Egle);
			objParser.clear();
		}{
			Material cloudmat2 = new Material();
			objParser = new Loader3DSMax(this, R.raw.b2);
			cloudmat2.addTexture(new Texture("b1", LoadImgfromAsset("b1.png")));
			objParser.parse();
			m3d_B2 = objParser.getParsedObject();
			m3d_B2.setScale(.2f);
			m3d_B2.setRotZ(180);
			m3d_B2.setPosition(.46,0, 0);
			m3d_B2.setMaterial(cloudmat2);
			m3d_B2.setColor(0);
			getCurrentScene().addChild(m3d_B2);
			objParser.clear();
		
		}}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void position(float sca,float x,float y) {
		scal = sca;
		for (int i = 0; i < Road.length; i++) {
			Road[i].setScale(scal*.01f);
			switch (i) {
			case 0:case 1:case 2:
				Road[i].setPosition(x+scal*0,y+i*scal,0);
				Road[i].setRotZ(90);
				break;
			case 3:
				Road[i].setPosition(x+scal*2,y+4*scal,0);
				Road[i].setRotZ(0);
				break;
			case 4:
				Road[i].setPosition(x+scal*4,y+6*scal,0);
				Road[i].setRotZ(90);
				break;
			case 5:
				Road[i].setPosition(x+scal*6,y+8*scal,0);
				Road[i].setRotZ(0);
				break;
			case 6:case 7:case 8:
				Road[i].setPosition(x+scal*8,y+(12-i)*scal,0);
				Road[i].setRotZ(90);
				break;
			case 9:
				Road[i].setPosition(x+scal*6,y+(11-i)*scal,0);
				Road[i].setRotZ(0);
				break;
			case 10:
				Road[i].setPosition(x+scal*4,y+(10-i)*scal,0);
				Road[i].setRotZ(90);
				break;
			case 11:
				Road[i].setPosition(x+scal*2,y+(9-i)*scal,0);
				Road[i].setRotZ(0);
				break;
			case 12:
				Road[i].setPosition(x+scal*1.5,y+2.5*scal,0);
				Road[i].setRotZ(-90);
				break;
			case 13:
				Road[i].setPosition(x+scal*2.5,y+5.5*scal,0);
				Road[i].setRotZ(90);
				break;
			case 14:
				Road[i].setPosition(x+scal*5.5,y+6.5*scal,0);
				Road[i].setRotZ(-90);
				break;
			case 15:
				Road[i].setPosition(x+scal*6.5,y+6.5*scal,0);
				Road[i].setRotZ(0);
				break;
			case 16:
				Road[i].setPosition(x+scal*6.5,y+3.5*scal,0);
				Road[i].setRotZ(90);
				break;
			case 17:
				Road[i].setPosition(x+scal*5.5,y+0.5*scal,0);
				Road[i].setRotZ(-90);
				break;
			case 18:
				Road[i].setPosition(x+scal*2.5,y-.5*scal,0);
				Road[i].setRotZ(90);
				break;
			case 19:
				Road[i].setPosition(x+scal*1.5,y-.5*scal,0);
				Road[i].setRotZ(180);
			break;
			default:
				Road[i].setVisible(false);
				break;
			}
			
		}
		

	}
	
	public void Box() {
		Box = new Object3D[20];
		for (int i = 0; i < Box.length; i++) {
				objParser = new Loader3DSMax(this, R.raw.box);
			try {
				objParser.parse();
				Material cloudmat2 = new Material();
				cloudmat2.addTexture(new Texture("Box"+i,LoadImgfromAsset("box.png")));
				Box[i] = objParser.getParsedObject();
				Box[i].setScale(.01f);
				Box[i].setPosition(0,i*.6, 0);
				Box[i].setMaterial(cloudmat2);
//				Box[i].setColor(0);
				getCurrentScene().addChild(Box[i]);
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
//		root.Draw();
		
		
		super.onDrawFrame(glUnused);
		
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		root.onTouch(v, event);
		return true;
	}
	
	
	
	
	
	

	void setVisible(boolean visible) {}

	

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		System.out.println(event.values[0] + "     " + event.values[1]+"   "+event.values[2]);
	}
}
	