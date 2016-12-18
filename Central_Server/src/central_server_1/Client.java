/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package central_server_1;

import java.net.Socket;

/**
 *
 * @author user
 */
public class Client {
    
	private Socket socket;
	private String username;
	private int port;
	public Client(Socket socket, String username, int port) {
		super();
		this.socket = socket;
		this.username = username;
		 
		this.port = port;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	 
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}



}
