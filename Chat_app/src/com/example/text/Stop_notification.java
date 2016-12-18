package com.example.text;

import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class Stop_notification extends Thread{

	Context context;

	public Stop_notification(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			
			//update the file 
			FileStatus fileStatus=new FileStatus(context); 
			String serviceStatus=fileStatus.readfromfile("Service");
			
			Log.d("Sajjad","StopNotification- The service is "+serviceStatus);

			//update the service file to false
			fileStatus.writeToFile("Service","false");
			
			//stop the foreground service 
			Service_Socket.service.stopForeground(true);

			//make the Map to null
			Service_Socket.service.mapService=null;

			//close all the connection 
 			Service_Socket.service.socket7001.socket.close();
			Service_Socket.service.socketClient.client_8000.close();
			Service_Socket.service.imageSocket.socket.close();
			Service_Socket.service.imageDownload.socket.close();
			Service_Socket.service.imageUpload.socket.close();

		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In Stop_notification, Onreceive(),Exception- "+e.getLocalizedMessage());
			FileStatus.writelog("In Stop_notification, Onreceive(),Exception- "+e.getLocalizedMessage());
		}
 	}
 
}
