package com.httgames.shootthebottle3d;

import rajawali.primitives.Plane;

public class Particle {
	float vx,vz;
	byte rot = 0;
	public Particle(Plane _Plan){
		oPlan = _Plan;
		oPlan.setScale(2);
		oPlan.setDoubleSided(true);
	}
	
	void set(float _x){
		oPlan.setPosition(_x,0,1f);
		vx = (M.mRand.nextInt(50)-25)*.001f;
		vz = (M.mRand.nextInt(10)+10)*.005f;
		oPlan.setVisible(true);
		rot =(byte) (M.mRand.nextInt(30)+5);
		
	}
	void update(){
		if(oPlan.getZ()>.9){
			oPlan.setPosition(oPlan.getX()+vx,-.1,oPlan.getZ()+vz);
			oPlan.setRotation(oPlan.getRotX()+rot, oPlan.getRotY()+rot, oPlan.getRotZ()+rot);
//			oPlan.setVisible(true);
			vz-=.002f;
		}
	}
	Plane oPlan;
}
