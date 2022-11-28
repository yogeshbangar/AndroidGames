package com.onedoy.games24.helicoptercontrol3d;

import java.util.ArrayList;

import org.rajawali3d.Object3D;
import org.rajawali3d.primitives.Plane;

import android.graphics.Bitmap;

public class Player {
	
	boolean isTap;
	
	int OverCount = 0;
	int imgNo;
	
	float vz = .1f;
	
	ArrayList<Object3D> obj = new ArrayList<Object3D>();
	Object3D m3D_Blade;
	Plane m2D_Shado;
	
	HTTRenderer mGR;
	
	public Player(HTTRenderer GR, Bitmap b) {
		mGR = GR;
		m2D_Shado = mGR.addPlan2(b);
	}

	void set() {
		if (obj.size() > 0) {
			obj.get(0).setVisible(true);
			obj.get(0).setRotX(-90);
			obj.get(0).setPosition(-7, 1.4, 8);
			obj.get(0).setScale(.2f);
			m2D_Shado.setPosition(-8.5, 1.4, 2.23);
			m2D_Shado.setRotX(180);
			m2D_Shado.setScale(.5);
			m3D_Blade.setPosition(-7.8, 1.4, 9.8);
			m3D_Blade.setScale(.32);
		}
		isTap = false;
		vz = 0.2f;
		OverCount = 0;
	}

	void update(int Counter) {
//		obj.get(0).setVisible(false);
		obj.get(0).setZ(obj.get(0).getZ() + vz);
		obj.get(0).setVisible(true);
		m2D_Shado.setVisible(true);
		m3D_Blade.setVisible(true);
		m3D_Blade.setRotation(Counter*20,0,0);
		m3D_Blade.setZ(obj.get(0).getZ()+1.8);
		if (!isTap)
			vz -= .013f;
		else
			vz += .013f;
		if(mGR.iSGamePause){
			if(obj.get(0).getZ()<7)
				vz = .1f;
		}
		
		if (obj.get(0).getZ() < 2 || obj.get(0).getZ() > 16){
			mGR.root.SetOver();
		}
	}
	void setOvet(){
		obj.get(0).setVisible(false);
		m2D_Shado.setVisible(false);
		m3D_Blade.setVisible(false);
		OverCount =1;
	}
}
