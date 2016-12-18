package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class Server_3000_friend extends Thread{

	static Map<String,Socket_3000> mapServer3000;

	public Server_3000_friend(){
		System.out.println("Server 3000 is running");
		this.mapServer3000=new HashMap<String,Socket_3000>();
	}

	@Override
	public void run() {
		super.run();

		try {

			ServerSocket server=new ServerSocket(3000);
			while(true)
			{
				Socket socket=server.accept();
				Socket_3000 socket3000=new Socket_3000(socket);
				socket3000.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
