package com.example.text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Detect_connection9000 extends Thread{

	Socket socket; 
	DataOutputStream outputStream;
	DataInputStream inputStream;
	String userNumber="";


	@Override
	public void run() {
		// TODO Auto-generated method stub

		super.run();
		Log.d("Sajjad", "Detect_connection 9000 started ");
		
		FileStatus.writelog("Detect_connection 9000 started");

		try{
			
			//read the file to get username and phone number
			String userNameAndphone=readProfileFile();
		 	String[] namePhone=new String[3];
			namePhone=userNameAndphone.split(" ");
			this.userNumber=namePhone[1];




			//connect to server
			socket =new Socket(IP.Ip,9000);
			this.outputStream=new DataOutputStream(socket.getOutputStream());
			this.inputStream=new DataInputStream(socket.getInputStream());

			//send the usernumber
			outputStream.writeUTF(userNumber);

			//to-do list -----
			//send the flag number to the server 

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

			Log.d("Sajjad","Detect_connection,Connection break");
			FileStatus.writelog("Detect_connection,Connection break");


		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Sajjad", "Detect_connection,Exception- "+e.getLocalizedMessage());
			FileStatus.writelog("Detect_connection,Exception- "+e.getLocalizedMessage());
		}
 
	}

	public String readProfileFile(){

		FileStatus file=new FileStatus(Service_Socket.service);
		String temp=file.readfromfile("profile");

		if(temp!=null){
			return  temp;

		}

		return null; 

	}

}
