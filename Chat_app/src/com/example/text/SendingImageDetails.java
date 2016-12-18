package com.example.text;

public class SendingImageDetails {

	String friendName="";
	long size;
	String fileName="";
	String url="";
	int statusFileWriting;

	public SendingImageDetails(String friendName, long size, String fileName,String url,int statusFileWriting) {
		super();
		this.friendName = friendName;
		this.size = size;
		this.fileName = fileName;
		this.url=url;
		this.statusFileWriting=statusFileWriting;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
			
	
}
