package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class Socket_3000 extends Thread{

	Socket socket;
	DataOutputStream outputStream;
	DataInputStream inputStream;
	File [] file_array;
	ImageFile imageFile;
	byte[] data;
	ArrayList<String> friendList;								/** socket 3000 is used for fetch friend image from the server**/
	String userNumber="";
	int count=0;

	public Socket_3000(Socket socket) {
		super();
		this.socket = socket;
		friendList=new ArrayList<String>();
	}

	@Override
	public void run() {

		super.run();
		System.out.println("Socket3000 thread  Running");
		imageFile=new ImageFile();

		try {
			outputStream=new DataOutputStream(socket.getOutputStream());
			inputStream=new DataInputStream(socket.getInputStream());

			this.userNumber=inputStream.readUTF();

			Socket_3000 y=Server_3000_friend.mapServer3000.get(userNumber);
			if(y!=null){
				Server_3000_friend.mapServer3000.remove(userNumber);
				y=null;
			}

			Server_3000_friend.mapServer3000.put(userNumber, this);
			System.out.println("Socket 3000,The number is "+userNumber);

			while(true){

				int friendListSize=inputStream.readInt();
				System.out.println("No of Friends  is "+friendListSize);

				for(int i=0;i<friendListSize;i++){

					String number=inputStream.readUTF();

					if(imageFile.isFile(number)==true){

						if(!friendList.contains(number)){
							friendList.add(number);
						}
					}
				}

				//image2.ListFile();

				outputStream.writeInt(friendList.size());

				for(int j=0;j<friendList.size();j++){

					String number=friendList.get(j);
					data=imageFile.readFriendImage(number);

					if(data!=null){
						outputStream.writeUTF(number);
						outputStream.writeInt(data.length);
						outputStream.write(data, 0, data.length);
					}

					System.out.println(number+" sent to Client");
				}
				System.out.println("Socket 3000,All files are sent");
			}
		}catch (IOException e1) {
 			System.out.println("In socket 3000,"+e1.getMessage());
			Socket_3000 socket3000=Server_3000_friend.mapServer3000.get(userNumber);  
			Server_3000_friend.mapServer3000.remove(userNumber);
			socket3000=null;
		}
	}
}
