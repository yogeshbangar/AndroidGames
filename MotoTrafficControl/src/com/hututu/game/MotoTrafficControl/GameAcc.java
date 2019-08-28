package com.hututu.game.MotoTrafficControl;

public class GameAcc
{
	 int mUnlockVal,mUpgradeVal,noBuy;
	 int mActivetime;
	 float x,y,ang;
	 int no=0;
	 boolean isUnlock,isActive =false;
 
	 public GameAcc(int _UlockVal,int _upgVal,int _mBuy)
	 {
		 mUnlockVal  = _UlockVal;
		 mUpgradeVal = _upgVal;
		 noBuy       = _mBuy;
		 isUnlock    = false;
		 isActive    = false;
		 x=y=100;
	 }
	 void Set(int _mBuy)
	 {
		 noBuy       = _mBuy;
		 mUpgradeVal = (1+_mBuy)*20;
		 mActivetime = noBuy*25; 
	 }
	 void SetPos(float _x,float _y,float _ang)
	 {
		  x   =  _x;
		  y   =  _y;
		  ang = _ang;
	 }
	 void resetPower()
	 {
		 if(isActive)
		 {
		   x=y=10;
		   mActivetime=0;
		   isActive=false;
		 }
	 }

}
