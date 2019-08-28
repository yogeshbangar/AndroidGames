package com.hututu.extreamdargerace;

public class Bike {
	boolean netro = false;
	
	int Gear = 0;
	int BikeNo;
	int m0to60;
	int m0to100;
	int time = 100;
	int MaxSpeed = 0;
	int parfect = 0;
	int Good = 0;
	int over = 0;
	int bonus = 0;
	int price = 0;
	int gearChange = 0;
	int smock =0;
	
	float x,y;
	float Dis;
	
	float Engine;
	float Exhaust;//Increase Speed
	int Nitro;////Increase Speed sort time
	float Weight;////Increase Speed 
	float GearBox;
	float rpm;
	
	void set(float _x, float _y,  float _Exhaust, int _Nitro,
			float _Weight, float _GearBox,int _BikeNo) {
		x = _x;
		y = _y;
		Engine = 0;
		Exhaust = _Exhaust;
		Nitro = _Nitro;
		Weight = _Weight;
		GearBox = _GearBox;
		netro = false;
		Dis = -.94f;
		Gear = 0;
		rpm = 0;
		gearChange = 0;
		BikeNo = _BikeNo;
		m0to60 = 0;
		m0to100 = 0;
		time = 0;
		MaxSpeed = 0;
		netro = false;
		smock =0;
	}

	void pos(float vx) {
		x += vx;
	}

	void update() {
		if(smock>0)
			smock--;
		if (rpm < 162){
			float Lnitro = 0;
			if(Nitro>0 && netro) {
				Lnitro = .0001f;
				Nitro--;
			}
			Engine += ((Exhaust + Lnitro + Weight + GearBox) * ((rpm * .01f) + Gear * (.1f)));
		}

		if (Gear == 1 && rpm < 175) {
			rpm += 1;
		}
		if (Gear == 2 && rpm < 175) {
			rpm += .8f;
		}
		if (Gear == 3 && rpm < 175) {
			rpm += .7f;
		}
		if (Gear == 4 && rpm < 175) {
			rpm += .5f;
		}
		if (Gear == 5 && rpm < 175) {
			rpm += .25f;
		}
		if (Gear == 6 && rpm < 175) {
			rpm += .125f;
		}
		if (gearChange > 0)
			rpm -= 6;

		if (gearChange > 0) {
			gearChange--;
			if (rpm < 30)
				gearChange = 0;
		}
	}
	void reset()
	{
		Engine = 0;
		Exhaust = 0;
		Nitro = 0;
		Weight = 0;
		GearBox = 0;
		netro = false;
		Gear = 0;
		rpm = 0;
		gearChange = 0;
	}
	
	
}
