package com.hututu.games.freekickshoot;

public class Keeper {
	int anim;
	int img;
	int counter = 0; 
	
	float x, y;
	float ay;
	
	GameRenderer mGR;
	public Keeper(GameRenderer GR)
	{
		mGR = GR;
	}

	void reset(float _x, float _y) {
		x = _x;
		y = _y;
		ay = _y;
		anim=0;
		counter = 0;
	}
	void set() {
		
		float ratio = (.3f-mGR.mPlayer.by)/mGR.mPlayer.bvy;
		ratio = (ratio*mGR.mPlayer.bvx)+mGR.mPlayer.bx;
		ratio -= mGR.mPlayer.bgx;
		
		if(ratio<=0.1 && ratio>=-.1)
			anim=1;//Jump
		else if(ratio>=0.1 && ratio<.21)
			anim=2;//Jump
		else if(ratio>=.21 )
			anim=4;//Jump
		else if(ratio<=-.1 && ratio>=-.21)
			anim=3;//Jump
		else if(ratio<=-.21 )
			anim=5;//Jump
		
//		System.out.println(anim+" = Degree = "+ratio+"  <-->  "+mGR.mPlayer.bgx); 		
		
		counter =0;
		img = 0;
	}
	void update()
	{
		counter++;
		switch (anim) {
		case 1://Jump
			if (counter > 15 && counter < 35) {
				img = 1;
			} else if (counter > 35) {
				anim = 0;
			}
			break;
		case 2://Sort Right
			if (counter > 10 && counter < 40) {
				img = 2;
				x +=.004f;
			} else if (counter > 40) {
				anim = 0;
			}
			break;
		case 3://Sort Left
			if (counter > 10 && counter < 40) {
				img = 6;
				x -=.004f;
			} else if (counter > 40) {
				anim = 0;
			}
			break;
		case 4://Long Right
			if (counter > 9 && counter < 40) {
				img = (counter/10)+2;
				if(img == 3 || img == 4)
					y = ay +.03f;
				else
					y = ay -.03f;
				x +=.012f;
			} else if (counter > 40) {
				anim = 0;
			}
			break;
		case 5://Long Left
			if (counter > 9 && counter < 40) {
				img = (counter/10)+6;
				if(img == 7 || img == 8)
					y = ay +.03f;
				else
					y = ay -.03f;
				x -=.012f;
			} else if (counter > 40) {
				anim = 0;
			}
			break;
		default:
			img =0;
			y = ay;
			break;
		}
	}
}
