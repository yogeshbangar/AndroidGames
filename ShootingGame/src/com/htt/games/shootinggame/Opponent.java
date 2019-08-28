package com.htt.games.shootinggame;

import rajawali.Object3D;

public class Opponent {
	Object3D Obj3D;
	float vx,vy;
	float ang;
	public Opponent(Object3D Obj){
		Obj3D = Obj;
		Obj3D.setScale(.05f);
		Obj3D.setPosition(0, 0, 0);
		Obj3D.setColor(0);
		vx= 0;vy=0;
	}
	
	public void set(){
		Obj3D.setPosition((M.mRand.nextBoolean()?.1:-.1)*(M.mRand.nextInt(480)), 60, 0);
		ang = (float)M.GetAngle(-Obj3D.getX() , -(Obj3D.getY()+14));
		vx = (float)(Math.cos(ang)*.1);
		vy = (float)(Math.sin(ang)*.1);
	}
	public void update(){
		if(Obj3D.getY() > -14){
			Obj3D.setPosition(Obj3D.getX()+vx,Obj3D.getY()+vy,0);
			Obj3D.setVisible(true);
		}else{
			Obj3D.setVisible(false);
		}
	}
	public void reset(){
		Obj3D.setPosition(0, -50, 0);
	}
}
