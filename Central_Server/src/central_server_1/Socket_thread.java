/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package central_server_1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Socket_thread implements Runnable{
     
        ArrayList<String> chatgroup;
        Socket socket;
	int port;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	String message="",username="",chatPerson="";
	Client client;
        byte[] data=new byte[32];
        ArrayList<Socket> listSocket;


	public Socket_thread(Socket socket,int port, ArrayList<Socket> listSocket)
	{
		this.socket=socket;
		this.port=port;
                this.listSocket=new ArrayList<Socket>();
                this.listSocket=listSocket;
	}

	@Override
	public void run(){
		// TODO Auto-generated method stub

		System.out.println("Socket of Port number "+port+" running");
		System.out.println("In SocketThread,Ip connected"+socket.getInetAddress().toString());

         	try {

			//inputstream of username's socket   
			inputStream=new DataInputStream(socket.getInputStream());
                        outputStream=new DataOutputStream(socket.getOutputStream());
			 
                       //receiving the details from the client 
			username=inputStream.readUTF();
                         
			System.out.println("In SocketThread,The Username is "+username);
        		
                        handleConnectionwithDatabase(username);

                        //the  object added to map
			Main.map.put(username,this);
                      
                        //socket is added to the arraylist of the server with specified port
                         this.listSocket.add(socket);
                        
                       //update the gui
                      //  Main.gui.populate();
                        
                       	System.out.println("In SocketThread,Array size of port "+port+"is "+listSocket.size());
                        System.out.println("In SocketThread,Connected client no. is"+Main.map.size());
		
                              
                                 
                       
                       
			while(true)
			{ 
				//read the text from the username's client
				 
                                        
                             
                                     chatPerson= inputStream.readUTF();
                                     message=inputStream.readUTF();
                                  
                                      System.out.println("chatperson number-"+chatPerson);
                
                                     System.out.println("Message- "+message);
                
				     Main.map.get(chatPerson).outputStream.writeUTF(this.username);
                               
                                    Main.map.get(chatPerson).outputStream.writeUTF(message);
                                  
  			}
 
		} catch (Exception e){
			// TODO Auto-generated catch block
 			System.out.println("In SocketThread,Exception in Socket thread ");
			try {
 				//close the socket which becomes inactive as the client has close the application
				 socket.close();
 			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
			//remove the connected client from the map
			
			Main.map.remove(this.username);
                        
                       // Main.gui.populate();
            try {
                this.inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Socket_thread.class.getName()).log(Level.SEVERE, null, ex);
            }
			
                        System.out.println("In SocketThread,The number of final client is"+Main.map.size());
			
                        removefromServerArray(socket);
                          //update the number of connected client in a server port by removing the socket fromthe arraylist 	
			  // removeUsernamefromChatgroup();
  			
		}
 
	}

    public String getChatPerson() {
        return chatPerson;
    }
/*
    public ArrayList<String> getChatgroup() {
        return chatgroup;
    }*/
    public void handleConnectionwithDatabase(String username){
    
        Database_Checkconnect check=new Database_Checkconnect(username);
        check.connectDatabase();
        int no=check.getConnectionNo();
        
        if(no==0){
            //do the part
            
            check.updateconnectionNo(1);
            
            
        }
        else if(no==1){
            //do the part
            Socket_thread x=Main.map.get(username);
            if(x!=null)
            {
                Main.map.remove(username);
                this.removefromServerArray(x.socket);
                x=null;
         
            }
            System.out.println("In SocketThread,The number of final client is"+Main.map.size());
			  
              
            
            check.updateconnectionNo(2);
            
            
       }
        
        
    }

        public String getUsername() {
        return username;
    }
        
      /*  
        public void groupText(String message) throws IOException
        {
            
            for(int i=0;i<chatgroup.size();i++)
            {
                System.out.println(chatgroup.get(i));
                outputStream= new DataOutputStream(Main.map.get(chatgroup.get(i)).socket.getOutputStream());
                outputStream.writeUTF(this.username+" sent- "+message);
                
                //outputStream.write(data,0,data.length);
                System.out.println("Group message sent to "+chatgroup.get(i)+" by "+this.username);
             }
             
        }*/
         
	public void removefromServerArray(Socket socket)
	{
		if(port==8001)
		{
 			Main.thread_8001.listSocket.remove(socket);
 			System.out.println("SocketThread,ArraySize of port 8001 is "+Main.thread_8001.listSocket.size());
		}
		else if(port==8002)
		{
		        Main.thread_8002.listSocket.remove(socket);
 		 	System.out.println("SocketThread,ArraySize of port 8002 is "+Main.thread_8002.listSocket.size());

		}

		else if(port==8003)
		{
		       Main.thread_8003.listSocket.remove(socket);

		       System.out.println("SocketThread,ArraySize of port 8003 is "+Main.thread_8003.listSocket.size());
 		}
 
	}
        
     /*   
       public void removeUsernamefromChatgroup()
       {
           
           
           for(int i=0;i<this.chatgroup.size();i++)
           {
               
               Main.map.get(chatgroup.get(i)).chatgroup.remove(username);
               
               
               
               
           }
           
           
       }*/

}
