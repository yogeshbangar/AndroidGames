package com.oneday.games24.clashofbattles;

public class CarValue {
	
	boolean Buy;
	
	byte upgred[] = new byte[3];
	
	public CarValue(boolean _Buy) {
		for (int i = 0; i < upgred.length; i++)
			upgred[i] = 1;
		Buy = _Buy;
	}
}
