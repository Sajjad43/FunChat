package com.example.text;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageDisplayActivity extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	
	String filename;
	Bitmap bitmap=null;
	ImageView imageview;
	ProgressDialog dialog;
	Activity activity=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.image_display);
		try{


			imageview=(ImageView) this.findViewById(R.id.imageView1Display);
			TextView text=(TextView) this.findViewById(R.id.textViewDisplay);
			this.activity=this;

			//get the file name
			Intent intent=this.getIntent();
			filename=intent.getStringExtra("filename");
			//set the file name 
			text.setText(filename.toString());

			//start the thread for getting the image from sdcard and display it 
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stubzz
					//extract the bitmap from sd card
					BitmapSdCard sdCard=new BitmapSdCard();
					//bitmap=sdCard.readImage(filename,120,120);
					bitmap=sdCard.readImage(filename, 1, 120, 120);
					//set the image
					activity.runOnUiThread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub

							imageview.setImageBitmap(bitmap);
						}

					});

				}

			}).start();

		}catch(Exception e){
			FileStatus.writelog("ImageDisplayActivity, Exception-"+e.getLocalizedMessage());
		}
	}

}
