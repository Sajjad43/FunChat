package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server_3001_profilePic extends Thread{

	static Map<String,ProfileImage_socket> map_server3001=new HashMap<String,ProfileImage_socket>();

	@Override
	public void run() {
		super.run();
		System.out.println("Server 3001 is running");

		try {

			ServerSocket server=new ServerSocket(3001);
			while(true){
				Socket socket=server.accept();
				ProfileImage_socket socketProfileImage=new ProfileImage_socket(socket);
				socketProfileImage.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
