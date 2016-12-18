package com.example.text;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;

public class Sending_profilePic extends Thread{

	String userNumber;

	public Sending_profilePic(String username){
		this.userNumber=username;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		
		Socket socket;
		DataOutputStream outputStream;

		try {
			socket =new Socket(IP.Ip,3001);
			outputStream=new DataOutputStream(socket.getOutputStream());
			//sent the number
			outputStream.writeUTF(userNumber);
			 
			BitmapSdCard sdCard=new BitmapSdCard();
 			String path=Environment.getExternalStorageDirectory()+"/DCIM/FunChat/"+userNumber+".jpg";
			Bitmap bitmap=sdCard.readImage(path, 2, 120, 120);

			ByteArrayOutputStream temp=new ByteArrayOutputStream();
 			bitmap.compress(CompressFormat.JPEG,50, temp);
 			byte [] imageData=new byte[temp.toByteArray().length];
			imageData=temp.toByteArray();

			//send the imagedata and its size
		 	outputStream.writeInt(imageData.length);
			outputStream.write(imageData, 0, imageData.length);
 
			Log.d("Sajjad","Profile image send");
 
		} 
		 catch (Exception e) {
			// TODO Auto-generated catch block
			 Log.d("Sajjad", "Sending_profilepic,Exception-"+e.getLocalizedMessage());
			 FileStatus.writelog("Sending_profilepic,Exception-"+e.getLocalizedMessage());
			
		}
 
	}
 
}
