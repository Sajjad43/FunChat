package com.example.text;

import java.util.ArrayList;


 

 

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Event_Fragment extends Fragment implements OnItemClickListener{
	
	ArrayList<Event_item> array=new ArrayList<Event_item>();
	String eventcatagory;
	public Event_Fragment(String eventcatagory,ArrayList<Event_item> array){
		this.eventcatagory=eventcatagory;
		this.array=array;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Log.d("Sajjad","fragment");
		
		this.getActivity().getActionBar().setSubtitle(eventcatagory);
		 
		View view=inflater.inflate(R.layout.event_frag,null,false);
		GridView gridview=(GridView) view.findViewById(R.id.gridView1);
		
		gridview.setAdapter(new EventFragment_adapter(this.getActivity().getApplicationContext(),R.layout.grid_item,array));

		gridview.setOnItemClickListener(this);
		
		return  view;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Log.d("Sajjad","Item on fragment- "+array.get(position).getUrl());
		Uri webpage=Uri.parse(array.get(position).getUrl());
		Intent intent=new Intent(Intent.ACTION_VIEW,webpage);
		//intent.setType("text/html");
		//set the intent chooser 
		Intent chooser=Intent.createChooser(intent, "Choose browser");
		this.startActivity(chooser);
		
	}

	 

}
