/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package central_server_1;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Socket_for_groupChat extends Thread{

  /*   Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    ArrayList<String>chatGroup2;
    ArrayList<String> chatGroup;
    String username=" ",chatperson=" ";
    
    
    public Socket_for_groupChat(Socket socket)
    {
        this.socket=socket;
    }
    
    
    @Override
    public void run() {
        
         System.out.println("Group Chat Socket thread");
                       
        try {

                inputStream=new ObjectInputStream(socket.getInputStream());
                outputStream=new ObjectOutputStream(socket.getOutputStream());            
             
             
                System.out.println("Got the call from the client to send arraylist");
             
                
          
                
                try {
                     
                    
                  //get the username and chatperson name of the connected client who wants to get the arraylist
                    username=(String)inputStream.readObject();
                 
                    
                    
                    // chatperson=(String)inputStream.readObject();
                    
                    System.out.println(username +"want to create the group chat");
            
                    
                    
                      //array of connected clients
                     chatGroup = new ArrayList<String>(Main.map.keySet());
                             
                    for(int i=0;i<chatGroup.size();i++)
                     {
                         System.out.println(" "+chatGroup.get(i));
                     }
                       
                         
                           
                            
                          
                        //remove the username and chatperson of the connected client  from the arraylist
                       
                         chatGroup.remove(username); 
                   //      chatGroup.remove(chatperson); 
                      
                         
                         //sent the arraylist for the group chat
                         outputStream.writeObject(chatGroup);  
                         //sent the usergroupchat 
                         outputStream.writeObject(Main.map.get(username).getChatgroup());
                        
                         System.out.println("Group arraylist sent from server");
                         
                         //get the arralist of the groupchat from the connected client,"Username"
                         ArrayList<String>chatGroup2=new ArrayList<String>();
                          
                         chatGroup2 =(ArrayList<String>) inputStream.readObject();
              
             
                                
                         System.out.println("Group arraylist receive from client and the arrat size is "+chatGroup2.size());
                         
                        for(int i=0;i<chatGroup2.size();i++)
                        {
                            
                            System.out.println(""+chatGroup2.get(i));
                        }
                         
                        this.checkgroup(); 
                        
                          remove(chatGroup2,username); 
                        this.checkgroup();  
                          populate2(chatGroup2,username);
                        this.checkgroup();
                          populate(chatGroup2);
                        this.checkgroup();  
                           
                       for(int i=0;i<Main.map.get(username).chatgroup.size();i++)           
                       {
                           System.out.println(""+Main.map.get(username).chatgroup.get(i));
                       }
                      
                   //}   
                  } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Socket_for_groupChat.class.getName()).log(Level.SEVERE, null, ex);
                }
            //}
            
            
            
            
        } catch (IOException ex) {
            
            System.out.println("IO exception in group chat");
             System.out.println(ex.getMessage());
        
  
        }
          
    
    
    
    }
    
   public void populate2(ArrayList<String> chatgroup,String username)
   {
       System.out.println("populate2");

       boolean check=true;
       
       for(int i=0;i<chatgroup.size();i++)
       {
           ArrayList<String> eachMemberinChatgroup=Main.map.get(chatgroup.get(i)).getChatgroup(); 
           
           check=true;
          
           for(int j=0;j<eachMemberinChatgroup.size();j++)
           {
               if(eachMemberinChatgroup.get(j).equals(username))
               {
                   check=false;
                   break;
               }
           }
           
           if(check)
           {
               eachMemberinChatgroup.add(username);
           }
           
       }
       
       
       
   }
    
   
   public void checkgroup()
   {
       
       for(int i=0;i<Main.map.get(username).chatgroup.size();i++)
       {
           System.out.println(" "+Main.map.get(username).chatgroup.get(i));
           
       }
       
   }
   
   
    public void remove(ArrayList<String> chatgroup,String username)
    {
        
        System.out.println("Remove from the arraylist");
        boolean flag;
        
        for(int i=0;i<Main.map.get(username).chatgroup.size();i++)
        {
             flag=false;
            for(int j=0;j<chatgroup.size();j++)
            {
                if(Main.map.get(username).chatgroup.get(i).equals(chatgroup.get(j)))
                {
                    flag=true;
                    break;
                }
                if(flag==false)
                {
                   // Main.map.get(username).chatgroup.remove(Main.map.get(username).chatgroup.get(i));
                    Main.map.get(Main.map.get(username).chatgroup.get(i)).chatgroup.remove(username); 
                
                }
                
            }
        }
            
        
        
    }
    
    
    
   public void populate(ArrayList<String> chatgroup)
   {
              System.out.println("populate");

       
       ArrayList<String> User_chatgroup_array=new ArrayList<String>();

        Main.map.get(username).chatgroup=chatgroup;
       System.out.println(" "+Main.map.get(username).chatgroup.size());
               
       
   } */
    
}
