package com.example.text;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ShutdownReceiver extends BroadcastReceiver{
	/** receiver when the mobile is turning off**/
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		
		Log.d("Sajjad","ShutDown Broadcast receive");
		FileStatus.writelog("ShutDown Broadcast receive");
		
		//stop the service 
		Stop_notification stop=new Stop_notification(context);
		stop.start();
		
		
	}

}
