package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_7000 extends Thread{

	public Server_7000()
	{
		System.out.println("Server 7000 made");
	}

	@Override
	public void run(){

		ServerSocket server;
		try {

			server = new ServerSocket(7000);

			while(true)
			{
				Socket socket =server.accept();
				Socket_7000 socket7000=new Socket_7000(socket);
				socket7000.start();
			}
		} catch (IOException e) {
			System.out.println("In Server 7000,Exception- "+e.getMessage());
		}
	}
}
