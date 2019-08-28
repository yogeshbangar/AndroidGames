package com.hututu.game.trafficracing;


public class BGPATH {
	
	public static short x[][] ={	{  0, 64,120,167,243}, 		//1
									{  0, 74,135,174,248},		//3
									{  0,135,144,165,253},		//7
									{  0, 88,130,188,249},		//8
								};		
	
	public static short y[][] ={	{ 81,113,144,150,150},		//1
									{150,150,130,110, 70},		//3
									{402,367,362,350,350},		//7
									{350,350,366,376,430},		//8
								};
	
	public static float getX(float a)
	{
		float c = ((a / M.mMaxX)- 0.5f)*2;
		return c+.84f;
	}
	public static float getY(float a)
	{
		float c = ((a / M.mMaxY)- 0.5f)*(-2);
		return c;
	}
}
