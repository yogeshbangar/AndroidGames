package com.fun2sh.craftcar;

import rajawali.Object3D;

public class Opponent {
	Object3D m3ds;
	public Opponent(Object3D _3ds){
		m3ds = _3ds;
		m3ds.setColor(0);
		m3ds.setPosition((M.mRand.nextBoolean()?.1:-.1)*M.mRand.nextInt(25), -10, 0);
		m3ds.setRotZ(90);
		m3ds.setScale(.03);
		m3ds.setVisible(false);
	}
	void set(){
		m3ds.setPosition((M.mRand.nextBoolean()?.1:-.1)*M.mRand.nextInt(25), 60, 0);
	}
}
