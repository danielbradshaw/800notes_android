package com.dnsmobile.eighthundrednotes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ResponseListAdapter extends ArrayAdapter<UserResponse> {

	private final Activity activity;
	private final ArrayList<UserResponse> responses;
	
	public ResponseListAdapter(Activity activity, ArrayList<UserResponse> objects) {
		super(activity, R.layout.response_list_item, objects);
		this.activity = activity;
		this.responses = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		
		if (rowView == null) {
			// create new response list item view
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.response_list_item, null);
		}
		
		TextView nameView = (TextView) rowView.findViewById(R.id.text_name);
		nameView.setText(responses.get(position).getUserName());
		
		TextView messageView = (TextView) rowView.findViewById(R.id.text_message);
		messageView.setText(responses.get(position).getMessage());

		TextView dateView = (TextView) rowView.findViewById(R.id.text_date);
		DateFormat dateFormat = new SimpleDateFormat("M/dd/yy h:mma");
		String formattedDate = dateFormat.format(responses.get(position).getPostDate());
		dateView.setText(formattedDate);
		
		return rowView;
	}
	
}
