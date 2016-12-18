package com.example.text;

import java.io.IOException;
import java.util.ArrayList;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class Event_Activity extends Activity implements ListView.OnItemClickListener,PopupMenu.OnMenuItemClickListener{

	ArrayList<String> eventCatagoryList=new ArrayList<String>();
	ArrayList<String> districtList=new ArrayList<String>();
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	Fragment fragment;
	String district="Dhaka";
	Socket_7002 socket7002;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_activity);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		districtList.add("Dhaka");
		districtList.add("Chittagong");

		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,districtList);
		this.getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		ActionBar.OnNavigationListener navigationlistener=new ActionBar.OnNavigationListener() {

			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				// TODO Auto-generated method stub

				district=districtList.get(itemPosition);
				Log.d("Sajjad"," "+district);
				return true;
			}
		};
		this.getActionBar().setListNavigationCallbacks(adapter, navigationlistener);

		socket7002=new Socket_7002(this);
		socket7002.start();

		eventCatagoryList.add("Concert");
		eventCatagoryList.add("Workshop");
		eventCatagoryList.add("Movie");
		eventCatagoryList.add("Drama");
		eventCatagoryList.add("Fest");
		eventCatagoryList.add("Cinema");
		eventCatagoryList.add("Fair");
		eventCatagoryList.add("Examination");

		// Set the adapter for the list view
		mDrawerList.setAdapter(new EventNavigation_adapter(this,eventCatagoryList));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
  	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}
 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
 
		if(item.getItemId()==R.id.action_settings){
 			Toast.makeText(this, "Item click",1000).show();

			PopupMenu menu=new PopupMenu(this,this.findViewById(R.id.action_settings));
			menu.inflate(R.menu.second_pop);
			menu.show();
			menu.setOnMenuItemClickListener(this);
 		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
 
		Log.d("Sajjad","item click- "+adapter.getItemAtPosition(position).toString());

		try {

			socket7002.outputStream.writeUTF(adapter.getItemAtPosition(position).toString());
			socket7002.outputStream.writeUTF(district);
			socket7002.setCatagory(adapter.getItemAtPosition(position).toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In EventActivity Onitemclick(),Exception- "+e.getLocalizedMessage());
		}

		this.mDrawerLayout.closeDrawers();
 	}

	@Override
	protected void onDestroy() {
 
		super.onDestroy();
		try {
			socket7002.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In eventactivity on destroy,Exception- "+e.getLocalizedMessage());
		}

	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub

		if(item.getItemId()==R.id.Event){
			Toast.makeText(this, "Item click",1000).show();
		}

		return true;
	}

}
