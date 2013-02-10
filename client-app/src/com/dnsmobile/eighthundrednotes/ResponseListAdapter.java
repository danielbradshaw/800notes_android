package com.dnsmobile.eighthundrednotes;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ResponseListAdapter extends ArrayAdapter<String> {

	private final Activity mActivity;
	private final ArrayList<String> mResponses;
	
	public ResponseListAdapter(Activity activity, ArrayList<String> objects) {
		super(activity, R.layout.response_list_item, objects);
		this.mActivity = activity;
		this.mResponses = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		
		if (rowView == null) {
			// create new response list item view
			LayoutInflater inflater = mActivity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.response_list_item, null);
		}
		
		TextView nameView = (TextView) rowView.findViewById(R.id.text_name);
		nameView.setText("Person's Name");
		
		TextView messageView = (TextView) rowView.findViewById(R.id.text_message);
		messageView.setText(mResponses.get(position));
		
		return rowView;
	}
	
}
