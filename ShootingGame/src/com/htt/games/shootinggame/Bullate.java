package com.htt.games.shootinggame;

import rajawali.Object3D;

public class Bullate {
	Object3D Obj3D;
	float vx,vy,vz;
	float ang;
	public Bullate(Object3D Obj){
		Obj3D = Obj;
		Obj3D.setScale(.05f);
		Obj3D.setPosition(0, 0, 0);
		Obj3D.setColor(0);
		vx= 0;vy=0;
		ang =0;
		Obj3D.setVisible(false);
	}
	public void reset(){
		Obj3D.setPosition(0, 70, 0);
		vx = vy =0;
		Obj3D.setVisible(false);
	}
	public void set(float x,float y){
		Obj3D.setPosition(0, -14, 0);
		vx = (x)*2;
		vy = (1.7f+y)*2;
	}
	
	void update() {
		if (Obj3D.getY() < 60) {
			Obj3D.setVisible(true);
			Obj3D.setPosition(Obj3D.getX() + vx, Obj3D.getY() + vy,0);
		} else {
			Obj3D.setVisible(false);
		}
	}
}
