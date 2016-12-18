package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server_7002 extends Thread{
 	
	static Map<String,Socket_7002> mapServer7002;
	
	public  Server_7002(){
		
		System.out.println("Server 7002 running");
		 mapServer7002=new HashMap<String,Socket_7002>();
	}

	@Override
	public void run(){
		// TODO Auto-generated method stub
		 super.run();
		
		try {
			ServerSocket server=new ServerSocket(7002);
			
			while(true)
			{
				Socket socket=server.accept();
				Socket_7002 socket7002=new Socket_7002(socket);
				socket7002.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("In Server 7002,Exception- "+e.getMessage());
 		}
 	}
}
