package com.hututu.game.stuntracingcar;


public class Car {
	float x,y,z,vx,vy,vz,temp;
	int[] cars= new int[3]; 
	void Set(float _x,float _y,float _z,float _vx,float _vy,float _vz,
			int car1,int car2,int car3)
	{
		x  =_x;
		y  =_y;
		z  =_z;
		temp =y;
		vy =-.0001f;
		vx =0.00004f;
		vz =0.00005f;
		
		cars[0]	= car1;
		cars[1]	= car2;
		cars[2]	= car3;
		
	}
	void update()
	{
		x+=vx;
		y+=vy;
		z+=vz;
		if(M.speed > 1)
		{
			vy*=(M.speed+M.oPPSpeed);
			vx*=(M.speed+M.oPPSpeed);
			vz*=(M.speed+M.oPPSpeed);
			vx = Math.abs(vx);
			vy = -Math.abs(vy);
			vz = Math.abs(vz);
		}
		else
		{
			if(vy>0.01)
			{
				vy*=(0.95f);
				vx*=(0.95f);
				vz*=(0.95f);
			}
			vx = -Math.abs(vx);
			vy =  Math.abs(vy);
			vz = -Math.abs(vz);
			//Log.d(vz+"    --------------      "+vx,vy+"      ==============     "+temp);
			
			//Log.d("[ "+vx+" = vx ="+(int)(vx*100000)+"]"+"[ "+vy+" = vy ="+(int)(vy*100000)+"]","[ "+vz+" = vz ="+(int)(vz*100000)+"]");
			if(z<.0||y>temp)
			{
//				Log.d(vz+"    --------------      ",y+"      ==============     "+temp);
				y = -100;
			}
		}
	}
}
