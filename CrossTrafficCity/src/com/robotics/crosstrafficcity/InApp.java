package com.robotics.crosstrafficcity;
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
	static final String SKU_10000 = "one";//Ads_free (ads_free)
	static final String SKU_20000 = "piggy";//Get 30000 Coin (two)
	static final String SKU_30000 = "three";//Get 50000 Coin (three)
	static final String SKU_40000 ="four";//Get 100000 Coin (four)
//	static final String SKU_50000 ="four";//Get 100000 Coin (four)
	static final int RC_REQUEST = 10001;
	static final int TANK_MAX = 4;
	IabHelper mHelper;

	// @Override
	public void onCreate() {
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApME3r0DyNbXiq5yrh7h4AF/UI9v71OiVqK6OJx27Pc8oazUQkGExm241bFR+DoLeFALOo0sVYYihOy1VbQ/+iB5Epnh3XuxSCALLgTuwV3t/hSYeZsRKR4xrn+lam8P5ewK1kZ/EMXkr8GrlLDg58+4342uYD7pOa7mJiiz4fnGn+bRlAi7hjcvy8hLrnHJgPo8MvkOwOChljOmUyEGbNTLvJ7hB+cL6KBxI4rY/tUnPtf4vD8/bkGN2PbcIMWoYIUKUpajtuC6tgNb8231jLd04FYi+xAzeZDU7QBAZi0IGRlPtRuRcppjAOCZOLnWDULjyidz1sqohT64wVXn2xwIDAQAB";
		// Some sanity checks to see if the developer (that's you!) really
		// followed the
		// instructions to run this sample (don't put these checks on your app!)
		if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
			throw new RuntimeException(
					"Please put your app's public key in MainActivity.java. See README.");
		}
		if (HTTRenderer.mContext.getPackageName().startsWith("com.example")) {
			throw new RuntimeException(
					"Please change the sample's package name! See README.");
		}

		// Create the helper, passing it our context and the public key to
		// verify signatures with
		Log.d(TAG, "Creating IAB helper.");
		mHelper = new IabHelper(HTTRenderer.mContext, base64EncodedPublicKey);

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
//			Purchase infiniteGasPurchase = inventory.getPurchase(SKU_30000);
//			mSubscribedToInfiniteGas = (infiniteGasPurchase != null && verifyDeveloperPayload(infiniteGasPurchase));
//			Log.d(TAG, "User "
//					+ (mSubscribedToInfiniteGas ? "HAS" : "DOES NOT HAVE")
//					+ " infinite gas subscription.");
//			if (mSubscribedToInfiniteGas)
//				mTank = TANK_MAX;
//
//			// Check for gas delivery -- if we own gas, we should fill up the
//			// tank immediately
//			Purchase gasPurchase = inventory.getPurchase(SKU_50000);
//			if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
//				Log.d(TAG, "We have gas. Consuming it.");
//				mHelper.consumeAsync(inventory.getPurchase(SKU_50000), mConsumeFinishedListener);
//				return;
//			}
//
//			Purchase goldPurchase = inventory.getPurchase(SKU_100000);
//			if (goldPurchase != null && verifyDeveloperPayload(goldPurchase)) {
//				Log.d(TAG, "We have gas. Consuming it.");
//				mHelper.consumeAsync(inventory.getPurchase(SKU_100000), mConsumeFinishedListener);
//				return;
//			}

			updateUi();
			setWaitScreen(false);
			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};

	/*************************************************************/
	public void onBuyCharecter(View arg0) {
		setWaitScreen(true);
		String payload = "";
		mHelper.launchPurchaseFlow(HTTRenderer.mStart, M.BUYID[HTTRenderer.mStart.mGR.mPlayer.buyChar], RC_REQUEST,mPurchaseFinishedListener, payload);
	}
	public void onBuyPiggy(View arg0) {
		setWaitScreen(true);
		String payload = "";
		mHelper.launchPurchaseFlow(HTTRenderer.mStart, SKU_20000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}

	public void onBuyGold30000(View arg0) {
		setWaitScreen(true);
		String payload = "";
		mHelper.launchPurchaseFlow(HTTRenderer.mStart, SKU_30000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}
	public void onBuyGold40000(View arg0) {
		setWaitScreen(true);
		String payload = "";
		mHelper.launchPurchaseFlow(HTTRenderer.mStart, SKU_40000, RC_REQUEST,mPurchaseFinishedListener, payload);
	}
//	public void onBuyGold50000(View arg0) {
//		setWaitScreen(true);
//		String payload = "";
//		mHelper.launchPurchaseFlow(HTTRenderer.mStart, SKU_50000, RC_REQUEST,mPurchaseFinishedListener, payload);
//	}
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

//		mHelper.launchPurchaseFlow(HTTRenderer.mStart, SKU_350000, RC_REQUEST,mPurchaseFinishedListener, payload);
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
//		mHelper.launchPurchaseFlow(HTTRenderer.mStart, SKU_150000, RC_REQUEST,mPurchaseFinishedListener, payload);
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

//		mHelper.launchPurchaseFlow(HTTRenderer.mStart, SKU_10000, RC_REQUEST,
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
//		mHelper.launchPurchaseFlow(HTTRenderer.mStart, SKU_50000, RC_REQUEST,
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
			
			for(int i =0;i<M.BUYID.length;i++){
				if (purchase.getSku().equals(M.BUYID[HTTRenderer.mStart.mGR.mPlayer.buyChar])) {
					HTTRenderer.mStart.mGR.isbuyPlayer[HTTRenderer.mStart.mGR.mPlayer.buyChar] = true;
					alert("Success");
					break;
				}
			}
			if (purchase.getSku().equals(SKU_20000)) {
				HTTRenderer.mStart.mGR.cent+=1000;
				HTTRenderer.mStart.mGR.isdpublecent = true;
				alert("Success");
			}
			HTTRenderer.mStart.mGR.addFree = true;
			HTTRenderer.mStart.pause();
			System.out.println(" Purchase 2 successful " + purchase.getSku());
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
		AlertDialog.Builder bld = new AlertDialog.Builder(HTTRenderer.mStart);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		Log.d(TAG, "Showing alert dialog: " + message);
		bld.create().show();
	}

}
