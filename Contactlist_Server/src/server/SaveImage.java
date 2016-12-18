package server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public  class SaveImage extends Thread{
/**
	String userNumber="";
	String userName="";
	String friendNumber="";
	String fileName="";
	long dataSize=0;
	byte[] data;
	boolean isFileWrite;
	Socket_6000 socket6000;



	 public SaveImage(String userNumber, String userName,Socket_6000 socket6000) {
		super();
		this.userNumber = userNumber;
		this.userName = userName;
		this.socket6000=socket6000;
		System.out.println("Saving image");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		isFileWrite=false;
		socket6000.images.add(this);
			try {
			
			friendNumber=socket6000.inputStream.readUTF();
			System.out.println(friendNumber);
	  
			dataSize=socket6000.inputStream.readLong();
			fileName=socket6000.inputStream.readUTF();
			data=new byte[1024];
			readDataAndSave(); 
 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void readDataAndSave(){

		System.out.println("Data Size- "+dataSize);
		
		File file=new File(Main.file2+friendNumber+"/"+userNumber+" "+fileName+".jpg");

		Server_6001.mapServer6001.get(friendNumber).isReadFile=false;
		Server_6001.mapServer6001.get(friendNumber).incrementCount();
		isFileWrite=true;

		try {
		
			FileOutputStream fOutputStream=new FileOutputStream(file);
			int count=0;
			long length=0;

			while((count=socket6000.inputStream.read(data))>0){


				fOutputStream.write(data, 0, count);
				length+=count;
				if(length==dataSize){
					break;
				}
			}

			System.out.println("Data saved- "+length);
			fOutputStream.close();
			
			if(Server_6001.mapServer6001.get(friendNumber).getCount()>0){
				Server_6001.mapServer6001.get(friendNumber).decrementCount();

			}
			if(Server_6001.mapServer6001.get(friendNumber).getCount()==0){
				Server_6001.mapServer6001.get(friendNumber).isReadFile=true;

			}

			isFileWrite=false;
			socket6000.images.remove(this);



		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
**/
}