package model;

import javax.swing.ImageIcon;

public class User {
	private static int counter = 0;
	private int cod;
	private String mail;
	private String password;
	private ImageIcon photo; 
	private String username; 



	//constructor
	public User(String mail, String password, String username) {
		this.cod = counter;
		counter += 1;
		this.mail = mail;
		this.password = password;
		this.photo = new ImageIcon("src\\resources\\icons\\SongIcon.png"); 
		this.username = username;

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

	public ImageIcon getPhoto() {
		return photo;
	}

	public void setPhoto(ImageIcon photo) {
		this.photo = photo;
	}

	public String getName() {
		return username;
	}
	
	public void setName(String name) {
		this.username = name;
	}
	

};





