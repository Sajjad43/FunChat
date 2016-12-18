/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package central_server_1;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author user
 */
public class Main {
     
    
	static ServerConnect thread_8001;
	static ServerConnect thread_8002;
	static ServerConnect thread_8003;
	static Socket_for_groupChat thread_8006;
        static ImageServer imageServer;
	
        
        Server_9000 server_9000;
       
        static Map<String,Socket_thread> map;
        //public static GUI gui;
        
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	       // gui=new GUI();
                //create a hashmap for collecting all the connected socket along with the username
                map=new HashMap<String,Socket_thread>();
                
		
            
                
                
                
                
                //this thread is used for notifying the client which port to chose
		Thread thread_8000=new Thread(new Server_8000());
		thread_8000.start();
                
                      
               //thread for image transfer server                  
                imageServer=new ImageServer();
                imageServer.start();
                
               
                //server_9000 for connection check
                 Server_9000 server_9000=new Server_9000();
                server_9000.start();
                

		Thread[] thread=new Thread[4]; 

		//three other thread created with different port number
		thread_8001=new ServerConnect(8001);
		thread[0]=new Thread(thread_8001);
		thread[0].start();

		thread_8002=new ServerConnect(8002);
		thread[1]=new Thread(thread_8002);
		thread[1].start();

		thread_8003=new ServerConnect(8003);
		thread[2]=new Thread(thread_8003);
		thread[2].start();


	}

}
