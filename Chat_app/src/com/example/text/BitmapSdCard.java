package com.example.text;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

public class BitmapSdCard {

	/* 
	public void writeImage(Bitmap bitmap,String imagename){

		try {
			//save the image to the SD card using the image name
			File file=new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera/");

			if(file!=null){

 				System.out.print(file.toString());

				//set the path
				String pathname=file.toString()+"/"+imagename+".jpg";

				//write image data to the file
				FileOutputStream outputStream=new FileOutputStream(pathname);

				BufferedOutputStream buffer=new BufferedOutputStream(outputStream);

				bitmap.compress(CompressFormat.JPEG, 80, buffer);

				//close the buffer
				buffer.flush();
				buffer.close();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In Bitmapsccard writeImage(),Exception- "+e.getLocalizedMessage());

			FileStatus.writelog("In Bitmapsccard writeImage(),Exception-"+e.getLocalizedMessage());

		}

	}
	public Bitmap readImage(String filename,int width,int height){

		Bitmap bitmap=null;
		try{
			//read image from the file using the image name
			File file=new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera/");

			//set the path
			String pathname=file.toString()+"/"+filename+".jpg";

			//get the image
			bitmap=decodeBitmap(pathname,width,height);

			return bitmap;
		}catch(Exception e){
			FileStatus.writelog("In Bitmapsccard writeImage(),Exception-"+e.getLocalizedMessage());

		}

		return bitmap;

	}

	public Bitmap decodeBitmap(String filename,int width,int height){

		try{
			BitmapFactory.Options option=new BitmapFactory.Options();
			//get the information of the image before loading to the memory
			option.inJustDecodeBounds=true;
			BitmapFactory.decodeFile(filename, option);

			if(option.outHeight>50&&option.outWidth>50){

				option.inSampleSize=getSamplingFactor(option,width,height);
				//load the image to the memory
				option.inJustDecodeBounds=false;
				Bitmap bitmap=BitmapFactory.decodeFile(filename, option);

				int  bitmapWidth=bitmap.getWidth();
				int  bitmapHeight=bitmap.getHeight();
				float scaleWidth=(float)bitmapWidth/width;
				float scaleHeight=(float)bitmapHeight/height;

				Matrix matrix=new Matrix();
				matrix.postScale(scaleWidth, scaleHeight);

				return  bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);

			}
			else{
				option.inJustDecodeBounds=false;
				return  BitmapFactory.decodeFile(filename, option);

			}

		}catch(Exception e){

			FileStatus.writelog(e.getLocalizedMessage());
		}

		return null;	
	}*/

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


	//todo list

	//read and write the friendimage to and from the funchat folder 
	//read the profile image from the funchat folder 


	/*
	public void writeFriendImage(Bitmap bitmap,String imagename){

		//save the image to the SD card using the image name
		File file2=new File(Environment.getExternalStorageDirectory()+"/DCIM/FunChat/");
		try{

			if(file2.isDirectory()){
				file2.mkdir();

			}


			Log.d("Sajjad","write image");

			File file=new File(Environment.getExternalStorageDirectory()+"/DCIM/FunChat/");


			System.out.print(file.toString());

			String nameModified="";

			if(imagename.substring(imagename.length()-4,imagename.length()).equals(".JPG")||imagename.substring(imagename.length()-4,imagename.length()).equals(".jpg")){
				nameModified=imagename.substring(0,imagename.length()-4);

			}
			else{
				nameModified=imagename;
			}

			//set the path
			String pathname=file.toString()+"/"+nameModified+".jpg";



			if(file.exists()){


				//write image data to the file
				FileOutputStream outputStream=new FileOutputStream(pathname);

				BufferedOutputStream buffer=new BufferedOutputStream(outputStream);

				bitmap.compress(CompressFormat.JPEG, 50, buffer);

				buffer.flush();
				buffer.close();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In Bitmapsccard writeImage(),Exception- "+e.getLocalizedMessage());
			FileStatus.writelog("In Bitmapsccard writeImage(),Exception-"+e.getLocalizedMessage());

		}
	}


	public Bitmap readFriendImage(String filename){

		//get the image
		Bitmap bitmap=decodeBitmap(filename,43,43);

		return bitmap;
	}

	public Bitmap readImageforProfile(String filename){

		Bitmap bitmap=this.decodeBitmap(filename, 120, 120);

		return bitmap;
	}*/

	public Bitmap  convertBytetoImage(byte[] data){
		//get the details of the bitmap without loading it to the memory
		BitmapFactory.Options option=new BitmapFactory.Options();
		option.inJustDecodeBounds=true;
		BitmapFactory.decodeByteArray(data, 0, data.length, option);

		int sampleSize=1;
		//scale the bitmap
		if(option.outWidth>=1000&&option.outHeight>=1000)
		{
			sampleSize=this.getSamplingFactor(option, 1000,1000);
		}
		else
		{
			sampleSize=this.getSamplingFactor(option,1000,1000);

		}
		//decode byte array to bitmap
		option.inJustDecodeBounds=false;
		option.inSampleSize=sampleSize;
		Bitmap bitmap=BitmapFactory.decodeByteArray(data, 0, data.length, option);

		return  bitmap;
	}

