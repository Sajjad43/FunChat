/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package central_server_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 *//*
public class ServerGroupChat  implements Runnable{

     ServerSocket server;
    Socket socket;
    
    public ServerGroupChat()
    {
        
    }
    
    
    @Override
    public void run() {
       
        System.out.println("Server Group chat thread created");
        
        try {
             
            
            
            server=new ServerSocket(7000);
        
              while(true)
              {
                    socket=server.accept();
                    Socket_for_groupChat group=new Socket_for_groupChat(socket);
                            group.start();
   
                    
                 }
    

        
        
        
        } catch (IOException ex) {
       
            System.out.println("IO exception in serverGroupchatq");
        }
             
    
                  
    } 
    
}*/
