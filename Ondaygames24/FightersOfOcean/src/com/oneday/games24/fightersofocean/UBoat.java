package com.oneday.games24.fightersofocean;

public class UBoat {
	float x;
	final String Name;
	final int mDamage;
	int mCost;
	boolean mIsPurchased;
	public UBoat(String str,int _Damage,int _Cost)
	{
		Name= new String(str);
		mDamage 	= _Damage;
		mCost		=_Cost;
		mIsPurchased = false;
	}
	void set(float _x)
	{
		x= _x;
	}
}
