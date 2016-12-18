package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import server.ImageFile;

public class Socket_6001 extends Thread{

	Socket socket;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	String userNumber="";
	String userName="";
	File[] filelist;
	boolean isReadFile;
	int count=0;

	public Socket_6001(Socket socket) {
		super();
		this.socket = socket;
		System.out.println("Socket 6001 created.....");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		isReadFile=true;

		try {

			this.inputStream=new DataInputStream(socket.getInputStream());
			this.outputStream=new DataOutputStream(socket.getOutputStream());

			userName=this.inputStream.readUTF();
			userNumber=this.inputStream.readUTF();

			this.makeDir(userNumber);

			Socket_6001 y=Server_6001.mapServer6001.get(userNumber);
			if(y!=null){
				Server_6001.mapServer6001.remove(userNumber);
				y=null;
			}

			Server_6001.mapServer6001.put(userNumber, this);
			System.out.println("In Socket6001,The username and the number- "+userName+" and "+userNumber);

			while(true){

				String number=inputStream.readUTF();
				//System.out.println("The number is "+number);
				
				if(userNumber.equals(number)){

 

					if(isReadFile==true||count==0){


						File file1=new File(Main.file2+userNumber+"/");

						if(file1.listFiles().length==0){

							outputStream.writeInt(0);
							//System.out.println("No file is there in the "+userNumber+" folder");
						}
						else{


							filelist=file1.listFiles();
							System.out.println("The filelength status is"+filelist.length+"and the count is "+count);


							System.out.println("In Socket 6001,The file size is- "+filelist.length);
							this.outputStream.writeInt(filelist.length);
 
							//System.out.println("File number send");
							
							for(int i=0;i<filelist.length;i++){

								String senderName=filelist[i].getName().split(" ")[0];
								String filename=filelist[i].getName().split(" ")[1];

								this.outputStream.writeUTF(senderName);
								this.outputStream.writeUTF(filename.substring(0,filename.length()-4));
								outputStream.writeLong(filelist[i].length());

								sendFileToClient(filelist[i]);

								//	System.out.println("The filelength status is"+filelist.length+"and the count is "+count);

								ImageFile imagefile=new ImageFile();
								imagefile.fileDelete(senderName,filelist[i].getPath());



							}
						}
					}
					
					else{
						outputStream.writeInt(0);
						//System.out.println("The filelength status is"+filelist.length+"and the count is "+count);

					}
					//outputStream.writeInt(0);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				socket.close();
				Server_6001.mapServer6001.remove(userName);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("In socket 6001,"+e.getLocalizedMessage());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}


	public void sendFileToClient(File afile) {

		int count=0;
		long length=0;
		byte[] data=new byte[1024];

		try {

			FileInputStream fInputStream=new FileInputStream(afile);

			while((count=fInputStream.read(data))>0){

				outputStream.write(data, 0, count);
				length+=count;
				if(length==afile.length()){
					break;
				}

			}
			System.out.println(afile.getName() +" sent to "+userNumber+"- "+length);
			fInputStream.close();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				socket.close();
				Server_6001.mapServer6001.remove(userName);
				System.out.println("In socket 6001,Exception is "+e.getMessage());
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} 
	}

	public void makeDir(String number){

		File file=new File("D:/Store_image/"+number);
		if(!file.isDirectory()){
			file.mkdir();
		}
	}
	public synchronized  void incrementCount(){
		if(count>=0){
			count++;
		}
	}
	public synchronized  void decrementCount(){
		if(count>0)
		{
			count--;
		}
	}
	public synchronized  int getCount() {
		return count;
	}
}

