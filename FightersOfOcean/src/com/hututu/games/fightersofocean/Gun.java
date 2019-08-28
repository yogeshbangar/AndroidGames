package com.hututu.games.fightersofocean;

public class Gun {
	float x;
	final String Name;
	final int mDamage;
	final int mClipSize;
	final int mCost;
	boolean mIsPurchased;
	public Gun(String str,int _Damage,int _ClipSize,int _Cost)
	{
		Name		= new String(str);
		mDamage 	= _Damage;
		mClipSize 	=_ClipSize;
		mCost		=_Cost;
		mIsPurchased= false;
	}
	void set(float _x)
	{
		x= _x;
	}
}
