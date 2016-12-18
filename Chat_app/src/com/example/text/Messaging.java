package com.example.text;

import java.io.File;
import java.io.IOException;

import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class Messaging extends Fragment implements OnClickListener{

	static Messaging messagingFragment;	
	static LinearLayout linearLayout;
	static EditText textBox;
	Context context; 
	static ScrollView scrollView;
	String friendNumber,
	messageReceive,       
	messageTextBox,  //string from the edittext textbox
	message,
	imageName;
	Bitmap loadImage,//load image from sd card
	addImage;

	int index=0;

	public Messaging(String friendNumber,String message,String imagename)
	{
		this.friendNumber=friendNumber; 
		this.imageName=imagename;
		this.message=message;
	}



	@Override
	public void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
		//get the reference of the fragment 
		messagingFragment=this;

		if(message!=null)
		{
			addViewForMessageReceive(message.toString());
			message=null;
		}

		if(imageName!=null){

			setImageOnActivity();

		}

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		Log.d("Sajjad","Messaging Fragment has been created");
		FileStatus.writelog("Messaging Fragment has been created");

		//inflate the  view for the fragment
		View view=inflater.inflate(R.layout.messaging,null,false);
		//RelativeLayout relativeLayout =(RelativeLayout)view;
		//set the header of the activity
		TextView header=(TextView)view.findViewById(R.id.header);
		header.setText("Texting is fun");

		//scrollview of the messaging fragment  
		scrollView=(ScrollView)view.findViewById(R.id.scrollView1) ; 

		//Linearlayout of the messaging fragment  
		this.linearLayout=(LinearLayout)view.findViewById(R.id.message_one);

		//textbox of the messaging fragment  		
		textBox=(EditText)view.findViewById(R.id.message_two);

		//button to send the text in the messaging gui     
		Button sendText=(Button)view.findViewById(R.id.message_two_buton);
		sendText.setOnClickListener(this);


		return  view; 

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("Sajjad","sent button click");


		// button is clicked and then view of the text from the username would be added to the linearlayout 
		if(v==(Button)getActivity().findViewById(R.id.message_two_buton))
		{
			//set the view of sending text to the linearlayout	
			linearLayout.post(new Runnable(){
				@Override
				public void run() {

					//get the view of sending text 
					View view=addViewForMessageSent();
					linearLayout.addView(view);

				}

			});


			messageTextBox=Messaging.textBox.getText().toString();	//get the text from textbox

			//start the thread to sent the message
			Thread thread=new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub

					try {

						Log.d("Sajjad","Service "+Service_Socket.service.socketClient.outputStream+"chatnumber "+friendNumber);
						//send the friendNumber the message is sent to along with the message
						Service_Socket.service.socketClient.outputStream.writeUTF(friendNumber);
						Service_Socket.service.socketClient.outputStream.writeUTF(messageTextBox);

					} catch (Exception e) {

						Log.d("Sajjad","In Messaging Onclickrun(),Exception- "+e.getLocalizedMessage());
						FileStatus.writelog("In Messaging Onclickrun(),Exception- "+e.getLocalizedMessage());

					}

					Log.d("Sajjad","Usertext- "+messageTextBox+" is sent to server");

				}

			});
			thread.start();

			scrollView.postDelayed(new Runnable(){

				@Override
				public void run() {								//scroll the linearlayout
					// TODO Auto-generated method stub
					scrollView.fullScroll(ScrollView.FOCUS_DOWN);
				}

			},1500);

			Messaging.textBox.setText("");	//clear the textbox
		}

		else{

			//click on the image
			TextView textView=(TextView) v.findViewById(R.id.imagetext);
			Log.d("Sajjad","The text is- "+textView.getText().toString());

			//	Intent intent=new Intent(this.getActivity().getApplicationContext(),ImageDisplayActivity.class);
			//intent.putExtra("filename",imagename);
			//this.getActivity().startActivity(intent);

			//view the image with the default application
			File file=new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera/"+imageName+".jpg");
			Intent intent =new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "image/*");
			this.startActivity(intent);

		}

	}

	public void setImageOnActivity(){

		//get the context of thirdActivity
		context=Third_Activity.thirdActivity.getApplicationContext();

		Thread thread=new Thread(new Runnable(){

			@Override
			public void run() {

				//load the image
				BitmapSdCard sdCard=new BitmapSdCard();
				addImage=sdCard.readImage(imageName, 1, 120, 120);

				Third_Activity.thirdActivity.runOnUiThread(new Runnable(){

					@Override
					public void run() {

						messagingFragment.addImageView(addImage,imageName);
					}
				});

			}

		});
		thread.start();
	}


	public  View addViewForMessageSent()
	{
		Log.d("Sajjad","Inflating the view for sending message");

		LayoutInflater inflater = (LayoutInflater)this.getActivity().getApplicationContext().getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);

		View view=inflater.inflate(R.layout.text_2,null);
		view.setMinimumHeight(40);
		RelativeLayout relativeLayout=(RelativeLayout)view;
		TextView usernameText=(TextView)relativeLayout.getChildAt(0);
		usernameText.setText(messageTextBox);

		return view;
	}
	public void addImageView(Bitmap bitmap,String title){

		Log.d("Sajjad","Receiver ImageView added to Linearlayout");

		this.loadImage=bitmap;
		this.imageName=title;

		this.linearLayout.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				View view=inflateImageLayout(loadImage,imageName);

				linearLayout.addView(view);
				Messaging.scrollView.fullScroll(ScrollView.FOCUS_DOWN);

			}

		});
		this.scrollView.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Messaging.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
			}

		},1200);

	}

	public void addViewForMessageReceive(String amessageReceive){
		Log.d("Sajjad","Receiver View added to Linearlayout");

		//add the view of receiving text to linearlayout
		this.messageReceive=amessageReceive;

		linearLayout.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub

				View view=inflateMessageReceiverView(messageReceive);
				linearLayout.addView(view);

			}

		});
		scrollView.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Messaging.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
			}

		},150);



	}
	private View inflateMessageReceiverView(String message)
	{
		//view for chatperson  inflated

		LayoutInflater inflater = (LayoutInflater)this.getActivity().getApplicationContext().getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.text,null);
		view.setMinimumHeight(40);
		LinearLayout linearlayout=(LinearLayout)view;
		TextView chatPersontext=(TextView)linearlayout.getChildAt(0);
		chatPersontext.setText(message);

		return view;

	}

	private View inflateImageLayout(Bitmap bitmap,String title){
		LayoutInflater inflater = (LayoutInflater)this.getActivity().getApplicationContext().getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.image, null);
		view.setOnClickListener(this);
 		ImageView imageView=(ImageView) view.findViewById(R.id.imageView1);
		TextView text=(TextView)view.findViewById(R.id.imagetext);
	 	imageView.setImageBitmap(bitmap.createScaledBitmap(bitmap,100,100, true));
		text.setText(title);

		return view;
	}

}