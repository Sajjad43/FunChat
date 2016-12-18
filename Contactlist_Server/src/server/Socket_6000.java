package server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import server.ImageFile;

public class Socket_6000 extends Thread{

	Socket socket;
	DataInputStream inputStream;
	String userNumber="";
	String userName="";
	boolean isFileWrite;
	String friendNumber="";
	long dataSize=0;
	String fileName="";
	byte[] data;

	public Socket_6000(Socket socket) {
		super();
		this.socket = socket;
		System.out.println("Socket 6000 created...");
		isFileWrite=false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		try {


			inputStream=new DataInputStream(socket.getInputStream());
			userName=inputStream.readUTF();
			userNumber=inputStream.readUTF();
			this.makeDir(userNumber);

			Socket_6000 y=Server_6000.mapServer6000.get(userNumber);
			if(y!=null){
				Server_6000.mapServer6000.remove(userNumber);
				y=null;
			}

			Server_6000.mapServer6000.put(userNumber, this);
			System.out.println("In Socket6000, username and number-"+userNumber+" and "+userName);

			//file2
			File file=new File(Main.file2+userNumber);
			if(!file.isDirectory()){
				file.mkdir();
			}

			while(true){


				//int type=inputStream.readInt();
				//System.out.println("type-"+type);


				friendNumber=inputStream.readUTF();
				dataSize=inputStream.readLong();
				fileName=inputStream.readUTF();
				data=new byte[1024];
				readDataAndSave();

				//SaveImage saveImage=new SaveImage(userNumber, userName, this);
				//saveImage.start();

				/**

				userNumber=inputStream.readUTF();
				String filename=inputStream.readUTF();
				int arraySize=inputStream.readInt();

				System.out.println("In socket6000,Arraysize in Socket6001 -"+arraySize);

				for(int i=0;i<arraySize;i++){

					String number=inputStream.readUTF();
					this.selectedNumber.add(number); 
					makeDir(number);

				}

				int datasize=this.inputStream.readInt();
				data=new byte[datasize];
				System.out.println("In socket6000,The size of the data "+datasize);

				inputStream.readFully(data,0,datasize);

				for(int i=0;i<arraySize;i++){

					ImageFile imageFile=new ImageFile();
					imageFile.saveImagefile(filename, data, selectedNumber.get(i), userNumber);
				}**/
			}
		} catch (IOException e) {

			try {
				socket.close();
			} catch (IOException e1) {

				System.out.println("In socket 6000,"+e1.getLocalizedMessage());

				Server_6000.mapServer6000.remove(userName);
			}
			System.out.println("In socket 6000,"+e.getLocalizedMessage());
		}

	}
	public void makeDir(String number){

		File file=new File("D:/Store_image/"+number);
		if(!file.isDirectory()){
			file.mkdir();
		}
	}

	public void readDataAndSave(){

		System.out.println("Data Size- "+dataSize);
		File file=new File(Main.file2+friendNumber+"/"+userNumber+" "+fileName+".jpg");

		Server_6001.mapServer6001.get(friendNumber).isReadFile=false;
		Server_6001.mapServer6001.get(friendNumber).incrementCount();
		isFileWrite=true;

		try {

			FileOutputStream fOutputStream=new FileOutputStream(file);
			int count=0;
			long length=0;

			while((count=inputStream.read(data))>0){


				fOutputStream.write(data, 0, count);
				length+=count;
				if(length==dataSize){
					break;
				}
			}
			System.out.println("Data saved- "+length);
			fOutputStream.close();

			Server_6001.mapServer6001.get(friendNumber).decrementCount();

			if(Server_6001.mapServer6001.get(friendNumber).getCount()==0){

				Server_6001.mapServer6001.get(friendNumber).isReadFile=true; 

			}

			isFileWrite=false;

			System.out.println("Count is - "+Server_6001.mapServer6001.get(friendNumber).getCount());

		} catch (Exception e) {
			// TODO Auto-generated catch block

				e.printStackTrace();
			if( isFileWrite==true){

				System.out.println(fileName+" is deleted");
				String filePath=Main.file2+friendNumber+"/"+userName+" "+fileName+".jpg";
				File fileD =new File(filePath);
				
				if(fileD.exists()){

					boolean status=fileD.delete();

					System.out.println(fileName+"- "+status);	
				}

				if(Server_6001.mapServer6001.get( friendNumber).getCount()>0){
					Server_6001.mapServer6001.get( friendNumber).decrementCount();

				}
				if(Server_6001.mapServer6001.get(friendNumber).getCount()==0){
					Server_6001.mapServer6001.get(friendNumber).isReadFile=true;

				}
			}  
		}
	}

}
