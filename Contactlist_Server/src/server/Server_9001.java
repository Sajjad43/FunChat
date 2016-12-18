package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_9001 extends Thread{

	@Override
	public void run() {
		super.run();

		System.out.println("Server 9001 is running");

		try{

			ServerSocket server=new ServerSocket(9001);
			while(true){
				Socket socket=server.accept();
				Detect_Connection detectConnect=new Detect_Connection(socket);
				detectConnect.start();
			}
		}catch (IOException ex) {
 			System.out.println("In server 9001,Exception- "+ex.getMessage());
		}
	}
}




