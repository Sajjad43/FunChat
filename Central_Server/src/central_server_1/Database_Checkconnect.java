/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package central_server_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Database_Checkconnect {
    Statement statement;
    String usernumber="";

    public Database_Checkconnect(String username) {
     this.usernumber=username;
    }
    
    
    public void  connectDatabase(){
        
        //connect to the database
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection  connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/funchat","sajjad","sajjad");
            statement=connection.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_Checkconnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database_Checkconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
    }
    public int getConnectionNo(){
       
        //get the connection number for query whether is is connected or not
        
        int i=0;
        try {
            String sql="SELECT * FROM `centraldatabase` WHERE phoneNumber='"+usernumber+"'";
            ResultSet result=statement.executeQuery(sql);
            
            if(result.next()){
                
                i=result.getInt(4);
             }
            else{
                i=0;
            }
             
        } catch (SQLException ex) {
            Logger.getLogger(Database_Checkconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
         return i;
    }
    
    public void updateconnectionNo(int no){
     //update the connection no   
         String sql="UPDATE `centraldatabase` SET  `netbean_connectNo`="+no+" WHERE phoneNumber='"+usernumber+"'";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Database_Checkconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
