package com.oneday.games24.extreme.drag.racing;

public class Spec {
	
	float getExhost(){
		return Exhaust+.00005f+(lvlExhaust*.000002f);
	}
	int getNitro(){
		if(lvlNitro == 1)
			return 30;
		if(lvlNitro == 2)
			return 60;
		if(lvlNitro == 3)
			return 100;
		return 0;
	}
	float getWeight(){
		return Weight+.00005f+(lvlWeight*.000002f);
//		return (Weight*(lvlWeight+1))+.00005f;
	}
	float getGearBox(){
		return GearBox+.00005f+(lvlGearBox*.000002f);
//		return (GearBox*(lvlGearBox+1))+.00005f;
	}
	
	float get4OppExhost(float val){
		return Exhaust+.00005f+(val*.000002f);
//		return Exhaust*(val+1)+.00005f;
	}
	int get4OppNitro(float val){
		if(val >= 2 && val < 3)
			return 30;
		if(val >= 3 && val < 4)
			return 60;
		if(val >= 4)
			return 100;
		return 0;
	}
	String Name = new String();;
	private final int no;
	private final float Exhaust;
	private final float Weight;
	private final float GearBox;
	
	int lvlExhaust;
	int lvlNitro;
	int lvlWeight;
	int lvlGearBox;

	public Spec(String _Name, int _no) {
		Name = _Name;
		no = _no;
		Exhaust = (no+2)*.00001f;
//		Nitro = 0;
		Weight = (no+2)*.00001f;
		GearBox = (no+2)*.00001f;
		eng();
	}
	float get4OppWeight(float val){
//		return Weight*(val+1)+.00005f;
		return Weight+.00005f+(val*.000002f);
	}
	float get4OppGearBox(float val){
//		return GearBox*(val+1)+.00005f;
		return GearBox+.00005f+(val*.000002f);
	}
	
	
	
	float eng() {
		return (getExhost() +  getWeight() + getGearBox());
	}
}