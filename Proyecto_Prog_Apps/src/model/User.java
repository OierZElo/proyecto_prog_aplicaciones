package model;

import java.io.File;

import javax.swing.ImageIcon;

public class User {
	private static int counter = 0;
	private int id;
	private int cod;
	private String mail;
	private String password;
	private ImageIcon photo; 
	private String photoString; 
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
	
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getPhotoString(User user) {
		File carpeta = new File("user_images");

		File[] resultados = carpeta.listFiles((dir, nombre) -> nombre.startsWith( user.getId() + "_" + user.getMail()));

		if (resultados != null && resultados.length > 0) {
		    System.out.println("Archivo encontrado: " + resultados[0].getName());
			return "user_images/"+resultados[0].getName();
		}
		return "user_images\\No_Icon.jpg";


	}

	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
    

};





