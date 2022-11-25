package com.oneday.games24.clashofbattles;

public class Dusman {
	byte img;
	byte anim =0;
	
	short ang = 0;
	short counter = 0;
	
	float x, y;
	float vx, vy;
	float variX;
	float life = 0;
	
	void reset() {
		y = x = -100;
		vx = vy = ang = counter = img = 0;
	}

	boolean isIn() {
		if (x > -2 && x < 2 && y > -2 && y < 2) {
			return true;
		}
		return false;
	}

	void set(final float _x, final float _y, final byte _img) {
		variX = .7f - M.mRand.nextFloat();
		x = _x;
		y = _y;
		img = _img;
		counter = 0;
		life = (short)((_img*10+10)*1.5f);
		switch (img) {
		case 0:
			x = 1.2f;
			ang = 180;
			vx = -.01f;
			break;
		case 1:
		case 3:
			M.sound1(R.raw.air_oppo_intry);
			ang = 0;
			counter = 3;
			vx = _x;
			vy = _y;
			
			break;
		case 5:
			x = _x;
			y = -.2f;
			vx = -.01f;
			vy = 0.1f;
			ang = 0;
			counter = 0;
			break;
		default:
			x = _x;
			vx = -.01f;
			vy = 0;
			ang = 0;
			counter = 0;
			if (img == 2)
				y = -.20f;
			if (img == 4)
				y = -.20f;
			if (img == 6)
				y = -.18f;
			if (img == 7)
				y = -.29f;
			if (img == 8)
				y = -.23f;
			break;
		}

	}

	boolean update(int count) {
		if (x > -2 && x < 2 && y > -2 && y < 2) {
			if(anim>0 && count%2==0)
				anim--;
			switch (img) {
			case 0:
				counter++;
				if (counter > 20 && counter < 120) {
					if (ang < 360)
						ang += 10;
					else
						ang -= 2;
				} else if (ang > 180)
					ang -= 10;
				counter %= 250;
				if (x < 0)
					vx = 0.01f;
				if (x > .8f)
					vx = -.01f;
				x += vx;
				break;
			case 1:
			case 3:
				ang += counter;
				x = (float) Math.cos(Math.toRadians(ang)) * .4f + vx;// .5
				y = (float) Math.sin(Math.toRadians(ang)) * .4f + vy;// .3
				if (ang >= 360)
					counter = (short) -Math.abs(counter);
				if (ang < 0)
					counter = (short) Math.abs(counter);
				if (vx > variX) {
					vx -= 0.01f;
					if (vx < variX)
						vx = variX;
				}
				if (vx < variX) {
					vx += 0.01f;
					if (vx > variX)
						vx = variX;
				}
				if (vy > 0.3) {
					vy -= 0.01f;
					if (vy < 0.3)
						vy = 0.3f;
				}
				if (vy < 0.3) {
					vy += 0.01f;
					if (vy > 0.3)
						vy = 0.3f;
				}
				if (img == 3 && ang % 60 == 0) {
					GameRenderer.mStart.mGR.root.setOppMissile(x/*-.09f*/,
							0.055f + y, img);
				}
				break;
			case 2:
				counter++;
				if (x > .3f)
					x += vx;
				if (counter % 20 == 0) {
					GameRenderer.mStart.mGR.root.setNewOpp(x, y, 1);
				}
				break;
			case 4:
				counter++;
				if (x > .3f)
					x += vx;
				if (counter % 10 == 0) {
					GameRenderer.mStart.mGR.root.setOppMissile(x, y, img);
				}
				break;
			case 5:
				if (x > 0.8) {
					vx = -.01f;
				}
				if (x < 0.1) {
					vx = 0.01f;
				}
				if (y > 0.8) {
					vy = -.005f;
				}
				if (y < 0.2) {
					vy = 0.004f;
				}
				x += vx;
				y += vy;
				break;
			case 6:
				counter++;
				if (x > .3f)
					x += vx;
				if (counter % 50 == 0) {
					GameRenderer.mStart.mGR.root.setOppMissile(x - .15f,
							0.1f + y, img);
				}
				break;
			case 7:
				counter++;
				if (x > .1f)
					x += vx;
				break;
			case 8:
				counter++;
				if (x > .2f)
					x += vx;
				break;
			}
			return true;
		}
		return false;
	}
	void lifeLoss(float by,int get,int power){
		if(get ==1){
			by*=.1f;
			if(by==0)
				by=.1f;
			if(by == 2||by == 3||by == 10){
				life -=by+0.1f*power;
			}if(by == 5||by == 6||by == 12){
				life -=by+0.2f*power;
			}if(by == 13||by == 9){
				life -=by+0.3f*power;
			}else{
				life -=by+0.4f*power;
			}
//			System.out.println("[life = "+life+"][power = "+power+"][img = "+img+"][by = "+by+"]");
		}else 
			life -=by;
		if(life<=0){
			GameRenderer.mStart.mGR.kill++;
			GameRenderer.mStart.mGR.Totalkill++;
			GameRenderer.mStart.mGR.killVal+=img*10+10;
			if(img == 5){
				GameRenderer.mStart.mGR.Wall+=1;
			}
			if(img ==0){
				GameRenderer.mStart.mGR.root.setBlast(x, y,0);
			}
			else if(img == 1||img == 3||img ==5){
				GameRenderer.mStart.mGR.root.setBlast(x, y,1);
			}else{
				GameRenderer.mStart.mGR.root.setBlast(x, y,2);
			}
			x =-10;
		}
		anim = 10;
	}
}
