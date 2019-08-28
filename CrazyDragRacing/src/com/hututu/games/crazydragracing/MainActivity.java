/* Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hututu.games.crazydragracing;

//import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.content.SharedPreferences;
//import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.widget.ImageView;

import com.hututu.billing.util.IabHelper;
import com.hututu.billing.util.IabResult;
import com.hututu.billing.util.Inventory;
import com.hututu.billing.util.Purchase;

/**
 * Example game using in-app billing version 3.
 * 
 * Before attempting to run this sample, please read the README file. It
 * contains important information on how to set up this project.
 * 
 * All the game-specific logic is implemented here in MainActivity, while the
 * general-purpose boilerplate that can be reused in any app is provided in the
 * classes in the util/ subdirectory. When implementing your own application,
 * you can copy over util/*.java to make use of those utility classes.
 * 
 * This game is a simple "driving" game where the player can buy gas and drive.
 * The car has a tank which stores gas. When the player purchases gas, the tank
 * fills up (1/4 tank at a time). When the player drives, the gas in the tank
 * diminishes (also 1/4 tank at a time).
 * 
 * The user can also purchase a "premium upgrade" that gives them a red car
 * instead of the standard blue one (exciting!).
 * 
 * The user can also purchase a subscription ("infinite gas") that allows them
 * to drive without using up any gas while that subscription is active.
 * 
 * It's important to note the consumption mechanics for each item.
 * 
 * PREMIUM: the item is purchased and NEVER consumed. So, after the original
 * purchase, the player will always own that item. The application knows to
 * display the red car instead of the blue one because it queries whether the
 * premium "item" is owned or not.
 * 
 * INFINITE GAS: this is a subscription, and subscriptions can't be consumed.
 * 
 * GAS: when gas is purchased, the "gas" item is then owned. We consume it when
 * we apply that item's effects to our app's world, which to us means filling up
 * 1/4 of the tank. This happens immediately after purchase! It's at this point
 * (and not when the user drives) that the "gas" item is CONSUMED. Consumption
 * should always happen when your game world was safely updated to apply the
 * effect of the purchase. So, in an example scenario:
 * 
 * BEFORE: tank at 1/2 ON PURCHASE: tank at 1/2, "gas" item is owned
 * IMMEDIATELY: "gas" is consumed, tank goes to 3/4 AFTER: tank at 3/4, "gas"
 * item NOT owned any more
 * 
 * Another important point to notice is that it may so happen that the
 * application crashed (or anything else happened) after the user purchased the
 * "gas" item, but before it was consumed. That's why, on startup, we check if
 * we own the "gas" item, and, if so, we have to apply its effects to our world
 * and consume it. This is also very important!
 * 
 * @author Bruno Oliveira (Google)
 */

public class MainActivity {
	// Debug tag, for logging
	static final String TAG = "Payment~  ";

	// Does the user have the premium upgrade?
	boolean mIsPremium = false;

	// Does the user have an active subscription to the infinite gas plan?
	boolean mSubscribedToInfiniteGas = false;

	// SKUs for our products: the premium upgrade (non-consumable) and gas
	// (consumable)
	
	
//	
//	 10000 Coin (coin10000)
//	 Rs.50.00
//	 Managed product
//	 May 6, 2014
//	 Active
//	 Get 100000 Coin (coin100000)
//	 Rs.250.00
//	 Managed product
//	 May 6, 2014
//	 Active
//	 Get 30000 Coin (coin3000)
//	 Rs.100.00
//	 Managed product
//	 May 6, 2014
//	 Active
//	 Page 1 of 1
	
	
	static final String SKU_10000 = "coin10000";// "premium";10000
	static final String SKU_30000 = "coin3000";// "gas";
	static final String SKU_100000 = "coin100000";// "infinite_gas"; // SKU for
													// our subscription
													// (infinite gas)
//	static final String SKU_350000 = "350000";// "gas";
	// (arbitrary) request code for the purchase flow
	static final int RC_REQUEST = 10001;

