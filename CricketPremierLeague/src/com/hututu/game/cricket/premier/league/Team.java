package com.hututu.game.cricket.premier.league;

public class Team {
	boolean isTwo = false;
	
	int no;
	int p1 = 0;
	int p2 = 0;
	int Run = 0;
	int ball = 0;
	int falWicket = 0;
	int s4;
	int s6;
	
	Player mPly[];
	
	public Team() {
		mPly = new Player[11];
		for (int i = 0; i < mPly.length; i++)
			mPly[i] = new Player();
	}

	void set(int _no, int _p1, int _p2) {
		no = _no;
		p1 = _p1;
		p2 = _p2;
		isTwo = false;
		Run = 0;
		ball = 0;
		falWicket = 0;
		s4 = 0;
		s6 = 0;
		for (int i = 0; i < mPly.length; i++)
			mPly[i].reset();

		mPly[p1].Ball = 0;
		mPly[p2].Ball = 0;
	}

	void update(int run, boolean Isbat) {
		if (Isbat)
			Run += run;
		ball++;

		if (isTwo)
			mPly[p2].update(run);
		else
			mPly[p1].update(run);

		if (run == 4)
			s4++;
		if (run == 6)
			s6++;
	}

	void out(boolean Isbat, int player, int _HowOut) {

		if (Isbat) {
			int next = 0;
			for (int i = falWicket + 2; i < mPly.length; i++) {
				if (mPly[i].Ball == -1) {
					next = i;
					break;
				}
			}
			if (isTwo) {
				mPly[p2].out(player, _HowOut, Run);
				if (next != 0) {
					mPly[next].set();
					p2 = next;
				}
			} else {
				mPly[p1].out(player, _HowOut, Run);
				if (next != 0) {
					mPly[next].set();
					p1 = next;
				}

			}
		} else {
			if (isTwo) {
				mPly[p2].forbowler();
			} else {
				mPly[p1].forbowler();
			}
		}
		falWicket++;
		ball++;
	}
}
