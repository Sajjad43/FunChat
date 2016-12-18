package com.example.text;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Fetch_friendimage_Socket implements Runnable{

	ArrayList<String> numberList;
	Context context;
	int flag;

	public Fetch_friendimage_Socket(Context context,int flag) {
		super();
		this.context = context;
		this.flag=flag;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

 
		numberList=new ArrayList<String>();

		//get the name and the number
		String userNameAndphone=readProfilFile();
		String[] namePhone=new String[2];
		namePhone=userNameAndphone.split(" ");
		
		
		Log.d("Sajjad", "FetchFriend socket,The username and phone are"+userNameAndphone);
		FileStatus.writelog("FetchFriend socket,The username and phone are"+userNameAndphone);
		
		try{
			 
			FileStatus file=new FileStatus(context);

			if(flag==2){
				Log.d("Sajjad","Fetch friend socket thread running due to service friend image");
				FileStatus.writelog("Fetch friend socket thread running due to service friend image");
			}
			else{

				Log.d("Sajjad","Fetch friend socket thread running due to service Socket image");
				FileStatus.writelog("Fetch friend socket thread running due to service Socket image");
			}

			//use a Arraylist here 

			//initialize the socket 
			//datainputStream and outputStream
			//connect to the server

			if(file.isFile("FriendList")==false&&flag==2){

				file.createFile("FriendList");
				LoadingContacts_thread loadingcontacts=new LoadingContacts_thread(context,numberList,flag);
				loadingcontacts.start();

			}
			else{

				this.numberList=file.fetchFriendlist("FriendList");
				SendFriendList send=new SendFriendList(context ,numberList,flag);
				send.start();
				//fetch the arraylist from the friends file;
			
			}
		}catch(Exception e){
			
			FileStatus.writelog("Fetch_friendImage-"+e.getLocalizedMessage());
		
		}

	}
	public String readProfilFile(){

		FileStatus file=new FileStatus(context);

		return file.readfromfile("profile");
	}

}
