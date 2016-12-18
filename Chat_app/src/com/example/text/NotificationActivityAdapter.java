package com.example.text;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationActivityAdapter extends ArrayAdapter<NotificationItem>{

	ArrayList<NotificationItem> notificationList=new ArrayList<NotificationItem>(); 
	Context context;
	
	//view for the notification list item
	public NotificationActivityAdapter(Context context, ArrayList<NotificationItem> objects) {
		super(context,R.layout.notification_item, objects);
	 	this.context=context;
		this.notificationList=objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.notification_item, null,false);
 		ImageView image=(ImageView)view.findViewById(R.id.noti_image);
 		TextView name=(TextView) view.findViewById(R.id.name);
		TextView text=(TextView) view.findViewById(R.id.file_text);
		TextView date=(TextView) view.findViewById(R.id.date);


		name.setText(notificationList.get(position).getName());
 
		if(notificationList.get(position).getType()==2){//if the notification item is image

			if(notificationList.get(position).getFilename_message().length()>12){
		
				text.setText(notificationList.get(position).getFilename_message().substring(0,9)+"......");
 			}
			else
			{
				text.setText(notificationList.get(position).getFilename_message()+"...");

			}
 		}

		else if(notificationList.get(position).getType()==1){
 
			if(notificationList.get(position).getFilename_message().length()>10){
				text.setText("(image)"+(notificationList.get(position).getFilename_message().substring(0, 7)+"..."));
			}
			else{

				text.setText("(image)"+notificationList.get(position).getFilename_message()+"...");
 
			}

		}
		
		//set the date in the listview
		date.setText(notificationList.get(position).getTime());

 		return  view;
	}
  
}
