/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package central_server_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class ServerConnect implements Runnable{
        
        Socket socket;
	int port;
	
        
        ArrayList<Socket> listSocket;

	public ServerConnect(int port) {
		this.port = port;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

		System.out.println("Server "+port+" is running");

		ServerSocket server;
		listSocket=new ArrayList<Socket>();
		
		try {

			server = new ServerSocket(port);


			while(true)
			{
				socket=server.accept();
			 
				//Socket thread is created after each socket is accepted from the server	
				Thread thread=new Thread(new Socket_thread(socket,port,listSocket));
				thread.start();
                                 
                                

			}




		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}


}
