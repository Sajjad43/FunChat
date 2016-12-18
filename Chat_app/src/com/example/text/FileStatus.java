package com.example.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileStatus {

	Context context;

	public FileStatus(Context context) {
		super();
		this.context = context;
	}



	public void createFile(String filename){

		//intialize the  file

		File file_activity=new File(context.getCacheDir(),filename);

		try {

			file_activity.createNewFile();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In FileStatus createFileActivity(),Exception- "+e.getLocalizedMessage());
			FileStatus.writelog("In FileStatus createFileActivity(),Exception- "+e.getLocalizedMessage());
		}
	}
	public String readfromfile(String filename)
	{
		String text="";

		byte[] data;

		try {
			//intialize the file
			File file=new File(context.getFilesDir(),filename);

			//check the existence of the file
			if(file.isFile())
			{
				data=new byte[(int) file.length()];

				FileInputStream inputStream=context.openFileInput(filename);

				inputStream.read(data);

				text=new String(data);

				inputStream.close();


			}



		} catch ( Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In FileStatus readfromfile(),Exception- "+e.getLocalizedMessage());
			FileStatus.writelog("In FileStatus readfromfile(),Exception- "+e.getLocalizedMessage());
		} 

		return text;  
	}
	public boolean isFile(String filename){

		//check the presence of the file

		File file=new File(context.getCacheDir(),filename);

		return file.isFile();
	}

	public void writeToFile(String filename,String text){

		try {

			//write the data on the file 
			FileOutputStream outputStream=context.openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(text.getBytes());
			outputStream.close();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad","In filestatus writetofile(),Exception- "+e.getLocalizedMessage());

			FileStatus.writelog("In filestatus writetofile(),Exception- "+e.getLocalizedMessage());

		} 
	}

	/*public void readActivityStatus(String filename){

		File file=new File(context.getCacheDir(),"Activity");
		//file.c

	}*/

	public void writeFriendList(String Filename,ArrayList<String> friendList){

		FileOutputStream outputStream;
		ObjectOutputStream object;
		try {
			outputStream = context.openFileOutput(Filename,context.MODE_PRIVATE);
			object=new ObjectOutputStream(outputStream);

			object.writeObject(friendList);

			object.close();
			outputStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public ArrayList<String> fetchFriendlist(String name){

		FileInputStream inputStream;
		ObjectInputStream object;
		ArrayList<String> list=new ArrayList<String>();

		try {

			inputStream=context.openFileInput(name);
			object=new ObjectInputStream(inputStream);

			list=(ArrayList<String>)object.readObject();

			object.close();
			inputStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		return list;
	}

	public static void writelog(String temp){

		File file=new File(Environment.getExternalStorageDirectory()+"/DCIM/error.txt");
		Calendar c=Calendar.getInstance();

		String date="("+c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+")";
		String time ="["+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+"]";
		try {

			if(file.isFile()==false){
				file.createNewFile();

			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(temp+" "+date+" "+time+"\n");
			fw.close();
			bw.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
