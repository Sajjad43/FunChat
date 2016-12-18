package com.example.text;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ScrollView;

public class Text_Image_Receiver extends BroadcastReceiver{


	String message="";
	String imageName="";
	Bitmap bitmap;
	Context receiverContext;


	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		try{

			Log.d("Sajjad", "Text Broadcast intent receive");

			FileStatus.writelog("TextReceiver");

			this.receiverContext=context;
			message=intent.getStringExtra("message");
			imageName=intent.getStringExtra("imagename");

			Log.d("Sajjad", "The message is"+message);

			if(message!=null)
			{
				((Third_Activity)context).messaging.addViewForMessageReceive(message);
				((Third_Activity)context).messaging.scrollView.postDelayed(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Messaging.messagingFragment.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
					}

				},150);

			}

			if(imageName!=null){

				Log.d("Sajjad","Image receiver");
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub

						Thread thread=new Thread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								BitmapSdCard sdCard=new BitmapSdCard();
								bitmap=sdCard.readImage(imageName, 1, 120, 120);

							}
						});
						thread.start();

						try {
							thread.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						((Third_Activity)receiverContext).messaging.addImageView(bitmap,imageName);

					}

				}).start();

			}

		}catch(Exception e){
			FileStatus.writelog("TextReceiver,Exception-"+e.getLocalizedMessage());
		}

	}

}
