/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package central_server_1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author user
 */
public class Server_8000 implements Runnable{
    
    
	Socket socket;
	ServerSocket server;
	DataOutputStream outputStream;

	@Override
	public void run() {
		// TODO Auto-generated method stub

		System.out.println("Server 8000 running");

		try {

			server=new ServerSocket(8000);


			while(true)
			{

				socket=server.accept();
				outputStream=new DataOutputStream(socket.getOutputStream());
             
			 //this is created to send the port number of the server to connect with .This is done by checking the 
			 //arraylist size of each of the server	
				
				for(int i=0;i<3;i++)
				{
					if(Main.thread_8001.listSocket.size()<5)
					{
						outputStream.writeUTF("8001");
					}
					else if(Main.thread_8002.listSocket.size()<5)
					{
						outputStream.writeUTF("8002");
					}
					else if(Main.thread_8003.listSocket.size()<5)
					{
						outputStream.writeUTF("8003");
					}


				}



			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}



    
    
}
