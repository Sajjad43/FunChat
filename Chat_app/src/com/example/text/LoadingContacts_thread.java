package com.example.text;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LoadingContacts_thread extends Thread {

	Context context;
	ArrayList<String> nameList,numberList, fetchList;
	ListView listView;
	String activityName="";
	Socket socket;
	int flag;
	DataInputStream inputStream;
	String name="",phoneNumber="";

	public LoadingContacts_thread(Context context,ListView List,ArrayList<String> Name_list,
			ArrayList<String> Number_list,String activity_name){

		this.listView=List;
		this.nameList=Name_list;
		this.numberList=Number_list;
		this.activityName=activity_name;
		this.context=context;

	}

	public LoadingContacts_thread(Context context, ArrayList<String> FetchList, int flag) {
		super();
		this.context = context;
		this.fetchList = FetchList;
		this.flag=flag;

	}


	public LoadingContacts_thread(Context context){
		this.context=context;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		Log.d("Sajjad","Loading Contacts Thread running ");
		FileStatus.writelog("Loading Contacts Thread running ");


		if(Service_Socket.mapService!=null&&Service_Socket.service.mapService==null){
			Service_Socket.service.mapService=new HashMap<String,Person>();
		}

		fetchDatabase();


	}

	public void fetchDatabase(){


		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;



		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);	

		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {

			while (cursor.moveToNext()) {

				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER)));

				if (hasPhoneNumber > 0) {

					// Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

					while(phoneCursor.moveToNext()) {

						String Number = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						String phoneNumber=parsingNumber(Number);

						//if nameList arraylist is not null ,then add the name and number to
						//the arraylist
						Log.d("Sajjad","phone number-"+phoneNumber);
						if(nameList!=null) 
						{
							if(!numberList.contains(phoneNumber)){
								nameList.add(name);
								numberList.add(phoneNumber);
							}

						}

						//todo list----
						//if fetchfriend arraylist is not null, add the number to the fetch friend arraylist
						//---------
						if(fetchList!=null){

							if(!fetchList.contains(phoneNumber)){
								fetchList.add(phoneNumber);

							}
						}

						//if service is not null
						//a problem when app crash ,service stop,we have to wake it up again

						if(Service_Socket.service!=null){

							//todo list
							//create a person object using the name and  number parameters
							//add the object to the service MAp using number as the key
							if(!Service_Socket.service.mapService.containsKey(phoneNumber)){
								Person person=new Person(null,name,phoneNumber);
								Service_Socket.service.mapService.put(phoneNumber,person);
							}

						}

					}



					phoneCursor.close();
				}

			}

		}
		cursor.close();



		if(fetchList!=null){

			Log.d("Sajjad","Loading contacts-FetchFriends Contact arraylist size is"+fetchList.size());
			FileStatus.writelog("Loading contacts-FetchFriends Contact arraylist size is"+fetchList.size());

			SendFriendList sendList=new SendFriendList(context,fetchList,flag);
			sendList.start();

		}

		if(nameList!=null){

			Loading_FriendsIcon loadContactsIcon=new Loading_FriendsIcon(activityName,context,nameList,numberList,listView);
			loadContactsIcon.start();

		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In SecondActivity COntactDatabase,Exception- "+e.getMessage());
			FileStatus.writelog("In SecondActivity COntactDatabase,Exception- "+e.getMessage());
		}
	}
	public String parsingNumber(String number){

		String[]  temp=number.split("[^0-9]");
		String phonenumber="";

		for(int i=0;i<temp.length;i++){
			phonenumber+=temp[i];
		}

		if(phonenumber.length()>=11){

			phonenumber=phonenumber.substring(phonenumber.length()-11,phonenumber.length());
		}

		return phonenumber;
	}

}







