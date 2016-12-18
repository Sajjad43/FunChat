package com.example.text;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.net.Uri;
import android.util.Log;

public class Image_uploadsocket extends Thread{

	Socket socket;
	DataOutputStream outputStream;


	public Image_uploadsocket() {
		super();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		this.initiateConnection();
		 
	}

	
	public void sendPendingImages(){
		
		Service_Socket.service.sendingImageFiles=true;

		while(Service_Socket.service.pendingImageList.size()>0){

			for(int i=0;i<Service_Socket.service.pendingImageList.size();i++)
			{
				SendingImageDetails x=Service_Socket.service.pendingImageList.get(i);

				if(x.statusFileWriting==0){

					sendImage(x);
					break;

				}
				else if(x.statusFileWriting==1){
					break;
				}
				else if(x.statusFileWriting==2){
					Service_Socket.service.pendingImageList.remove(x);
					break;

				}

			}

		}

		Service_Socket.service.sendingImageFiles=false;


	}
	
	
	public void initiateConnection(){
		Log.d("Sajjad","ImageUpload");
		FileStatus.writelog("ImageUpload socket");


		//set the socket with ip and port
		try {
			socket=new Socket(IP.Ip,6000);
			outputStream=new DataOutputStream(socket.getOutputStream());

			//read the username and number from the "profile" file
			String userNameAndphone=readProfileFile();
			Log.d("Sajjad", "The username and phone are"+userNameAndphone);
			String[] namePhone=new String[2];
			namePhone=userNameAndphone.split(" ");
			
			//send the name and the number of the mobile user to server
			outputStream.writeUTF(namePhone[0]);
			outputStream.writeUTF(namePhone[1]);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}



	public void sendImage(SendingImageDetails sendingDetails){

		Log.d("Sajjad","ImageUpload");
		FileStatus.writelog("ImageUpload socket");
		sendingDetails.statusFileWriting=1;
		
		try {
	 
			InputStream cinputStream=Service_Socket.service.getContentResolver().openInputStream(Uri.parse(sendingDetails.url));
 
			Log.d("Sajjad","sent  name and number to server in ImageUpload");
 
			outputStream.writeUTF(sendingDetails.friendName);
			outputStream.writeLong(sendingDetails.size);
			outputStream.writeUTF(sendingDetails.fileName);

			int count=0;
			long size=0;
			byte[] imageData=new byte[1024];


			while((count=cinputStream.read(imageData))>0){

				outputStream.write(imageData,0,count);
				//Log.d("Sajjad","ImageSending,Count is "+count);

				size+=count;
				if(size==sendingDetails.size){
					Log.d("Sajjad","send image data to server---"+size+" & "+count);
					break;
				}
			}

			Log.d("Sajjad","Left-"+cinputStream.available());
			cinputStream.close();

			sendingDetails.statusFileWriting=2;



		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","Imageupload exception");
			FileStatus.writelog("Imageupload exception"+e.getLocalizedMessage());
			Service_Socket.service.sendingImageFiles=false;

		} 
	}


	public String readProfileFile(){

		//read the mobile user name and number from the File 
		FileStatus file=new FileStatus(Service_Socket.service);
		return file.readfromfile("profile");


	}

}
