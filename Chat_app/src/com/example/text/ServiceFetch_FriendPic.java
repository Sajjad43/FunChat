package com.example.text;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServiceFetch_FriendPic extends Service{


	static ServiceFetch_FriendPic serviceFetchFriendsPic;



	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
 		
		serviceFetchFriendsPic=this;

		Log.d("Sajjad","Service Friends Image running");
		FileStatus.writelog("Service Friends Image running");
		
		//start the detectconnection thread  for the server when the client is disconnected
 		Detect_connection9001 detectConnection=new Detect_connection9001(this);
		detectConnection.start();
 
		//start the fetch_friend thread with the flag_fetch parameter as 2
 		Fetch_friendimage_Socket  fetch=new Fetch_friendimage_Socket(this,2);
		Thread thread=new Thread(fetch);
		thread.start();

		//start the service on the foreground with the notification "Loading friends picture"
 		Notification notification=new Notification(R.drawable.ic_launcher,"Funchat",System.currentTimeMillis());	
		notification.setLatestEventInfo(getApplicationContext(), "Funchat", "Image Loading", null);
		this.startForeground(2, notification);

		return this.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
