package com.hututu.game.minehero;

public class Tile {
	byte no;
	byte reset=0;
	byte obj=0;

	void set(byte _no) {
		no = _no;
		reset = 0;
		obj = (byte) M.mRand.nextInt(30);
		if (obj < 6)
			obj = 1;
		if (obj >= 6 && obj < 8)
			obj = 2;
		if (obj > 7 && obj < 12)
			obj = (byte) (obj - 5);
		if (obj > 11||no==0)
			obj = 0;
			
	}
}
