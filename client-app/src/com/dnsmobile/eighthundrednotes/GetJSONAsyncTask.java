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
import android.os.AsyncTask;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GetJSONAsyncTask extends AsyncTask<Void, Void, Void> {
	
    private final ArrayList<UserResponse> userResponses = new ArrayList<UserResponse>();
    private final ListActivity activity;
    private final String phoneNumber;
    
    GetJSONAsyncTask(ListActivity activity, String phoneNumber) {
    	this.activity = activity;
    	this.phoneNumber = phoneNumber;
    }
    	
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        ResponseListAdapter responseAdapter = new ResponseListAdapter(activity, userResponses);
        activity.setListAdapter(responseAdapter);
        
        View loadingSpinner = activity.findViewById(R.id.loading_spinner);
        if (loadingSpinner != null) {
        	loadingSpinner.setVisibility(View.INVISIBLE);
        }
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        if (activity.getListAdapter() != null) {
        	activity.setListAdapter(null);
        }
        
        TextView numberView = (TextView) activity.findViewById(R.id.text_phonenumber);
        if (numberView != null) {
        	numberView.setText(phoneNumber);
        	numberView.setVisibility(View.VISIBLE);
        }
        
        View loadingSpinner = activity.findViewById(R.id.loading_spinner);
        if (loadingSpinner != null) {
        	loadingSpinner.setVisibility(View.VISIBLE);
        }
    }

	@Override
	protected Void doInBackground(Void... params) {
		String formattedPhoneNumber = PhoneNumberUtils.formatNumber(phoneNumber);
		String json = retrieveJSON("http://10.1.10.50/800notes/index.php?phonenumber=" + formattedPhoneNumber);
//		String json = retrieveJSON("http://flashpass.redirectme.net/800notes/index.php?phonenumber=" + formattedPhoneNumber);
		
		try {
			JSONObject jsonData = new JSONObject(json);
			JSONArray jsonArray = jsonData.getJSONArray("responses");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				UserResponse response = new UserResponse(
						jsonObject.getString("name"), jsonObject.getString("message"), 
						jsonObject.getJSONObject("postdate").getString("date"));
				userResponses.add(response);
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
