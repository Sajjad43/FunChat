package com.example.text;
import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;



public class Socket_for_client implements Runnable{

	static Socket_for_client socket_thread;
	String messageReceive,
	chatNumber,
	userNumber,
	friendNumber;
	Activity activity;
	int port;
	Socket client_8000;
	int i=0; 
	TextView usernameText,chatPersonText; 
	LinearLayout linearlayout;   Fragment fragment;
	DataOutputStream outputStream;
	DataInputStream inputStream;

 	public Socket_for_client( )
	{
		Log.d("Sajjad","Socket_for_Client- SocketClient is running");
	}
 
	public void run() {
 
		socket_thread=this;
 
		//read the file to get username and phone number
		String userNameAndphone=readProfileFile();
		String[] namePhone=new String[3];
		namePhone=userNameAndphone.split(" ");
		this.userNumber=namePhone[1];

		try {

			//connect to 8000 port of the server
			client_8000 = new Socket(IP.Ip,IP.port3);
			if(client_8000!=null)
			{
				inputStream=new DataInputStream(client_8000.getInputStream()); 

				//After  getting the port number,close the connection 
				port=Integer.parseInt(inputStream.readUTF());
				Log.d("Sajjad", "Socket_for_Client- Port number to connect  is "+port);
				client_8000.close();

				//a linearlayout variable is created to get the reference of the messaging fragment's linearlayout
				this.linearlayout=Messaging.linearLayout;

				//initiate a new connection with a different port 
				Socket client=new Socket(IP.Ip,port);
				outputStream=new DataOutputStream(client.getOutputStream()); 
				inputStream=new DataInputStream(client.getInputStream()); 

				Log.d("Sajjad","Socket_for_Client- Username is "+this.userNumber+"  sent to server");

				//send the user nuber(phone number) to the server
				outputStream.writeUTF(userNumber);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad", "Socket_for_Client,Exception- "+e.getMessage());

		}


		while(true){

			try {

				//receive the text and the sender phone number		
				friendNumber=inputStream.readUTF();
				messageReceive=inputStream.readUTF();

				Log.d("Sajjad","Socket_for_Client- Sender phone number-"+this.friendNumber);
				Log.d("Sajjad", "Socket_for_Client- The message is "+messageReceive);

				if(isActivityVisible()){

					//send the broadcast intent if the appropiate activity is visible
					Intent intent=new Intent("com.example.text.Third_Activity1");
					intent.putExtra("message",messageReceive);
					Service_Socket.service.sendBroadcast(intent);
					Log.d("Sajjad","Socket_for_Client- send broadcast");

				}
				//read the activity file to know the status
				//if true send broadcast intent,containing the message as well
				//else send the notification with pending intent to third activity,moving to messaging fragment and print the message
				//the notification title would be the senderphonenumber's name ,coming from the map/arraylist in the service 
				//set notification autocancel

				else{

					Notificate notify=new Notificate(Service_Socket.service.mapService.get(this.friendNumber).getName(),friendNumber,
							messageReceive,2);
					notify.notifies();
					Log.d("Sajjad","Socket_for_Client- send notification");

				}

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				Log.d("Sajjad", "Socket_for_Client,Exception- "+e1.getLocalizedMessage());
				break;
			}
		}
	}

	public boolean isActivityVisible(){

		String[] string=new String[2];
 		boolean status=false;

		FileStatus file=new FileStatus(Service_Socket.service);
		Log.d("Sajjad","check Activity status" );

		String fileStatus=file.readfromfile("Activity");

		if(fileStatus.equals("Nothing")){
			status=false;
		}

		else{
			
			string=file.readfromfile("Activity").split(" ");
			Log.d("Sajjad",string[0]+" "+string[1]);
			
			if(string[0].equals("ActivityTwo")){
				
				if(string[1].equals(this.friendNumber))
				{
					status=true;
				}
				else{

					status=false;
				}
			}

		}

		return status;
	}

	public String readProfileFile(){

		FileStatus file=new FileStatus(Service_Socket.service);
		return file.readfromfile("profile");

	}


	public String getChatNumber() {
		return chatNumber;
	}
 
	public void setChatNumber(String chatNumber) {
		this.chatNumber = chatNumber;
	}
 
	public String getUserNumber() {
		return userNumber;
	}
 
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}


	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public Fragment getFragment() {
		return fragment;
	}
	
	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}
}
