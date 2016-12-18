package com.example.text;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

 

public class EventNavigation_adapter extends BaseAdapter {
	
	ArrayList<String> array=new ArrayList<String>();
	Context context;
	public EventNavigation_adapter(Context context,ArrayList<String> array){
	      this.array=array;
	      this.context=context;
	
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return  array.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view= inflater.inflate(R.layout.nav_listview,null,false);
		
		TextView textview=(TextView) view.findViewById(R.id.textView1);
		textview.setText(array.get(position));
		
		return view;
	}


}
