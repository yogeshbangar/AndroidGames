package com.Oneday.games24.towerempireglory;

public class Player {
	int PowerResume[] = { 100, 100, 100 };
	int TCo = 0;
	int TCy = 0;
	int T$ = 100;
	int TCrystal = 1;
	int TKey = 0;
	int TKill = 0;
	int SelBow = 100;
	int bgNo = 0;
	int kill = 0;
	int Score = 0;
	int HScore = 0;
	int Crystal = 0;
	int Coints = 0;
	int Damage = 1;
	int firepower = 10;
	int fireSpeedRate = 0;
	int Freezpower = 100;
	int FreezSpeedRate = 0;
	int Sparkalpower = 100;
	int SparkalSpeedRate = 0;
	int tLife = 100;
	int Mana = 100;
	int tMana = 100;
	int fireArrowRateTower = 30;
	int fireArrowRate=30;
	int Fatalshoot=31;
	int mulArrow = 1;
	int key = 0;
	boolean blink =false;
	
	float fireArea = .2f;
	float FreezArea = .2f;
	float SparkalArea = .2f;
	float bornby = 5;
	float Sparkalby = 5;
	float Life = 100;
	float LifeDefence=1;
	float powerShoot = 0;
	float FatalDamage =1;
	float toxic=0;

	GameRenderer mGR;

	public Player(GameRenderer GR) {
		mGR = GR;
	}

	void set() {
		blink =false;
		key = 0;
		Fatalshoot = 31;
		fireArrowRate = 30;
		bgNo = 0;
		kill = 0;
		Score = 0;
		Crystal = 0;
		Coints = 0;
		firepower = 10;

		Freezpower = 100;
		Life = tLife = 100;
		tMana = Mana = 100;
		Damage = mGR.uPower[0] + 1;
		fireArrowRate -= mGR.uPower[1];
		powerShoot = mGR.uPower[2] * .03f;
		toxic = mGR.uPower[3] * .01f;
		Fatalshoot -= mGR.uPower[4];
		if (Fatalshoot < 30)
			FatalDamage = 1 + mGR.uPower[4] * .5f;
		else
			FatalDamage = 0;


		mulArrow = mGR.uPower[5] + 1;

		if (SelBow != 100) {
			switch (SelBow % 3) {
			case 0:
				Damage += ((SelBow / 3) + 1);
				fireArrowRate -= ((SelBow / 3) + 1);
				break;
			case 1:
				Damage += ((SelBow / 3) + 2);
				fireArrowRate -= ((SelBow / 3) + 1);
				break;
			case 2:
				Damage += ((SelBow / 3) + 2);
				fireArrowRate -= ((SelBow / 3) + 2);
				break;
			}
		}
		tMana = Mana = 100 + mGR.Mana[0] * 20;

		PowerResume[0] = 1000;
		firepower = mGR.Mana[1] * 3 + 10;
		fireArea = mGR.Mana[2] * .05f + .2f;
		fireSpeedRate = mGR.Mana[3] * 10 + 20;
		bornby = 5 - mGR.Mana[3] * .5f;
		if (bornby < 1)
			bornby = 1;

		PowerResume[1] = 1000;
		Freezpower = mGR.Mana[4] * 20 + 100;
		FreezArea = mGR.Mana[5] * .05f + .2f;
		FreezSpeedRate = mGR.Mana[6] * 10;

		PowerResume[2] = 1000;
		Sparkalpower = mGR.Mana[7] * 3 + 10;
		SparkalArea = mGR.Mana[8] * .05f + .2f;
		SparkalSpeedRate = mGR.Mana[9] * 10;
		Sparkalby = 5;
		Life = tLife = mGR.Tower[1] * 10 + 100;

		LifeDefence = 1 - (mGR.Tower[3] * .03f + mGR.Tower[4] * .03f);
		if (LifeDefence < .4)
			LifeDefence = .4f;

		fireArrowRateTower = 31 - mGR.Tower[2] - mGR.Tower[5] - mGR.Tower[6];
		if (fireArrowRateTower < 10)
			fireArrowRateTower = 10;

		bornby = 5;
		Sparkalby = 5;
		if(fireArrowRate < 3)
			fireArrowRate = 3;
	}
	int counter = 0;
	void life() {
		if(counter != mGR.root.Counter){
			counter = mGR.root.Counter;
			M.Wallhit(GameRenderer.mContext);
		}
		Life -= LifeDefence;
		blink =true;
	}

	void update(int no) 
	{
		for (int i = 0; i < 3; i++) {
			if (mGR.Mana[no * 3 + i] > 0){
				PowerResume[no] += 1;
				if(PowerResume[no] >= 1000)
					M.sound19(GameRenderer.mContext, R.drawable.power_full);
			}
		}
	}
}
