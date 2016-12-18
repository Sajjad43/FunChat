package com.example.text;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class GroupImageActivity extends Activity implements OnItemClickListener{
 
	static ArrayList<String> selectedname;
	ArrayList<String> Name_list;
	ArrayList<String> Number_list;
	ImageFragment fragment;
	GroupImageAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.group_image);


		selectedname=new ArrayList<String>();
	 	Name_list=new ArrayList<String>();
		Number_list=new ArrayList<String>();

        Log.d("Sajjad","GroupImageActivity started");
        FileStatus.writelog("GroupImageActivity");
        
        //set the drawerlayout to display friend's list
        DrawerLayout drawer=(DrawerLayout)this.findViewById(R.id.drawer_layout);
		ListView listView=(ListView)this.findViewById(R.id.left_drawer);
		listView.setOnItemClickListener(this);
		
		
		
		//get the uri of the image selected
		Intent intent=this.getIntent();
		String uri=intent.getStringExtra("URI");

		
		 
		
		//load the contacts
		LoadingContacts_thread thread=new LoadingContacts_thread(this,listView,Name_list,Number_list,"group");
		thread.start();

		//set the imagefragment to the navigation drawer
		android.app.FragmentManager manager=this.getFragmentManager();
		FragmentTransaction transaction=manager.beginTransaction();

		fragment=new ImageFragment(uri);
		transaction.replace( R.id.content_frame,fragment ).commit();


	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		// TODO Auto-generated method stub
		Log.d("Sajjad","In groupImageActivity,Item- "+Name_list.get(position));
		
		//handle the selecting of the friend in order to sent the image to the selected names
		if(GroupImageActivity.selectedname.contains(Number_list.get(position)))
		{
			GroupImageActivity.selectedname.remove(Number_list.get(position));
			view.setBackgroundColor(Color.parseColor("#008B8B"));
            
		}
		else{
			
			GroupImageActivity.selectedname.add(Number_list.get(position));
			view.setBackgroundColor(Color.CYAN);

		}
		
		System.out.println(GroupImageActivity.selectedname);
		FileStatus.writelog(GroupImageActivity.selectedname.toString());
		
	}

}
