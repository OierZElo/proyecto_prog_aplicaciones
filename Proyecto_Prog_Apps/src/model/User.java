package model;

public class User {
	private static int counter = 0;
	private int id;
	private String username;
	private String password;
	
	//constructor
	public User(String username, String password) {
		this.id = counter;
		counter += 1;
		this.username = username;
		this.password = password;
	}

	//getters and setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
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
