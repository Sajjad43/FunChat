package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server_6001 extends Thread
{
	Socket socket;
	static Map<String,Socket_6001> mapServer6001;

	public Server_6001(){
		mapServer6001=new HashMap<String,Socket_6001>();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		System.out.println("Server 6001 running");

		try {

			ServerSocket server=new ServerSocket(6001);

			while(true){

				socket=server.accept();
				Socket_6001 socket6001=new Socket_6001(socket);
				socket6001.start();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("In Server 6001,Exception- "+e.getMessage());
		}
	}
}
