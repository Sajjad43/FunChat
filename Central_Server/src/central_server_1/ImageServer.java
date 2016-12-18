
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package central_server_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author user
 */
public class ImageServer extends Thread{
     
	ServerSocket server;
	Map<String,ImageSocket>map=new HashMap<String,ImageSocket>();
 	
      @Override
	 public void run(){
		// TODO Auto-generated method stub
         
	 	try {
			System.out.println("Image Server running");
			server = new ServerSocket(65000);
			 
			while(true)
			{
				Socket socket= server.accept();
                                ImageSocket imageSocket=new ImageSocket(socket);
                                imageSocket.start();

			}

               } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
