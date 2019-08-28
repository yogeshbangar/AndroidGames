package com.hututu.game.scarygames;
public interface AccelerometerListener {

	public void onAccelerationChanged(float x, float y, float z);
	
	public void onShake(float force);
	
}
