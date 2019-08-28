package com.hututu.game.empiresglory;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import com.hututu.billing.util.IabHelper;
import com.hututu.billing.util.IabResult;
import com.hututu.billing.util.Inventory;
import com.hututu.billing.util.Purchase;


public class InApp {
	static final String TAG = "Payment~  ";
	boolean mIsPremium = false;
	boolean mSubscribedToInfiniteGas = false;

	static final String CRYSTAL_25 ="crystal25";//Get 25
	static final String CRYSTAL_50 ="crystal50";//Get 50
	static final String CRYSTAL_125 ="crystal125";//Get 125
	static final String CRYSTAL_250 ="crystal250";//Get 250
	
	static final String KEY_25 ="key25";//Get 25
	static final String KEY_50 ="key50";//Get 50
	static final String KEY_125 ="key125";//Get 100
	static final String KEY_250 ="key250";//Get 250
	
	
	static final String COIN_5000 ="coin50";//Get 5000
	static final String COIN_10000 ="coin100";//Get 10000
	static final String COIN_25000 ="coin250";//Get 25000
	static final String COIN_50000 ="coin500";//Get 50000
	
	
	
	static final int RC_REQUEST = 10001;
	static final int TANK_MAX = 4;
	IabHelper mHelper;

	// @Override
	public void onCreate() {
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArcyobfkMQNWuPzUve28gVV8h3Tp97FS7KOV+tgznhjFhQl2/oDRbJg/BDqQwp6qm2+pk34x36oQBAB7C4VookH36zErrFf8WHbOg4sU3zob+BMEYK388atQpBe0/a6tMV6IHfI6KQN2brvZzUS3EHB5z5YljR9ks3woRGBCdbPlFBYgFFjsvAzxZRfskuL3KJesR4SEF+fLOlJqT2VFOIqyCOjJj0hb7Pwgxx8INcjlXAWWkIQ9KqUclNMZF6pZtoSCuNUD4ymEw/JVCpa2eB2wglPdwTNPfmhBhixB7Av4vvdBvWXCgsDexk7iEvGn/qiUI9mrYjmvx8maHEqfJAQIDAQAB";
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
			updateUi();
			setWaitScreen(false);
			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};

	/*************************************************************/
	public void onCrystal(int no) {
		switch (no) {
		case 1:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, CRYSTAL_25, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		case 2:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, CRYSTAL_50, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		case 3:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, CRYSTAL_125, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		case 4:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, CRYSTAL_25, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		}
		
	}
	public void onKey(int no) {
		switch (no) {
		case 1:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, KEY_25, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		case 2:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, KEY_50, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		case 3:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, KEY_125, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		case 4:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, KEY_25, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		}
		
	}
	public void onCoin(int no) {
		switch (no) {
		case 1:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, COIN_5000, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		case 2:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, COIN_10000, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		case 3:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, COIN_25000, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		case 4:
			mHelper.launchPurchaseFlow(GameRenderer.mStart, COIN_50000, RC_REQUEST,mPurchaseFinishedListener, "");
			break;
		}
		
	}
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
//		String payload = "";

		setWaitScreen(true);
//		Log.d(TAG, "Launching purchase flow for infinite gas subscription.");
//		mHelper.launchPurchaseFlow(GameRenderer.mStart, SKU_50000, RC_REQUEST,
//				mPurchaseFinishedListener, payload);
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
			Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
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

			if (purchase.getSku().equals(CRYSTAL_25)) {
				GameRenderer.mStart.mGR.mPlayer.TCrystal+=25f;
				alert("Success");
			}
			if (purchase.getSku().equals(CRYSTAL_50)) {
				GameRenderer.mStart.mGR.mPlayer.TCrystal+=50f;
				alert("Success");
			}
			if (purchase.getSku().equals(CRYSTAL_125)) {
				GameRenderer.mStart.mGR.mPlayer.TCrystal+=125f;
				alert("Success");
			}
			if (purchase.getSku().equals(CRYSTAL_250)) {
				GameRenderer.mStart.mGR.mPlayer.TCrystal+=250f;
				alert("Success");
			}

			if (purchase.getSku().equals(KEY_25)) {
				GameRenderer.mStart.mGR.mPlayer.TKey+=25f;
				alert("Success");
			}
			if (purchase.getSku().equals(KEY_50)) {
				GameRenderer.mStart.mGR.mPlayer.TKey+=50f;
				alert("Success");
			}
			if (purchase.getSku().equals(KEY_125)) {
				GameRenderer.mStart.mGR.mPlayer.TKey+=125f;
				alert("Success");
			}
			if (purchase.getSku().equals(KEY_250)) {
				GameRenderer.mStart.mGR.mPlayer.TKey+=250f;
				alert("Success");
			}
			
			if (purchase.getSku().equals(COIN_5000)) {
				GameRenderer.mStart.mGR.mPlayer.T$+=5000f;
				alert("Success");
			}
			if (purchase.getSku().equals(COIN_10000)) {
				GameRenderer.mStart.mGR.mPlayer.T$+=10000f;
				alert("Success");
			}
			if (purchase.getSku().equals(COIN_25000)) {
				GameRenderer.mStart.mGR.mPlayer.T$+=25000f;
				alert("Success");
			}
			if (purchase.getSku().equals(COIN_50000)) {
				GameRenderer.mStart.mGR.mPlayer.T$+=50000f;
				alert("Success");
			}
			
			
			GameRenderer.mStart.mGR.addFree = true;
			GameRenderer.mStart.pause();
			System.out.println(" Purchase > "+purchase.getSku()+" < successful " + purchase.getSku());
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

}
