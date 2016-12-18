package server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageFile {

	public boolean isFile(String number){

		//initialize the file for the friends image
		String filePath=Main.file1+number+".jpg";
		File file=new File(filePath);
		return file.exists();
	}

	public void listFile(){

		File file=new File(Main.file1);
		File[] list=new File[file.listFiles().length];
		list=file.listFiles();

		for(int j=0;j<list.length;j++){
			System.out.println("FileName- "+list[j].getName());
		}

	}


	public void saveImagefile(String filename,byte[] data,String sendername,String username){

		//initialize the filepath for saving image on the storeimage folder
		String filePath=Main.file2+sendername+"/"+username+" "+filename+".jpg";
		File file=new File(filePath);
		System.out.println("Saving image on the Store folder....");

		FileOutputStream fileStream;
		try {

			fileStream = new FileOutputStream(file);
			fileStream.write(data,0,data.length);
			fileStream.close();

			System.out.println("Image file Saved");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("In imageFile,"+e.getLocalizedMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("In imageFile,"+e.getLocalizedMessage());
		}


	}

	public byte[] readImageFile(String filename,String username,String sendername) throws IOException{

		//filepath
		String filePath=Main.file2+username+"/"+filename;
		File file=new File(filePath);

		System.out.println("File reading...");
		ByteArrayOutputStream byteOutputStream=new ByteArrayOutputStream();
		FileInputStream inputStream=null;

		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// this is storage overwritten on each iteration with bytes
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		// we need to know how may bytes were read to write them to the byteBuffer
		int length = 0;
		try {

			while ((length = inputStream.read(buffer)) != -1) {

				byteOutputStream.write(buffer, 0,length);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}
		inputStream.close();
		System.out.println("file reading done");

		//then we can return your byte array.
		return byteOutputStream.toByteArray();
	}

	public void fileDelete(String username,String filename){

		File file=new File(filename);
		System.out.println("filepath- "+filename);
		System.out.println("File exist- "+file.exists());

		if(file.exists()){

			boolean status=file.delete();

			System.out.println("Delete Status - "+status);	
			System.out.println("File exist- "+file.exists());

		}

	}//-----
	public void writeFriendImage(String filename,byte[]data){

		String temp=Main.file1+filename+".jpg";
		//initialize the file
		File file=new File(temp);
		System.out.println("FileImage writing....");

		FileOutputStream fileStream;
		try {

			fileStream = new FileOutputStream(file);
			fileStream.write(data,0,data.length);
			fileStream.close();
			System.out.println("FiendsImage writing done");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public  byte[] readFriendImage(String number) throws IOException{
 
		String filePath1=Main.file1+number+".jpg";
		String filePath2=Main.file1+number+".JPG";

		File file=new File(filePath1);
		File file2=new File(filePath2);
 
		ByteArrayOutputStream byteBuffer=new ByteArrayOutputStream();
 		 FileInputStream inputStream=null;

		if(file.exists()||file2.exists()){
 
			try {

				inputStream = new FileInputStream(file);

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// this is storage overwritten on each iteration with bytes
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
 			// we need to know how may bytes were read to write them to the byteBuffer
			int len = 0;

			try {
				while ((len = inputStream.read(buffer)) != -1) {
 					byteBuffer.write(buffer, 0, len);
 				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
			inputStream.close();
			//System.out.println("FriendsImage reading done");
 			//	System.out.println(byteBuffer.toByteArray().length);
			//then we can return your byte array.
			return byteBuffer.toByteArray();
		}
 		return null;	
	}
 
}
