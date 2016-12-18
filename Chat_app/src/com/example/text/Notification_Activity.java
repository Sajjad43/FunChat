package com.example.text;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


public class Notification_Activity extends Activity implements OnItemClickListener {

	ListView listView;
	NotificationActivityAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.notify_activity);

		//set the actionbar title
		ActionBar actionBar=this.getActionBar();
		actionBar.setSubtitle("Notification");

		Log.d("Sajjad","NotificationActivity");
		FileStatus.writelog("NotificationActivity");

		//listview of the notication
		listView=(ListView)this.findViewById(R.id.listView_notify);
		listView.setOnItemClickListener(this);



	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//if there is no elements in the arraylist ,then return back to second Activity 
		try{
			if(Service_Socket.service.notification.size()==0){
				this.finish();
			}
			else{
				//set the adapter to listView
				if(Service_Socket.service!=null){
				
					Log.d("Sajjad","Notification array size is"+Service_Socket.service.notification.size());
					FileStatus.writelog("Notification array size is"+Service_Socket.service.notification.size());
					//set the adapter to the listview
					adapter=new NotificationActivityAdapter(this,Service_Socket.service.notification); 
					listView.setAdapter(adapter);

				}
			}

		}catch(Exception e){
			FileStatus.writelog("NotificationActivity-Exception :"+e.getLocalizedMessage());
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if(item.getItemId()==R.id.home){

			Intent intent=new Intent(this,Second_activity.class);
			this.startActivity(intent);
 
			//if there is unseen notification left ,notify again 
			if(Service_Socket.service.notification.size()!=0)
			{
				 
				Notificate notifi=new Notificate(3);
				notifi.notifies();
  			}
			//close the notification activity
			this.finish();  

		}

		return true;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		//get the name of the item clicked in the list view
		TextView textView=(TextView) view.findViewById(R.id.name);
		String name=textView.getText().toString();
 		Log.d("Sajjad","In Notification Activity,Name is -"+name); 

		
 		NotificationItem notificationItem= (NotificationItem) parent.getItemAtPosition(position);

		if(notificationItem!=null){
  			//if type is 1 ,then it is image
			if(notificationItem.getType()==1){

				//start third activity 
				Intent intent =new Intent(this,Third_Activity.class);
				intent.putExtra("imagename",notificationItem.getFilename_message());
				intent.putExtra("SenderName",Service_Socket.service.mapService.get(notificationItem.getPhoneNumber()).getName());
				intent.putExtra("senderNumber", notificationItem.getPhoneNumber());
				//remove the notificationItem from the service notificationList
				Service_Socket.service.notification.remove(notificationItem);
				this.startActivity(intent);
 
			}
			//if type is 2,then it is message
 			else if(notificationItem.getType()==2){

				//start  third activity
				Intent intent =new Intent(this,Third_Activity.class);
				intent.putExtra("senderNumber",notificationItem.getPhoneNumber());
				intent.putExtra("SenderName",Service_Socket.service.mapService.get(notificationItem.getPhoneNumber()).getName());
				intent.putExtra("message",notificationItem.getFilename_message());

				//remove the notificationItem from the service notificationList
				Service_Socket.service.notification.remove(notificationItem);

				this.startActivity(intent);


			}


		}
	}

	public NotificationItem getNotitype(String name){

		for(int i=0;i<Service_Socket.service.notification.size();i++){


			if(name.equals(Service_Socket.service.notification.get(i).getName())){

				return Service_Socket.service.notification.get(i);

			}

		}

		return null;
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();


		if(Service_Socket.service.notification.size()!=0)
		{
			Notificate notifi=new Notificate(3);
			notifi.notifies();
		}

	}



}
