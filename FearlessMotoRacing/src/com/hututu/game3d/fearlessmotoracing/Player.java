package com.hututu.game3d.fearlessmotoracing;

import rajawali.animation.mesh.VertexAnimationObject3D;
import rajawali.parser.LoaderMD2;
import rajawali.primitives.Plane;

public class Player {
	
	VertexAnimationObject3D obj[];
	VertexAnimationObject3D o3d_Player, o3d_PExd;
	Plane oShadow;
	boolean isTap;
	boolean isRoad = false;
	
	int level;
	int gear;
	int rowOpp = 0;
	int counter = 0;
	int bike = 0;
	int Crash = 0;
	int Crosscar =0;
	int Road = 2;
	int CollectCoin =0;
	int tapBot = 0; 
	

	float vx;
	float Spd = 0.2f;
	float newOpp = 0;
	float Distance = 0;

	public Player(LoaderMD2 parser1,LoaderMD2 parser2,LoaderMD2 parser3,LoaderMD2 ply,LoaderMD2 exp,Plane shdow) {
		obj = new VertexAnimationObject3D[3];
		obj[0] = (VertexAnimationObject3D) parser1.getParsedAnimationObject();
		obj[1] = (VertexAnimationObject3D) parser2.getParsedAnimationObject();
		obj[2] = (VertexAnimationObject3D) parser3.getParsedAnimationObject();
		o3d_Player= (VertexAnimationObject3D) ply.getParsedAnimationObject();
		o3d_PExd= (VertexAnimationObject3D) exp.getParsedAnimationObject();
		oShadow=shdow;
		oShadow.setRotation(180, 0, 180);
		vx = 0;
	}

	void set() {
//		obj.setScale(.02f, .01, .01);
		for(int i =0;i<obj.length;i++){
			obj[i].setScale(.011f);
			obj[i].setPosition(0, -.5f, 1);
			obj[i].setRotation(0, 0, 0);
			obj[i].setBackSided(true);
		}
		
		o3d_Player.setScale(.011f);
		o3d_Player.setBackSided(true);
		o3d_Player.setRotation(0, 0, 0);
		
		o3d_PExd.setScale(.011f);
		o3d_PExd.setBackSided(true);
		o3d_PExd.setRotation(0, 0, 0);
		
		oShadow.setScale(.9);
		oShadow.setRotation(180, 0, 0);
		oShadow.setPosition(obj[bike].getX(), obj[bike].getY()+.3, .65);
		
		isTap = false;
		level = gear = rowOpp = 0;
		Distance = vx = Spd = newOpp = 0;
		Crash =0;
		CollectCoin =Crosscar =0;
		Road = 2;
		isRoad = false;
		counter = 200;
	}

	void update() {
		Spd = .2f + gear * .005f + level * .05f;
		newOpp += Spd;
		Distance+= Spd;
		if (Distance > 560 * (level + 1) && level < 5) {
			level++;
			isRoad = true;
		}
		if (isTap) {
			if (gear < 30) {
				gear++;
			}
			if (tapBot < 4 && counter < 100) {
				tapBot++;
				counter = 206;
			}
			if (counter > 98){
				counter-= 5-tapBot;
			}
		} else {
			counter = 206;
			tapBot = 0;
			if(gear>2)
				gear -=2;
		}
		
		
		if (vx != 0) {
			obj[bike].setX(obj[bike].getX() + vx);
			obj[bike].setRotY(-vx*500);
			o3d_Player.setRotY(-vx*500);
			oShadow.setX(obj[bike].getX()-vx*3);
		}
		else{
			obj[bike].setRotY(0);
			o3d_Player.setRotY(0);
			oShadow.setX(obj[bike].getX());
		}
		if (obj[bike].getX() < -1.0) {
			obj[bike].setX(-1.0);
			obj[bike].setRotY(0);
			o3d_Player.setRotY(0);
			oShadow.setX(obj[bike].getX());
			
//			if(Crash ==0)
				M.sound6(R.drawable.blast_k);
			M.Bikepause(); 
//			M.stop();
			gameCrash();
			HTTRenderer.mCoin += CollectCoin;
			
			
			
		}
		if (obj[bike].getX() > 1.0) {
			obj[bike].setX(1.0);
			obj[bike].setRotY(0);
			o3d_Player.setRotY(0);
			oShadow.setX(obj[bike].getX());
			

//			if(Crash ==0)
				M.sound6(R.drawable.blast_k);
			M.Bikepause(); 
//			M.stop();
			gameCrash();
			HTTRenderer.mCoin += CollectCoin;
			
		}
		if(Crash>0){
			Crash++;
			Spd = 0f;
			obj[bike].setRotY(90);
			obj[bike].setRotZ(-Crash*.5);
			obj[bike].setPosition(obj[bike].getX()+(obj[bike].getX()>0?0.02f:-.02), obj[bike].getY()+.02, obj[bike].getZ());
			o3d_PExd.setPosition(o3d_PExd.getX()-(obj[bike].getX()>0?0.03f:-.03),o3d_PExd.getY() + .02,o3d_PExd.getZ());
			o3d_Player.setVisible(false);
			o3d_PExd.setVisible(true);
			oShadow.setVisible(false);
		}else {
			if (bike == 0) {
				o3d_Player.setPosition(obj[bike].getX(),obj[bike].getY() + .01,obj[bike].getZ() + .00);
				// -0.37999994f, 0.03f, 5.5134296E-7f
			} else if (bike == 1) {
				o3d_Player.setPosition(obj[bike].getX(),obj[bike].getY() + .12,obj[bike].getZ() - .06);
				// -0.55999976f, 0.11999998f, -0.059999444f
			} else {
				o3d_Player.setPosition(obj[bike].getX(),obj[bike].getY() + .11,obj[bike].getZ() - .03);
				// -0.55999976f, 0.109999985f, -0.029999448f
			}
			o3d_Player.setVisible(true);
			o3d_PExd.setVisible(false);
			oShadow.setVisible(true);
		}
		
	}
	void gameCrash(){
		Crash++;
		o3d_PExd.setPosition(obj[bike].getX(),obj[bike].getY() + .03,obj[bike].getZ() + .00);
	}
}
