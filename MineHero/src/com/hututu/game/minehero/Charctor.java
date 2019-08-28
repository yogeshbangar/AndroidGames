package com.hututu.game.minehero;

public class Charctor {
	int slow = 0;
	int megnat = 0;
	int Timer = 0;
	int Drill = 0;
	int CoinBox = 0;
	int Power[] = new int[7];
	
	float x, y;
	float spd = 0;
	

	public Charctor() {
		for (int i = 0; i < Power.length; i++) {
			Power[i] = 1;
		}
	}

	void set(float _x, float _y) {
		x = _x;
		y = _y;
		spd = 0;
		slow = 0;
		CoinBox = 0;
	}

	boolean update() {
		if (x > 1.0f)
			x = -1.0f;
		if (x < -1.0f)
			x = 1.0f;
		if (megnat > 0)
			megnat--;
		if (Timer > 0)
			Timer--;
		if (Drill > 0)
			Drill--;
		if (slow > 0)
			slow--;
		if (spd != 0) {
			if (slow > 0)
				x += spd * .5f;
			else{
				x += spd;
				return true;
			}
		}
		return false;
	}

	void setslow() {
		slow = 100 - Power[1] * 9;
	}

	void setMegnat() {
		megnat = 100 + Power[2] * 15;
	}

	void setTimer() {
		Timer = 100 + Power[3] * 15;
	}

	void setCoinBox() {
		CoinBox = Power[4];
	}

	void setDrill() {
		Drill = 100 + Power[5] * 15;
	}

}
