package com.example.text;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

public class Loading_FriendsIcon extends Thread{


	String activity_name="";
	Context context;
	ArrayList<String> nameList;
	ArrayList<String> numberList;
	ListView listView;

	public Loading_FriendsIcon(String activity_name, Context context,
			ArrayList<String> name_list, ArrayList<String> number_list,
			ListView list) {
		super();
		this.activity_name = activity_name;
		this.context = context;
		this.nameList = name_list;
		this.numberList = number_list;
		this.listView = list;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try{

			//get the name and the number
			String userNameAndphone=readProfileFile();
			String[] namePhone=new String[2];
			namePhone=userNameAndphone.split(" ");

			Log.d("Sajjad", "LoadingFriends Icon,The username and phone are"+userNameAndphone);
			Log.d("Sajjad","Arraylist-"+numberList.toString());
			FileStatus.writelog("LoadingFriendsIcon,The username and phone are"+userNameAndphone);


			//initialize the file ,using the path specify
			//get the filelist
			//use a loop ,set filelist length as the limit
			//get the file path
			//separate the number from the file name
			//load the image
			//add the image to the ServiceMap using the number as key


			File file=new File(Environment.getExternalStorageDirectory()+"/DCIM/FunChat/");

			Log.d("Sajjad", "Dir is"+file.isDirectory());
			if(file.isDirectory()){
				file.mkdir();
			}

			File [] file_array=new File[file.listFiles().length]; 
			file_array=file.listFiles();
			Log.d("Sajjad", "FileArray-"+file_array.length);
			BitmapSdCard sdCard=new BitmapSdCard();

			for(int i=0;i<file.listFiles().length;i++){

				Bitmap bitmap=sdCard.readImage(file_array[i].getAbsolutePath(), 2, 43, 43);

				if(file_array[i].getName().length()>=11){

					String phoneNumber=file_array[i].getName().substring(0,11);	

					Log.d("Sajjad","LoadingFriends Icon name -"+phoneNumber);

					if(phoneNumber!=null){

						if(Service_Socket.service.mapService.get(phoneNumber)!=null){	

							Service_Socket.service.mapService.get(phoneNumber).setImage(bitmap);

						}
					}

				}

			}

			DisplayContactList display=new DisplayContactList(activity_name,context, nameList, numberList,listView);
			display.start();

		}catch(Exception e){
			FileStatus.writelog("Loading FriendsIcon,Exception -"+e.getLocalizedMessage());
		}

	}
	public String readProfileFile(){

		FileStatus file=new FileStatus(Service_Socket.service);

		return file.readfromfile("profile");

	}

}