	public void writeImage(Bitmap bitmap,String imagename,int flag){

		//flag=1:imageSocket&imageDownload--path of the file:Environment.getExternalStorageDirectory()+"/DCIM/Camera/
		//flag=2:FetchImageSocket--path of the file:Environment.getExternalStorageDirectory()+"/DCIM/FunChat/
		String filePath="";
		String nameModified="";


		if(imagename.substring(imagename.length()-4,imagename.length()).equals(".JPG")||imagename.substring(imagename.length()-4,imagename.length()).equals(".jpg")){

			nameModified=imagename.substring(0,imagename.length()-4);

		}
		else{
			nameModified=imagename;
		}

		if(flag==1){
			filePath=Environment.getExternalStorageDirectory()+"/DCIM/Camera/";

		}
		else{
			filePath=Environment.getExternalStorageDirectory()+"/DCIM/FunChat/";
		}

		//set the file and file path
		File file=new File(filePath);
		if(file.isDirectory()==false){
			file.mkdirs();
		}
		filePath=filePath+"/"+nameModified+".jpg";

		try {

			FileOutputStream outputStream=new FileOutputStream(filePath);
			BufferedOutputStream buffer=new BufferedOutputStream(outputStream);
			bitmap.compress(CompressFormat.JPEG, 100, buffer);

			buffer.flush();
			buffer.close();
			outputStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In Bitmapsccard writeImage(),Exception- "+e.getLocalizedMessage());
			FileStatus.writelog("In Bitmapsccard writeImage(),Exception-"+e.getLocalizedMessage());

		}  	
	}

	public Bitmap readImage(String fileName,int flag,int width,int height){

		//flag=1:imageDisplayActivity&Messaging,path:Environment.getExternalStorageDirectory()+"/DCIM/Camera/"
		//flag=2:sendingProfilePic&loadingFriendsImage,path:Environment.getExternalStorageDirectory()+"/DCIM/FunChat/"

		String filePath="";

		if(flag==1){
			File file=new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera/");
			filePath=file.toString()+"/"+fileName+".jpg";
		}
		else{
			filePath=fileName;
		}

		//get the details of the file without loading it to the memory
		BitmapFactory.Options option=new BitmapFactory.Options();
		option.inJustDecodeBounds=true;
		BitmapFactory.decodeFile(filePath, option);

		int sampleSize=this.getSamplingFactor(option, width, height);

		option.inJustDecodeBounds=false;
		option.inSampleSize=sampleSize;
		Bitmap bitmap=BitmapFactory.decodeFile(filePath, option);

		return  bitmap;
	}
	
	public void writeFile(String imageName,byte[] data,int flag){
		//flag=1:imageSocket&imageDownload--path of the file:Environment.getExternalStorageDirectory()+"/DCIM/Camera/
		//flag=2:FetchImageSocket--path of the file:Environment.getExternalStorageDirectory()+"/DCIM/FunChat/
		String filePath="";
		String fileName="";


		if(imageName.substring(imageName.length()-4,imageName.length()).equals(".JPG")||imageName.substring(imageName.length()-4,imageName.length()).equals(".jpg")){
			fileName=imageName.substring(0,imageName.length()-4);

		}
		else{
			fileName=imageName;
		}

		if(flag==1){
			filePath=Environment.getExternalStorageDirectory()+"/DCIM/Camera/";

		}
		else{
			filePath=Environment.getExternalStorageDirectory()+"/DCIM/FunChat/";
		}

		File file=new File(filePath);
		if(file.isDirectory()==false){
			file.mkdirs();
		}
 
		filePath=filePath+"/"+fileName+".jpg";

		try {

			FileOutputStream outputStream=new FileOutputStream(filePath);
			outputStream.write(data, 0, data.length);
			outputStream.close();

		}catch(Exception e){
			Log.d("Sajjad", "In bitmapsdcard,Exception-"+e.getLocalizedMessage());
		}

	}

	public byte[] getByteFromFile(String fileName,int  flag){
	
		String filePath="";
		byte[] data = null;

		if(flag==1){

			File file=new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera/");
			filePath=file.toString()+"/"+fileName+".jpg";
		}
		else{
			filePath=fileName;
		}

		try {

			FileInputStream inputStream=new FileInputStream(filePath);
 			int byteRead= inputStream.read(data, 0, data.length);

			Log.d("Sajjad", "In bitmapsdcard,status of data read-"+byteRead);
 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad", "In bitmapsdcard,Exception-"+e.getLocalizedMessage());
		}

		return data;
	}
 
}
