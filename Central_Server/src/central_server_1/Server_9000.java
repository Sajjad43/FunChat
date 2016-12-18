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
 */
public class Server_9000 extends Thread{
    
    //for detect connection
    public void run(){
        try {
            
            System.out.println("Server 9000 is running");
            
            ServerSocket server=new ServerSocket(9000);
            
            while(true){
                
                Socket socket=server.accept();
                Detect_Connect connect=new Detect_Connect(socket);
                connect.start();
                 
            }
        } catch (IOException ex) {
        
        System.out.println("In server 9000,Exception- "+ex.getMessage());
         
        }
        
        
    }
    
    
}
