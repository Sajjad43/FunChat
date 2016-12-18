package com.example.text;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

public class ImageReceiver extends BroadcastReceiver{

	String imagename;
	Bitmap bitmap;
	Context CoNtext;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		  imagename=intent.getStringExtra("imagename");
		  this.CoNtext=context;
		  Log.d("Sajjad","Image receiver- On");
		   
			
				Thread thread=new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						BitmapSdCard sdCard=new BitmapSdCard();
					//	bitmap=sdCard.readImage(imagename,120,120);
					
						((Third_Activity)CoNtext).runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								((Third_Activity)CoNtext).messaging.addImageView(bitmap,imagename);
								
							}
							
						});
						
						
					 	
					
					}
					
				});
				thread.start(); 
				 
			 		
		 
	
	}

}
