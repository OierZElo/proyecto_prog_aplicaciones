package model;

public class User {
	private String name;
	private String password;
	private String mail; 

	public User(String mail, String password) {
		super();
		this.mail = mail;
		this.password = password;
		this.name = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	

}
