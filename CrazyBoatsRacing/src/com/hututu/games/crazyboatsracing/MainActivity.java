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
package com.hututu.games.crazyboatsracing;

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

// SKUs for our products: the premium upgrade (non-consumable) and gas(consumable)
//	static final String SKU_10000 = "coins1000";// "premium";10000
	static final String SKU_50000 = "free50000";// "gas";Get 50000 Coin (free50000)
//	static final String SKU_150000 = "freecoin_u";// "infinite_gas"; // SKU for// our subscription// (infinite gas)
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
//		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkNBN29lThY5DDRv2KWib5CepZ92KlzCSQ4K9ZY5c5YXrNwMYVjsn0cOM4FB46Gc9B4G6oZnSDlp0V1TO26s6EPNuiEox6M6GI6s42t/m3nlgcYFiXasKMe0uO032sU4bzjE4YBAFfJHEIEqRKan/K/ayz/11/Iv6hLFDsvObmzjg3PM87I8zTHglAQrUdkopSWQcxMesp4W8zEOR5k34LdPKign7ARSuL24Z9yBdHmibNv8Av0w33bjiRap3yFOKfhNTlxxLstRa2AJYyGykvGk8oatRWPWgYPKUTTxQe2lCpI1/Gtb7exreX4H4JrVYKw9MOJldR6KXfDNAhpYgOQIDAQAB";
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq7fmDocHQ3/BGZpYlrDXVhda4bEmtcyHxfGVHCVvHYNTosL4zsBj3F2PreB+8x40llZmdiB1RgBGRxEyuQOQruNa3intK5bowNB4ewP61b7a+0lphf0XK7uJCoAeOyEGRo41uAhVhfz0qmMityQUEpZ8vOry7r0By7ZeMJLdIbw5IhcHNfhMc5HMbIM267XvtwD6xKu32CJutlywuDH8YZlsSZ7cI9hrf+8BaVixaMDTDCqwFJ0SOYcoQrb94VK2ilvm3A9jaALaj8VLYjNJpyOmg0GfUj+IqrRCcmRZh4vwyexTVVzY5RXK+twzLo3BP0DG8VRLulQRetoLtIwQjwIDAQAB";
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
//			Purchase premiumPurchase = inventory.getPurchase(SKU_10000);
//			mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
//			Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));

			// Do we have the infinite gas plan?
			Purchase infiniteGasPurchase = inventory.getPurchase(SKU_50000);
			mSubscribedToInfiniteGas = (infiniteGasPurchase != null && verifyDeveloperPayload(infiniteGasPurchase));
			Log.d(TAG, "User "
					+ (mSubscribedToInfiniteGas ? "HAS" : "DOES NOT HAVE")
					+ " infinite gas subscription.");
			if (mSubscribedToInfiniteGas)
				mTank = TANK_MAX;

			// Check for gas delivery -- if we own gas, we should fill up the
			// tank immediately
//			Purchase gasPurchase = inventory.getPurchase(SKU_150000);
//			if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
//				Log.d(TAG, "We have gas. Consuming it.");
//				mHelper.consumeAsync(inventory.getPurchase(SKU_150000),
//						mConsumeFinishedListener);
//				return;
//			}

//			Purchase goldPurchase = inventory.getPurchase(SKU_350000);
//			if (goldPurchase != null && verifyDeveloperPayload(goldPurchase)) {
//				Log.d(TAG, "We have gas. Consuming it.");
//				mHelper.consumeAsync(inventory.getPurchase(SKU_350000),
//						mConsumeFinishedListener);
//				return;
//			}

			updateUi();
			setWaitScreen(false);
			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};

	/*************************************************************/
