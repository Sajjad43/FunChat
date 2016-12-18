package com.example.text;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.RingtoneManager;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

public class ImageSocket65000 extends Thread{

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	Socket socket;
	DataOutputStream dataOutputStream;
	DataInputStream dataInputStream;


	String userNumber="";
	String imagename="";
	String friendNumber="";
	byte[] data=null;
	private static int i=0;
	Activity activity;
	int dataSize=0; 
	BitmapSdCard sdCard;

	@Override
	public void run() {
		// TODO Auto-generated method stub

		super.run();

		try {

			String userNameAndphone=readfile();
			String[] namePhone=new String[3];
			namePhone=userNameAndphone.split(" ");
			this.userNumber=namePhone[1];

			FileStatus.writelog("ImageSocket65000 running");

			//socket
			socket=new Socket(IP.Ip,65000);
			dataOutputStream=new DataOutputStream(socket.getOutputStream());
			dataInputStream=new DataInputStream(socket.getInputStream());

			//sent userNumber to the server
			dataOutputStream.writeUTF(userNumber);

			//receive  the image
			while(true){

				this.friendNumber=  dataInputStream.readUTF();
				this.imagename= dataInputStream.readUTF();
				long fileSize=dataInputStream.readLong();			


				Log.d("Sajjad",friendNumber+"&"+imagename+"&"+fileSize);

				/**
				//read the data
				dataSize= dataInputStream.readInt();
				data=new byte[dataSize];
				dataInputStream.readFully(data, 0, data.length);
				 **/


				//save the data to the file   
				long size=0;
				int count=0;
				byte[] imageData=new byte[1024];
				sdCard=new BitmapSdCard();

				FileOutputStream fOutputStream=new FileOutputStream(initiateFile(imagename));

				while((count=dataInputStream.read(imageData))>0){


					fOutputStream.write(imageData, 0, count);

					//	sdCard.writeFile(this.friendNumber, imageData, 1);
					//Log.d("Sajjad","DataReceiving,Count is "+count);
					size+=count;
					if(size==fileSize){
						Log.d("Sajjad","Image data Received");
						break;

					}

				}   
				fOutputStream.close();

				if(isActivityVisible()){

					Log.d("Sajjad", "ImageSocket- Activity Status for image is yes");
					/**
					//save the image to the sdcard
					//BitmapSdCard sdCard=new BitmapSdCard();
					//sdCard.writeFile(imagename, data, 1);
					 **/
					//send the broadcast to thirdActivity
					Intent intent=new Intent("com.example.text.Third_Activity1");
					intent.putExtra("imagename",this.imagename);
					Service_Socket.service.sendBroadcast(intent);
					Log.d("Sajjad", "Image intent send broadcast");

				}
				else{
					/**
					//store the bitmap in the sd card
					//BitmapSdCard sdCard=new BitmapSdCard();
					//Bitmap bitmap=sdCard.convertBytetoImage(data);

					//storeinSdcard(bitmap);

					 **/
					//send the notification
					Notificate notify=new Notificate(Service_Socket.service.mapService.get(this.friendNumber).getName(),friendNumber,
							this.imagename,1);
					notify.notifies();

					Log.d("Sajjad", "send notification");
				}

			}


		}catch(Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In ImageSocket run(),Exception- "+e.getLocalizedMessage());

			FileStatus.writelog("In ImageSocket run(),Exception- "+e.getLocalizedMessage());
		} 

	}
	public String readfile(){
		//read the profile file
		FileStatus file=new FileStatus(Service_Socket.service);
		return file.readfromfile("profile");


	}
	public void  storeinSdcard(Bitmap bitmap){
		//store bitmap in the sdcard
		BitmapSdCard sdCard=new BitmapSdCard();
		sdCard.writeImage(bitmap, imagename, 1);
	}

	public boolean isActivityVisible(){

		Log.d("Sajjad","ImageSocket- check Activity status" );
		FileStatus.writelog("ImageSocket- check Activity status");

		String[]  splitName=new String[2];
		boolean status=false;

		FileStatus file=new FileStatus(Service_Socket.service);
		String fileStatus=file.readfromfile("Activity");

		if(fileStatus.equals("Nothing")){

			status=false;
		}
		else{

			splitName=file.readfromfile("Activity").split(" ");

			Log.d("Sajjad",splitName[0]+" "+splitName[1]);

			if(splitName[0].equals("ActivityTwo")){

				if(splitName[1].equals(this.friendNumber))
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
	/**
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}
	/**
	 * @param activity the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String initiateFile(String pimageName){
		String fileName="";
		String filePath="";
		if(pimageName.substring(pimageName.length()-4,pimageName.length()).equals(".JPG")||pimageName.substring(pimageName.length()-4,pimageName.length()).equals(".jpg")){
			fileName=pimageName.substring(0,pimageName.length()-4);

		}
		else{
			fileName=pimageName;
		}

		filePath=Environment.getExternalStorageDirectory()+"/DCIM/Camera/";
		File file=new File(filePath);
		if(file.isDirectory()==false){
			file.mkdirs();
		}

		filePath=filePath+"/"+fileName+".jpg";


		return filePath;	
	}


}
