package com.example.text;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.ImageView;

public class LoadImage_SDcard extends Thread{

	ImageView image=null;
	Uri uri=null;
	private final WeakReference<ImageView> imageViewReference;
	Activity activity;
	int scale=0;
	Bitmap bitmap=null;
	int activityNo;

	public LoadImage_SDcard(ImageView image, Uri uri,Activity activity,int activityNo) {
		super();
		
		this.image = image;
		imageViewReference = new WeakReference<ImageView>(image);
		this.uri = uri;
		this.activity=activity;
		this.activityNo=activityNo;
	
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

		super.run();
		Log.d("Sajjad","ImageLoading running");
		FileStatus.writelog("ImageLoading running");
		
		try {

			//fileDescriptor of the uri
			ParcelFileDescriptor parcelFD = null;
			parcelFD = activity.getContentResolver().openFileDescriptor(uri, "r");
 			FileDescriptor imageSource = parcelFD.getFileDescriptor();

			// Decode image size
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFileDescriptor(imageSource, null, options);
			scale=getSamplingFactor(options,200,300);

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale ;
 			bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);
 		
 			//set the image on the activity 
			activity.runOnUiThread(new Runnable(){

				public void run() {
					// TODO Auto-generated method stub
					image.setImageBitmap(bitmap);
				}
 
			});



			Log.d("Sajjad","Bitmap loaded from uri");
			//1 means,this objects has been called by Group_imageActivity
			//2 means,this objects has been called by Full image

			if(activityNo==1)
				((GroupImageActivity)activity).fragment.bitmap=bitmap;

			else if (activityNo==2){
				((SendingImage_Activity)activity).bitmap=bitmap;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In ImageLoading,Exception- "+e.getLocalizedMessage());
			FileStatus.writelog("In ImageLoading,Exception- "+e.getLocalizedMessage());
		}

	}
	public int getSamplingFactor(BitmapFactory.Options options, int reqWidth, int reqHeight){

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;

	}


}
