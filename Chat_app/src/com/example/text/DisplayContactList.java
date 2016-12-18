package com.example.text;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

public class DisplayContactList extends Thread{

	String activityName="";
	Context context;
	ArrayList<String> NameList;
	ArrayList<String> NumberList;
	ListView listView;
	Activity activity;


	public DisplayContactList(String activity_name, Context context,
			ArrayList<String> name_list, ArrayList<String> number_list,
			ListView list) {
		super();
		this.activityName = activity_name;
		this.context = context;
		NameList = name_list;
		NumberList = number_list;
		this.listView = list;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		Log.d("Sajjad","Display ContactList,Activity name-"+activityName);
		Log.d("Sajjad","Display ContactList,ListView- "+listView);

		FileStatus.writelog("Display ContactList,activity name-"+activityName);
		try{

			if(activityName.equals("group")){
				listView=(ListView) ((GroupImageActivity)context).findViewById(R.id.left_drawer);

				activity=((GroupImageActivity)context);

			}
			else{
				activity=((Second_activity)context);

			}


			if(listView!=null&&activity!=null)
			{
				activity.runOnUiThread(new Runnable(){


					@Override
					public void run() {
						// TODO Auto-generated method stub



						if(activityName.equals("second"))
						{

							//((Second_activity)context).dialog.dismiss();

							//((Second_activity)context).dialog.cancel();
 							((Second_activity)context).adapter=new SecondActivity_Adapter(context,NameList,NumberList);

							((Second_activity)context).listview.setAdapter(((Second_activity)context).adapter);

							Log.d("Sajjad","Display ContactList,listView visible");
							FileStatus.writelog("Display ContactList,listView visible");
						}

						else if(activityName.equals("group")){

							((GroupImageActivity)context).adapter=new GroupImageAdapter(context,NameList,NumberList);

							Log.d("Sajjad","Adapter added to list view for group-"+NameList.size());
						
							FileStatus.writelog("Adapter added to list view for group-"+NameList.size());
							listView.setAdapter(((GroupImageActivity)context).adapter);

						}

					}

				});

			}

		}catch(Exception e){
			FileStatus.writelog("DisplayContact,Exception-"+e.getLocalizedMessage());
		}
	}

}
