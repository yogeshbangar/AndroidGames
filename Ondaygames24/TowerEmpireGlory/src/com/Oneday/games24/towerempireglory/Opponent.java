package com.Oneday.games24.towerempireglory;

public class Opponent {
	int img;
	int mode;
	int Cter;
	int pos =0;
	int bourn = 0;
	int Freez = 0;
	int Sparkal = 0;
	int reduceSpeed;
	int lBar = 0;
	
	float x,y;
	float vx,vy;
	float Life;
	float toxic;
	//.82f-.27f*no
	void set(float _x, float _y, int _img, int _mode,int _pos) {
		img = _img;
		x = _x-.3f;
//		x =1.2f;
		y = _y;
		switch (img) {
		case 0:
			vx = - .004f;
			vy = 0;
			break;
		case 1:
			vx = - .005f;
			break;
		case 2:
			vx = - .006f;
			break;
		default:
			vx = - .007f;
			break;
		}
		bourn = 0;
		pos = _pos;
		mode = _mode;
		Life = (img+1)*2+10;
		reduceSpeed = 0;
		toxic =0;
	}

	void update(int cont) {
		if(toxic > 0){
			Life-=toxic;
			lBar =M.time;
		}
		if (bourn > 0){
			bourn--;
		}
		if (Sparkal > 0){
			Sparkal--;
		}
		if (Freez > 0){
			Freez--;
		} else {
			switch (img) {
			case 11:
				Cter++;
				break;
			case 0:
				if (cont % 2 == 0)
					Cter++;
				break;
			default:
				if (cont % 3 == 0)
					Cter++;
				break;
			}
			if (mode == 1 && x > -.59f) {
				if(reduceSpeed > 0)
					x += (vx-(reduceSpeed*(vx/100f)));
				else
					x += (vx);
				y += vy;
				if (img == 2 && x < -.35f) {
					mode = 0;
				}
				if ((img == 11 || img == 9) && x < -.0f) {
					mode = 0;
				}
			} else {
				mode = 0;
				if(Cter%10==0 && Life>10)
					GameRenderer.mStart.mGR.mPlayer.life();
			}
		}
		if(Life<11){
			lBar =0;
			Life-=1;
			mode =0;
			if(Life<=0){
				GameRenderer.mStart.mGR.mPlayer.kill ++;
				GameRenderer.mStart.mGR.mPlayer.T$+=1+(img*2);
				GameRenderer.mStart.mGR.mPlayer.TCo+=1+(img*2);
				if(img == 9)
					M.sound16(GameRenderer.mContext, R.drawable.kart_crash);
				else
					M.sound1(GameRenderer.mContext, R.drawable.anime_dead);
				reset();
			}
		}
//		return false;
	}

	boolean isScreen() {
		if (mode >= 0 && mode < 3 && x > -.9f && x < 1.4f)
			return true;
		return false;
	}
	void reset(){
		bourn = 0;
		x = -10;
		y = -10;
		mode = -1;
	}
	void arrowShot(Player mPlayer){
		toxic = mPlayer.toxic;
		Life -=mPlayer.Damage;
		lBar =M.time;
		x+=mPlayer.powerShoot;
		if(mPlayer.kill % mPlayer.Fatalshoot==0)
			Life -=(mPlayer.Damage*mPlayer.FatalDamage);
	}
	float Lbar(){
		return (((Life-10)/((img+1)*2))*30);
	}
}
