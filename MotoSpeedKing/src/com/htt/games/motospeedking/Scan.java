package com.htt.games.motospeedking;

public class Scan {
	float y;
	int no;
	
	void set(float _y){
		y = _y;
		no = M.n++;//M.mRand.nextInt(5);
		no%=5;
	}
}
