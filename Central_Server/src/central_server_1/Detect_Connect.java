 
package central_server_1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Detect_Connect extends Thread{

    
    
    Socket socket ;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    String username="";
    
    public Detect_Connect(Socket socket) {
        
        this.socket = socket;
        
    }
    
    @Override
    public void run() {
        super.run();
        try {
     
            System.out.println("Detectconnect thread  running ");
           
            this.inputStream=new DataInputStream(socket.getInputStream());
            this.outputStream=new  DataOutputStream(socket.getOutputStream());
            
            username=inputStream.readUTF();
        
            System.out.println("In DetectConnect,username is - "+username);
                    
            while(true){
                //receive the pin
                int i=inputStream.read();
                if(i==1){
                    //send the feedback
                    outputStream.write(1);
                }
                
                else if(i==-1){ 
                    //lost the connection
                    break;
                }
                
                
                
            }
             handleConnectionwithDatabase(username);
            
              
        } catch (IOException ex) {
        //if connection disconnected ,then this will work
             System.out.println("In DetectConnect,Exception- "+ex.getMessage());
             handleConnectionwithDatabase(username);
              
        
        }
    
    
    
    }
     public void handleConnectionwithDatabase(String username){
      
         // connect to the database and get the connection number 
        Database_Checkconnect check=new Database_Checkconnect(username);
        check.connectDatabase();
        int no=check.getConnectionNo();
        
        if(no==1){
            check.updateconnectionNo(0);
           //remove the socket  from the map 
            removesocket();
        }
        else if(no==2){
            check.updateconnectionNo(1);
            System.out.println("Connected number of user-"+Main.map.size()+"and image servermap no- "+Main.imageServer.map.size());
             
        }
        
         
     }
    
    public void removesocket(){
        
            
          Socket_thread k= Main.map.get(username);
          Socket x=null;
          if(k!=null){
            
              x=Main.map.get(username).socket;
             
          }
              
            
            if(x!=null){
            
             this.removefromServerArray(x);
            
             
             Main.map.remove(username);
             
             Main.imageServer.map.remove(username);
             
            // Main.gui.populate();
             
             System.out.println("In detectConnect,After disconnection");
             System.out.println("Connected number of user-"+Main.map.size()+"and image servermap no- "+Main.imageServer.map.size());
             
             socket=null;
            
             }
     
    }
    
    public void removefromServerArray(Socket socket)
	{
		if(Main.map.get(username).
                    port==8001)
		{
	 
			Main.thread_8001.listSocket.remove(socket);
			 
			System.out.println("Disconnect,ArraySize of port 8001 is "+Main.thread_8001.listSocket.size());
		}
		else if(Main.map.get(username).port==8002)
		{
		        Main.thread_8002.listSocket.remove(socket);

		 	System.out.println("Disconnect,ArraySize of port 8002 is "+Main.thread_8002.listSocket.size());

		}

		else if(Main.map.get(username).port==8003)
		{
		       Main.thread_8003.listSocket.remove(socket);

		       System.out.println("Disconnect,ArraySize of port 8003 is "+Main.thread_8003.listSocket.size());

		}
 
	}  
}
