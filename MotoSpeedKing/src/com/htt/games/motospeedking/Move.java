package com.htt.games.motospeedking;

public class Move {
	float move;
	float mv =1;
	void set(){
		move = (byte)(M.mRand.nextInt(30)-15);
		mv = (move>0?-.3f:.3f);  
	}
	void update(){
		if(move > 10)
			mv = -.3f;
		if(move < -10)
			mv = .3f;
		move +=mv;  
	}
}
