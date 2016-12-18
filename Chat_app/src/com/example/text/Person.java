package com.example.text;

import android.graphics.Bitmap;

public class Person {

	private	Bitmap image=null;
	private	String name="";
	private	String number="";
	public Person(Bitmap image, String name, String number) {
		super();
		this.image = image;
		this.name = name;
		this.number = number;
	}


	public Person(String name, String number) {
		super();
		this.name = name;
		this.number = number;
	}


	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}



}
