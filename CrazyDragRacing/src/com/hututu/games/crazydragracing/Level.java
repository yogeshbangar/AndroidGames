package com.hututu.games.crazydragracing;

public class Level {
	int mUPower;//Power
	int mSLevel;//Playing Level
	int mULevel;//Unlock Level
	float x,z,vz;
	float strength[];
	public Level()
	{
		strength= new float[6];
		mUPower=1;
		mULevel = mSLevel = 0;
	}
	void set(float _x,float _z,float _vz)
	{
		x = _x;
		z = _z;
		vz = _vz;
	}
	void CStrength(float _acl,float _speed,float _handling,float _strength,float _boost,float _tyre)
	{
		strength[0]	=_acl;
		strength[1]	=_speed;
		strength[2]	=_handling;
		strength[3]	=_strength;
		strength[4]	=_boost;
		strength[5]	=_tyre;
	}
}
