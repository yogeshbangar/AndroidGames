package com.httgames.shootthebottle3d;

import rajawali.Object3D;

public class Ball {
	boolean isCracked = false;
	float vx,vy,vz;
	int img;
	Object3D obj3d;
	int count;
	void set(double ang){
		obj3d.setPosition(0, -4, 0);
		vx = (float) Math.sin(ang) * .12f;
		vy =-(float) Math.cos(ang) * .15f;
		vz = .07f;
		isCracked = false;
	}
	void update(){
		if(obj3d.getZ()>=0){
			obj3d.setPosition(obj3d.getX()+vx,obj3d.getY()+vy,obj3d.getZ()+vz);
			obj3d.setRotation(count*2.0, -count*2.5, count*3.0);
			count++;
			vz-=.002f;
			obj3d.setVisible(true);
		}else{
			obj3d.setVisible(false);
		}
	}
}
