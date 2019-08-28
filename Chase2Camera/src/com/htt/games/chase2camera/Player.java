package com.htt.games.chase2camera;

import rajawali.Object3D;
import rajawali.math.Quaternion;
import rajawali.math.vector.Vector3;
import rajawali.math.vector.Vector3.Axis;

public class Player {
	float spd = .1f;
	float vx,vy = spd;
	Object3D Gofor;
	HTTRenderer mGR;
	float com = 10;
	
	Vector3 baseDirection = new Vector3(1,0,0);
	Vector3 direction = new Vector3();
	Quaternion q = new Quaternion().fromAngleAxis(Axis.Y, 0.0);
	
	public Player(HTTRenderer GR){
		mGR = GR;
	}
	void set(){
		vx = 0;
		vy = spd;
	}
	float mTime =0;
	void updateNew() {
		
		
		
		
		Gofor.setZ(Gofor.getZ() + 2f);
		Gofor.setX((float) Math.sin(mTime) * 20f);
		/*Gofor.setRotZ((float) Math.sin(mTime + 8f) * -30f);
		Gofor.setRotY(180 + (Gofor.getRotZ() * .1f));
		Gofor.setRotY(180);
		Gofor.setY((float) Math.cos(mTime) * 10f);*/
		Gofor.setRotX((float) Math.cos(mTime + 1f) * -20f);

//		mSphere.setZ(Gofor.getZ());
		mTime += .01f;
		
		
		
		
		
	/*	Gofor.setVisible(true);
		Gofor.setPosition(Gofor.getX() + 0, Gofor.getY() + 1f, 0);*/
	}
	
	boolean update() {
		Gofor.setVisible(true);
		Gofor.setPosition(Gofor.getX() + vx, Gofor.getY() + vy, 0);
		
		if (Gofor.getY() > com) {
			vx = spd;
			vy = 0;
			Gofor.setY(com);
			setRotetaion();
			Gofor.setRotation(0, 0, 90);
			return true;
		}
		if (Gofor.getX() > com) {
			vx = 0;
			vy = -spd;
//			vy =0;
			Gofor.setX(com);
			Gofor.setRotation(0, 0, 180);
			setRotetaion();
			return true;
		}
		if (Gofor.getY() < -com) {
			vx = -spd;
			vy = 0.0f;
//			vx = 0;
			Gofor.setY(-com);
			Gofor.setRotation(0, 0, 270);
			setRotetaion();
			return true;
		}
		if (Gofor.getX() < -com) {
			vx = 0;
			vy = spd;
			Gofor.setX(-com);
			Gofor.setRotation(0, 0, 0);
			setRotetaion();
			return true;
		}
		return false;
	}
	
	void setRotetaion(){
		/*Vector3 Position = new Vector3();
		float[] newPosition4 = new float[4];
		Position = mGR.oLogo.getPosition();
		float[] posVec4 = {(float)Position.x, (float)Position.y, (float)Position.z, 1.0f};

		float[] HeadViewMatrix_inv = new float[16];
		Matrix4 HeadViewMatrix4 = new Matrix4();
		HeadViewMatrix4.setAll(mGR.oLogo.getModelMatrix());
		HeadViewMatrix4 = HeadViewMatrix4.inverse();
		HeadViewMatrix4.toFloatArray(HeadViewMatrix_inv);

		Matrix.multiplyMV(newPosition4, 0, HeadViewMatrix_inv, 0, posVec4, 0);

		Gofor.setPosition(newPosition4[0], newPosition4[1], newPosition4[2]);*/	
	}
	
}
