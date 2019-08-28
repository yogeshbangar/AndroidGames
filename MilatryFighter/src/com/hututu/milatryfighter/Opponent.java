package com.hututu.milatryfighter;

import rajawali.animation.mesh.VertexAnimationObject3D;
import rajawali.parser.LoaderMD2;
import rajawali.primitives.Plane;

public class Opponent {
	int indi;
	
	float spd = 0;
	float vx;
	float up = .02f;
	
	VertexAnimationObject3D obj;
	Plane oShadow;
	Plane oIndicator;
	public Opponent(LoaderMD2 parser,Plane shdow,Plane Indicator) {
		obj = (VertexAnimationObject3D) parser.getParsedAnimationObject();
		obj.setScale(.01f);
		obj.setPosition(0,-.5f,1);
		obj.setBackSided(true);
		oShadow =shdow;
		oIndicator=Indicator;
		oShadow.setRotation(180, 0, 180);
		oIndicator.setPosition(0, -1.1f, 1.5f);
		oIndicator.setScale(.56f);
		oIndicator.setRotation(120, 0, 180);
		
	}
	
	void set(float _spd,double y,int _indi){
		spd = _spd;
		obj.setScale(.01f);
		obj.setPosition(-.7+(M.mRand.nextInt(3))*.7, y, 1);
		obj.setRotation(0, 0, 0);
		obj.setBackSided(true);
		indi=_indi;
		vx =0;
	}

	void update(float _spd) {
		obj.setY(obj.getY() - _spd);
		if (obj.getY() < 12 && indi != 0) {
			if (indi == 1 && vx < .75) {
				vx += up;
				obj.setX(obj.getX() + up);
				if (vx >= .7)
					indi = 0;
			}
			if (indi == 2 && vx < .75) {
				vx += up;
				obj.setX(obj.getX() - up);
				if (vx >= .7)
					indi = 0;
			}
		}
	}
}
