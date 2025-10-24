package model;

import java.util.ArrayList;

public class User {
	private static int counter = 0;
	private int id;
	private String username = "";
	private String password;
	private ArrayList<String> usernames;
	
	//constructor
	public User(String username, String password) {
		this.id = counter;
		counter += 1;
		this.setUsername(username);
		this.password = password;
	}

	//getters and setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (usernames.contains(username)) {
			this.username = "User-"+counter;
		}
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}
	



	
	
	
	
	
}
