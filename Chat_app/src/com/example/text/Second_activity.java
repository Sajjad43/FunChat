
package com.example.text;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Second_activity extends FragmentActivity implements OnItemClickListener,PopupMenu.OnMenuItemClickListener{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	static Second_activity second;
	Socket_7001 socket;
	String numberListItem;
	String nameListItem,
	username;
	EditText editText;
	ListView listview;
	//Map<String,String> map=new HashMap<String,String>();
	ArrayList<String> nameList;
	ArrayList<String> numberList;
	SecondActivity_Adapter adapter;
	ProgressDialog dialog;
	String[] namePhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		//reference of the secondactivity
		second=this;
		setContentView(R.layout.activity_two);

		// arraylist to store list of name and number
		nameList=new ArrayList<String>();
		numberList=new ArrayList<String>();


		//editext for the search
		editText=(EditText)this.findViewById(R.id.filter);
		listview=(ListView) this.findViewById(R.id.listView1);
		listview.setOnItemClickListener(this);


		//progress Dialog
		//dialog=ProgressDialog.show(this,"Contacts","Loading this");

		Log.d("Sajjad","Second Activity created");
		FileStatus.writelog("Second Activity created");


		//know the service status
		FileStatus file=new FileStatus(this);
		String service_status=file.readfromfile("Service");
		Log.d("Sajjad","Second Activity- The service is "+service_status);
		FileStatus.writelog("Second Activity- The service is "+service_status);


		//get the list of name and number from the database
		//ContactsFromDatabase();
		LoadingContacts_thread contact=new LoadingContacts_thread(this,listview,nameList,numberList,"second");
		contact.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.second, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.action_settings){

			//show the popup menu
			PopupMenu menu=new PopupMenu(this,this.findViewById(R.id.action_settings));
			menu.inflate(R.menu.second_pop);
			menu.show();
			menu.setOnMenuItemClickListener(this);
		}
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//get the reference of the second activity
		second=this;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Log.d("Sajjad","back button pressed");


	}
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
		// TODO Auto-generated method stub

		//get the name and the number of the item clicked
		nameListItem=second.adapter.getItem(position);
		numberListItem=second.numberList.get(position);

		Log.d("Sajjad","Activity_two item-Name:"+nameListItem+" Number: "+numberListItem);

		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {

					Log.d("Sajjad","ServiceSocket is -"+Service_Socket.service);	

					//send the name and number to the server for its presence in the server
					Service_Socket.service.socket7001.outputStream.writeUTF(nameListItem);
					Service_Socket.service.socket7001.outputStream.writeUTF(numberListItem.substring(numberListItem.length()-11,numberListItem.length()));

				} catch (Exception e) {

					Log.d("Sajjad","In SecondActivity Onitemclickrun(),Exception- "+e.getMessage());
					FileStatus.writelog("In SecondActivity Onitemclickrun(),Exception- "+e.getMessage());

				}
			}

		}).start();

	}


	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub

		if(item.getItemId()==R.id.Event){
			//start the event activity
			Intent intent=new Intent(this,Event_Activity.class);
			this.startActivity(intent);

		}

		if(item.getItemId()==R.id.Group_photo){
			//open the gallery to select the image
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent,"Select Image"),1);  

		}

		if(item.getItemId()==R.id.notification){

			//start the notification activity if any notification pending by checking the service Map for notification
			if(Service_Socket.service.notification.size()==0){

				Toast.makeText(getApplicationContext(), "No Notification", 2000).show();
			}
			else{

				Intent intent=new Intent(this,Notification_Activity.class);
				this.startActivity(intent);

			}

		}

		if(item.getItemId()==R.id.photoUpload){

			//get the name and the number
			String userNameAndphone=readProfileFile();
		 	Log.d("Sajjad", "The username and phone are"+userNameAndphone);
			namePhone=new String[2];
			namePhone=userNameAndphone.split(" "); 

		
			Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			String filepath=Environment.getExternalStorageDirectory()+"/DCIM/FunChat/"+namePhone[1]+".jpg";
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filepath)));
			startActivityForResult(intent, 6);

		}
 		//todo list
 		//add an item to the xml file to the popup menu
		//if the item click is Profile picture
		//start a intent for capture a profile picture 
		//use start activityfor result and add a requestCode to it i.e 6
 
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
 
		Log.d("Sajjad","requestCode is"+requestCode);
		
		if(requestCode==1&&resultCode==RESULT_OK&&data!=null)
		{
			//start the group image activity  
			Intent intent=new Intent(this,GroupImageActivity.class);
		 	intent.putExtra("URI",data.getData().toString());		//send the uri of the selected image with the intent
		 	this.startActivity(intent);
		}

		//if requestCode is 6
		// use the filepath,get the image and send the bitmap to the server via fetchFriends Socket
 		if(requestCode==6&&resultCode==RESULT_OK){
			
 			Log.d("Sajjad","Second Activity, profile image capture");
 			Sending_profilePic send=new Sending_profilePic(namePhone[1]);  //parametre is phonenumber
			send.start();

		}

	}
	public String readProfileFile(){

		FileStatus file=new FileStatus(Service_Socket.service);
		return file.readfromfile("profile");


	}


}
