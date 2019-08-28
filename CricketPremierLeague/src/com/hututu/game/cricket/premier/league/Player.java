package com.hututu.game.cricket.premier.league;

public class Player {
	
	byte OutBy=-1;
	byte HowOut=-1;
	byte fallOff=-1;
	
	short s4=0;
	short s6=0;
	
	int run=0;
	int Ball=-1;
	
	void set() {
		run = s6 = s4 = 0;
		Ball = 0;
		OutBy = -1;
		HowOut = -1;//0 bold //1 LBW //caught
		fallOff =-1;
	}
	
	void reset() {
	 	run = s6 = s4 =0;
		Ball = -1;
		OutBy = -1;
		HowOut = -1;//0 bold //1 LBW //caught
		fallOff =-1;
	}

	void update(int _run) {
		run += _run;
		Ball++;
		if (_run == 4)
			s4++;
		if (_run == 6)
			s6++;
		
	}
	
	void out(int player,int _HowOut,int _fallOff) {
		Ball++;
		OutBy = (byte)player;
		HowOut = (byte)_HowOut; 
		fallOff = (byte)_fallOff;
	}
	void forbowler(){
		OutBy++;
		Ball++;
	}
}
