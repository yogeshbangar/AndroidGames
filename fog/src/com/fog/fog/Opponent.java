package com.fog.fog;

import rajawali.Object3D;
import rajawali.materials.Material;

public class Opponent {
	Object3D m3ds;
	public Opponent(Object3D _3ds,Material cloudmat2){
		m3ds = _3ds;
		m3ds.setMaterial(cloudmat2);
		m3ds.setColor(0);
		m3ds.setPosition((M.mRand.nextBoolean()?.1:-.1)*M.mRand.nextInt(25), 0, 10);
		m3ds.setRotation(90, 0,-90);
		m3ds.setScale(.025);
		m3ds.setVisible(false);
	}
	void set(){
		m3ds.setPosition((M.mRand.nextBoolean()?.1:-.1)*M.mRand.nextInt(25), 0, -60);
	}
}
