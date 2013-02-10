package com.dnsmobile.eighthundrednotes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		(new GetJSONAsyncTask()).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	class GetJSONAsyncTask extends AsyncTask<Void, Void, Void> {
		
        private TextView textView;
        private String responses;
        
        GetJSONAsyncTask()    {
            textView = (TextView)findViewById(R.id.hello_text);            
        }
        
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            textView.setText(responses);
        }
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setText("Retrieving JSON...");
        }

		@Override
		protected Void doInBackground(Void... params) {
//			String json = retrieveJSON("http://10.1.10.50/800notes/index.php");
			String json = retrieveJSON("http://flashpass.redirectme.net/800notes/index.php");
			
			try {
				JSONObject jsonData = new JSONObject(json);
				JSONArray jsonArray = jsonData.getJSONArray("responses");
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					sb.append(jsonObject.getString("message"));
					sb.append("\n\n");
				}
				responses = sb.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		private String retrieveJSON(String url) {
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
