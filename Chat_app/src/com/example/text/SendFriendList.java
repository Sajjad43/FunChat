package com.example.text;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class SendFriendList extends Thread{

	Socket socket;
	ArrayList<String> numberList;
	DataOutputStream outputStream;
	DataInputStream inputStream;
	Context  context;
	int flag;
	byte[]	data;
	int byteSize;	

	public SendFriendList(Context context , ArrayList<String> list,int flag) {

		super();
		this.socket = socket;
		this.numberList = list;
		this.context=context;
		this.flag=flag;
		this.inputStream=inputStream;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {

			String userNameAndphone=readProfileFile();
			Log.d("Sajjad", "The username and phone are"+userNameAndphone);
			String[] namePhone=new String[2];
			namePhone=userNameAndphone.split(" ");
			FileStatus file=new FileStatus(context);


			Socket socket=new Socket(IP.Ip,3000);
			this.inputStream=new DataInputStream(socket.getInputStream());
			this.outputStream=new DataOutputStream(socket.getOutputStream());

			//send the number to the server with the port 3000
			outputStream.writeUTF(namePhone[1]);
			//send the the list size to the server
			outputStream.writeInt(numberList.size());

			//send the number
			for(int i=0;i<numberList.size();i++){
				outputStream.writeUTF(numberList.get(i));
			}

			//get the list size  
			//use a loop
			//get the number along with the image 
			//if yes ,get the image, store the photo on the funchat folder along with the number
			//remove the number to a arraylist 
			//loop--
			//read the imagelist from the server
			int listsize=inputStream.readInt();

			for(int j=0;j<listsize;j++){
				//read the number
				String friendsImageFileName=inputStream.readUTF(); 

				//read the data size
				this.byteSize=inputStream.readInt();
				//initialoze the byte array
				this.data=new byte[byteSize];
				//read the byte array
				inputStream.readFully(data);

				Log.d("Sajjad","Fetch_friendSocket,datasize is-"+data.length+"and data is-"+data);

				//remove the number from the list
				if(friendsImageFileName.length()>=11){

					String mobileNumber=friendsImageFileName.substring(0,11);
					this.numberList.remove(mobileNumber);

				}
				else{
					this.numberList.remove(friendsImageFileName);
				}

				String imageFile=(namePhone[1]+".jpg");

				if(!friendsImageFileName.equals(imageFile)){

					BitmapSdCard sdCard=new BitmapSdCard();
					/*
					convert the byte array to  bitmap
				 	BitmapFactory.Options option=new BitmapFactory.Options();
					option.inSampleSize=4;
 					BitmapFactory factory=new BitmapFactory();
					convert the byte array to bitmap
					Bitmap bitmap=factory.decodeByteArray(data, 0,data.length,option);
					 */
					Bitmap bitmap=sdCard.convertBytetoImage(data);
					Log.d("Sajjad","InSendList,bitmap is-"+bitmap);
					//store the bitmap
					sdCard.writeImage(bitmap, friendsImageFileName, 2);

					//store.writeFriendImage(bitmap, imagename);

				}	

			}
			//store the arraylist on a file so that on the next time we can get to know the list of number whose photos are pending to fetch from server
			file.writeFriendList("FriendList",numberList);

			//if flag==2
			//dismiss the progressDialog using MainActivity as the reference 
			//finish the activity

			//if the call of this object is from Service_Friend i.e flag==2,
			//start the service_socket and then 
			//then move to second activity

			//here,put a value with the intent to make the service understand that the call for service coming from 
			//fetch_friend
			//value would be 2 

			//else do nothing 

			if(flag==2& MainActivity.main!=null)
			{

				if(file.isFile(("Service"))){

					if(file.readfromfile("Service").equals("true")){

					}
					else
					{ 
						Intent intentService=new Intent(context,Service_Socket.class);
						intentService.putExtra("flag",2);
						(MainActivity.main).startService(intentService);

					}
				}
				else{

					Intent intentService=new Intent(context,Service_Socket.class);
					intentService.putExtra("flag",2);
					(MainActivity.main).startService(intentService);

				}
				//stop the fetchfriendsImage service
				ServiceFetch_FriendPic.serviceFetchFriendsPic.stopForeground(true);

				//start the second Activity
				MainActivity.main.runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub

						Intent intent=new Intent(context,Second_activity.class);

						MainActivity.main.dialog.dismiss();
						MainActivity.main.dialog.cancel();

						Log.d("Sajjad","FetchfriendSocket,remove foreground & move to second activity");

						MainActivity.main.startActivity(intent);
						MainActivity.main.overridePendingTransition(R.anim.slide_left,R.anim.slide_right);
						MainActivity.main.finish();

					}

				});

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			Log.d("Sajjad", "SendFriendList,Exception-"+e.getLocalizedMessage());
			FileStatus.writelog("SendFriendList,Exception-"+e.getLocalizedMessage());
		}
	}

	public String readProfileFile(){

		FileStatus file=new FileStatus(context);
		return file.readfromfile("profile");
	}

}
