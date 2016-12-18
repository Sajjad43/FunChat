package com.example.text;

import java.util.ArrayList;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SecondActivity_Adapter extends ArrayAdapter<String>{


	Context context;
	ArrayList<String> friendsNameList;
	ArrayList<String>phoneNumberList;
	ArrayList<String> backupList=new ArrayList<String>();
	SecondActivity_Adapter adapter;

	public SecondActivity_Adapter(Context context,ArrayList<String> list,ArrayList<String>number) {
		super(context,R.layout.item,list);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.friendsNameList=list;
		this.phoneNumberList=number;
		backupList.addAll(list);
		adapter=this;

	}


 	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		//inflate the view of the listitem
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 		View view=inflater.inflate(R.layout.item,parent,false);
 		view.setMinimumHeight(25);
		view.setKeepScreenOn(true);

 		ImageView image =(ImageView)view.findViewById(R.id.item_one);
		TextView name=(TextView)view.findViewById(R.id.item_two);
		TextView status=(TextView)view.findViewById(R.id.item_four);
		
		//set the bitmap of the person object to the imageview
        Bitmap bitmap=Service_Socket.mapService.get(phoneNumberList.get(position)).getImage();
		
        if(bitmap!=null){
			image.setImageBitmap(bitmap);
				
		}
     	
		name.setText(friendsNameList.get(position));
		
 
		if(position!=0&&position!=2)	
		{
			ImageView online=(ImageView)view.findViewById(R.id.item_three);
			online.setImageResource(R.drawable.online);
			status.setText("online/offline");

		}	
 
		return  view;
	}

	public void filter(String condition){

		friendsNameList.clear();
		Log.d("Sajjad",""+this.backupList.size());
		Log.d("Sajjad",condition);
		int count=0;
		if(condition.length()>0)
		{
			for(int i=0;i<backupList.size()-100;i++){
				if(backupList.get(i)!=null) 
				{
					if(backupList.get(i).toString().charAt(0)==condition.toUpperCase().charAt(0))
					{
						friendsNameList.add( backupList.get(i).toString());
						Log.d("Sajjad",backupList.get(i).toString());
						count++;
					}
				}
			}

			//	Log.d("Sajjad","Good");
			//Second_activity.second.adapter.notifyDataSetChanged();
			//Log.d("Sajjad","Bad");

			Second_activity.second.runOnUiThread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					//Second_activity.second.adapter.notifyDataSetChanged();
					Second_activity.second.listview.setAdapter(adapter);

				}

			});


			Log.d("Sajjad",""+count);
		}
	}


}
