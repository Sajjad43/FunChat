package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Socket_7000  extends Thread{

	Socket socket;

	public Socket_7000(Socket socket) {
		super();
		this.socket = socket;
		System.out.println("Socket 7000 made......");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		try {

			DataInputStream inputStream=new DataInputStream(socket.getInputStream());
			DataOutputStream outputStream=new DataOutputStream(socket.getOutputStream());

			String userName=inputStream.readUTF();
			String phoneNumber=inputStream.readUTF();

			System.out.println("In Socket7000,"+userName+" "+phoneNumber);

			Database database=new Database();
			database.initializeDatabase();
			database.insertData(userName, phoneNumber);

			outputStream.writeUTF("ok");
			System.out.println("ok message sent to client ");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("In Socket 7000,Exception- "+e.getMessage());
		}
	}
}