	// Graphics for the gas gauge
//	static int[] TANK_RES_IDS = { R.drawable.gas0, R.drawable.gas1,
//			R.drawable.gas2, R.drawable.gas3, R.drawable.gas4 };

	// How many units (1/4 tank is our unit) fill in the tank.
	static final int TANK_MAX = 4;

	// Current amount of gas in tank, in units
	int mTank;
	int total;
	// The helper object
	IabHelper mHelper;

	// @Override
	public void onCreate() {
		// super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		// load game data
		loadData();

		/*
		 * base64EncodedPublicKey should be YOUR APPLICATION'S PUBLIC KEY (that
		 * you got from the Google Play developer console). This is not your
		 * developer public key, it's the *app-specific* public key.
		 * 
		 * Instead of just storing the entire literal string here embedded in
		 * the program, construct the key at runtime from pieces or use bit
		 * manipulation (for example, XOR with some other string) to hide the
		 * actual key. The key itself is not secret information, but we don't
		 * want to make it easy for an attacker to replace the public key with
		 * one of their own and then fake messages from the server.
		 */
//		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm3sEAo0Nlg+dnx7inCcS1tngy51nxoLAhUguup3srBKeZY93BrAblc0LZ1/DkD5qda53Oq56djWdH6qqbhBzd4Faf4YhnPwihpYqFN6t5nlXyXiIxWgwaHpu/K4nKZrO2GnlrrU/FgSlQ6dR+8/tZzOMOPkY/LKPYaGtqp/eG0t+U7x0YqxrBhAx7bjtOj1cwh8SdiUbKl25S25fxymFkYuERSbdJcRDWBU64I32ptrMd6BIVV+m+dEJpK45fbLp1MrtRyrGx52UWs1+kKdyr/HdUPgfYmGeC4KQB+pOtTfZrJ+NP2w8b1OqXpCLL1eGpsWmUfmcgbcHVSGcjRIHVwIDAQAB";
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApVQ2/64eNuaYaxEQEByiA4tr3jaweTsUSCg0bqsZFPdSItMlzP1VWUrF1y9b7viE6eIfdB84PttPbUh2rKmF36RS42mDi71TpBQuQ4nrc4V3MwTnrZVny1AVe48ql0Ts3F+xZmPWk2GK34KXxD1mmlhQiPkiyUK9sufTow577gWUo6boorUeWBO1S/4tsx6bCNHzGnT5LP9VT5xCdXsVaGA2aKI6mf/FzZ4NHXpSJ0aumwHJ/S34ZsETyBtX1VhnTHkZ2dypspN/5vGvtcd3qmqx5/AmcViOdFNa5J31OyXHn/QERou9rhwGCbJ7um/aubzOax0UZxfw7KG3WnYB3QIDAQAB";

		// Some sanity checks to see if the developer (that's you!) really
		// followed the
		// instructions to run this sample (don't put these checks on your app!)
		if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
			throw new RuntimeException(
					"Please put your app's public key in MainActivity.java. See README.");
		}
		if (GameRenderer.mContext.getPackageName().startsWith("com.example")) {
			throw new RuntimeException(
					"Please change the sample's package name! See README.");
		}

		// Create the helper, passing it our context and the public key to
		// verify signatures with
		Log.d(TAG, "Creating IAB helper.");
		mHelper = new IabHelper(GameRenderer.mContext, base64EncodedPublicKey);

		// enable debug logging (for a production application, you should set
		// this to false).
		mHelper.enableDebugLogging(true);

