package com.example.text;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{


	EditText userName,phoneNumber;
	static MainActivity main;
	Button login;
	FileStatus file;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		main=this;

		//check the activity status
		file=new FileStatus(this);
		Log.d("Sajjad","MainActivity- The activity status is"+file.isFile("ActivityOne"));
		FileStatus.writelog("MainActivity- The activity status is"+file.isFile("ActivityOne"));

		Log.d("Sajjad","The ip is -"+IP.Ip);

		//create a folder for the friends image icon
		File imageFolder=new File(Environment.getExternalStorageDirectory()+"/DCIM/FunChat/");
		imageFolder.mkdirs();

		//check the network
		NetworkCheck network=new NetworkCheck(this);
		Log.d("Sajjad","MainActivity- The network is- "+network.isConnected());
		FileStatus.writelog("MainActivity- The network is- "+network.isConnected());



		if(network.isConnected())
		{
			if(!file.isFile("ActivityOne"))
			{
				setContentView(R.layout.activity_main);
				main=this;
				Log.d("Sajjad","MainActivity- Activity one running");

				//initialize the login button and ueredittext
				login=(Button)MainActivity.main.findViewById(R.id.button1); 
				phoneNumber=(EditText)this.findViewById(R.id.editText2);
				userName=(EditText)this.findViewById(R.id.editText1);
				login.setOnClickListener(this);

			}
			else{

				String  isService="";
				//decision of starting a service

				if(file.isFile(("Service"))){

					//know the service status
					isService=file.readfromfile("Service");
					Log.d("Sajjad","MainActivity- The service is "+isService);

					if(isService.equals("true")&&Service_Socket.service!=null){

						//service is ruuning and start the second activity	
						Intent intent=new Intent(this,Second_activity.class);
						this.startActivity(intent);
						this.finish();

					}
					else
					{   
						AlertDialog.Builder builder=new AlertDialog.Builder(this);
						builder.setTitle("Fun_CHat");
						builder.setMessage("Click ok for the service to start");

						builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub

								//start the second activity
								Intent intent =new Intent(main.getApplicationContext(),Second_activity.class);
								main.startActivity(intent);

							}
						});
						builder.create().show();



						
						
						
						//no service running,so start a service
						Intent intentService=new Intent(this,Service_Socket.class);
						this.startService(intentService);

						this.finish();

					}
				}
				else{
 
					//start the service, if there is no service 
					Intent intentService=new Intent(this,Service_Socket.class);
					this.startService(intentService);
 
				}
 			}
		}
		else{

			//No internet connection
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("Fun_CHat");
			builder.setMessage("Turn on your internet and get back to funchat");

			builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					main.finish();
				}
			});
			builder.create().show();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
 
		Log.d("Sajjad","MainActivity,Username- "+userName.getText().toString()+"and Phone- "+phoneNumber.getText().toString());

		Socket_7000 socket=new Socket_7000(this,userName.getText().toString(),phoneNumber.getText().toString());
		socket.start();

 	}
 
}
