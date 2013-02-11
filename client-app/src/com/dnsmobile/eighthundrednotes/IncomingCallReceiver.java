package com.dnsmobile.eighthundrednotes;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IncomingCallReceiver extends BroadcastReceiver {

	private static final String INCOMING_CALL_SHARED_PREFS = "prefsIncomingCall";
	private static final String PREFS_CALL_RECEIVED = "prefsCallReceived";
	private static final String PREFS_LAST_NUMBER = "prefsLastNumber";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
        if (bundle == null) return;
        
        Log.i("IncomingCallReceiver", bundle.toString());
        String state = bundle.getString(TelephonyManager.EXTRA_STATE);
        Log.i("IncomingCallReceiver", "State: "+ state);
        
        SharedPreferences sharedPrefs = context.getSharedPreferences(INCOMING_CALL_SHARED_PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        
        if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) 
        {
        	String phonenumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            editor.putBoolean(PREFS_CALL_RECEIVED, true);
            editor.putString(PREFS_LAST_NUMBER, phonenumber);
            editor.commit();
            
        	Log.i("IncomingCallReceiver","Incoming Number: " + phonenumber);
        }
        else if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) 
        {
        	if (sharedPrefs.getBoolean(PREFS_CALL_RECEIVED, false)) {
        		Intent launchIntent = new Intent(context, MainActivity.class);
        		launchIntent.putExtra(MainActivity.EXTRA_PHONE_NUMBER, sharedPrefs.getString(PREFS_LAST_NUMBER, ""));
        		launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        		context.startActivity(launchIntent);
        	}
        	
            editor.putBoolean(PREFS_CALL_RECEIVED, false);
            editor.putString(PREFS_LAST_NUMBER, "");
            editor.commit();
            
            Log.i("IncomingCallReceiver","Phone went into idle");
        }
        else if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK))
        {
        	editor.putBoolean(PREFS_CALL_RECEIVED, false);
            editor.putString(PREFS_LAST_NUMBER, "");
            editor.commit();
        	
            Log.i("IncomingCallReceiver","Call was answered/started");
        }
	}

}
