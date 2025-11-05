package model;

public class User {
	private static int counter = 0;
	private int cod;
	private String mail;
	private String password;

	//constructor
	public User(String mail, String password) {
		this.cod = counter;
		counter += 1;
		this.mail = mail;
		this.password = password;
	}

	//getters and setters
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCod() {
		return cod;
	}
	
	

};





