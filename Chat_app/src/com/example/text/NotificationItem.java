package com.example.text;

public class NotificationItem {

	String name,
	phoneNumber,
	filename_message,
	time;
	int type;

	public NotificationItem(String name, String number, String filename_message, int type, String time) {
		super();
		this.name = name;
		this.phoneNumber = number;
		this.filename_message = filename_message;
		this.type = type;
		 
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setNumber(String number) {
		this.phoneNumber = number;
	}

	 
	 
 

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFilename_message() {
		return filename_message;
	}

	public void setFilename_message(String filename_message) {
		this.filename_message = filename_message;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
