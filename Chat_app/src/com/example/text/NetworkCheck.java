package com.example.text;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {

	Context context;
	
	public NetworkCheck(Context context){
		this.context=context;
	
	}
	
	public boolean isConnected(){
		
		boolean status=false; 
		//get the system service for internet
		ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if(manager!=null){
			//get all the network connection info 
			NetworkInfo [] Ninfo=manager.getAllNetworkInfo();
			
			if(Ninfo!=null)
			{
				for(int i=0;i<Ninfo.length;i++){
					if(Ninfo[i].getState()==NetworkInfo.State.CONNECTED){
						//if connected ,set the status as true
						status=true;
						break;
					}
				}
			}	
		}
		else{
			status=false;
		}
	 

      return status;
	}
}