/*	public void onBuyGold10000(View arg0) {
		Log.d(TAG, "Buy gas button clicked.");
		setWaitScreen(true);
		Log.d(TAG, "Launching purchase flow for gas.");
		String payload = "";
		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_10000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}
*/
	// User clicked the "Buy Gas" button
	public void onBuyGold50000(View arg0) {
		Log.d(TAG, "Buy gas button clicked.");
		setWaitScreen(true);
		Log.d(TAG, "Launching purchase flow for gas.");
		String payload = "";
		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_50000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}

	/*public void onBuyGold150000(View arg0) {
		Log.d(TAG, "Buy gas button clicked.");
		setWaitScreen(true);
		Log.d(TAG, "Launching purchase flow for gas.");
		String payload = "";
		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_150000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}

	public void onBuyGold350000(View arg0) {
		Log.d(TAG, "Buy gas button clicked.");
		setWaitScreen(true);
		Log.d(TAG, "Launching purchase flow for gas.");
		String payload = "";
		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_350000, RC_REQUEST, mPurchaseFinishedListener, payload);
	}
*/
	/*******************************************************************************************/

	// User clicked the "Buy Gas" button
	public void onBuyGoldButtonClicked(View arg0) {
		Log.d(TAG, "Buy gas button clicked.");

		// if (mSubscribedToInfiniteGas) {
		// complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
		// return;
		// }
		//
		// if (mTank >= TANK_MAX) {
		// complain("Your tank is full. Drive around a bit!");
		// return;
		// }

		// launch the gas purchase UI flow.
		// We will be notified of completion via mPurchaseFinishedListener
		setWaitScreen(true);
		Log.d(TAG, "Launching purchase flow for gas.");

		/*
		 * TODO: for security, generate your payload here for verification. See
		 * the comments on verifyDeveloperPayload() for more info. Since this is
		 * a SAMPLE, we just use an empty string, but on a production app you
		 * should carefully generate this.
		 */
//		String payload = "";

//		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_350000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}

	// User clicked the "Buy Gas" button
	public void onBuyGasButtonClicked(View arg0) {
		Log.d(TAG, "Buy gas button clicked.");
		// if (mSubscribedToInfiniteGas) {
		// complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
		// return;
		// }
		// if (mTank >= TANK_MAX) {
		// complain("Your tank is full. Drive around a bit!");
		// return;
		// }
		// launch the gas purchase UI flow.
		// We will be notified of completion via mPurchaseFinishedListener
		setWaitScreen(true);
		Log.d(TAG, "Launching purchase flow for gas.");
		/*
		 * TODO: for security, generate your payload here for verification. See
		 * the comments on verifyDeveloperPayload() for more info. Since this is
		 * a SAMPLE, we just use an empty string, but on a production app you
		 * should carefully generate this.
		 */
//		String payload = "";
//		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_150000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}

	// User clicked the "Upgrade to Premium" button.
	public void onUpgradeAppButtonClicked(View arg0) {
		Log.d(TAG,
				"Upgrade button clicked; launching purchase flow for upgrade.");
		setWaitScreen(true);

		/*
		 * TODO: for security, generate your payload here for verification. See
		 * the comments on verifyDeveloperPayload() for more info. Since this is
		 * a SAMPLE, we just use an empty string, but on a production app you
		 * should carefully generate this.
		 */
//		String payload = "";

//		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_10000, RC_REQUEST,
//				mPurchaseFinishedListener, payload);
	}

	// "Subscribe to infinite gas" button clicked. Explain to user, then start
	// purchase
	// flow for subscription.
	public void onInfiniteGasButtonClicked(View arg0) {
		if (!mHelper.subscriptionsSupported()) {
			complain("Subscriptions not supported on your device yet. Sorry!");
			return;
		}

		/*
		 * TODO: for security, generate your payload here for verification. See
		 * the comments on verifyDeveloperPayload() for more info. Since this is
		 * a SAMPLE, we just use an empty string, but on a production app you
		 * should carefully generate this.
		 */
		String payload = "";

		setWaitScreen(true);
		Log.d(TAG, "Launching purchase flow for infinite gas subscription.");
		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_50000, RC_REQUEST,
				mPurchaseFinishedListener, payload);
	}

	// @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + ","
				+ data);

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...
			// super.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.d(TAG, "onActivityResult handled by IABUtil.");
		}
	}

	/** Verifies the developer payload of a purchase. */
	boolean verifyDeveloperPayload(Purchase p) {
		/*String payload = */p.getDeveloperPayload();

		/*
		 * TODO: verify that the developer payload of the purchase is correct.
		 * It will be the same one that you sent when initiating the purchase.
		 * 
		 * WARNING: Locally generating a random string when starting a purchase
		 * and verifying it here might seem like a good approach, but this will
		 * fail in the case where the user purchases an item on one device and
		 * then uses your app on a different device, because on the other device
		 * you will not have access to the random string you originally
		 * generated.
		 * 
		 * So a good developer payload has these characteristics:
		 * 
		 * 1. If two different users purchase an item, the payload is different
		 * between them, so that one user's purchase can't be replayed to
		 * another user.
		 * 
		 * 2. The payload must be such that you can verify it even when the app
		 * wasn't the one who initiated the purchase flow (so that items
		 * purchased by the user on one device work on other devices owned by
		 * the user).
		 * 
		 * Using your own server to store and verify developer payloads across
		 * app installations is recommended.
		 */

		return true;
	}

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

			System.out.println(TAG+ GameRenderer.mStart.mGR.mTotalCoin
					+ "  Purchase successful~~~~~~~~~~~~~~~~~~~~~~~~~~~"
					+ purchase.getSku());

			/*if (purchase.getSku().equals(SKU_350000)) {
				GameRenderer.mStart.mGR.mTotalCoin += 50000;
//				alert("Thank you for subscribing to infinite gas! SKU_350000");
				alert("Success");
				// // bought 1/4 tank of gas. So consume it.
				// Log.d(TAG, "Purchase is gas. Starting gas consumption.");
				mHelper.consumeAsync(purchase, mConsumeFinishedListener);
			} else if (purchase.getSku().equals(SKU_150000)) {
				GameRenderer.mStart.mGR.mTotalCoin += 150000;
//				alert("Thank you for subscribing to infinite gas! SKU_150000");
				alert("Success");
				// // bought 1/4 tank of gas. So consume it.
				// Log.d(TAG, "Purchase is gas. Starting gas consumption.");
				mHelper.consumeAsync(purchase, mConsumeFinishedListener);
			} else if (purchase.getSku().equals(SKU_10000)) {
				GameRenderer.mStart.mGR.mTotalCoin += 10000;
				// // bought the premium upgrade!
				// Log.d(TAG,
				// "Purchase is premium upgrade. Congratulating user.");
//				alert("Thank you for upgrading to premium! SKU_10000");
				alert("Success");
				// mIsPremium = true;
				// updateUi();
				// setWaitScreen(false);
			} else*/ 
				if (purchase.getSku().equals(SKU_50000)) {
				GameRenderer.mStart.mGR.mTotalCoin += 50000;
				// // bought the infinite gas subscription
				// Log.d(TAG, "Infinite gas subscription purchased.");
//				alert("Thank you for subscribing to infinite gas! SKU_50000");
				// mSubscribedToInfiniteGas = true;
				// mTank = TANK_MAX;
				// updateUi();
				// setWaitScreen(false);
				alert("Success");
			}
			GameRenderer.mStart.mGR.addFree	= true;
			GameRenderer.mStart.pause();
			total = GameRenderer.mStart.mGR.mTotalCoin;
			System.out.println(TAG+ GameRenderer.mStart.mGR.mTotalCoin
					+ "  Purchase successful~~~~~~~~~~~~ ["+total+"] ~~~~~~~~~~~~~~~"
					+ purchase.getSku());
		}
	};

	// Called when consumption is complete
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		public void onConsumeFinished(Purchase purchase, IabResult result) {
			Log.d(TAG, "Consumption finished. Purchase: " + purchase
					+ ", result: " + result);

			// We know this is the "gas" sku because it's the only one we
			// consume,
			// so we don't check which sku was consumed. If you have more than
			// one
			// sku, you probably should check...
			if (result.isSuccess()) {
				// successfully consumed, so we apply the effects of the item in
				// our
				// game world's logic, which in our case means filling the gas
				// tank a bit
				Log.d(TAG, "Consumption successful. Provisioning.");
				mTank = mTank == TANK_MAX ? TANK_MAX : mTank + 1;
				saveData();
//				alert("You filled 1/4 tank. Your tank is now "+ String.valueOf(mTank) + "/4 full!");
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
