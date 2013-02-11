package com.dnsmobile.eighthundrednotes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	public static final String EXTRA_PHONE_NUMBER = "extraLastPhoneNumber";
	private static final String TEST_PHONE_NUMBER = "1-538-603-2145";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		(new GetJSONAsyncTask(getIntent())).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	class GetJSONAsyncTask extends AsyncTask<Void, Void, Void> {
		
        private final ArrayList<String> responses;
        private final String phoneNumber;
        
        GetJSONAsyncTask(Intent intent) {
        	responses = new ArrayList<String>();
        	phoneNumber = (intent != null && intent.hasExtra(EXTRA_PHONE_NUMBER)) ? 
        			intent.getStringExtra(EXTRA_PHONE_NUMBER) : TEST_PHONE_NUMBER;
        }
        
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ResponseListAdapter responseAdapter = new ResponseListAdapter(MainActivity.this, responses);
            setListAdapter(responseAdapter);
            
            View loadingSpinner = MainActivity.this.findViewById(R.id.loading_spinner);
            loadingSpinner.setVisibility(View.INVISIBLE);
        }
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            TextView numberView = (TextView) MainActivity.this.findViewById(R.id.text_phonenumber);
            numberView.setText(phoneNumber);
            numberView.setVisibility(View.VISIBLE);
            
            View loadingSpinner = MainActivity.this.findViewById(R.id.loading_spinner);
            loadingSpinner.setVisibility(View.VISIBLE);
        }

		@Override
		protected Void doInBackground(Void... params) {
			String formattedPhoneNumber = PhoneNumberUtils.formatNumber(phoneNumber);
			String json = retrieveJSON("http://10.1.10.50/800notes/index.php?phonenumber=" + formattedPhoneNumber);
//			String json = retrieveJSON("http://flashpass.redirectme.net/800notes/index.php");
			
			try {
				JSONObject jsonData = new JSONObject(json);
				JSONArray jsonArray = jsonData.getJSONArray("responses");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					responses.add(jsonObject.getString("message"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		private String retrieveJSON(String url) {
			
			Log.i("MainActivity", "Retreiving JSON data from URL: " + url);
			
			try {
				URL address = new URL(url);
				InputStream is = address.openConnection().getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				ByteArrayBuffer bab = new ByteArrayBuffer(64); 
				int current = 0;
		
				while((current = bis.read()) != -1) {
				  bab.append((byte)current); 
				}
		
				return new String(bab.toByteArray());
			} 
			catch (MalformedURLException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
    }   

}
