package com.example.text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class Socket_7000 extends Thread{

	Activity activity;
	String userName;
	String phoneNumber;
	FileStatus fileStatus;

	public Socket_7000(Activity activity, String username, String phonenumber) {
		super();
		this.activity = activity;
		this.userName = username;
		this.phoneNumber = phonenumber;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Log.d("Sajjad","Socket 7000 made");

		//initialize the fileStatus object
		fileStatus=new FileStatus(activity);

		try {

			//initialize the socket
			Socket socket=new Socket(IP.Ip,IP.port1);

			if(socket!=null)
			{
				DataInputStream inputStream=new DataInputStream(socket.getInputStream());
				DataOutputStream outputStream=new DataOutputStream(socket.getOutputStream());

				//send the username and the phone number to the server 7000
				outputStream.writeUTF(userName);
				outputStream.writeUTF(phoneNumber);

				//save the username and the phonenumber to the file
				saveUserDetails();

				Log.d("Sajjad",""+userName+" and " +phoneNumber+"sent");


				if(inputStream.readUTF().equals("ok"))
				{
					Log.d("Sajjad","Got ok message");

					//create the ActivityOne file
					fileStatus.createFile("ActivityOne");

					//to-do list----
					//start another service which will run a Fetch_friendimageSocket thread 
					//Along with this, start the detect_connection9000

					if(activity!=null){

						Intent intent=new Intent(activity,ServiceFetch_FriendPic.class);
						activity.startService(intent);

					}
					/**show the progress dialog**/
					activity.runOnUiThread(new Runnable(){

						@Override
						public void run() {

							Log.d("Sajjad","Socket 7000,Dialog popup");
							((MainActivity)activity).dialog=ProgressDialog.show(activity,"Funchat","Image is loading");

						}


					});
				}

			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In Socket 7000,Exception- "+e.getLocalizedMessage());
			FileStatus.writelog("In Socket 7000,Exception- "+e.getLocalizedMessage());

		}


	}

	public void saveUserDetails(){
 
		FileStatus file=new FileStatus(activity);
		file.createFile("profile");
		file.writeToFile("profile",this.userName+" "+this.phoneNumber);
 
		//this file is created to check the status of the activity
 		if(!file.isFile("Activity")){

			file.createFile("Activity");
 			file.writeToFile("Activity","Nothing");
			Log.d("Sajjad","Nothing");

		}
		else
		{
			file.writeToFile("Activity","Nothing");
			Log.d("Sajjad","Nothing");

		}
 
	}

}
