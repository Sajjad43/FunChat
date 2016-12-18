package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Socket_7002 extends Thread{

	Socket socket;
	String phone="";
	DataInputStream inputStream;
	DataOutputStream outputStream;
	String catagory;
	String district;
	ArrayList<Event_Item> eventList; 

	public Socket_7002(Socket socket) {
		super();
		this.socket = socket;
		System.out.println("Socket 7002 made.....");
	}

	@Override
	public void run() {
	 
		super.run();

		try {

			inputStream=new DataInputStream(socket.getInputStream());
			outputStream=new DataOutputStream(socket.getOutputStream());

			phone=inputStream.readUTF();

			Socket_7002 y=Server_7002.mapServer7002.get(phone);
			if(y!=null){
				Server_7002.mapServer7002.remove(phone);
				y=null;
			}

			Server_7002.mapServer7002.put(phone, this);

			Event_Database database=new Event_Database();
			database.initializeDb();

			while(true){

				catagory=(String)inputStream.readUTF();
				district=(String)inputStream.readUTF();
				System.out.println("In Socket 7002,Catagory- "+catagory);

				eventList=new ArrayList<Event_Item>();
				eventList=database.fetchEventList(catagory,district);

				outputStream.writeInt(eventList.size());

				for(int i=0;i<eventList.size();i++){

					outputStream.writeUTF(eventList.get(i).getTitle());
					outputStream.writeUTF(eventList.get(i).getAddress());
					outputStream.writeUTF(eventList.get(i).getDate());
					outputStream.writeUTF(eventList.get(i).getTime());
					outputStream.writeUTF(eventList.get(i).getUrl());
				}

				System.out.println("In socket 7002,Objectlist sent");
			}

		} catch (IOException e) {
			System.out.println("In Socket 7002,Exception- "+e.getMessage());
			try {
				this.socket.close();
			} catch (IOException e1) {

				System.out.println("In Socket 7002,Exception- "+e.getMessage());
				Server_7002.mapServer7002.remove(phone);
			}
			System.out.println("Socket close");
		}
	}
}
