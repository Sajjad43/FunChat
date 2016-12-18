package com.example.text;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class Populate extends ArrayAdapter<String>{
/**we don't need this   **/
	Context context;
	ArrayList<String> CentralGroupchatArray;
	ArrayList<String> userGroupchatArray;
	public Populate(Context context, ArrayList<String> list1,ArrayList<String> list2) {
		super(context,R.layout.list_item,list1);
		// TODO Auto-generated constructor stub
	    this.context=context;
	    this.CentralGroupchatArray=list1;
	    this.userGroupchatArray=list2;
	
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		LayoutInflater inflate=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflate.inflate(R.layout.list_item, null,true);
		 
		CheckedTextView check=(CheckedTextView) view.findViewById(R.id.checkedTextView1);
		
	 	
	    check.setText(CentralGroupchatArray.get(position));
	    
      
	  /* for(int i=0;i<Third_Activity.second.arrayofGroupChat.size();i++)
	    {
	    	
	    	if(CentralGroupchatArray.get(position).equals(Third_Activity.second.arrayofGroupChat.get(i)))
	    	{
	    		check.setChecked(true);
	    	}
	    	
	    	
	    }*/
	    //this part needs a revision
	    for(int i=0;i<userGroupchatArray.size();i++)
	    {
	    	
	    	if(CentralGroupchatArray.get(position).equals(this.userGroupchatArray.get(i)))
	    	{
	             		
	    		check.setChecked(true);
	    	    
	    		
	    	}
	    }
	    
		
		
		return  view;
	}
	
	
	
	
	
	
	
	
	
	
	

}
