package com.oneday.games24.clashofbattles;

public class Vahicle {
	
	boolean iSAlive;
	
	byte NoBomb = 1;
	
	short count =0;
	
	int img = 0; // Tyre 3,3,6,1,2
	int shild = 0;
	
	float x, y;
	float vx, vy;
	float ang;
	float life=0;
	float Totallife=0;
	
	void set(float _x, float _y,int _img,boolean _iSAlive,float _life,int _NoBomb) {
		iSAlive =_iSAlive;
		if(_img>10)
			_life*=2;
		x = _x;
		y = _y;
		float _ang =90;
		vx = (float) Math.sin(_ang) * .05f*1.5f;
		vy =-(float) Math.cos(_ang) * .08f*1.5f;
		ang = (float) Math.toDegrees(_ang);
		img =_img;
		NoBomb = (byte)(_NoBomb);
		shild = 0;
		switch (img) {
		case 0:
			y = -.28f;
			break;
		case 1:
			y = -.26f;
			break;
		case 2:
			y = -.28f;
			break;
		case 3:
			y = -.28f;
		case 4:
			y = -.28f;
		case 5:
			y = -.28f;
			break;
		case 6:
			y = -.22f;
			break;
		case 7:
			y = -.22f;
			break;
		case 8:
			y = -.22f;
			break;
		case 9:
			y = -.21f;
			break;
		case 10:
			y = -.16f;
			break;
		case 11:
			y = -.21f;
			break;
		case 12:
			y = -.19f;
			break;
		case 13:
			y = -.15f;
			break;
		case 14:
			y = -.19f;
			break;
		default:
			y = -.28f;
			break;
		}
		Totallife = life =_life;
	}

	void setAngle(double _ang) {
		vx = (float) Math.sin(_ang) * .05f*1.5f;
		vy =-(float) Math.cos(_ang) * .08f*1.5f;
		ang = (float) Math.toDegrees(_ang);
	}
	boolean IsIn() {
		if (x < 2 && x > -2 && y < 2 && y > -2 && iSAlive) {
			return true;
		}
		return false;
	}
	boolean update(int i) {
		if (x < 2 && x > -2 && y < 2 && y > -2 && iSAlive) {
			count++;
			if(i==0 && x <-.88f)
				x+=.003f;
			if(i==1 && x <-.65f)
				x+=.006f;
			if(i==2 && x <-.40f)
				x+=.009f;
			switch (img) {
			case 3:
				if(count%100==0){
					GameRenderer.mStart.mGR.root.setPlayerMisiile(x - .14f, 0.03f + y);
					M.sound11(R.raw.missile_shoot);
				}
				break;
			case 14:
				if(count%30==0){
					GameRenderer.mStart.mGR.root.setPlayerMisiile(x,.21f + y);
					M.sound11(R.raw.missile_shoot);
				}
				break;
			}
			
			return true;
		}
		return false;
	}

	void lifeLoss(float by) {
		if (shild <= 0) {
			life -= by*1.1f;
			if (life <= 0){
				M.sound17(R.raw.user_car_blast);
				reset();
			}
		}
	}
	void reset(){
		iSAlive=false;
		Totallife=life=ang=vx=vy=count =0;
		x= y=-100;
	}
}
