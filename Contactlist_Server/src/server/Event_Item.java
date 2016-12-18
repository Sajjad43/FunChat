package server;

public class Event_Item {
	
	String title,address,date,time,url;

	public Event_Item(String title, String address, String date, String time,String url) {
		super();
		this.title = title;
		this.address = address;
		this.date = date;
		this.time = time;
		this.url=url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
