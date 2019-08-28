package com.oneday.games24.fightersofocean;

public class Player {
	boolean tuch;			//Shoot Active
	
	int mB;					//Boat No 
	int mBullet;			//Reload Bullet
	int mDamageBy;			//Damage Capacity
	int mNoKill;			//Total Kill Opponent
	int mKillTarget;		//Number of Kill Opponent
	int mCoin;				//Player Coin
	int mLCoin;				//Player Coin
	int mXP;				//Player XP
	int FirBullate;			//Bullet used
	int Accurecy;			//Bullet Accuracy
	int Power;				//Boat Power
	int BoatMan;			//Boat Man
	int Bomb;				//Bomb Count
	int Net;				//Net Count
	int MaxLife;			//Player MaxLife
	int mGunType;			//Gun Type
	float x,y;				//Boat Position
	float tx,ty;			//Target position
	
	public Player()
	{
		mGunType	= BoatMan	= Net	= Bomb	=  mXP	= 0;
		mCoin 		= 0	;
		Power 		=-1	;
		MaxLife 	= 500;
		mDamageBy 	= 1	;
	}
	
	void set(float _x,float _y,int _Boat,int DemageBy,int KillTarget,int _Bullate)
	{
		x =_x;
		y =_y;
		mB =_Boat;
		mBullet = _Bullate;
		tuch = false;
		mDamageBy =DemageBy;
		mKillTarget =KillTarget;
		mNoKill =0;
		FirBullate =0;
		Accurecy = 0;
		mLCoin =0;
	}
}
