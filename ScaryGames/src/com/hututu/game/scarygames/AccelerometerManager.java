package com.hututu.game.scarygames;


import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Android Accelerometer Sensor Manager Archetype
 * @author antoine vianey
 * under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 */
public class AccelerometerManager {
	
	/** Accuracy configuration */
	/*private static float threshold 	= 0.2f;
	private static int interval 	= 1000;
	*/
	private static Sensor sensor;
	private static SensorManager sensorManager;
	
	/** you could use an OrientationListener array instead if you plans to use more than one listener*/
	private static AccelerometerListener listener;
	
	/** indicates whether or not Accelerometer Sensor is supported */
	private static Boolean supported;
	
	/** indicates whether or not Accelerometer Sensor is running */
	private static boolean running = false;
	
	/** Returns true if the manager is listening to orientation changes*/
	public static boolean isListening() {return running;}
	
	/** Unregisters listeners */
	public static void stopListening() {
		running = false;
		Log.d("stopListening", "  ~~~ ");
		try {
			if (sensorManager != null && sensorEventListener != null) {
				sensorManager.unregisterListener(sensorEventListener);
			}
		} catch (Exception e) { Log.d("stopListening", e.toString());}
	}
	
	/** Returns true if at least one Accelerometer sensor is available */
	public static boolean isSupported(){
		if (supported == null){
			if (Start.getContext() != null){
				sensorManager = (SensorManager) Start.getContext().getSystemService(Context.SENSOR_SERVICE);
				List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
				supported = new Boolean(sensors.size() > 0);
			}else{
				supported = Boolean.FALSE;
			}
		}
		return supported;
	}

	/**
	 * Configure the listener for shaking
	 * @param threshold
	 * 			minimum acceleration variation for considering shaking
	 * @param interval
	 * 			minimum interval between to shake events
	 */
	public static void configure(int threshold, int interval) {
		/*AccelerometerManager.threshold = threshold;
		AccelerometerManager.interval = interval;*/
	}
	
	/**
	 * Registers a listener and start listening
	 * @param accelerometerListener
	 * 			callback for accelerometer events
	 */
	public static void startListening(
			AccelerometerListener accelerometerListener) {
		sensorManager = (SensorManager) Start.getContext().
				getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensors = sensorManager.getSensorList(
				Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			sensor = sensors.get(0);
			running = sensorManager.registerListener(
					sensorEventListener, sensor, 
					SensorManager.SENSOR_DELAY_FASTEST);
			listener = accelerometerListener;
		}
	}
	
	/**
	 * Configures threshold and interval
	 * And registers a listener and start listening
	 * @param accelerometerListener
	 * 			callback for accelerometer events
	 * @param threshold
	 * 			minimum acceleration variation for considering shaking
	 * @param interval
	 * 			minimum interval between to shake events
	 */
	public static void startListening(
			AccelerometerListener accelerometerListener, 
			int threshold, int interval) {
		//configure(threshold, interval);
		startListening(accelerometerListener);
	}

	/**
	 * The listener that listen to events from the accelerometer listener
	 */
	private static SensorEventListener sensorEventListener = 
		new SensorEventListener() {

		private float x = 0;
		private float y = 0;
		private float z = 0;
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
		
		public void onSensorChanged(SensorEvent event) {
			// use the event timestamp as reference
			// so the manager precision won't depends 
			// on the AccelerometerListener implementation
			// processing time
			x = event.values[0];
			y = event.values[1];
			z = event.values[2];
    		listener.onAccelerationChanged(x, y, z);
		}
	};

}
