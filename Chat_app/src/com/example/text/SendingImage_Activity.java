package com.example.text;

import java.io.ByteArrayOutputStream;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SendingImage_Activity extends Activity implements OnClickListener{

	String url="",
			friendNumber="",
			friendName="";
	byte[] data;
	EditText editText;
	Bitmap bitmap=null;
	ImageView imageView;
	Uri uri;
	InputStream inputStream;
	long dataSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.image2);
		imageView=(ImageView)this.findViewById(R.id.fullimageView);
		editText=(EditText)this.findViewById(R.id.fulleditText);
		Button button=(Button)this.findViewById(R.id.fullbutton);
		button.setOnClickListener(this);


		//get the details from intent
		Intent intent=this.getIntent();
		this.friendName=intent.getStringExtra("chatperson");
		this.friendNumber=intent.getStringExtra("chatnumber");
		this.url=intent.getStringExtra("url");
		this.getActionBar().setSubtitle(friendName);

		//decode the image
		this.decodeUriToBitmap(Uri.parse(url)); 

	}

	@Override
	public void onClick(View v) {

		//decode the file and get the image
		Log.d("Sajjad", "Full image,getting the data");

		try {
			 
			this.dataSize=getFileSize(Uri.parse(url));

			if(bitmap!=null&&dataSize>0){  

				//get the size of the file
				String fileName=editText.getText().toString();

				SendingImageDetails imageDetails=new SendingImageDetails(friendNumber, dataSize, fileName, url,0);
				Service_Socket.service.pendingImageList.add(imageDetails);

				if(Service_Socket.service.sendingImageFiles==false){


					new Thread (new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Service_Socket.service.imageUpload.sendPendingImages();

						}
					}).start();
				}


				this.finish();
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}

	public void decodeUriToBitmap(Uri aUri) {

		this.uri=aUri;

		//load the image from sd card based on the uri
		LoadImage_SDcard loading=new LoadImage_SDcard(imageView,uri,this,2);
		loading.start();

	}

	public long getFileSize(Uri auri){

		Cursor cursor=getContentResolver().query(auri, null, null, null, null); 
		int sizeIndex=cursor.getColumnIndex(OpenableColumns.SIZE);
		int fileNameIndex=cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME); 

		cursor.moveToFirst();
		Toast.makeText(this,"FileSize"+cursor.getLong(sizeIndex),1000).show();

		return  cursor.getLong(sizeIndex);
		
	}

	public byte[] getBytes(InputStream inputStream) throws IOException {

		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		byte[] data=null;
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {

			Log.d("Sajjad","Length of data is "+len);
			byteBuffer.write(buffer, 0, len);
		}
		data=byteBuffer.toByteArray();
		byteBuffer.close();
		inputStream.close();

		return  data;
	}
}
