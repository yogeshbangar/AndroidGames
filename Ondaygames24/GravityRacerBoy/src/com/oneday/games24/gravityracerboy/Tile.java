package com.oneday.games24.gravityracerboy;

public class Tile {
	byte down;
	short arry[] = new short[15];
	 float x;
	 
	 void set(float _x,short _arry[]){
		 for(int i=0;i<_arry.length;i++)
			 arry[i] = _arry[i];
		 x =_x;
		 down =0;
	 }
}
