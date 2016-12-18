package com.example.text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Environment;
import android.util.Log;

public class Image_downloadsocket extends Thread{

	Socket socket;
	String imageName="";
	String senderNumber="";
	byte[] data;
	String senderName;
	File file;
	int no=0;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	boolean isFileWrite=false;
	
	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		super.run();


		int nofile;

		Log.d("Sajjad","Image download");
		FileStatus.writelog("Image download");

		try {
			//initialize the socket
			socket=new Socket(IP.Ip,6001);
			inputStream=new DataInputStream(socket.getInputStream());
			outputStream=new DataOutputStream(socket.getOutputStream());


			//read the mobile user name and number from the File 
			String userNameAndphone=readProfileFile();
			String[] namePhone=new String[2];
			namePhone=userNameAndphone.split(" ");	

			//send the name and the number of the mobile user to server
			outputStream.writeUTF(namePhone[0]);
			outputStream.writeUTF(namePhone[1]);

			Log.d("Sajjad",namePhone[0]+"and"+namePhone[1]+"are sent" );


			while(true){

				//send the mobile number to the server
				outputStream.writeUTF(namePhone[1]);
				//Log.d("Sajjad","username sent to server");
				
				//read the no of image file from the server
				nofile=inputStream.readInt();

				if(nofile>0){

					for(int i=0;i<nofile;i++){

						//read the sendername and the filename
						senderNumber=inputStream.readUTF();
						imageName=inputStream.readUTF();
						long dataSize=inputStream.readLong();

						saveDataToFile(dataSize);


						

						if(isActivityVisible()){

							Log.d("Sajjad", "ImageSocket- Activity Status for image is yes");
							FileStatus.writelog("ImageSocket- Activity Status for image is yes");
 
							//send local broadcast intent to receiver in third activity
							Intent intent=new Intent("com.example.text.Third_Activity1");
							intent.putExtra("imagename",this.imageName);
							Service_Socket.service.sendBroadcast(intent);

							Log.d("Sajjad", "Image intent send broadcast");
							FileStatus.writelog("Image intent send broadcast");

						}
						else{

 
							if(Service_Socket.service.mapService.get(this.senderNumber).getName()!=null){

								Notificate notifi=new Notificate(Service_Socket.service.mapService.get(this.senderNumber).getName(),senderNumber,
										this.imageName,1);
								notifi.notifies();

								Log.d("Sajjad", "IMage Download,send notification");
								FileStatus.writelog("IMage Download,send notification");
							}

						}
						
						
						
						/**	
						//read the byte array size and byte data
						int datasize=inputStream.readInt();
						data=new byte[datasize];
						inputStream.readFully(data);

						Log.d("Sajjad","In download image,ImageFile-"+imageName+"sent by "+senderNumber);

						//ActivityStatus() is used to check whether activity thread is visible or not .If visible broadcast the intent so that image 
						//can be placed on the activity without doing nothing .If not visible ,send a notification

						if(isActivityVisible()){

							Log.d("Sajjad", "ImageSocket- Activity Status for image is yes");
							FileStatus.writelog("ImageSocket- Activity Status for image is yes");

							//save the bitmap to sd card
							BitmapSdCard sdCard=new BitmapSdCard();
 							storeinSdcard(sdCard.convertBytetoImage(data));


							//send local broadcast intent to receiver in third activity
							Intent intent=new Intent("com.example.text.Third_Activity1");
							intent.putExtra("imagename",this.imageName);
							Service_Socket.service.sendBroadcast(intent);

							Log.d("Sajjad", "Image intent send broadcast");
							FileStatus.writelog("Image intent send broadcast");

						}
						else{

							//save the bitmap to sd card
							BitmapSdCard sdCard=new BitmapSdCard();
							storeinSdcard(sdCard.convertBytetoImage(data));

							if(Service_Socket.service.mapService.get(this.senderNumber).getName()!=null){

								Notificate notifi=new Notificate(Service_Socket.service.mapService.get(this.senderNumber).getName(),senderNumber,
										this.imageName,1);
								notifi.notifies();

								Log.d("Sajjad", "IMage Download,send notification");
								FileStatus.writelog("IMage Download,send notification");
							}


						}**/

					} 
				}
				 

			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","Imagedownload exception"+e.getLocalizedMessage());
			FileStatus.writelog("Imagedownload exception"+e.getLocalizedMessage());
			Log.d("Sajjad", "file is "+file);
		
			if(isFileWrite==true){
				if(file!=null){
					
				boolean status=file.delete();
				
				Log.d("Sajjad",file.getName()+" is getting deleted and status- "+status);
				
				}
				
			}
		
		
		
		}
	}
	public void  saveDataToFile(long size){

		String filePath="";
		String nameModified="";
		int count=0;
		long length=0;
		data=new byte[1024];

		if(imageName.substring(imageName.length()-4,imageName.length()).equals(".JPG")||imageName.substring(imageName.length()-4,imageName.length()).equals(".jpg")){

			nameModified=imageName.substring(0,imageName.length()-4);

		}
		else{
			nameModified=imageName;
		}

		filePath=Environment.getExternalStorageDirectory()+"/DCIM/Camera/";

		//set the file and file path
		 file=new File(filePath);
		if(file.isDirectory()==false){
			file.mkdirs();
		}
		filePath=filePath+"/"+nameModified+".jpg";

		file=new File(filePath);
		isFileWrite=true;
		try {
			FileOutputStream fOutputStream= new FileOutputStream(file);

			while((count=inputStream.read(data))>0){

				fOutputStream.write(data, 0, count);
				length+=count;
				if(length==size){
					break;
				}

			}
			
			fOutputStream.close();
			isFileWrite=false;
 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}

	public String readProfileFile(){

		FileStatus file=new FileStatus(Service_Socket.service);
		return file.readfromfile("profile");

	}
	public void  storeinSdcard(Bitmap bitmap){

		//save the image file		
		BitmapSdCard sdCard=new BitmapSdCard();
		sdCard.writeImage(bitmap, imageName, 1);
	}

	public boolean isActivityVisible(){

		Log.d("Sajjad","Imagedownload- check Activity status");

		String[] string=new String[2];
		boolean status=false;

		//intialize the file
		FileStatus file=new FileStatus(Service_Socket.service);
		String file_status=file.readfromfile("Activity");

		Log.d("Sajjad","ImageDownload,File status -"+file_status);

		if(file_status.equals("Nothing")){
			status=false;
		}
		else{

			string=file.readfromfile("Activity").split(" ");

			Log.d("Sajjad",string[0]+" "+string[1]);

			if(string[0].equals("ActivityTwo")){
				if(string[1].equals(this.senderNumber))
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
}
