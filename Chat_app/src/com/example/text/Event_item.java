package com.example.text;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class Event_item {
	
	String title,address,date,time,url;
	BitmapDrawable bitmapdrawable;
	Activity activity;
	Bitmap bitmap;
	
	 
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	public Event_item(String title, String address, String date, String time,String url,Bitmap bitmap) {
		super();
		this.title = title;
		this.address = address;
		this.date = date;
		this.time = time;
		this.url=url;
		 
		this.bitmap=bitmap;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public Bitmap getBitmap() {
		
		return	this.bitmap.createScaledBitmap(bitmap ,100,150 ,true);
		
	   
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}


}
