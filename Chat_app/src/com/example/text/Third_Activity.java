
package com.example.text;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;

import android.app.ActionBar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class Third_Activity extends Activity implements PopupMenu.OnMenuItemClickListener{ 
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	static Third_Activity thirdActivity;
	ListView listView;
	String userNumber,
	friendNumber,
	friendName,
	message,
	imageName;
	IntentFilter intentFilter;

	Text_Image_Receiver receiver;
	Messaging messaging;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		FileStatus.writelog("ThirdActivity created");

		try{

			//intentfilter of the receiver
			intentFilter=new IntentFilter("com.example.text.Third_Activity1");
			receiver=new Text_Image_Receiver();

			String userNameAndphone=readProfileFile();
			String[] namePhone=new String[2];
			namePhone=userNameAndphone.split(" ");

			setContentView(R.layout.activity_three);

			ActionBar actionbar=this.getActionBar();

			//get the detail from the second activity
			Intent intent=this.getIntent();
			userNumber=namePhone[1];
			friendNumber=intent.getStringExtra("senderNumber");
			friendName=intent.getStringExtra("SenderName");
			this.imageName=intent.getStringExtra("imagename");
			message=intent.getStringExtra("message");

			actionbar.setSubtitle(friendName);
			Log.d("Sajjad","The username is"+ userNumber+"and the second activity is running");

			//set the socket client activity,fragment...
			Service_Socket.service.socketClient.setActivity(this);
			Service_Socket.service.socketClient.setFragment(messaging);
			Service_Socket.service.socketClient.setChatNumber(friendNumber);
			Service_Socket.service.socketClient.setUserNumber(userNumber);
			Service_Socket.service.imageSocket.setActivity(this);

		}catch(Exception e){
			FileStatus.writelog("ThirdActivity,Exception-"+e.getLocalizedMessage());
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("Sajjad","Third Actiivty on Destroy");
		Log.d("Sajjad","The size of array is "+Service_Socket.service.notification.size());
	}

	@Override
	protected void onPause() {

		super.onPause();
		Log.d("Sajjad","Third Activity OnPause");

		//unregister the receiver
		this.unregisterReceiver(receiver);

		this.imageName=null;
		Log.d("Sajjad","UnRegister receiver");

		FileStatus file=new FileStatus(this);

		//erase the activity file 
		if(!file.isFile("Activity")){

			file.createFile("Activity");
			file.writeToFile("Activity","Nothing");
			Log.d("Sajjad","Nothing");

		}
		else
		{
			file.writeToFile("Activity","Nothing");
			Log.d("Sajjad","Nothing");

		}

	}

	@Override
	protected void onResume() {

		super.onResume();
		thirdActivity=this;
		FileStatus file=new FileStatus(this);

		//register the local broadcast receiver
		this.registerReceiver(receiver, intentFilter);

		messaging = new Messaging(this.friendNumber,message,imageName);
		android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		transaction.replace(android.R.id.content, messaging);
		// Commit the transaction
		transaction.commit();

		//write on the activity file,the activity name along with the chatperson name
		if(!file.isFile("Activity")){
			file.createFile("Activity");
			file.writeToFile("Activity","ActivityTwo "+this.friendNumber);
		}
		else
		{
			file.writeToFile("Activity","ActivityTwo "+this.friendNumber);
		}

		Log.d("Sajjad","OnResumeand register receiver");

	}
	public String readProfileFile(){

		FileStatus file=new FileStatus(this);
		String nameAndphone=file.readfromfile("profile");
		return  nameAndphone;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle presses on the action bar items
		if(item.getItemId()==R.id.audio_vedio){

			Log.d("Sajjad", "Hello");

			//inflate the popmenu	
			PopupMenu popupmenu=new PopupMenu(this.getApplicationContext(),this.findViewById(R.id.audio_vedio));
			popupmenu.inflate(R.menu.pop_menu_audio_vedio);
			popupmenu.show();
			popupmenu.setOnMenuItemClickListener(this);

		}
		else if (item.getItemId()==R.id.groupChat)
		{

		}

		return true;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {

		if(item.getItemId()==R.id.Picture)
		{
			//go to gallary image section via intent
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent,"Select Audio"),1);  

		}
		else if(item.getItemId()==R.id.vedio)
		{
			Log.d("Sajjad","Vedio");
		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode==1&&resultCode==RESULT_OK&&data!=null){
			//go to full image activity and transfer all the details
			Intent intent=new Intent(this,SendingImage_Activity.class);
			intent.putExtra("url",data.getData().toString());
			intent.putExtra("chatnumber",this.friendNumber);
			intent.putExtra("chatperson",this.friendName);
			this.startActivity(intent);

		}

	}

}
