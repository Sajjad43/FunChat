package server;



import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;



public class Server_6000 extends Thread{

	Socket socket;
	static Map<String,Socket_6000> mapServer6000;

	public Server_6000(){
		mapServer6000=new HashMap<String,Socket_6000>();
	}

	@Override
	public void run() {
		super.run();

		System.out.println("Server 6000 running");
		try {

			File file=new File("D:/MP3_android");
			if(!file.isDirectory()){
				System.out.println("Directory -"+file.mkdir());
			}

			ServerSocket server=new ServerSocket(6000);

			while(true){

				socket=server.accept();
				Socket_6000 socket6000=new Socket_6000(socket);
				socket6000.start();
			}
		} catch (IOException e) {
		 	System.out.println("In Server 6000,Exception- "+e.getMessage());
		}
	}
}
