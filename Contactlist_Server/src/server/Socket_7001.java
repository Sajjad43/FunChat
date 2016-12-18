package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class Socket_7001 extends Thread{

	String phoneNumber;   
	Socket socket;

	public Socket_7001(Socket socket) {
		super();
		this.socket = socket;
		System.out.println("Socket 7001 created");
	}

	@Override
	public void run() {

		super.run();

		try {

			DataInputStream inputStream=new DataInputStream(socket.getInputStream());
			DataOutputStream outputStream=new DataOutputStream(socket.getOutputStream());

			phoneNumber=inputStream.readUTF();
 			System.out.println("In socket7001,phone number is "+phoneNumber);

 			handleConnection();
 			Server_7001.mapServer7001.put(phoneNumber, this);

			System.out.println("The connected client are-");
			System.out.println(""+Server_7001.mapServer7001.keySet().toString());

			while(true)
			{
				String userName=inputStream.readUTF();
				String phoneNumber=inputStream.readUTF();
 				System.out.println("In socket 7001- "+userName+" "+phoneNumber);

				//get all register username from the database 
				Database database=new Database();
				database.initializeDatabase();
				Map<String,String>map=database.fetchData();

				if(map.containsKey(phoneNumber)&&Server_7001.mapServer7001.containsKey(phoneNumber)){
					outputStream.writeUTF("yes");
					System.out.println("query successful");
				}
				else{
					outputStream.writeUTF("no");
					System.out.println("query unsuccessful");
				}
			}
		} catch (IOException e) {
			System.out.println("In Socket 7001,Exception- "+e.getMessage());
			Socket_7001 x=Server_7001.mapServer7001.get(phoneNumber);  
			Server_7001.mapServer7001.remove(phoneNumber);
			x=null;
		}
	}

	public void handleConnection(){

		int no=0;
		Database db=new Database();
		db.initializeDatabase();
		no=db.getConnectionNo(phoneNumber);

		if(no==0){
			db.updateConnectionNo(1, phoneNumber);
		}
		else{
 			Socket_7001 y=Server_7001.mapServer7001.get(phoneNumber);
			if(y!=null){
				Server_7001.mapServer7001.remove(phoneNumber);
				y=null;
			}
			System.out.println("Map of server7001 size is "+Server_7001.mapServer7001.size());
			db.updateConnectionNo(2, phoneNumber);
 		}
	}
}
