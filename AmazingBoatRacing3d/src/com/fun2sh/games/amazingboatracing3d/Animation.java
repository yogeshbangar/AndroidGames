package com.fun2sh.games.amazingboatracing3d;
import rajawali.Object3D;
public class Animation {
	
	 Object3D mobj[] = new Object3D[5];
	 float x,y,z,sx,sy,sz;
	 int mCnt=0,mAniCnt=0;
	 void set(float _x,float _y,float _z,float _sx,float _sy,float _sz,int _no)
	 {
		 x = _x;
		 y = _y;
		 z = _z;
		 sx = _sx;
		 sy = _sy;
		 sz = _sz;
		 mCnt=0;
		 mAniCnt =0;
		 for(int i=0;i<mobj.length;i++)
		 {
			 mobj[i].setPosition(x,y,z);
			 mobj[i].setScale(sx,sy,sz);
			 mobj[i].setVisible(false);
		 }
		 
	 }
	 void DrawAni(int _no,float _x,boolean isVisible)
	 {
		  x = _x;
		 for(int i=0;i<mobj.length;i++)
		 {
		    mobj[i].setVisible(i==_no?isVisible:false);
		    if(i==_no)
		    {
		      float rad = (float)Math.toRadians(mCnt);
		  	  mCnt+=5;
			  mCnt%=360;
		      mobj[i].setPosition(x,y,z-=(float)(Math.sin(rad))*.015f);
		      x = (float)mobj[i].getX();
		      y = (float)mobj[i].getY();
			  z = (float)mobj[i].getZ();
		    }
		 }
	 }
	 void DrawOppAni(int _no,float _x,float _y,float _z,boolean isVisible)
	 {
		  x = _x;
		  y = _y;
		  z = _z;
		 for(int i=0;i<mobj.length;i++)
		 {
		    mobj[i].setVisible(i==_no?isVisible:false);
		    if(i==_no)
		    {
		      float rad = (float)Math.toRadians(mCnt);
		  	  mCnt+=5;
			  mCnt%=360;
		      mobj[i].setPosition(x,y,z-=(float)(Math.sin(rad))*.015f);
		      x = (float)mobj[i].getX();
		      y = (float)mobj[i].getY();
			  z = (float)mobj[i].getZ();
		    }
		 }
	 }
	 
}
