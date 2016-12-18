package com.example.text;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;

public class Handler_one implements Runnable{

	Activity activity;
	ArrayList<String>  CentralGroupchatarray=new ArrayList<String>();
	ArrayList<String>  UserGroupchatarray=new ArrayList<String>();
	
	
	public Handler_one(Activity activity,ArrayList<String> arraylist1,ArrayList<String> arraylist2) {
		super();
		this.activity = activity;
		this.CentralGroupchatarray=arraylist1;
		this.UserGroupchatarray=arraylist2;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Log.d("Sajjad","handler");
		
		 //make the dialog visible
		  android.app.FragmentManager manager = activity.getFragmentManager();
          
		 // Dialog_fragment dialog=new Dialog_fragment(this.CentralGroupchatarray,this.UserGroupchatarray);
	     
		  //dialog.show(manager,"Sajjad");
	
	
	
	}

}
