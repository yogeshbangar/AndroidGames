package com.hututu.games.ninjajumper;

public class Spike {
	float x;
	boolean rShow;
	
	void set(boolean _Show){
		x = 1.1f;
		rShow = _Show;
	}
	
	void Reset(boolean _Show){
		rShow = _Show;
	}
	
	void update() {
		if (rShow && x > .95f) {
			x -= .01f;
			if (x < .95f)
				x = .95f;
		}
		if (!rShow && x < 1.1f) {
			x += .01f;
		}
	}
}
