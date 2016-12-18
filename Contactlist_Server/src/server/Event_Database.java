package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import java.util.ArrayList;

 

import com.mysql.jdbc.Statement;

 

public class Event_Database {

	Connection connection;
	
	public void  initializeDb(){
 
		try {

			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Event database initialize");

			System.out.println("Driver loaded");

			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/funchat","sajjad","sajjad");
 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<Event_Item> fetchEventList(String Catagory,String district){
		int count=0;
		System.out.println("Event item fetching");

		ArrayList<Event_Item> array=new ArrayList<Event_Item>();

		String query="SELECT * FROM `event` WHERE EventType='"+Catagory+"' and Location='"+district+"'";
        Statement statement;
		try {
			
			statement = (Statement) connection.createStatement();
			ResultSet result=statement.executeQuery(query);
			 
			while(result.next()){
		 
			  Event_Item item=new Event_Item(result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(7));
		 	  System.out.println(""+(++count));
			 
			  array.add(item);    
			
			}
			
		 	//connection.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	return  array;
	}
}
