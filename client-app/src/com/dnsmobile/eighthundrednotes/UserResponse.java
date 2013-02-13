package com.dnsmobile.eighthundrednotes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserResponse {

	private final String userName;
	private final String message;
	private final Date postDate;
	
	public UserResponse(String userName, String message, String postDate) {
		this.userName = userName;
		this.message = message;

		Date tempDate;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			tempDate = formatter.parse(postDate);
		} catch (ParseException e) {
			tempDate = null;
			e.printStackTrace();
		}
		
		this.postDate = tempDate;
	}

	public String getUserName() {
		return userName;
	}

	public String getMessage() {
		return message;
	}

	public Date getPostDate() {
		return postDate;
	}
	
}
