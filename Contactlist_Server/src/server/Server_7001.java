package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server_7001 extends Thread{

	static Map<String,Socket_7001> mapServer7001;

	public Server_7001()
	{
		System.out.println("Server 7001 made....");
		mapServer7001=new HashMap<String,Socket_7001>();
	}

	@Override
	public void run(){

		ServerSocket server;
		try {
			server = new ServerSocket(7001);

			while(true)
			{
				Socket socket =server.accept();
				Socket_7001 socket7001=new Socket_7001(socket);
				socket7001.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("In Server 7001,Exception- "+e.getMessage());
		}
	}
}