		// Start setup. This is asynchronous and the specified listener
		// will be called once setup completes.
		Log.d(TAG, "Starting setup.");
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				Log.d(TAG, "Setup finished.");

				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					complain("Problem setting up in-app billing: " + result);
					return;
				}

				// Hooray, IAB is fully set up. Now, let's get an inventory of
				// stuff we own.
				Log.d(TAG, "Setup successful. Querying inventory.");
				mHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});
	}

	// Listener that's called when we finish querying the items and
	// subscriptions we own
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result,
				Inventory inventory) {
			Log.d(TAG, "Query inventory finished.");
			if (result.isFailure()) {
				complain("Failed to query inventory: " + result);
				return;
			}

			Log.d(TAG, "Query inventory was successful.");

			/*
			 * Check for items we own. Notice that for each purchase, we check
			 * the developer payload to see if it's correct! See
			 * verifyDeveloperPayload().
			 */

			// Do we have the premium upgrade?
			Purchase premiumPurchase = inventory.getPurchase(SKU_10000);
			mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
			Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));

			// Do we have the infinite gas plan?
			Purchase infiniteGasPurchase = inventory.getPurchase(SKU_30000);
			mSubscribedToInfiniteGas = (infiniteGasPurchase != null && verifyDeveloperPayload(infiniteGasPurchase));
			Log.d(TAG, "User "
					+ (mSubscribedToInfiniteGas ? "HAS" : "DOES NOT HAVE")
					+ " infinite gas subscription.");
			if (mSubscribedToInfiniteGas)
				mTank = TANK_MAX;

			// Check for gas delivery -- if we own gas, we should fill up the
			// tank immediately
			Purchase gasPurchase = inventory.getPurchase(SKU_100000);
			if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
				Log.d(TAG, "We have gas. Consuming it.");
				mHelper.consumeAsync(inventory.getPurchase(SKU_100000),
						mConsumeFinishedListener);
				return;
			}

			Purchase goldPurchase = inventory.getPurchase(SKU_10000);
			if (goldPurchase != null && verifyDeveloperPayload(goldPurchase)) {
				Log.d(TAG, "We have gas. Consuming it.");
				mHelper.consumeAsync(inventory.getPurchase(SKU_10000),
						mConsumeFinishedListener);
				return;
			}

			updateUi();
			setWaitScreen(false);
			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};
	// @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
			if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			} else { Log.d(TAG, "onActivityResult handled by IABUtil."); }
		}

		/** Verifies the developer payload of a purchase. */
		boolean verifyDeveloperPayload(Purchase p) {
			/*String payload = */p.getDeveloperPayload();
			return true;
		}
	/*************************************************************/
	public void onBuyCoin10000(View arg0) {
		Log.d(TAG, "Buy gas button clicked.");
		setWaitScreen(true);
		Log.d(TAG, "Launching purchase flow for gas.");
		String payload = "";
		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_10000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}

	// User clicked the "Buy Gas" button
	public void onBuyCoin30000(View arg0) {
		Log.d(TAG, "Buy gas button clicked.");
		setWaitScreen(true);
		Log.d(TAG, "Launching purchase flow for gas.");
		String payload = "";
		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_30000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}

	public void onBuyCoin150000(View arg0) {
		Log.d(TAG, "Buy gas button clicked.");
		setWaitScreen(true);
		Log.d(TAG, "Launching purchase flow for gas.");
		String payload = "";
		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_100000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}
	/*******************************************************************************************/
		// Callback for when a purchase is finished
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			Log.d(TAG, "Purchase finished: " + result + ", purchase: "
					+ purchase);
			if (result.isFailure()) {
				complain("Error purchasing: " + result);
				setWaitScreen(false);
				return;
			}
			if (!verifyDeveloperPayload(purchase)) {
				complain("Error purchasing. Authenticity verification failed.");
				setWaitScreen(false);
				return;
			}

			System.out.println(TAG+GameRenderer.mStart.mGR.mCoint + " Purchase successful " + purchase.getSku());

			if (purchase.getSku().equals(SKU_10000)) {
				GameRenderer.mStart.mGR.mCoint += 20000;
				alert("Success");
			} else if (purchase.getSku().equals(SKU_30000)) {
				GameRenderer.mStart.mGR.mCoint += 50000;
				alert("Success");
			} else if (purchase.getSku().equals(SKU_100000)) {
				GameRenderer.mStart.mGR.mCoint += 150000;
				alert("Success");
			}
			GameRenderer.mStart.mGR.addFree	= true;
			GameRenderer.mStart.pause();
			total = GameRenderer.mStart.mGR.mCoint;
			System.out.println(TAG+ GameRenderer.mStart.mGR.mCoint + "  Purchase successful ["+total+"] " + purchase.getSku());
		}
	};

	// Called when consumption is complete
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		public void onConsumeFinished(Purchase purchase, IabResult result) {
			Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
			if (result.isSuccess()) {
				Log.d(TAG, "Consumption successful. Provisioning.");
				mTank = mTank == TANK_MAX ? TANK_MAX : mTank + 1;
				saveData();
				alert("Success");
			} else {
				complain("Error while consuming: " + result);
			}
			updateUi();
			setWaitScreen(false);
			Log.d(TAG, "End consumption flow.");
		}
	};

	// Drive button clicked. Burn gas!
	public void onDriveButtonClicked(View arg0) {
		Log.d(TAG, "Drive button clicked.");
		if (!mSubscribedToInfiniteGas && mTank <= 0)
			alert("Oh, no! You are out of Coin! Try buying some!");
		else {
			if (!mSubscribedToInfiniteGas)
				--mTank;
			saveData();
			alert("Vroooom, you drove a few miles.");
			updateUi();
			Log.d(TAG, "Vrooom. Tank is now " + mTank);
		}
	}

	// We're being destroyed. It's important to dispose of the helper here!
	// @Override
	public void onDestroy() {
		// super.onDestroy();

		// very important:
		try{
			Log.d(TAG, "Destroying helper.");
			if (mHelper != null)
				mHelper.dispose();
			mHelper = null;
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	// updates UI to reflect model
	public void updateUi() {
		// // update the car color to reflect premium status or lack thereof
		// ((ImageView)findViewById(R.id.free_or_premium)).setImageResource(mIsPremium
		// ? R.drawable.premium : R.drawable.free);
		//
		// // "Upgrade" button is only visible if the user is not premium
		// findViewById(R.id.upgrade_button).setVisibility(mIsPremium ?
		// View.GONE : View.VISIBLE);
		//
		// // "Get infinite gas" button is only visible if the user is not
		// subscribed yet
		// findViewById(R.id.infinite_gas_button).setVisibility(mSubscribedToInfiniteGas
		// ?
		// View.GONE : View.VISIBLE);
		//
		// // update gas gauge to reflect tank status
		// if (mSubscribedToInfiniteGas) {
		// ((ImageView)findViewById(R.id.gas_gauge)).setImageResource(R.drawable.gas_inf);
		// }
		// else {
		// int index = mTank >= TANK_RES_IDS.length ? TANK_RES_IDS.length - 1 :
		// mTank;
		// ((ImageView)findViewById(R.id.gas_gauge)).setImageResource(TANK_RES_IDS[index]);
		// }
	}

	// Enables or disables the "please wait" screen.
	void setWaitScreen(boolean set) {
		// findViewById(R.id.screen_main).setVisibility(set ? View.GONE :
		// View.VISIBLE);
		// findViewById(R.id.screen_wait).setVisibility(set ? View.VISIBLE :
		// View.GONE);
	}

	void complain(String message) {
		//Log.e(TAG, "**** TrivialDrive Error: " + message);
		alert("Error: " + message);
//		alert("Please try later  ");
	}

	void alert(String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(GameRenderer.mStart);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		Log.d(TAG, "Showing alert dialog: " + message);
		bld.create().show();
	}

	void saveData() {

		/*
		 * WARNING: on a real application, we recommend you save data in a
		 * secure way to prevent tampering. For simplicity in this sample, we
		 * simply store the data using a SharedPreferences.
		 */

		SharedPreferences.Editor spe = GameRenderer.mStart.getPreferences(
				android.content.Context.MODE_PRIVATE).edit();
		spe.putInt("tank", mTank);
		spe.commit();
		Log.d(TAG, "Saved data: tank = " + String.valueOf(mTank));
	}

	void loadData() {
		SharedPreferences sp = GameRenderer.mStart
				.getPreferences(Context.MODE_PRIVATE);
		mTank = sp.getInt("tank", 2);
		Log.d(TAG, "Loaded data: tank = " + String.valueOf(mTank));
	}
}
