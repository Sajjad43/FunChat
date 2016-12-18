package com.example.text;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Service_Socket extends Service{

	static Service_Socket service;
	Socket_for_client socketClient;
	Socket_7001 socket7001;
	ImageSocket65000 imageSocket;
	static Map<String,Person> mapService;
	ArrayList<NotificationItem> notification;
	ArrayList<SendingImageDetails> pendingImageList; 
	Image_downloadsocket imageDownload;
	boolean sendingImageFiles; 
	Image_uploadsocket imageUpload;
	Detect_connection9000 connection9000;
	Detect_connection9001 connection9001;


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("Sajjad","Service on create");
		//get the service reference 
		service=this;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		//write false on the file
		Log.d("Sajjad","In service Socket,Service stopped");

		//the service is stopped and updated on the file 
		FileStatus file=new FileStatus(this);
		file.writeToFile("Service","false");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Todo list---
	 	//get the flag_intent value from the intent	

		notification=new ArrayList<NotificationItem>();
		pendingImageList=new ArrayList<SendingImageDetails>();
		sendingImageFiles=false;
	
		try{
			
			int flag=intent.getIntExtra("flag",3);
			Log.d("Sajjad","Service_Socket,Flag is- "+flag);

			if(flag==2){

				Log.d("Sajjad","Service Socket runnign due to fetchfriend socket");
				FileStatus.writelog("Service Socket runnign due to fetchfriend socket");

			}
			else{
				Log.d("Sajjad","Service Socket runnign due to internet receiver");
				FileStatus.writelog("Service Socket runnign due to internet receiver");
			}


			//start the service 
			service=this;

			Log.d("Sajjad","Service- Service onCommand");
			FileStatus.writelog("Service- Service onCommand");
		
			//start the executor for 7 thread
			ExecutorService executor =Executors.newFixedThreadPool(8);
 
			FileStatus file=new FileStatus(this);

			//check where Activity one is created or not

			if(file.isFile("ActivityOne"))
			{
				if(!file.isFile("Service"))
				{ 
					//update on the service file
					file.createFile("Service");
					file.writeToFile("Service","true");

				}
				else{
					//update on the service file
					file.writeToFile("Service","true");

				}


				if(mapService==null)
				{
					//get the map for contacts
					mapService=new HashMap<String,Person>();
					LoadingContacts_thread  loadingContacts=new LoadingContacts_thread(this.getApplicationContext());
					executor.execute(loadingContacts);

				}

				//create all the socket threads
				socket7001=new Socket_7001();
				executor.execute(socket7001);

				socketClient=new Socket_for_client();
				executor.execute(socketClient);

				connection9000=new Detect_connection9000();
				executor.execute(connection9000);

				//todo list----
				//if flag_intent is 2 ,don't create the thread

				if(flag==1){
					connection9001=new Detect_connection9001(this);
					executor.execute(connection9001);
 				}

				//-----------
				imageSocket=new ImageSocket65000();
				executor.execute(imageSocket);

				imageUpload=new Image_uploadsocket();
				 executor.execute(imageUpload);

				imageDownload=new Image_downloadsocket();
				executor.execute(imageDownload);

				//todo list----
				//if flag_intent is  1,start the fetch_friend socket with flag_fetch parameter as 1
				//------ 

				if(flag==1){
					
					Fetch_friendimage_Socket  fetch=new Fetch_friendimage_Socket(this,1);
					Thread thread=new Thread(fetch);
					thread.start();

				}
 
				//set the notification in foreground
				Notification notification=new Notification(R.drawable.ic_launcher,"FunChat",System.currentTimeMillis());
				notification.setLatestEventInfo(service, "FunChat","Start Application",null);
				this.startForeground(100, notification);		 

			}

		}catch(Exception e){
			Log.d("Sajjad", "ServiceSocket-"+e.getLocalizedMessage());
 			FileStatus.writelog("ServiceSocket-"+e.getLocalizedMessage());
		}
 		//non-sticky mean that the service will not restart again once it stop abruptly if likely
		return this.START_NOT_STICKY;
	}
	 
 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
 
}
