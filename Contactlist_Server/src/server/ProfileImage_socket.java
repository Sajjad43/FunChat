package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ProfileImage_socket extends Thread{

	Socket socket;

	public ProfileImage_socket(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		DataInputStream inputStream;

		System.out.println("Profile image socket made");

		try {

			inputStream=new DataInputStream(socket.getInputStream());

			String name=inputStream.readUTF();
			System.out.println("In profileimage,username is"+name);

			ProfileImage_socket y=Server_3001_profilePic.map_server3001.get(name);
			if(y!=null){
				Server_3001_profilePic.map_server3001.remove(name);
				y=null;
			}

			Server_3001_profilePic.map_server3001.put(name, this);

			int dataSize=inputStream.readInt();
			byte[] data=new byte[dataSize];
			inputStream.readFully(data, 0, dataSize);

			ImageFile file=new ImageFile();
			file.writeFriendImage(name, data);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
