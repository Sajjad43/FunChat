package com.example.text;

import java.util.ArrayList;


 
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

 
public class EventFragment_adapter extends ArrayAdapter<Event_item>{
	
	 Context context;
	 ArrayList<Event_item> array=new ArrayList<Event_item>();
	
	 public EventFragment_adapter(Context context, int resource,ArrayList<Event_item> objects) {
		super(context,resource, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.array=objects;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view= inflater.inflate(R.layout.grid_item,null,false);
		TextView textview=(TextView) view.findViewById(R.id.grid_text1);
		TextView textview1=(TextView) view.findViewById(R.id.grid_text2);
		TextView textview2=(TextView) view.findViewById(R.id.grid_text3);
		//Log.d("image",""+ array.get(position)); 	
	 
		/*Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.success);
		
		Bitmap temp = bitmap.createScaledBitmap(bitmap, 100, 200, true);
		
		BitmapDrawable bDraw = new BitmapDrawable(context.getResources(),temp); 
	 */
		//Bitmap bitmap=array.get(position).getBitmap().createScaledBitmap(array.get(position).getBitmap() ,100,150 ,true);
		BitmapDrawable bDraw = new BitmapDrawable(context.getResources(),array.get(position).getBitmap()); 
		 		
		
		
		Log.d("Sajjad","Bitmap in frag is "+ array.get(position).getBitmap());
		  view.setBackground(bDraw);
		 
		 
		 textview1.setText(array.get(position).getAddress().substring(0,6));
		 textview2.setText(array.get(position).getDate());
		 textview.setText(array.get(position).getTitle());
		 
		
		
		
		return view;
	}

}
