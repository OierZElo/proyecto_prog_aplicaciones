package model;

import javax.swing.ImageIcon;

public class User {
	private String name;
	private String password;
	private String mail; 
	private ImageIcon photo; 

	public User(String mail, String password) {
		super();
		this.mail = mail;
		this.password = password;
		this.name = null;
		this.photo = new ImageIcon("src\\resources\\icons\\SongIcon.png"); 
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
