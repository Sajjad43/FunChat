package com.example.text;

import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Reboot_receiver extends BroadcastReceiver{

	/** receiver for reboot**/
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		Log.d("Sajjad","Reboot");
		FileStatus.writelog("Reboot");
		
		NetworkCheck networkCheck=new NetworkCheck(context);
	 	if(networkCheck.isConnected()){
	
	 		//read the service file to see is it on or off 
			FileStatus fileStatus=new FileStatus(context);
		 	Log.d("Sajjad",fileStatus.readfromfile("Service"));
			
		}
		else{

			FileStatus fileStatus=new FileStatus(context);
		 	Log.d("Sajjad",fileStatus.readfromfile("Service"));
			
		}
 	 
	}

}
