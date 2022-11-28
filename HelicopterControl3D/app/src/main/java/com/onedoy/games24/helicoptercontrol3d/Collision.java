package com.onedoy.games24.helicoptercontrol3d;

import org.rajawali3d.materials.Material;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Cube;


public class Collision {
	Cube mCube;
	Vector3 vx = new Vector3();
	int rot;
	public Collision(HTTRenderer mGR) {
		try {
			Material material1 = new Material();
			material1.setColorInfluence(1);
			mCube = new Cube(1);
			mCube.setMaterial(material1);
			mCube.setScale(1);
			mGR.getCurrentScene().addChild(mCube);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	void set(Vector3 v,int ml){
		mCube.setScale(1);
		mCube.setVisible(true);
		mCube.setPosition(v);
		vx.setAll((M.mRand.nextInt(20)-10)*.01f, (M.mRand.nextInt(20)-10)*.01f, (M.mRand.nextInt(20))*.01f);
		mCube.setColor(-1001000*(ml+1));
	}
	void update(){
		if(mCube.getZ()>0){
			mCube.setPosition(mCube.getPosition().add(vx));
			mCube.setScale(mCube.getScaleX()-.01);
			vx.z-=.01f;
			mCube.setVisible(true);
		}
	}
}
