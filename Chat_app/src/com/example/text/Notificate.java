
package com.example.text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;


public class Notificate {

	String senderName="",
			senderPhoneNumber="",
			message_file="";
	int type=0;

	public Notificate(String  name,String  number,String message_file,int type){
		this.senderName= name;
		this.senderPhoneNumber= number;
		this.message_file=message_file;
		this.type=type;

	}
	public Notificate(int type){

		this.type=type;
	}

	public void notifies(){

		try{

			//initialize the notification builder
			NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(Service_Socket.service);

			//set the notification layout
			if(type==1){
				//type-1 is image notification	
				mBuilder.setContentText("got a  Image"); 

				NotificationItem notificationItem=new NotificationItem(this.senderName,this.senderPhoneNumber,this.message_file,1,""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"/"+Calendar.getInstance().get(Calendar.MONTH)
						+"/"+Calendar.getInstance().get(Calendar.YEAR));
				mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

				//add to the notification arraylist in Service
				Service_Socket.service.notification.add(notificationItem);		

			}
			else if(type==2){


				NotificationItem notificationItem=new NotificationItem(this.senderName,this.senderPhoneNumber,this.message_file,2,""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"/"+Calendar.getInstance().get(Calendar.MONTH)
						+"/"+Calendar.getInstance().get(Calendar.YEAR));

				mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
				Service_Socket.service.notification.add(notificationItem);		
			}


			//set the notification feature 
			mBuilder.setTicker("Message");
			mBuilder.setContentTitle(Service_Socket.service.notification.size()+" New Messages");

			//friends' name
			mBuilder.setContentText(getNameFromList().toString()) ;
			mBuilder.setNumber(Service_Socket.service.notification.size());  
			mBuilder.setSmallIcon(R.drawable.ic_launcher);
			mBuilder.setWhen(System.currentTimeMillis()); 
			mBuilder.setAutoCancel(true);



			//set the intent
			Intent intent=new Intent(Service_Socket.service,Notification_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			//pending intent
			PendingIntent pending=PendingIntent.getActivity(Service_Socket.service, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
			mBuilder.setContentIntent(pending);

			//start the notification
			NotificationManager manager=(NotificationManager) Service_Socket.service.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(0,mBuilder.build());
		}catch(Exception e){
		
			FileStatus.writelog("Notificate- "+e.getLocalizedMessage());
		}
	}

	public String getNameFromList(){

		//prepare a line which list the name of the friend send the message
 		String [] nameList=new String[Service_Socket.service.notification.size()];
		ArrayList<String> filterName=new ArrayList<String>();
		String sortedNameList=""; //int k=0;
	
		for(int i=0;i<Service_Socket.service.notification.size();i++){

			nameList[i]=Service_Socket.service.notification.get(i).getName();	 	
 		}
 
		for(int i=0;i<nameList.length-1;i++){

			System.out.println(nameList[i]);
			
			for(int j=i+1;j<nameList.length;j++){

				if(nameList[i].equals("")){
					break;
				}
				else if(nameList[i].equals(nameList[j])){
					nameList[j]="";

				}

			}

		}

		 
		for(int i=0;i<nameList.length;i++){
			// System.out.println(name[i]);

			if(!nameList[i].equals("")){


				filterName.add(Service_Socket.service.notification.get(i).getName());

			}

		}

		for(int j=0;j<filterName.size();j++){

			sortedNameList=sortedNameList.concat(filterName.get(j));
			if(j!=filterName.size()-1){

				sortedNameList.concat(",");
			}

		}

		return sortedNameList;

	}

}
