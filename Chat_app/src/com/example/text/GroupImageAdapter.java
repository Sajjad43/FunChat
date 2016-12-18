package com.example.text;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupImageAdapter extends ArrayAdapter<String>{

	ArrayList<String> name;
	ArrayList<String>number;
	Context context;
	public GroupImageAdapter(Context context, ArrayList<String> name,ArrayList<String> number) {
		super(context,R.layout.itemnavigation, name);
		// TODO Auto-generated constructor stub
		this.name=name;
		this.context=context;
		this.number=number;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.itemnavigation, null,false);

		ImageView image=(ImageView)view.findViewById(R.id.DrawerImage);

		Bitmap bitmap=Service_Socket.mapService.get(number.get(position)).getImage();
		if(bitmap!=null){
			image.setImageBitmap(bitmap);
		}



		if(GroupImageActivity.selectedname.contains(number.get(position)))
		{
			TextView text=(TextView) view.findViewById(R.id.text);
			text.setText(name.get(position));
			view.setBackgroundColor(Color.CYAN);

		}
		else{

			TextView text=(TextView) view.findViewById(R.id.text);
			text.setText(name.get(position));
			view.setBackgroundColor(Color.parseColor("#008B8B")); 

		}

		return  view;
	}




}
