package com.example.text;

import java.io.IOException;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class InternetReceiver extends BroadcastReceiver{

	FileStatus file;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		try{
			//file 
			file=new FileStatus(context);

			//initialize the network
			NetworkCheck network=new NetworkCheck(context);
			Intent intentService=new Intent(context,Service_Socket.class); 
			intentService.putExtra("flag",1);

			//todo list---
			//put a value with the intent to make the service understand that the call for service coming from 
			//internet receiver
			//value would be 1  with it


			Log.d("Sajjad","InternerReceiver- Connectiivity change broadcast receive");
 			Log.d("Sajjad","InternerReceiver- Network is "+network.isConnected());
 			Log.d("Sajjad","InternerReceiver-ServiceStatus- "+file.readfromfile("Service"));
 			Log.d("Sajjad","Service Socket is "+Service_Socket.service);

			FileStatus.writelog("InternerReceiver- Connectiivity change broadcast receive");
			FileStatus.writelog("InternerReceiver- Network is "+network.isConnected());
			FileStatus.writelog("InternerReceiver-ServiceStatus- "+file.readfromfile("Service"));
			FileStatus.writelog("Service Socket is "+Service_Socket.service);


			//check the network
			if(network.isConnected()){

				String temp=file.readfromfile("Service");
				
				if((temp.equals("false")||Service_Socket.service==null)&&file.isFile("ActivityOne")){

					//if true,start the service
					context.startService(intentService);

				}

 			}
			else{

				//no internet 
				Log.d("Sajjad","Service is "+Service_Socket.service);

				//if service_friendimage is not null
				//stop the foreground service of the service_friendImage
 
				if(Service_Socket.service!=null) 
				{
					//stop the foreground service 
					Stop_notification stopService=new Stop_notification(context);
					stopService.start();

				}
				if(ServiceFetch_FriendPic.serviceFetchFriendsPic!=null){
					
					//stop the foreground service
					ServiceFetch_FriendPic.serviceFetchFriendsPic.stopForeground(true);
				
				}

			}
		}catch(Exception e){
			Log.d("Sajjad","Internet receiver-"+e.getLocalizedMessage());
			FileStatus.writelog("Internet receiver-"+e.getLocalizedMessage());
			
		}

	}

}
