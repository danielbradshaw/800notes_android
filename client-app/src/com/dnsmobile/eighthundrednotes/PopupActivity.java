package com.dnsmobile.eighthundrednotes;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class PopupActivity extends ListActivity {

	public static final String EXTRA_PHONE_NUMBER = "extraLastPhoneNumber";
	private static final String TEST_PHONE_NUMBER = "1-538-603-2145";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup);
		
    	Intent intent = getIntent();
    	String phoneNumber = (intent != null && intent.hasExtra(EXTRA_PHONE_NUMBER)) ? 
    			intent.getStringExtra(EXTRA_PHONE_NUMBER) : TEST_PHONE_NUMBER;
		(new GetJSONAsyncTask(this, phoneNumber)).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
   

}
