package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Detect_Connection extends Thread{

	Socket socket ;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	String userName="";

	public Detect_Connection(Socket socket) {
		super();
		this.socket = socket;
		System.out.println("Detectconnect on eclipse running");

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		try {


			this.inputStream=new DataInputStream(socket.getInputStream());
			this.outputStream=new  DataOutputStream(socket.getOutputStream());

			//get the username of the connected user 
			userName=inputStream.readUTF();
			System.out.println("In DetectConnect,username is - "+userName);

			while(true){

				//receive the pin
				int i=inputStream.read();

				if(i==1){
					//send the feedback
					outputStream.write(1);
				}

				else if(i==-1){ 

					System.out.println("Detect Connection,i is -1");
					handleConnectionwithDatabase(userName);
					break;
				}

			}

		} catch (IOException ex) {
			System.out.println("In  DetectConnect,Exception- "+ex.getMessage());
			handleConnectionwithDatabase(userName);
		}
	}

	public void handleConnectionwithDatabase(String username){

		Database  db=new Database();
		db.initializeDatabase();
		int no=db.getConnectionNo(username);

		if(no==0){
			Socket_3000 k=Server_3000_friend.mapServer3000.get(username);


			if(k!=null){
				Server_3000_friend.mapServer3000.remove(username);
				k=null;

				System.out.println("Map of socket 3000- "+Server_3000_friend.mapServer3000);

			}
		}
		else if(no==1){

			db.updateConnectionNo(0, username); 
			removeSocketFromMap( );
			System.out.println("In DetectConnect,Connected clients "+Server_7001.mapServer7001.keySet().toString());

		}
		else if(no==2){

			db.updateConnectionNo(1,username);
			System.out.println("In DetectConnect,Connected clients "+Server_7001.mapServer7001.keySet().toString());

		}


	}

	public void removeSocketFromMap(){


		Socket_6000 x=Server_6000.mapServer6000.get(userName);

		if(x!=null){
 
				String number=x.friendNumber;
				System.out.println("Friend Name "+number);
 
				if(x.isFileWrite==true){

			 		System.out.println(x.fileName+" is deleted");
					String filePath=Main.file2+number+"/"+userName+" "+x.fileName+".jpg";
					File file=new File(filePath);
					if(file.exists()){

						boolean status=file.delete();

						System.out.println(x.fileName+"- "+status);	
					}

					if(Server_6001.mapServer6001.get(number).getCount()>0){
						Server_6001.mapServer6001.get(number).decrementCount();

					}
					if(Server_6001.mapServer6001.get(number).getCount()==0){
						Server_6001.mapServer6001.get(number).isReadFile=true;

					}


				}

			}

			Server_6000.mapServer6000.remove(userName);
			x=null;
		
		Socket_6001 y=Server_6001.mapServer6001.get(userName);
		if(y!=null){
			Server_6001.mapServer6001.remove(userName);
			y=null;
		}
		Socket_7002 z=Server_7002.mapServer7002.get(userName);
		if(z!=null){
			Server_7002.mapServer7002.remove(userName);
			z=null;

		}
		Socket_7001 w=Server_7001.mapServer7001.get(userName);
		if(w!=null){
			Server_7001.mapServer7001.remove(userName);
			w=null;

		}

		Socket_3000 a=Server_3000_friend.mapServer3000.get(userName);
		if(a!=null){
			Server_3000_friend.mapServer3000.remove(userName);
			a=null;

		}
		ProfileImage_socket b=Server_3001_profilePic.map_server3001.get(userName);
		if(b!=null){

			Server_3001_profilePic.map_server3001.remove(userName);
			b=null;

		}



	}


}
