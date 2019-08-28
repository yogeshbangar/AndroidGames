package com.oneday.games24.motocitytraffic;

public class Car {
    float x,y,vx,vy;
    float sx,sy;
    float ang,Speed;
    int no,mTap=0,mAni=0;
    boolean isCollide,isBridge,isTuunel,isStop;
    
    GameRenderer mGR;
    public Car(GameRenderer _mGR)
    {
	   mGR = _mGR;
	   x=y=100;
	} 
    public void set(float _x,float _y,float _vx,float _vy,float _ang)
    {
    	sx =x    	  = _x;
    	sy =y    	  =  _y;
    	ang  	  = _ang;
    	no   	  =  M.mRand.nextInt(mGR.mTex_Car.length);
    	Speed 	  = 1;
    	mTap      = 0;
    	isCollide = false;
    	isBridge  = false;
    	isTuunel  = false;
    	isStop    = false;
    	vx =  (float)Math.cos(Math.toRadians(ang))*.015f; 
		vy = -(float)Math.sin(Math.toRadians(ang))*.025f;
    }
    void update()
    {
    	  x +=(vx*Speed);
    	  y +=(vy*Speed);
    }
    void resetCar(float _x,float _y)
    {
    	x =_x;
    	y =_y;
    	vx=vy=0;
    	mTap=0;
    }
    void setBlast(float _x,float _y)
    {
    	x = _x;
    	y = _y;
    	mAni =0;
    }
}
