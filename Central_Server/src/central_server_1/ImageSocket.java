/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package central_server_1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class ImageSocket extends Thread{
 	Socket socket;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	byte[] data;
        String usernumber="";
	String sendernumber="";
	String imagename="";
          

	public ImageSocket(Socket socket){
		this.socket=socket;
 	}

	@Override
	public void run(){

		System.out.println("ImageSocket thread running");

		try {
			outputStream=new DataOutputStream(socket.getOutputStream());
			inputStream=new DataInputStream(socket.getInputStream());

			//get the usernumber 
                        this.usernumber=this.inputStream.readUTF();
			
                      
                        //remove the  imagesocket thread in the map if  it is present prior
                        ImageSocket y=Main.imageServer.map.get(usernumber);
                            if(y!=null){
                                Main.imageServer.map.remove(usernumber);
                                y=null;
               
                             }
                        
         		
                        //add to imageserver map
                        Main.imageServer.map.put(usernumber,this);
                        
			
                        
                        System.out.println("In ImageSocket,Image map size is "+Main.imageServer.map.size());
           
                        System.out.println("In ImageSocket,Usernumber of the image socket- "+usernumber);

			while(true){

			     //get the sendernumber and the imagename
                                
                                this.sendernumber=this.inputStream.readUTF();
                                this.imagename=this.inputStream.readUTF();
                                long dataSize=inputStream.readLong();
                                System.out.println("Image Size is "+dataSize);
                                 
                                Main.imageServer.map.get(this.sendernumber).outputStream.writeUTF(this.usernumber);
                                Main.imageServer.map.get(this.sendernumber).outputStream.writeUTF(this.imagename);
                        	Main.imageServer.map.get(this.sendernumber).outputStream.writeLong(dataSize);
                            
                                System.out.println(sendernumber+"&"+imagename+"&"+dataSize);	
                                 
                                   long size=0;
                                   int count=0;                  
                                   
                                  byte[] imageData=new byte[1024];
                                  while((count=inputStream.read(imageData))>0){
                                                                          
                                      Main.imageServer.map.get(this.sendernumber).outputStream.write(imageData,0,count);
				                           //       System.out.println("Count is"+count);
                                      size+=count;  
                                      
                                     // System.out.println("Size is "+size);
                                      if(size==dataSize){
                                           System.out.println("Image Data sent-"+size);
                                 
                                          break;
                                      }
                                      
                                  }
                                    
                                /*
                               //get the size of the image file
				int data_length=inputStream.readInt();

				 if(data_length>0)
				  {
					System.out.println(data_length +"byte read  in image socket");

					data=new byte[data_length];


					inputStream.readFully(data,0,data.length);

					System.out.println("Image Data read ");
                                        //sent the image        
                                       
                                         
                                        Main.imageServer.map.get(this.sendernumber).outputStream.writeUTF(this.usernumber);
                                        Main.imageServer.map.get(this.sendernumber).outputStream.writeUTF(this.imagename);

					Main.imageServer.map.get(this.sendernumber).outputStream.writeInt(data_length);
						
					Main.imageServer.map.get(this.sendernumber).outputStream.write(data);
					System.out.println("Image Data sent ");
 
					 


                                    }*/
                                
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			 
                    //incomplete,do the handleconnection part from detectconnect class
                        Main.imageServer.map.remove(this.usernumber);
                         System.out.println(this.usernumber+" socket removed");
                         System.out.println("Map size in image server is "+Main.imageServer.map.size());

		}

	}
   
}
