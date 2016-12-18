package server;

import java.io.File;

public class Main {


	static String file1="D:/Friends_image/";
	static String file2="D:/Store_image/";

	public static void main(String[] args) {

		Server_7000 server1=new Server_7000();
		server1.start();

		Server_7001 server2=new Server_7001();
		server2.start();

		Server_7002 server3=new Server_7002();
		server3.start();

		Server_6000 server4=new Server_6000();
		server4.start();

		Server_6001 server5=new Server_6001();
		server5.start();

		Server_9001 server6=new Server_9001();
		server6.start();

		Server_3000_friend server7=new Server_3000_friend();
		server7.start();

		Server_3001_profilePic server8=new Server_3001_profilePic();
		server8.start();
		//Database database=new Database();
		//database.initialize();
		//database.create();

		//remember to create a separate directory for the group image sharing and the event image folder 
		File Friendsfile=new File(Main.file1);
		if(!Friendsfile.isDirectory()){
			Friendsfile.mkdir();
		}

		File Storefile=new File(Main.file2);
		if(!Storefile.isDirectory()){
			Storefile.mkdir();
		}
	}
}
