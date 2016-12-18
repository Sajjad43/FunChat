package com.example.text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class Socket_7002 extends Thread{

	Event_Activity eventActivity;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	String catagory="";
	ArrayList<Event_item> eventList =new  ArrayList<Event_item>();
 	Socket socket;
	String title="",address="",url="",time="",date="";

	public Socket_7002(Event_Activity activity) {
		super();
		this.eventActivity = activity;
		Log.d("Sajjad","Socket 7001 running");
		FileStatus.writelog("Socket 7001 running");
	}

	@Override
	public void run(){
		try {

			socket=new Socket(IP.Ip,7002);
			this.outputStream=new DataOutputStream(socket.getOutputStream());
			this.inputStream=new DataInputStream(socket.getInputStream());


			//get the username and the phonenumber
			String userNameAndphone=readProfileFile();
			Log.d("Sajjad", "The username and phone are"+userNameAndphone);
			String[] namePhone=new String[2];
			namePhone=userNameAndphone.split(" ");
			Log.d("Sajjad","The phonenumber of the client "+ namePhone[1]);

			//send the username to the server
			outputStream.writeUTF(namePhone[1]);

			while(true){

				//fetch the no of item for the event catagory
				int size=inputStream.readInt();

				for(int i=0;i<size;i++){

					//get the information for the event item 
					title=inputStream.readUTF();
					address=inputStream.readUTF( );
					date=inputStream.readUTF();
					time=inputStream.readUTF();
					url=inputStream.readUTF();

					//decode the image
					Bitmap  bitmap=BitmapFactory.decodeResource(eventActivity.getResources(), R.drawable.freedom);
					//create an event object and add it to the eventlist
					eventList.add(new Event_item(title,address,date,time,url,bitmap));
				}

				Log.d("Sajjad","Got the event object in arraylist "+eventList.size());

				eventActivity.runOnUiThread(new Runnable(){

					@Override
					public void run() {

						//create a event fragment and add it to the activity 
						FragmentManager manager=eventActivity.getFragmentManager();
						eventActivity.fragment=new Event_Fragment(catagory,eventList);
						manager.beginTransaction().replace(R.id.content_frame, eventActivity.fragment).commit();
						eventList=new  ArrayList<Event_item>();

					}
				});

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In socket 7002 run(),Exception- "+e.getLocalizedMessage() );
			FileStatus.writelog("In socket 7002 run(),Exception- "+e.getLocalizedMessage());
		}

	}
  
	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public String readProfileFile(){

		FileStatus file=new FileStatus(Service_Socket.service);
		return file.readfromfile("profile");
 	}
}
