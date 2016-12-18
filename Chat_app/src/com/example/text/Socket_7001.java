package com.example.text;

import java.io.BufferedReader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class Socket_7001 extends Thread{

	String username,phoneNumber;
	Activity activity;
	Socket socket;
	DataOutputStream outputStream;

	public Socket_7001( ) {
		super();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		Log.d("Sajjad","Socket 7001 made");
		FileStatus.writelog("Socket 7001 made");

		try {

			socket = new Socket(IP.Ip,IP.port2);

			if(socket!=null)
			{
				DataInputStream inputStream=new DataInputStream(socket.getInputStream());
				outputStream=new DataOutputStream(socket.getOutputStream());

				//get the name and the number
				String userNameAndphone=readProfileFile();
				Log.d("Sajjad", "The username and phone are"+userNameAndphone);
				String[] namePhone=new String[2];
				namePhone=userNameAndphone.split(" ");

				//send the phone number of the user to the server
				outputStream.writeUTF(namePhone[1]);
				Log.d("Sajjad","The phonenumber of the client "+ namePhone[1]);

				while(true){	

					//get the response from the server 
					String response=inputStream.readUTF();

					if(response.equals("yes"))
					{
						Log.d("Sajjad", "Contacts found");
						Second_activity.second.runOnUiThread(new Runnable(){

							@Override
							public void run() {

								Toast.makeText(Second_activity.second,"Contact found",3000).show();
							}

						});

						Log.d("Sajjad","Start the intent going to Third Activity");

						//start the third activity
						Intent intent =new Intent("com.example.text.Third_Activity");
						intent.putExtra("senderNumber", Second_activity.second.numberListItem.substring(Second_activity.second.numberListItem.length()-11,Second_activity.second.numberListItem.length()));
						intent.putExtra("SenderName",Second_activity.second.nameListItem);
						Second_activity.second.startActivity(intent);
						Second_activity.second.overridePendingTransition(R.anim.slide_right,R.anim.slide_left);

					}

					else
					{
						Log.d("Sajjad", "Contacts not found");
						Second_activity.second.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast.makeText(Second_activity.second,"Contact not found",3000).show();

							}

						});

					}

				}
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In socket 7001 run(),Exception- "+e.getLocalizedMessage() );
			FileStatus.writelog("In socket 7001 run(),Exception- "+e.getLocalizedMessage());
		}

	}

	/**
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}


	public String readProfileFile(){

		FileStatus file=new FileStatus(Service_Socket.service);
		return file.readfromfile("profile");
	}
}

