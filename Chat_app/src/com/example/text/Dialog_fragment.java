package com.example.text;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

//public class Dialog_fragment extends DialogFragment implements OnClickListener,OnItemClickListener{
/*

	ListView mylist;
	String listItem;
	CheckedTextView tick;
	View view;
	Populate adapter;
	ArrayList<String> CentralGroupchatArray=new ArrayList<String>();
	ArrayList<String> UserGroupchatArray=new ArrayList<String>();


	ArrayList<String> arrayofcheckitem=new ArrayList<String>();
    EditText GroupName;





	public Dialog_fragment(ArrayList<String> array,ArrayList<String> array2) {
		super();
		this.CentralGroupchatArray = array;
		this.UserGroupchatArray=array2;
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

 

		updatesecondActivityArray();
		checkofsecondActivityarrayWithcentralgroup();
 
		//get the arraylist from the second activity if already set  
		this.arrayofcheckitem=Third_Activity.second.arrayofGroupChat;


		//create the adapter of the listview
		adapter=new Populate(this.getActivity().getApplicationContext(),this.CentralGroupchatArray,this.UserGroupchatArray);


		//inflate the view of the dialog fragment
		view = inflater.inflate(R.layout.dialog, null,false);

		//get the listview of the dialog fragment
		mylist = (ListView) view.findViewById(R.id.list);

		//set the adapter of the listview of the dialog
		mylist.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mylist.setAdapter(adapter);
			}



		});

		//event handling of listview
		mylist.setOnItemClickListener(this);

		//editext for writing the group name 
		GroupName=(EditText)view.findViewById(R.id.groupName);

		//this is the button ok of the dialog fragment
		Button ok=(Button)view.findViewById(R.id.dialog_ok);
		ok.setOnClickListener(this);

		//this is the button cancel of the dialog fragment	
		Button cancel=(Button)view.findViewById(R.id.dialog_cancel);
		cancel.setOnClickListener(this);

		Log.d("Sajjad","Dialog fragment oncreate");

		//set the title of the dialog fragment
		getDialog().setTitle("Have Fun in unison\n Add people to the group");

		//get the groupname if already given from the second Activity which is in onpause state
		Log.d("Sajjad",Third_Activity.second.groupName);

		//set the group name in the editext of the dialog
		this.GroupName.setText(Third_Activity.second.groupName);  	 

		return view;
	}


	public void	updatesecondActivityArray(){


		boolean flag=false;
		for(int i=0;i<this.UserGroupchatArray.size();i++)
		{
			flag=false;
			for(int j=0;j<Third_Activity.second.arrayofGroupChat.size();j++)
			{
				if(UserGroupchatArray.get(i).equals(Third_Activity.second.arrayofGroupChat.get(j)))
				{
					flag=true;
					break;
				}
			}
			if(!flag)
			{
				Third_Activity.second.arrayofGroupChat.add(UserGroupchatArray.get(i));    	
			}

		}


	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

		//get the name of the clicked item of the listview
		listItem=(String) parent.getAdapter().getItem(position);

		//get the checkedtextview from the view 
		tick=(CheckedTextView)view.findViewById(R.id.checkedTextView1);

		//status of the checkmark of the checkedtextview
		boolean check=tick.isChecked();

		Log.d("Sajjad", "The status of the check mark is "+check);

		//update the status of the checkmark

		if(check)
		{
			arrayofcheckitem.remove(listItem);

			tick.setChecked(false);	
		}

		else
		{
			arrayofcheckitem.add(listItem);

			tick.setChecked(true);
		}


		Log.d("Sajjad","The  name  of the click item is "+listItem.toString());

	}









	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if(v==(Button)view.findViewById(R.id.dialog_ok))
		{

			try {

				Log.d("Sajjad","Array size sent to server is"+arrayofcheckitem.size());

				GroupChat_socket.groupChat.outputStream.writeObject(arrayofcheckitem);

				Log.d("Sajjad","Array sent to server");

				//update the header of the activity two
				updateHeader();

				this.dismiss();


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if(v==(Button)view.findViewById(R.id.dialog_cancel)){
			this.dismiss();
		}

	}




	private void updateHeader(){


		Third_Activity.second.groupName=this.GroupName.getText().toString();

		//set the arraylist of the dialog  to the activty groupvhat list
		Third_Activity.second.arrayofGroupChat=this.arrayofcheckitem;

		//ArrayRepetitionCheck();

		String name=Third_Activity.second.groupName+"(";

		for(int i=0;i<Third_Activity.second.arrayofGroupChat.size();i++)
		{
			name+=Third_Activity.second.arrayofGroupChat.get(i);
			if(i!=Third_Activity.second.arrayofGroupChat.size()-1)
			{
				name+="-";
			}

		}

		name+=")";

		//set the header of the fragment
		Messaging.fragments.header.setText(name);

		Log.d("Sajjad",Third_Activity.second.groupName);


	}


	public void checkofsecondActivityarrayWithcentralgroup()
	{
		boolean flag=false;
		for(int i=0;i<Third_Activity.second.arrayofGroupChat.size();i++)
		{
			flag=false;
			for(int j=0;j<this.CentralGroupchatArray.size();j++)
			{

				if(Third_Activity.second.arrayofGroupChat.get(i).equals(CentralGroupchatArray.get(j)))
				{
					flag=true;
					break;
				}
			}

			if(!flag)
			{
				Third_Activity.second.arrayofGroupChat.remove(i);
			}
		}


	}*/

//}
