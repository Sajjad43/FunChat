package com.example.text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.util.Log;

public class Detect_connection9001 extends Thread{


	Socket socket ; 
	DataOutputStream outputStream;
	DataInputStream inputStream;
	String userNumber="";
	Context context;

	public Detect_connection9001(Context context)
	{
		this.context=context;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

		super.run();
		Log.d("Sajjad", "Detect_connection 9001 started");
		FileStatus.writelog("Detect_connection 9001 started");
	
		try {


			//read the file to get username and phone number
			String userNameAndphone=readProfileFile();
			String[] namePhone=new String[3];
			namePhone=userNameAndphone.split(" ");
			this.userNumber=namePhone[1];


			//connect to server
			socket =new Socket(IP.Ip,9001);
			this.outputStream=new DataOutputStream(socket.getOutputStream());
			this.inputStream=new DataInputStream(socket.getInputStream());

			//send the usernumber
			outputStream.writeUTF(userNumber);


			while(true){

				//pin the server
				outputStream.write(1);
				//get response
				int i=inputStream.read();

				if(i==1){

				}
				else{

					break;
				}

			}

			Log.d("Sajjad","Detect_connection2,Connection break");
			FileStatus.writelog("Detect_connection2,Connection break");


		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad", "Detect_connection2,Exception- "+e.getLocalizedMessage());
			FileStatus.writelog( "Detect_connection2,Exception- "+e.getLocalizedMessage());

		}






	}

	public String readProfileFile(){

		FileStatus file=new FileStatus(context);
		return file.readfromfile("profile");


	}



}
