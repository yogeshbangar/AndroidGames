package com.onedoy.games24.helicoptercontrol3d;

import org.rajawali3d.Object3D;
import android.graphics.Bitmap;

public class Opponent {
	
	boolean isAdd;
	Object3D m3D_Obj;
	Object3D m2D_Shado;
	
	public Opponent(HTTRenderer GR,Bitmap b){
		m2D_Shado = GR.addPlan2(b);
		m2D_Shado.setScale(.45);
	}
	void set(double x){
		m3D_Obj.setPosition(x + M.mRand.nextInt(10)+30, 1.4, 3.5+M.mRand.nextInt(10));
		m3D_Obj.setScale(1.5);
		m3D_Obj.setColor(-(M.mRand.nextInt(16777216)));
		m2D_Shado.setPosition(x + M.mRand.nextInt(10)+30, 1.4,2.23);
		m3D_Obj.setRotation(0,0, 0);
		isAdd = false;
	}
	void reset(double x){
		set(x);
		m3D_Obj.setX(x);
	}
	int rotX =0;
	void update(HTTRenderer mGR){
		m3D_Obj.setRotX(rotX++);//Rotation(rotX,rotX*2, rotX*3);
		if(m3D_Obj.getX()<-4 && !isAdd){
			m3D_Obj.setColor(0);
			mGR.mScore++;
			mGR.mTotal++;
			if(mGR.mHScore<mGR.mScore)
				mGR.mHScore=mGR.mScore;
			mGR.mFont.update1Bitmap(0, 0, 0);
			isAdd = true;
		}
		if(!mGR.iSGamePause)
			m3D_Obj.setX(m3D_Obj.getX() + M.SPD);
		m2D_Shado.setX(m3D_Obj.getX());
		m3D_Obj.setVisible(true);
		m2D_Shado.setVisible(true);
	}
}
