package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Database {

	Statement statement;

	public void initializeDatabase(){

		//initialize the database
		System.out.println("Databas initialize");

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/funchat","sajjad","sajjad" );
			statement=connection.createStatement();

		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	public void createTable(){

		//create the table centraldatabase and event 
		try {

			statement.executeUpdate("create table CentralDatabase (Name char(20),phoneNumber char(20),eclipse_connectNo char(20),"
					+ "netbean_connectNo char(20));");
 
			statement.executeUpdate("create table  event (EventType char(20),Title char(20),Address char(20),"
					+ "Date char(20),Time char(20),Location char(20),Url char(20));");
 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertData(String username,String phonenumber)
	{
		//add data to the central database
		System.out.println("Registration of "+username+" done");

		try {
 			statement.executeUpdate("INSERT INTO `CentralDatabase`(`Name`, `phoneNumber`) VALUES ('"+username+"','"+phonenumber+"')");
 		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<String,String> fetchData(){

		//fetch the username and phonenumber from the centraldatabase
		System.out.println("Query request made");
 		Map<String, String> map=new HashMap<String,String>();

		try {

			ResultSet resultset=statement.executeQuery("SELECT * FROM `CentralDatabase`");

			while(resultset.next())
			{
 				map.put(resultset.getString(2),resultset.getString(1));
 			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return map;
 	}
 
	public int getConnectionNo(String usernumber){
 
		//get the number of connection of the user 
		int connectionNumber=0;

		try {
			String sql="SELECT * FROM `centraldatabase` WHERE phoneNumber='"+usernumber+"'";
			ResultSet result=statement.executeQuery(sql);

			if(result.next()){
 				connectionNumber=result.getInt(3);
 			}
			else{
				connectionNumber=0;
			}

			result.close();

		} catch (Exception ex) {
			System.out.println("Error in Database class"+ex.getMessage());
		}
		return connectionNumber;
	}
 
	public void updateConnectionNo(int no,String usernumber){

		//update the connection number
		String sql="UPDATE `centraldatabase` SET  `eclipse_connectNo`="+no+" WHERE phoneNumber='"+usernumber+"'";

		try {
 			statement.executeUpdate(sql);
 		} catch (SQLException ex) {
			System.out.println("Error in Database class"+ex.getMessage());
 		}
	}

	public int isUserPresent(String number){

		String sql="Select count(*) from centraldatabase where phoneNumber='"+number+"'";
		int isPresent=0;
		try {

			ResultSet result=statement.executeQuery(sql);

			if(result.next()){

				isPresent=result.getInt(1);
			}

			result.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in database,ispresent-"+e.getLocalizedMessage());
		}

		return isPresent;
	}

}
