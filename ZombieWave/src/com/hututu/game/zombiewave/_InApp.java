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
package com.hututu.game.zombiewave;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import com.hututu.billing.util.IabHelper;
import com.hututu.billing.util.IabResult;
import com.hututu.billing.util.Inventory;
import com.hututu.billing.util.Purchase;

public class _InApp {
	static final String TAG = "Payment~  ";
	static final String SKU_50000 = "ads_free";
	public static final int RC_REQUEST = 10001;
	IabHelper mHelper;
	public void onCreate() {
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhjaWhjxUe5S8bb/fDj4BfkBQfhhoFcK2WliEUYMhqeXWKHjjzWf++vybQZkedt/jJh6kYrGF2az4S2VyDaxzOdsFIMW+QvX66RjiUYuuKDKPrYee2y/YM91R4C9xe6xU3tCFHt2yRyRpTekiizCYkToZg1VG/Nkgn4p2v+ML/qvJeBNCz9YWNfjFMrzNODiAn5p8UPDpY7I/fGc7X37ClOFZG32+1U3zrV5BcnmIa16yrlMiYrssn4KaDKR2epPeDZKfG00oKmE7ITU2Qgl1o1L5J+vOWBKvEj9dmKDEZRTFnIikJW+2meSzRZiqsv0z2g4xLRnKPuIiJ9aPqZPHfQIDAQAB";
		if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
			throw new RuntimeException( "Please put your app's public key in MainActivity.java. See README.");
		}
		if (GameRenderer.mContext.getPackageName().startsWith("com.example")) {
			throw new RuntimeException( "Please change the sample's package name! See README.");
		}
		mHelper = new IabHelper(GameRenderer.mContext, base64EncodedPublicKey);
		mHelper.enableDebugLogging(true);
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess()) {
					complain("Problem setting up in-app billing: " + result);
					return;
				}
				mHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});
	}
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result,Inventory inventory) {
			if (result.isFailure()) {
				complain("Failed to query inventory: " + result);
				return;
			}
			Purchase infiniteGasPurchase = inventory.getPurchase(SKU_50000);
			boolean get = (infiniteGasPurchase != null && verifyDeveloperPayload(infiniteGasPurchase));
			System.out.println(get);
			setWaitScreen(false);
			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};

	/*************************************************************/
	public void onBuyGold50000(View arg0) {
		Log.d(TAG, "Buy gas button clicked.");
		setWaitScreen(true);
		Log.d(TAG, "Launching purchase flow for gas.");
		String payload = "";
		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_50000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}
	/*******************************************************************************************/

	// @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + ","+ data);
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
//				complain("Error purchasing: " + result);
				setWaitScreen(false);
				return;
			}
			if (!verifyDeveloperPayload(purchase)) {
				complain("Error purchasing. Authenticity verification failed.");
				setWaitScreen(false);
				return;
			}
			if (purchase.getSku().equals(SKU_50000)) {
				GameRenderer.mStart.mGR.Dollor += 20000000;
				alert("Success");
			}
			GameRenderer.mStart.mGR.addFree = true;
			GameRenderer.mStart.pause();
//			total = GameRenderer.mStart.mGR.mTotalCoin;
			System.out.println(TAG + GameRenderer.mStart.mGR.addFree
					+ "  Purchase successful~~~~~~~~~~~~ [] ~~~~~~~~~~~~~~~" + purchase.getSku());
		}
	};

	// Called when consumption is complete
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		public void onConsumeFinished(Purchase purchase, IabResult result) {
			if (result.isSuccess()) {
			} else {
				complain("Error while consuming: " + result);
			}
			setWaitScreen(false);
			Log.d(TAG, "End consumption flow.");
		}
	};

	public void onDestroy() {
		try{
			if (mHelper != null)
				mHelper.dispose();
			mHelper = null;
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	void setWaitScreen(boolean set) {
		// findViewById(R.id.screen_main).setVisibility(set ? View.GONE :
		// View.VISIBLE);
		// findViewById(R.id.screen_wait).setVisibility(set ? View.VISIBLE :
		// View.GONE);
	}

	void complain(String message) {
		alert("Error: " + message);
	}

	void alert(String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(GameRenderer.mStart);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		Log.d(TAG, "Showing alert dialog: " + message);
		bld.create().show();
	}
}
